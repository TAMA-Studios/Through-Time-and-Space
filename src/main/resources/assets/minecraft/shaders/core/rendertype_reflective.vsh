#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <emissive_utils.glsl> // Keep this import

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal; // Model-space normal

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat; // Inverse View Rotation Matrix (for world-space normal)
uniform int FogShape;
uniform vec3 ChunkOffset;

out float vertexDistance;
out float dimension;
out vec4 vertexColor;
out vec4 lightColor;
out vec4 maxLightColor;
out vec2 texCoord0;
out vec3 faceLightingNormal;
out vec3 worldNormal; // NEW: World-space normal to fragment

void main() {
    vec3 pos = Position + ChunkOffset;
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, pos, FogShape);
    dimension = get_dimension(minecraft_sample_lightmap(Sampler2, ivec2(0.0, 0.0)));
    vertexColor = Color;
    lightColor = minecraft_sample_lightmap(Sampler2, UV2);
    maxLightColor = minecraft_sample_lightmap(Sampler2, ivec2(240.0, 240.0));
    texCoord0 = UV0;
    faceLightingNormal = Normal; // This is a model-space normal.

    // Calculate world-space normal for use in reflective effect
    worldNormal = normalize(IViewRotMat * Normal);
}