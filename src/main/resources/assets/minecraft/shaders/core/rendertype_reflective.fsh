#version 150

#moj_import <fog.glsl>
#moj_import <emissive_utils.glsl> // Keep this import, as we modified it

uniform sampler2D Sampler0;
uniform sampler2D Sampler2; // Lightmap sampler (from VSH)

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec3 Light0_Direction; // Declare this uniform to make it available to emissive_utils

in float vertexDistance;
in float dimension;
in vec4 vertexColor;
in vec4 lightColor;
in vec4 maxLightColor;
in vec2 texCoord0;
in vec3 faceLightingNormal; // World-space normal for face lighting
in vec3 worldNormal;        // NEW: World-space normal (from VSH)

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    float alpha = textureLod(Sampler0, texCoord0, 0.0).a * 255.0;

    // Pass the worldNormal to make_emissive for use in make_fake_reflective
    color = make_emissive(color, lightColor, maxLightColor, vertexDistance, alpha, worldNormal) / face_lighting_check(faceLightingNormal, alpha, dimension);
    color.a = remap_alpha(alpha) / 255.0;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}