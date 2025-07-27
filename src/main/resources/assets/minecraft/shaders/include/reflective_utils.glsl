#version 150

// Checking for the exact alpha value breaks things, so I use this function to cut down on space while also making it work better.
bool check_alpha(float textureAlpha, float targetAlpha) {
    float targetLess = targetAlpha - 0.01;
    float targetMore = targetAlpha + 0.01;
    return (textureAlpha > targetLess && textureAlpha < targetMore);
}

// For cases in which you want something to have a lower light level, but still be bright when in light.
vec4 apply_partial_emissivity(vec4 inputColor, vec4 originalLightColor, vec3 minimumLightColor) {
    vec4 newLightColor = originalLightColor;
    newLightColor.r = max(originalLightColor.r, minimumLightColor.r);
    newLightColor.g = max(originalLightColor.g, minimumLightColor.g);
    newLightColor.b = max(originalLightColor.b, minimumLightColor.b);
    return inputColor * newLightColor;
}

// NEW FUNCTION: Simulates a reflective/shiny look
// This will NOT be true reflection as it lacks environment maps and accurate camera position.
// It will be a fixed "metallic" tint + a highlight.
vec4 make_fake_reflective(vec4 inputColor, vec4 lightColor, vec3 normal) {
    // Fixed metallic tint (silvery-blueish)
    vec3 metallicTint = vec3(0.8, 0.85, 0.9);
    vec3 baseColor = inputColor.rgb * metallicTint;

    // Simulate a simple specular highlight (fixed direction, or use Light0_Direction if available)
    // We don't have CameraPosition, so we'll simulate a view direction or rely on light.
    // Let's use the main light source for a highlight, assuming uniform Light0_Direction is available.
    // If not, you could use a fixed direction (e.g., vec3(0.0, 1.0, 0.0) for overhead light)
    // IMPORTANT: Make sure `Light0_Direction` is a uniform in your Fsh.json if you use it.
    // If you don't have Light0_Direction, you can remove this or use a fixed constant.
    // For this example, I'll assume Light0_Direction is available.
    uniform vec3 Light0_Direction; // Declare this if it's not already a globally available uniform

    vec3 N = normalize(normal);
    vec3 L = normalize(-Light0_Direction); // Direction from pixel to light source

    // Simulate a viewer at a fixed angle (approximation if no CameraPosition)
    // Or, you can make the highlight purely based on light and normal
    vec3 V_approx = normalize(vec3(0.5, 0.8, 0.5)); // Arbitrary "view" direction, or reuse L for Blinn-Phong

    // For a more direct Blinn-Phong like highlight (without V):
    vec3 H = normalize(L + V_approx); // Half vector (V_approx is dummy here without real camera)
    float specularStrength = pow(max(dot(N, H), 0.0), 200.0); // Higher power for sharper highlight

    // Add a slight color shift to the highlight for "chromium"
    vec3 highlightColor = vec3(1.0, 1.0, 1.0) * specularStrength * 0.7; // White highlight, adjust intensity

    return vec4(baseColor * lightColor.rgb + highlightColor, inputColor.a);
}


// The meat and bones of the pack, does all the work for making things emissive/reflective.
vec4 make_emissive(vec4 inputColor, vec4 lightColor, vec4 maxLightColor, float vertexDistance, float inputAlpha, vec3 faceNormal) { // Added faceNormal parameter
    if (vertexDistance > 800) return inputColor;

    if (check_alpha(inputAlpha, 252.0)) return make_fake_reflective(inputColor, lightColor, faceNormal); // NOW CALLS REFLECTIVE
    else if (check_alpha(inputAlpha, 251.0)) return apply_partial_emissivity(inputColor, lightColor, vec3(0.411, 0.345, 0.388));
    else if (check_alpha(inputAlpha, 250.0)) return inputColor;
    else return inputColor * lightColor;
}

// Gets the dimension that an object is in, -1 for The Nether, 0 for The Overworld, 1 for The End.
float get_dimension(vec4 minLightColor) {
    if (minLightColor.r == minLightColor.g && minLightColor.g == minLightColor.b) return 0.0;
    else if (minLightColor.r > minLightColor.g) return -1.0;
    else return 1.0;
}

// Gets the face lighting of a block. Credits to Venaxsys for the original function.
vec4 get_face_lighting(vec3 normal, float dimension) {
    vec4 faceLighting = vec4(1.0, 1.0, 1.0, 1.0);
    vec3 absNormal = abs(normal);
    float top = 229.0 / 255.0;
    float bottom = 127.0 / 255.0;
    float east = 153.0 / 255.0;
    float north = 204.0 / 255.0;

    // Using `dot` product with face normals for more robust face lighting detection
    // Assuming normal is world-space or consistent space
    if (dot(normal, vec3(0.0, 1.0, 0.0)) > 0.9 && check_alpha(dimension, -1.0)) faceLighting = vec4(top, top, top, 1.0); // Top face, Nether
    else if (dot(normal, vec3(0.0, -1.0, 0.0)) > 0.9) { // Bottom face
        if (!check_alpha(dimension, -1.0)) faceLighting = vec4(bottom, bottom, bottom, 1.0); // Overworld/End
        else faceLighting = vec4(top, top, top, 1.0); // Nether (mimic top)
    } else if (abs(dot(normal, vec3(1.0, 0.0, 0.0))) > 0.9 || abs(dot(normal, vec3(-1.0, 0.0, 0.0))) > 0.9) { // East/West
        faceLighting = vec4(east, east, east, 1.0);
    } else if (abs(dot(normal, vec3(0.0, 0.0, 1.0))) > 0.9 || abs(dot(normal, vec3(0.0, 0.0, -1.0))) > 0.9) { // North/South
        faceLighting = vec4(north, north, north, 1.0);
    }

    return faceLighting;
}

// Checks the alpha and removes face lighting if required.
vec4 face_lighting_check(vec3 normal, float inputAlpha, float dimension) {
    if (check_alpha(inputAlpha, 250.0)) return get_face_lighting(normal, dimension);
    else return vec4(1.0, 1.0, 1.0, 1.0);
}

// Makes sure transparent things don't become solid and vice versa.
float remap_alpha(float inputAlpha) {
    if (check_alpha(inputAlpha, 252.0)) return 255.0; // This will now be handled by make_fake_reflective
    else if (check_alpha(inputAlpha, 251.0)) return 190.0;
    else if (check_alpha(inputAlpha, 250.0)) return 255.0;
    else return inputAlpha;
}