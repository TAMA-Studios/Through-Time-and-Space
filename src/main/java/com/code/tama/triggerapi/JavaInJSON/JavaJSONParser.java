/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import static com.code.tama.tts.TTSMod.LOGGER;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

public class JavaJSONParser {

	private static final Gson GSON = new Gson();
	public static final JavaJSONRenderer NULL_PART = new JavaJSONRenderer();

	private static void addChildren(JavaJSONModel model, JavaJSONFile.Group parentGroup, JavaJSONRenderer parsedModel,
			int texWidth, int texHeight) {
		if (parentGroup.children != null && !parentGroup.children.isEmpty()) {
			for (JavaJSONFile.Group group : parentGroup.children) {
				List<ModelPart.Cube> cubes = new ArrayList<>();
				CubeListBuilder cubeList = CubeListBuilder.create();
				if (group.cubes != null) {
					for (JavaJSONFile.Cube cube : group.cubes) {
						ModelPart.Cube modelCube = new ModelPart.Cube(cube.uv[0], cube.uv[1], cube.origin[0],
								cube.origin[1], cube.origin[2], cube.size[0], cube.size[1], cube.size[2], cube.inflate,
								cube.inflate, cube.inflate, cube.mirror, texWidth, texHeight,
								Set.of(Direction.values()));
						cubes.add(modelCube);

						cubeList.texOffs(cube.uv[0], cube.uv[1]).addBox(cube.origin[0], cube.origin[1], cube.origin[2],
								cube.size[0], cube.size[1], cube.size[2], new CubeDeformation(cube.inflate));
						if (cube.mirror)
							cubeList.mirror();
					}
				}

				ModelPart modelPart = new ModelPart(cubes, new HashMap<>());
				modelPart.setPos(0, 0, 0);

				JavaJSONRenderer renderer = new JavaJSONRenderer(model, modelPart);
				// Set child group pivot
				renderer.setPosition(group.pivot[0], group.pivot[1], group.pivot[2]);
				// Set rotations in radians
				renderer.setRotation((float) Math.toRadians(-group.getRotation().x),
						(float) Math.toRadians(-group.getRotation().y), (float) Math.toRadians(-group.getRotation().z));

				addChildren(model, group, renderer, texWidth, texHeight);

				parsedModel.addChild(renderer);
				model.partsList.put(group.name, renderer);
			}
		}
	}

	public static JavaJSONParsed.ModelInformation getModelInfo(ResourceLocation location) {
		try {
			Resource resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(location);
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open()))) {
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null)
					builder.append(line);
				String file = builder.toString();

				JavaJSONFile parsedFile = GSON.fromJson(file, JavaJSONFile.class);
				JavaJSONModel model = null;

				if (parsedFile.getParent() != null) {
					model = getModelInfo(parsedFile.getParent()).getModel();
				} else {
					JavaJSONModel generatedModel = new JavaJSONModel(parsedFile.texWidth, parsedFile.texHeight,
							parsedFile.scale, parsedFile.fontData);

					for (JavaJSONFile.Group group : parsedFile.groups) {
						List<ModelPart.Cube> cubes = new ArrayList<>();
						CubeListBuilder cubeList = CubeListBuilder.create();
						if (group.cubes != null) {
							for (JavaJSONFile.Cube cube : group.cubes) {
								ModelPart.Cube modelCube = new ModelPart.Cube(cube.uv[0], cube.uv[1], cube.origin[0],
										cube.origin[1], cube.origin[2], cube.size[0], cube.size[1], cube.size[2],
										cube.inflate, cube.inflate, cube.inflate, cube.mirror, parsedFile.texWidth,
										parsedFile.texHeight, Set.of(Direction.values()));
								cubes.add(modelCube);

								cubeList.texOffs(cube.uv[0], cube.uv[1]).addBox(cube.origin[0], cube.origin[1],
										cube.origin[2], cube.size[0], cube.size[1], cube.size[2],
										new CubeDeformation(cube.inflate));
								if (cube.mirror)
									cubeList.mirror();
							}
						}

						ModelPart modelPart = new ModelPart(cubes, new HashMap<>());
						modelPart.setPos(0, 0, 0); // Pivot handled by renderer

						JavaJSONRenderer renderer = new JavaJSONRenderer(generatedModel, modelPart);
						// Set group pivot
						renderer.setPosition(group.pivot[0], group.pivot[1], group.pivot[2]);
						// Set rotations in radians
						renderer.setRotation((float) Math.toRadians(-group.getRotation().x),
								(float) Math.toRadians(-group.getRotation().y),
								(float) Math.toRadians(-group.getRotation().z));

						if (group.fontData != null) {
							generatedModel.fontData.put(group.name, group.fontData);
						}

						addChildren(generatedModel, group, renderer, parsedFile.texWidth, parsedFile.texHeight);

						generatedModel.renderList.add(renderer);
						generatedModel.partsList.put(group.name, renderer);
						model = generatedModel;
					}
				}

				return new JavaJSONParsed.ModelInformation(model, parsedFile.getTexture(), parsedFile.getLightMap(),
						parsedFile.getAlphaMap());
			}
		} catch (Exception e) {
			LOGGER.error("Failed to load model: {}", location, e);
		}

		return new JavaJSONParsed.ModelInformation();
	}

	public static JavaJSONParsed loadModel(ResourceLocation location) {
		if (!JavaJSONCache.unbakedCache.contains(location))
			JavaJSONCache.unbakedCache.add(location);
		if (JavaJSONCache.bakedCache.containsKey(location))
			return JavaJSONCache.bakedCache.get(location);

		JavaJSONParsed newModel = new JavaJSONParsed(location).load();
		JavaJSONCache.bakedCache.put(location, newModel);
		return newModel;
	}
}
