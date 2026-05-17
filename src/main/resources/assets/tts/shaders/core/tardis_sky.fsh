#version 150

in vec2 texCoord;
out vec4 fragColor;

uniform mat4 InvProjMat;
uniform mat4 InvViewMat;
uniform float uTime;
uniform vec2 uResolution;

//  UTILITIES

float hash11(float p) {
    p = fract(p * 0.1031);
    p *= p + 33.33;
    p *= p + p;
    return fract(p);
}

float hash13(vec3 p) {
    p = fract(p * vec3(0.1031, 0.1030, 0.0973));
    p += dot(p, p.zyx + 31.32);
    return fract((p.x + p.y) * p.z);
}

vec2 hash21(float p) {
    return fract(vec2(sin(p * 127.1), sin(p * 311.7)) * 43758.5453);
}

vec3 hash33(vec3 p) {
    p = fract(p * vec3(0.1031, 0.1030, 0.0973));
    p += dot(p, p.yxz + 33.33);
    return fract((p.xxy + p.yxx) * p.zyx);
}

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

vec3 nebulaCloud(vec3 dir, vec3 center, vec3 hue, float radius,
float density, float seed, float driftSpeed) {
    float alignment   = dot(normalize(dir), normalize(center));
    float angularDist = acos(clamp(alignment, -1.0, 1.0));
    if (angularDist > radius * 1.5) return vec3(0.0);

    vec3 drift = vec3(uTime * driftSpeed * 0.003,
    uTime * driftSpeed * 0.002,
    uTime * driftSpeed * 0.0025);

    float outerFade = smoothstep(radius * 1.5, radius * 1.2, angularDist);
    float centerBias = exp(-angularDist * angularDist / (radius * radius * 1.2)) * 0.15;

    vec3 p = dir * 6.0 + seed * 6.1 + drift;
    vec3 warp = vec3(fbm(p * 0.8 + 1.7, 3),
    fbm(p * 0.8 + 4.3, 3),
    fbm(p * 0.8 + 7.9, 3)) - 0.5;
    float n = fbm(p + warp * 0.6, 5);

    float threshold = 0.48 - centerBias;
    float cloud = max(n - threshold, 0.0);
    if (cloud < 0.001) return vec3(0.0);

    float alpha = cloud * outerFade * density * 4.0;
    vec3 coreCol = hue * 1.8;
    vec3 edgeCol = hue * 0.6;
    return mix(edgeCol, coreCol, smoothstep(0.0, 0.12, cloud)) * alpha;
}

