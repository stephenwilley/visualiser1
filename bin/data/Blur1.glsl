#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform mat4 texMatrix;

varying vec4 vertColor;
varying vec4 vertTexCoord;

float rt_w = 1280.0;
float rt_h = 720.0;

void main(void) {
  vec3 tc = vec3(1.0, 0.0, 0.0);
  vec2 uv = vertTexCoord.xy;
  
  tc = texture2D(texture, uv).rgb * 0.25;

  tc += texture2D(texture, uv + vec2(0.0, 2.0)/rt_h).rgb * 0.175;
  tc += texture2D(texture, uv - vec2(0.0, 2.0)/rt_h).rgb * 0.175;
  tc += texture2D(texture, uv + vec2(0.0, 4.0)/rt_h).rgb * 0.125;
  tc += texture2D(texture, uv - vec2(0.0, 4.0)/rt_h).rgb * 0.125;
  tc += texture2D(texture, uv + vec2(0.0, 6.0)/rt_h).rgb * 0.075;
  tc += texture2D(texture, uv - vec2(0.0, 6.0)/rt_h).rgb * 0.075;

  gl_FragColor = vec4(tc, 1.0);
}