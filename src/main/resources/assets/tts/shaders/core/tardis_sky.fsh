#version 150

in vec2 texCoord;

out vec4 fragColor;

uniform mat4 InvProjMat;
uniform mat4 InvViewMat;
uniform float uTime;
uniform vec2 uResolution;

//  UTILITIES

float hash13(vec3 p) {
    p = fract(p * vec3(0.1031, 0.1030, 0.0973));
    p += dot(p, p.zyx + 31.32);
    return fract((p.x + p.y) * p.z);
}

vec3 hash33(vec3 p) {
    p = fract(p * vec3(0.1031, 0.1030, 0.0973));
    p += dot(p, p.yxz + 33.33);
    return fract((p.xxy + p.yxx) * p.zyx);
}

//  NOISE

float vnoise(vec3 p) {
    vec3 i = floor(p);
    vec3 f = fract(p);
    vec3 u = f * f * (3.0 - 2.0 * f);
    return mix(
    mix(mix(hash13(i+vec3(0,0,0)), hash13(i+vec3(1,0,0)), u.x),
    mix(hash13(i+vec3(0,1,0)), hash13(i+vec3(1,1,0)), u.x), u.y),
    mix(mix(hash13(i+vec3(0,0,1)), hash13(i+vec3(1,0,1)), u.x),
    mix(hash13(i+vec3(0,1,1)), hash13(i+vec3(1,1,1)), u.x), u.y), u.z);
}

float fbm(vec3 p, int oct) {
    float v = 0.0, a = 0.5, total = 0.0;
    for (int i = 0; i < oct; i++) {
        v += a * vnoise(p);
        total += a;
        p = p * 2.07 + vec3(1.7, 9.2, 4.1);
        a *= 0.5;
    }
    return v / total;
}

//  CAMERA RAY

vec3 getRayDir() {
    vec2 ndc = texCoord * 2.0 - 1.0;
    vec4 viewDir = InvProjMat * vec4(ndc, -1.0, 1.0);
    viewDir = vec4(viewDir.xyz, 0.0);
    return normalize((InvViewMat * viewDir).xyz);
}

//  STARS

vec3 starLayer(vec3 dir, float scale, float threshold, float seed, float brightness) {
    vec3 scaled = dir * scale + seed;
    vec3 cell   = floor(scaled);
    vec3 local  = fract(scaled) - 0.5;
    vec3 jitter = (hash33(cell) - 0.5) * 0.72;
    vec3 offset = local - jitter;

    float h = hash13(cell);
    if (h < threshold) return vec3(0.0);

    float prominence = (h - threshold) / (1.0 - threshold);
    float size = 0.003 + prominence * 0.016;
    float dist = length(offset);
    float star = smoothstep(size, size * 0.15, dist);
    float twinkle = 0.72 + 0.28 * sin(uTime * (1.8 + h * 5.0) + h * 173.0);

    float temp = hash13(cell + 7.3);
    vec3 col;
    if      (temp < 0.12) col = vec3(0.55, 0.72, 1.00);
    else if (temp < 0.28) col = vec3(0.78, 0.88, 1.00);
    else if (temp < 0.58) col = vec3(1.00, 1.00, 0.97);
    else if (temp < 0.76) col = vec3(1.00, 0.94, 0.72);
    else if (temp < 0.90) col = vec3(1.00, 0.72, 0.42);
    else                  col = vec3(1.00, 0.38, 0.22);

    float spike = 0.0;
    if (prominence > 0.65) {
        float sp = (prominence - 0.65) / 0.35;
        float sh = smoothstep(size * 14.0, 0.0, abs(offset.x)) * smoothstep(size * 0.4, 0.0, abs(offset.y));
        float sv = smoothstep(size * 14.0, 0.0, abs(offset.y)) * smoothstep(size * 0.4, 0.0, abs(offset.x));
        spike = (sh + sv) * sp * 0.35;
    }

    float corona = smoothstep(size * 9.0, size, dist) * prominence * 0.10;
    return (star * twinkle + spike + corona) * col * brightness;
}

vec3 allStars(vec3 dir) {
    vec3 c = vec3(0.0);
    c += starLayer(dir, 230.0, 0.942, 0.00,  0.50);
    c += starLayer(dir, 125.0, 0.956, 13.70, 0.72);
    c += starLayer(dir,  62.0, 0.969, 27.30, 1.00);
    c += starLayer(dir,  28.0, 0.981, 41.90, 1.45);
    return c;
}

//  NEBULA
//  Noise sampled at dir * 45 so small nebulae have internal detail

