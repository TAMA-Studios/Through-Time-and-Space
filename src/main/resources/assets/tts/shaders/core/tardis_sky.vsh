#version 150

in vec3 Position;
in vec2 UV0;

out vec2 texCoord;
out vec2 fragPos;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord = UV0;
    fragPos = Position.xy;
}
