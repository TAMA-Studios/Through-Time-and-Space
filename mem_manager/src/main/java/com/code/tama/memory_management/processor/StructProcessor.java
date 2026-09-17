package com.code.tama.memory_management.processor;

import com.code.tama.memory_management.Struct;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@SupportedAnnotationTypes(
        "com.code.tama.memory_management.Struct"
)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class StructProcessor extends AbstractProcessor {

    @Override
    public boolean process(
            java.util.Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Struct.class)) {

            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(
                        javax.tools.Diagnostic.Kind.ERROR,
                        "@Struct can only be used on classes!",
                        element
                );
                continue;
            }

            generateStruct((TypeElement) element);
        }

        return true;
    }

    private void generateStruct(TypeElement type) {
        String className = type.getSimpleName().toString();
        String generatedName = "Native" + className;

        PackageElement packageElement =
                processingEnv.getElementUtils().getPackageOf(type);

        String packageName =
                packageElement.getQualifiedName().toString();

        List<VariableElement> fields = new ArrayList<>();

        for (Element element : type.getEnclosedElements()) {

            if (element.getKind() != ElementKind.FIELD)
                continue;

            VariableElement field = (VariableElement) element;

            if (field.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }

            if (!field.asType().getKind().isPrimitive()) {
                processingEnv.getMessager().printMessage(
                        javax.tools.Diagnostic.Kind.ERROR,
                        "@Struct currently only supports primitive fields. "
                                + "Field '" + field.getSimpleName()
                                + "' is not primitive.",
                        field
                );
                continue;
            }

            if (field.getModifiers().contains(Modifier.FINAL)) {
                processingEnv.getMessager().printMessage(
                        javax.tools.Diagnostic.Kind.ERROR,
                        "@Struct fields cannot be final because generated setters "
                                + "need to modify them.",
                        field
                );
                continue;
            }

            fields.add(field);
        }

        long totalSize = 0;

        List<FieldInfo> fieldInfo = new ArrayList<>();

        for (VariableElement field : fields) {

            TypeKind kind = field.asType().getKind();
            long size = getTypeSize(kind);

            if (size == -1) {
                processingEnv.getMessager().printMessage(
                        javax.tools.Diagnostic.Kind.ERROR,
                        "Unsupported primitive type: " + kind,
                        field
                );
                continue;
            }

            fieldInfo.add(
                    new FieldInfo(
                            field.getSimpleName().toString(),
                            kind,
                            totalSize
                    )
            );

            totalSize += size;
        }

        writeFile(
                packageName,
                generatedName,
                fields,
                fieldInfo,
                totalSize
        );
    }

    private long getTypeSize(TypeKind kind) {
        return switch (kind) {
            case BOOLEAN, BYTE -> Byte.BYTES;
            case CHAR, SHORT -> Short.BYTES;
            case INT, FLOAT -> Integer.BYTES;
            case LONG, DOUBLE -> Long.BYTES;
            default -> -1;
        };
    }

    private void writeFile(
            String packageName,
            String generatedName,
            List<VariableElement> fields,
            List<FieldInfo> info,
            long totalSize
    ) {
        Filer filer = processingEnv.getFiler();

        try {
            var file = filer.createSourceFile(
                    packageName + "." + generatedName
            );

            try (Writer writer = file.openWriter()) {

                writer.write("package " + packageName + ";\n\n");

                writer.write("import org.lwjgl.system.MemoryUtil;\n");
                writer.write("import com.code.tama.triggerapi.memory_management.ImAnArena;\n");
                writer.write("import com.code.tama.triggerapi.memory_management.MemAccessException;\n");
                writer.write("\n");

                writer.write("/**\n");
                writer.write(" * Generated native-memory representation of "
                        + generatedName.replace("Native", "") + ".\n");
                writer.write(" * DO NOT EDIT.\n");
                writer.write(" */\n");

                writer.write("public final class " + generatedName + " {\n\n");

                writer.write("    private " + generatedName + "() {}\n\n");

                writer.write("    public static final long SIZE = "
                        + totalSize + "L;\n\n");

                // Field offsets
                for (FieldInfo field : info) {
                    writer.write(
                            "    public static final long "
                                    + field.name().toUpperCase()
                                    + "_OFFSET = "
                                    + field.offset()
                                    + "L;\n"
                    );
                }

                writer.write("\n");

                // Native allocation
                writer.write("""
                        public static long create() {
                            return MemoryUtil.nmemAlloc(SIZE);
                        }

                        """);

                // Arena allocation
                writer.write("""
                        public static long create(ImAnArena arena)
                                throws MemAccessException {
                            return arena.alloc(SIZE);
                        }

                        """);

                writer.write("""
                        public static long copy(long address, ImAnArena arena)
                                throws MemAccessException {
                            long offset = arena.alloc(SIZE);
                            MemoryUtil.memCopy(address, arena.getAddr(offset), SIZE);
                            return offset;
                        }

                        """);

                writer.write("""
                        public static long copy(long address) {
                            long addr = create();
                            MemoryUtil.memCopy(address, addr, SIZE);
                            return addr;
                        }

                        """);

                writer.write("""
                        public static void copy(long source, long destination) {
                            MemoryUtil.memCopy(source, destination, SIZE);
                        }

                        """);

                // Free native allocation
                writer.write("""
                        public static void free(long address) {
                            MemoryUtil.nmemFree(address);
                        }

                        """);

                // Generate getters/setters
                for (FieldInfo field : info) {
                    generateAccessor(writer, field);
                }

                writer.write("}\n");
            }

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(
                    javax.tools.Diagnostic.Kind.ERROR,
                    "Failed to generate " + generatedName + ": "
                            + e.getMessage()
            );
        }
    }

    private void generateAccessor(
            Writer writer,
            FieldInfo field
    ) throws IOException {

        String name = field.name();
        String methodName =
                Character.toUpperCase(name.charAt(0))
                        + name.substring(1);

        String type = getJavaType(field.kind());
        String getter = getGetter(field.kind());
        String setter = getSetter(field.kind());

        writer.write(
                "    public static " + type + " get" + methodName
                        + "(long address) {\n"
        );

        writer.write(
                "        return MemoryUtil." + getter
                        + "(address + " + field.offset() + "L);\n"
        );

        writer.write("    }\n\n");

        writer.write(
                "    public static void set" + methodName
                        + "(long address, " + type + " value) {\n"
        );

        writer.write(
                "        MemoryUtil." + setter
                        + "(address + " + field.offset() + "L, value);\n"
        );

        writer.write("    }\n\n");
    }

    private String getJavaType(TypeKind kind) {
        return switch (kind) {
            case BOOLEAN -> "boolean";
            case BYTE -> "byte";
            case CHAR -> "char";
            case SHORT -> "short";
            case INT -> "int";
            case LONG -> "long";
            case FLOAT -> "float";
            case DOUBLE -> "double";
            default -> throw new IllegalArgumentException(
                    "Unsupported type: " + kind
            );
        };
    }

    private String getGetter(TypeKind kind) {
        return switch (kind) {
            case BOOLEAN, BYTE -> "memGetByte";
            case CHAR -> "memGetChar";
            case SHORT -> "memGetShort";
            case INT -> "memGetInt";
            case LONG -> "memGetLong";
            case FLOAT -> "memGetFloat";
            case DOUBLE -> "memGetDouble";
            default -> throw new IllegalArgumentException(
                    "Unsupported type: " + kind
            );
        };
    }

    private String getSetter(TypeKind kind) {
        return switch (kind) {
            case BOOLEAN, BYTE -> "memPutByte";
            case CHAR -> "memPutChar";
            case SHORT -> "memPutShort";
            case INT -> "memPutInt";
            case LONG -> "memPutLong";
            case FLOAT -> "memPutFloat";
            case DOUBLE -> "memPutDouble";
            default -> throw new IllegalArgumentException(
                    "Unsupported type: " + kind
            );
        };
    }

    private record FieldInfo(
            String name,
            TypeKind kind,
            long offset
    ) {}
}