vec3 nebulaCloud(vec3 dir, vec3 center, vec3 hue, float radius,
float density, float seed, float driftSpeed) {

    float alignment   = dot(normalize(dir), normalize(center));
    float angularDist = acos(clamp(alignment, -1.0, 1.0));

    // Tighter cull
    if (angularDist > radius * 1.5) return vec3(0.0);

    vec3 drift = vec3(uTime * driftSpeed * 0.003,
    uTime * driftSpeed * 0.002,
    uTime * driftSpeed * 0.0025);

    // Center bias, gentle gaussian falloff, not a mask
    float centerBias = exp(-angularDist * angularDist / (radius * radius * 0.5));

    // LOW frequency, dir * 6 means large smooth features, not tiny sharp cells
    // This is what makes individual cloud features blurry and soft
    vec3 p = dir * 6.0 + seed * 6.1 + drift;

    // Light warp, just enough to break symmetry, not enough to create creases
    vec3 warp = vec3(fbm(p * 0.8 + 1.7, 3),
    fbm(p * 0.8 + 4.3, 3),
    fbm(p * 0.8 + 7.9, 3)) - 0.5;

    float n = fbm(p + warp * 0.6, 5);

    // Sliding threshold, center is denser, edges require higher noise to show
    float threshold = 0.52 - centerBias * 0.25;
    float cloud = max(n - threshold, 0.0);

    if (cloud < 0.001) return vec3(0.0);

    float alpha = cloud * density * 4.0;

    vec3 coreCol = hue * 1.8;
    vec3 edgeCol = hue * 0.6;
    return mix(edgeCol, coreCol, smoothstep(0.0, 0.12, cloud)) * alpha;
}

vec3 allNebulae(vec3 dir) {
    vec3 col = vec3(0.0);
    col += nebulaCloud(dir, normalize(vec3(-0.55,  0.35,  0.75)), vec3(0.04, 0.50, 0.65), 0.50, 0.70, 1.10, 1.0);
    col += nebulaCloud(dir, normalize(vec3( 0.80, -0.15,  0.45)), vec3(0.80, 0.07, 0.18), 0.38, 0.65, 3.70, 0.8);
    col += nebulaCloud(dir, normalize(vec3( 0.10, -0.75,  0.25)), vec3(0.38, 0.06, 0.72), 0.42, 0.60, 6.20, 1.2);
    col += nebulaCloud(dir, normalize(vec3(-0.35,  0.05, -0.85)), vec3(0.90, 0.48, 0.04), 0.22, 0.85, 9.40, 0.6);
    col += nebulaCloud(dir, normalize(vec3( 0.55,  0.72, -0.35)), vec3(0.18, 0.38, 0.90), 0.35, 0.55, 12.10, 1.5);
    col += nebulaCloud(dir, normalize(vec3(-0.20,  0.30, -0.55)), vec3(0.75, 0.18, 0.45), 0.30, 0.45, 15.80, 0.9);
    return col;
}

//  DISTANT STAR CLUSTERS, faint smudges for depth

vec3 starClusters(vec3 dir) {
    vec3 col = vec3(0.0);

    // Hardcoded cluster centers
    vec3 c0 = normalize(vec3( 0.30,  0.60,  0.50));
    vec3 c1 = normalize(vec3(-0.70, -0.30,  0.40));
    vec3 c2 = normalize(vec3( 0.10,  0.20, -0.90));
    vec3 c3 = normalize(vec3(-0.50,  0.10,  0.60));

    float r0 = 0.18; float r1 = 0.22; float r2 = 0.16; float r3 = 0.20;

    vec3 col0 = vec3(0.07, 0.08, 0.13);
    vec3 col1 = vec3(0.11, 0.08, 0.06);
    vec3 col2 = vec3(0.06, 0.09, 0.12);
    vec3 col3 = vec3(0.10, 0.07, 0.07);

    float d0 = acos(clamp(dot(dir, c0), -1.0, 1.0));
    float d1 = acos(clamp(dot(dir, c1), -1.0, 1.0));
    float d2 = acos(clamp(dot(dir, c2), -1.0, 1.0));
    float d3 = acos(clamp(dot(dir, c3), -1.0, 1.0));

    if (d0 < r0) { float f = smoothstep(r0, 0.0, d0); col += col0 * f * f * fbm(dir * 28.0 + 1.3, 3) * 0.55; }
    if (d1 < r1) { float f = smoothstep(r1, 0.0, d1); col += col1 * f * f * fbm(dir * 28.0 + 5.7, 3) * 0.55; }
    if (d2 < r2) { float f = smoothstep(r2, 0.0, d2); col += col2 * f * f * fbm(dir * 28.0 + 9.1, 3) * 0.55; }
    if (d3 < r3) { float f = smoothstep(r3, 0.0, d3); col += col3 * f * f * fbm(dir * 28.0 + 2.9, 3) * 0.55; }

    return col;
}

//  MAIN

void main() {
    vec3 dir = getRayDir();

    vec3 color = vec3(0.0); // pure black base

    color += starClusters(dir);
    color += allNebulae(dir);
    color += allStars(dir);

    // Tone map
    color *= 1.8;
    color  = color / (color + vec3(1.0));
    color  = pow(max(color, 0.0), vec3(1.0 / 2.0));

    // Saturation
    float lum = dot(color, vec3(0.2126, 0.7152, 0.0722));
    color = mix(vec3(lum), color, 1.25);

    fragColor = vec4(clamp(color, 0.0, 1.0), 1.0);
}
