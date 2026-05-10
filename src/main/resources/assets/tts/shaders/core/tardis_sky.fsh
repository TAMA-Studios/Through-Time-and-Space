#version 150

in vec2 texCoord;
in vec2 fragPos;

out vec4 fragColor;

uniform float uTime;
uniform vec2 uResolution;

float hash11(float p) {
    p = fract(p * 0.1031);
    p *= p + 33.33;
    p *= p + p;
    return fract(p);
}

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

vec2 hash22(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * vec3(0.1031, 0.1030, 0.0973));
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.xx + p3.yz) * p3.zy);
}

float smoothNoise(vec2 uv) {
    vec2 i = floor(uv);
    vec2 f = fract(uv);
    vec2 u = f * f * (3.0 - 2.0 * f);

    float a = hash12(i + vec2(0.0, 0.0));
    float b = hash12(i + vec2(1.0, 0.0));
    float c = hash12(i + vec2(0.0, 1.0));
    float d = hash12(i + vec2(1.0, 1.0));

    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float fbm(vec2 uv, int octaves) {
    float value = 0.0;
    float amplitude = 0.5;
    float frequency = 1.0;
    for (int i = 0; i < octaves; i++) {
        value += amplitude * smoothNoise(uv * frequency);
        frequency *= 2.0;
        amplitude *= 0.5;
    }
    return value;
}

// Returns brightness of a star field at a given UV and grid scale
float starField(vec2 uv, float scale, float threshold, float seed) {
    vec2 grid = floor(uv * scale + seed);
    vec2 jitter = hash22(grid) - 0.5;
    vec2 localUV = fract(uv * scale + seed) - 0.5 + jitter * 0.6;

    float h = hash12(grid);
    if (h < threshold) return 0.0;

    float size = 0.003 + (h - threshold) / (1.0 - threshold) * 0.012;
    float dist = length(localUV);
    float star = smoothstep(size, size * 0.3, dist);

    // Twinkle
    float twinkle = 0.75 + 0.25 * sin(uTime * (2.0 + h * 5.0) + h * 100.0);
    return star * twinkle;
}

// Star color based on hash — blue dwarfs, white, warm yellow giants
vec3 starColor(vec2 grid) {
    float t = hash12(grid + 77.7);
    if (t < 0.3) return vec3(0.6, 0.8, 1.0);       // blue-white
    else if (t < 0.6) return vec3(1.0, 1.0, 0.95);  // white
    else if (t < 0.85) return vec3(1.0, 0.9, 0.7);  // warm yellow
    else return vec3(1.0, 0.5, 0.3);                 // orange giant
}

vec3 starsLayer(vec2 uv, float scale, float threshold, float seed) {
    vec2 grid = floor(uv * scale + seed);
    vec2 jitter = hash22(grid) - 0.5;
    vec2 localUV = fract(uv * scale + seed) - 0.5 + jitter * 0.6;

    float h = hash12(grid);
    if (h < threshold) return vec3(0.0);

    float size = 0.003 + (h - threshold) / (1.0 - threshold) * 0.012;
    float dist = length(localUV);
    float star = smoothstep(size, size * 0.3, dist);

    float twinkle = 0.75 + 0.25 * sin(uTime * (2.0 + h * 5.0) + h * 100.0);
    vec3 color = starColor(grid);

    // Soft glow halo around bright stars
    float glow = smoothstep(size * 8.0, size, dist) * 0.15 * (h - threshold) / (1.0 - threshold);
    return (star * twinkle + glow) * color;
}

vec3 nebula(vec2 uv) {
    // Three overlapping nebula clouds with different hues and drift speeds
    vec2 drift1 = vec2(uTime * 0.008, uTime * 0.003);
    vec2 drift2 = vec2(-uTime * 0.005, uTime * 0.006);
    vec2 drift3 = vec2(uTime * 0.003, -uTime * 0.004);

    float n1 = fbm(uv * 2.5 + drift1, 5);
    float n2 = fbm(uv * 3.0 + drift2 + 4.3, 5);
    float n3 = fbm(uv * 1.8 + drift3 + 8.1, 4);

    // Domain-warped extra cloud for depth
    vec2 warp = vec2(fbm(uv * 2.0 + 1.3, 3), fbm(uv * 2.0 + 4.7, 3));
    float n4 = fbm(uv * 2.2 + warp * 0.4 + drift1 * 0.5, 4);

    // Nebula colors, deep gallifreyan amber/teal/crimson palette
    vec3 col1 = vec3(0.05, 0.15, 0.35) * pow(n1, 2.5) * 1.8; // deep teal
    vec3 col2 = vec3(0.35, 0.05, 0.18) * pow(n2, 3.0) * 2.0; // deep crimson
    vec3 col3 = vec3(0.20, 0.08, 0.40) * pow(n3, 2.2) * 1.5; // violet
    vec3 col4 = vec3(0.40, 0.20, 0.02) * pow(n4, 3.5) * 1.2; // amber accent

    return col1 + col2 + col3 + col4;
}

vec3 galacticBand(vec2 uv) {
    // A faint milky way-esque density band
    float band = smoothstep(0.3, 0.0, abs(uv.y - 0.45 + sin(uv.x * 2.0) * 0.08));
    float density = fbm(uv * vec2(6.0, 3.0) + vec2(uTime * 0.002, 0.0), 4);
    return vec3(0.08, 0.10, 0.15) * band * density * 0.6;
}

void main() {
    vec2 uv = texCoord;

    // Deep space base, not pure black, slight blue-black gradient
    vec3 color = mix(vec3(0.0, 0.0, 0.02), vec3(0.0, 0.01, 0.04), uv.y);

    // Galactic dust band
    color += galacticBand(uv);

    // Nebula clouds
    vec3 neb = nebula(uv);
    color += neb;

    // Stars — three layers at different scales/densities
    color += starsLayer(uv, 80.0,  0.94, 0.0);   // dense small stars
    color += starsLayer(uv, 40.0,  0.96, 13.7);  // medium stars
    color += starsLayer(uv, 18.0,  0.97, 27.3);  // sparse large/bright stars

    // Subtle vignette
    vec2 vigUV = uv * 2.0 - 1.0;
    float vignette = 1.0 - dot(vigUV, vigUV) * 0.25;
    color *= vignette;

    // Tone mapping — slight filmic curve so nebulae don't blow out
    color = color / (color + vec3(0.6)) * 1.6;
    color = clamp(color, 0.0, 1.0);

    fragColor = vec4(color, 1.0);
}
