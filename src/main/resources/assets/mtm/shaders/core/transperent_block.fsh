#version 150

uniform sampler2D Sampler0;
uniform float Alpha;  // Custom uniform to control transparency

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 texColor = texture(Sampler0, texCoord);
    fragColor = vec4(texColor.rgb, texColor.a * Alpha);  // Apply dynamic alpha
}
