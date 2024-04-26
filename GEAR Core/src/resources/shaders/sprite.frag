#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float smoothness;
uniform float reflectivity;

void main(void) {

    vec4 textureColor = texture(textureSampler, pass_textureCoords);

    if(textureColor.a < 0.05) {
        discard;
    }

    float _smoothness = max(smoothness, 0.0);
    float _reflectivity = max(reflectivity, 0.0);

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDotl = dot(unitNormal, unitLightVector);
    float brightness = max(nDotl, 0.1);
    vec3 diffuse = brightness * lightColor;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float smoothedFactor = pow(specularFactor, _smoothness);
    vec3 finalSpecular = smoothedFactor * _reflectivity * lightColor;

    out_Color = textureColor;
    //out_Color = vec4(lightDirection, 1.0);
}