vec3 allNebulae(vec3 dir) {
    vec3 col = vec3(0.0);
    col += nebulaCloud(dir, normalize(vec3(-0.55,  0.35,  0.75)), vec3(0.04, 0.50, 0.65), 0.50, 0.70, 1.10, 1.0);
    col += nebulaCloud(dir, normalize(vec3( 0.80, -0.15,  0.45)), vec3(0.80, 0.07, 0.18), 0.38, 0.65, 3.70, 0.8);
//    col += nebulaCloud(dir, normalize(vec3( 0.10, -0.75,  0.25)), vec3(0.38, 0.06, 0.72), 0.42, 0.60, 6.20, 1.2);
//    col += nebulaCloud(dir, normalize(vec3(-0.35,  0.05, -0.85)), vec3(0.90, 0.48, 0.04), 0.22, 0.85, 9.40, 0.6);
    col += nebulaCloud(dir, normalize(vec3( 0.55,  0.72, -0.35)), vec3(0.18, 0.38, 0.90), 0.35, 0.55, 12.10, 1.5);
    col += nebulaCloud(dir, normalize(vec3(-0.20,  0.30, -0.55)), vec3(0.75, 0.18, 0.45), 0.30, 0.45, 15.80, 0.9);
    return col;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  DISTANT STAR CLUSTERS
// ═══════════════════════════════════════════════════════════════════════════════

vec3 starClusters(vec3 dir) {
    vec3 col = vec3(0.0);

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

// ═══════════════════════════════════════════════════════════════════════════════
//  GALAXY
//  A distant spiral rendered as an elongated elliptical smear with a bright core
//  and faint arm structure. Placed upper-back where no nebulae sit.
// ═══════════════════════════════════════════════════════════════════════════════

vec3 galaxy(vec3 dir) {
    // Galaxy center direction — upper back-right, away from nebulae
    vec3 center = normalize(vec3(0.40, 0.55, -0.73));
    float d = acos(clamp(dot(dir, center), -1.0, 1.0));
    if (d > 0.30) return vec3(0.0);

    // Build a local 2D coordinate frame in the galaxy plane
    // so we can apply elliptical distortion for the tilt
    vec3 up    = normalize(vec3(-0.3, 0.9, 0.1));
    vec3 right = normalize(cross(center, up));
    up = normalize(cross(right, center));

    // Project dir onto local axes
    vec3 local = dir - center * dot(dir, center);
    float lx = dot(local, right);
    float ly = dot(local, up);

    // Tilt: squash one axis to make it look like an inclined disc
    float tiltX = lx * 1.0;
    float tiltY = ly * 3.5; // elongated — more edge-on
    float ellipDist = sqrt(tiltX * tiltX + tiltY * tiltY);

    if (ellipDist > 0.18) return vec3(0.0);

    // Spiral arm angle
    float angle = atan(tiltY, tiltX);
    float r = ellipDist;

    // Logarithmic spiral arms — two arms offset by PI
    float armPitch = 3.5;
    float arm1 = cos(angle - armPitch * log(r + 0.01) * 2.0);
    float arm2 = cos(angle + 3.14159 - armPitch * log(r + 0.01) * 2.0);
    float arms = max(arm1, arm2);
    arms = smoothstep(0.3, 1.0, arms);

    // Core: bright gaussian bulge
    float core = exp(-ellipDist * ellipDist / 0.0004) * 3.0;

    // Disc: faint elliptical haze
    float disc = smoothstep(0.18, 0.0, ellipDist) * 0.4;

    // Combine: warm core, blue-white arms, faint disc haze
    float total = disc + arms * smoothstep(0.18, 0.02, ellipDist) * 0.6 + core;

    vec3 coreCol = vec3(1.00, 0.90, 0.75); // warm yellow core
    vec3 armCol  = vec3(0.70, 0.85, 1.00); // blue-white arms
    vec3 col = mix(armCol, coreCol, smoothstep(0.05, 0.0, ellipDist));

    return col * total * 0.35;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  DARK NEBULA
//  A region that subtracts star/background light, creating an eerie void patch.
//  Returns a scalar 0-1 where 1 = fully dark.
// ═══════════════════════════════════════════════════════════════════════════════

float darkNebula(vec3 dir) {
    // Two dark patches — placed where background stars are dense
    vec3 c0 = normalize(vec3(-0.60, -0.40,  0.55));
    vec3 c1 = normalize(vec3( 0.30,  0.10,  0.80));

    float d0 = acos(clamp(dot(dir, c0), -1.0, 1.0));
    float d1 = acos(clamp(dot(dir, c1), -1.0, 1.0));

    float dark = 0.0;

    if (d0 < 0.28) {
        vec3 p = dir * 7.0 + 22.3;
        vec3 w = vec3(fbm(p * 0.9 + 1.1, 3), fbm(p * 0.9 + 3.7, 3), 0.0) - 0.5;
        float n = fbm(p + w * 0.8, 4);
        float fade = smoothstep(0.28, 0.02, d0);
        dark = max(dark, smoothstep(0.42, 0.58, n) * fade * 0.85);
    }

    if (d1 < 0.22) {
        vec3 p = dir * 7.0 + 41.7;
        vec3 w = vec3(fbm(p * 0.9 + 2.3, 3), fbm(p * 0.9 + 5.1, 3), 0.0) - 0.5;
        float n = fbm(p + w * 0.8, 4);
        float fade = smoothstep(0.22, 0.01, d1);
        dark = max(dark, smoothstep(0.44, 0.60, n) * fade * 0.80);
    }

    return dark;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  PULSARS
//  Two point sources that rhythmically pulse with a directional jet
// ═══════════════════════════════════════════════════════════════════════════════

vec3 pulsars(vec3 dir) {
    vec3 col = vec3(0.0);

    // Pulsar 1 — blue-white, fast sharp flash, upper left
    {
        vec3 pos    = normalize(vec3(-0.72, 0.50, -0.20));
        vec3 jetDir = normalize(vec3(0.10, 0.80, 0.30));
        float d     = acos(clamp(dot(dir, pos), -1.0, 1.0));

        // Sharp isolated flash: sawtooth that fires briefly then stays dim
        float cycle = mod(uTime * 0.4, 1.0);  // fires once per 2.5 seconds
        float pulse = smoothstep(0.0, 0.04, cycle) * smoothstep(0.12, 0.04, cycle);

        float glow = smoothstep(0.035, 0.0, d) * 0.25;
        float core = smoothstep(0.005, 0.0, d) * 0.9;

        float jetDot = abs(dot(dir, jetDir));
        float jet = smoothstep(0.994, 1.0, jetDot) * smoothstep(0.03, 0.0, d) * 0.4;

        col += (glow + core + jet) * (0.08 + pulse * 0.92) * vec3(0.70, 0.85, 1.00);
    }

    // Pulsar 2 — amber, slower distinct flash, lower right
    {
        vec3 pos    = normalize(vec3( 0.65, -0.55,  0.30));
        vec3 jetDir = normalize(vec3(-0.20, 0.60, 0.50));
        float d     = acos(clamp(dot(dir, pos), -1.0, 1.0));

        float cycle = mod(uTime * 0.18 + 0.5, 1.0); // fires once per 5.5 seconds
        float pulse = smoothstep(0.0, 0.06, cycle) * smoothstep(0.18, 0.06, cycle);

        float glow = smoothstep(0.03, 0.0, d) * 0.22;
        float core = smoothstep(0.004, 0.0, d) * 0.8;

        float jetDot = abs(dot(dir, jetDir));
        float jet = smoothstep(0.992, 1.0, jetDot) * smoothstep(0.025, 0.0, d) * 0.35;

        col += (glow + core + jet) * (0.06 + pulse * 0.94) * vec3(1.00, 0.70, 0.30);
    }

    return col;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  SHOOTING STARS / METEORS
//  A handful of streaks cycling on staggered timers.
//  Each meteor has a random direction and a head + fading trail.
// ═══════════════════════════════════════════════════════════════════════════════

vec3 shootingStars(vec3 dir) {
    vec3 col = vec3(0.0);

    for (int i = 0; i < 4; i++) {
        float fi     = float(i);
        float period = 22.0 + fi * 8.1;
        float offset = fi * 5.13 + 0.5;
        float t      = mod(uTime * 0.5 + offset, period);

        float visible = smoothstep(0.0, 0.05, t) * smoothstep(0.5, 0.3, t);
        if (visible < 0.001) continue;

        vec2 h1 = hash21(fi * 17.3 + 1.0);
        vec2 h2 = hash21(fi * 17.3 + 2.0);
        float phi1   = h1.x * 6.283;
        float theta1 = acos(clamp(1.0 - h1.y * 0.6 + 0.1, -1.0, 1.0));
        float phi2   = phi1 + (h2.x - 0.5) * 0.6;  // shorter arc
        float theta2 = theta1 + h2.y * 0.3;

        vec3 start = normalize(vec3(sin(theta1)*cos(phi1), cos(theta1), sin(theta1)*sin(phi1)));
        vec3 end   = normalize(vec3(sin(theta2)*cos(phi2), cos(theta2), sin(theta2)*sin(phi2)));

        float headT = clamp(t / 0.5, 0.0, 1.0);
        vec3 head = normalize(mix(start, end, headT));

        // Only draw head dot + very short tail — no great circle projection
        float headDist = acos(clamp(dot(dir, head), -1.0, 1.0));

        // Core dot
        float core = smoothstep(0.003, 0.0, headDist);

        // Short tail: a few points behind the head along the arc
        float tail = 0.0;
        for (int j = 1; j <= 4; j++) {
            float jf = float(j);
            float tailT = clamp(headT - jf * 0.06, 0.0, 1.0);
            vec3 tailPos = normalize(mix(start, end, tailT));
            float tailDist = acos(clamp(dot(dir, tailPos), -1.0, 1.0));
            tail += smoothstep(0.002, 0.0, tailDist) * (1.0 - jf * 0.22);
        }

        col += vec3(0.85, 0.92, 1.00) * (core * 0.5 + tail * 0.15) * visible;
    }

    return col;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  AURORA
//  Vertical curtains of shifting color. Anchored to a region of the sky and
//  animated with sine waves + noise for the rippling ribbon look.
// ═══════════════════════════════════════════════════════════════════════════════

vec3 aurora(vec3 dir) {
    if (dir.y > 0.55 || dir.y < -0.55) return vec3(0.0);

    vec3 forward = normalize(vec3(-0.50, 0.0, 0.85)); // keep it horizontal
    vec3 right   = normalize(cross(vec3(0.0, 1.0, 0.0), forward));

    float dist = acos(clamp(dot(normalize(vec3(dir.x, 0.0, dir.z)), forward), -1.0, 1.0));
    if (dist > 1.1) return vec3(0.0);

    // Horizontal region fade — soft on left/right sides
    float regionFade = smoothstep(1.1, 0.0, dist);

    // Vertical fades — soft top and bottom, no hard lines
    float elevFade = smoothstep(0.55, 0.08, dir.y)
    * smoothstep(-0.55, -0.05, dir.y);

    // u = position along the horizontal axis — drives column variation
    float u = dot(dir, right);
    // v = actual world-space vertical — drives curtain height and color
    float v = dir.y;

    // ── CURTAIN COLUMNS ───────────────────────────────────────────────────────
    // vnoise sampled only on u — smooth horizontal variation, no snapping
    vec3 colP = vec3(u * 3.0 + uTime * 0.025, 0.5, 0.5);
    float columns = vnoise(colP) * 0.55
    + vnoise(colP * vec3(2.3, 1.0, 1.0) + 3.7) * 0.30
    + vnoise(colP * vec3(5.1, 1.0, 1.0) + 7.9) * 0.15;
    columns = columns * 0.75 + 0.25; // lift floor, no gaps

    // ── VERTICAL SHAPE ────────────────────────────────────────────────────────
    // Curtain top: gently wavy in u, around y=0.25
    float topEdge = 0.25 + 0.04 * sin(u * 2.1 + uTime * 0.06)
    + 0.02 * sin(u * 4.8 + uTime * 0.08 + 1.3);
    // Soft top fade
    float topFade = smoothstep(topEdge + 0.10, topEdge - 0.02, v);
    // Bottom fades out softly — no hard floor
    float botFade = smoothstep(-0.40, 0.05, v);

    float vShape = topFade * botFade;
    if (vShape < 0.001) return vec3(0.0);

    // ── RIPPLE ────────────────────────────────────────────────────────────────
    float ripple = sin(v * 10.0 - uTime * 0.5 + u * 1.5) * 0.5 + 0.5;
    ripple = mix(0.65, 1.0, ripple);

    float intensity = columns * vShape * ripple * regionFade * elevFade;
    if (intensity < 0.005) return vec3(0.0);

    // ── COLOR — green bottom, blue-violet mid, magenta top ───────────────────
    float vT = clamp((v + 0.40) / 0.65, 0.0, 1.0); // 0 at bottom, 1 at top
    vec3 botCol = vec3(0.04, 0.88, 0.22);
    vec3 midCol = vec3(0.22, 0.42, 0.95);
    vec3 topCol = vec3(0.78, 0.08, 0.52);
    vec3 col = vT < 0.5
    ? mix(botCol, midCol, vT * 2.0)
    : mix(midCol, topCol, (vT - 0.5) * 2.0);

    // Bright hem at the bottom fringe
    float fringe = smoothstep(0.05, -0.30, v) * 0.4;

    return col * (intensity + fringe * columns) * 0.75;
}

// ═══════════════════════════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════════════════════════

void main() {
    vec3 dir = getRayDir();

    vec3 color = vec3(0.0);

    // Dark nebulae computed first — applied as a multiplier after base layers
    float dark = darkNebula(dir);

    color += starClusters(dir);
    color += allNebulae(dir);
    color += galaxy(dir);
    color += allStars(dir);

    // Dark nebula suppresses stars and background behind it
    color *= (1.0 - dark * 0.92);

    // Additive effects on top — not affected by dark nebula
    color += pulsars(dir);
    color += shootingStars(dir);
    color += aurora(dir);

    // Tone map
    color *= 1.8;
    color  = color / (color + vec3(1.0));
    color  = pow(max(color, 0.0), vec3(1.0 / 2.0));

    float lum = dot(color, vec3(0.2126, 0.7152, 0.0722));
    color = mix(vec3(lum), color, 1.25);

    fragColor = vec4(clamp(color, 0.0, 1.0), 1.0);
}
