// Generate one native model for the entire awakened shrine.
// Coordinates are authored in world blocks, then packed into an 18-block display.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const scale = 16 / 18;
const elements = [];
const point = ([x,y,z]) => [(x+7)*scale, y*scale, (z+7)*scale];
const faces = texture => Object.fromEntries(['down','up','north','south','east','west'].map(face =>
  [face, {uv:[0,0,16,16], texture:'#'+texture}]));
function box(from, to, texture='stone', rotation=null) {
  const element = {from:point(from), to:point(to), faces:faces(texture)};
  if (rotation) element.rotation = {origin:point(rotation.origin), axis:rotation.axis,
    angle:rotation.angle, rescale:false};
  elements.push(element);
}
function crystal(x, y, z, height, width) {
  const bands = [[0,.12,.14],[.12,.28,.30],[.28,.72,.50],[.72,.88,.30],[.88,1,.14]];
  for (const [low,high,half] of bands) box([x-width*half,y+height*low,z-width*half],
    [x+width*half,y+height*high,z+width*half], 'crystal',
    {origin:[x,y+height*.5,z],axis:'y',angle:45});
}
// Broad carved plinths avoid the mosaic of individual block faces.
// The pedestal is a real interactive block, so the visual model leaves it out.
box([-6,0,-7],[7,1,8]);
box([-7,0,-6],[-6,1,7]);
box([7,0,-6],[8,1,7]);
box([-6,1,-6],[7,1.5,7]);
box([-5,1.5,-5],[6,2,6]);
box([0,2,-3],[1,3,-2]);
// Narrow continuous teal inlays on the floor.
box([.46,2.01,-5],[.54,2.025,3],'rune');
box([-4,2.01,2.46],[5,2.025,2.54],'rune');
for (const side of [-1,1]) {
  const x = side*5+.5;
  const height = side < 0 ? 16 : 13;
  // Long tapered courses and fractured crown, not stacked texture cubes.
  box([x-.5,2,3],[x+.5,height-2,4]);
  box([Math.min(side*5,side*6),2,3.15],[Math.max(side*5,side*6)+1,height-6,4.65]);
  box([x-.40,height-2,3.08],[x+.40,height,3.92]);
  box([x-.25,height,3.18],[x+.25,height+.65,3.82]);
  for (let y=3; y<height-1; y+=4) {
    box([x-.045,y,2.98],[x+.045,y+1.65,3.01],'rune');
    box([x-.28,y+.3,2.98],[x+.04,y+.38,3.01],'rune');
  }
  crystal(x,height+.65,3.5,.85,.60);
  for (const z of [-4,5]) {
    const top = z < 0 ? 6 : 8;
    box([x-.50,2,z],[x+.50,top-.35,z+1]);
    box([x-.36,top-.35,z+.14],[x+.36,top,z+.86]);
    box([x-.045,3,z-.02],[x+.045,top-1,z+.01],'rune');
    crystal(x,top,z+.5,1,.68);
  }
}
// Fifteen connected diagonal edges, leaving one fracture in the upper right.
const length = Math.SQRT2;
for (let x=-4;x<4;x++) {
  const segments = [[13-Math.abs(x), x<0?45:-45],[5+Math.abs(x), x<0?-45:45]];
  for (let i=0;i<segments.length;i++) {
    if (i===0 && x===1) continue;
    const [y,angle]=segments[i];
    const origin=[x+.5,y+.5,.5];
    box([origin[0],origin[1]-.11,.32],[origin[0]+length,origin[1]+.11,.68],
      'stone',{origin,axis:'z',angle});
    box([origin[0],origin[1]-.025,.305],[origin[0]+length,origin[1]+.025,.32],
      'rune',{origin,axis:'z',angle});
  }
}
crystal(.5,8,.5,3,1.45);
const model = {
  parent:'minecraft:block/block',
  textures:{particle:'deep_end:block/ancient_shrine_stone',
    stone:'deep_end:block/ancient_shrine_stone',
    rune:'deep_end:block/ancient_rune_inlay',
    crystal:'deep_end:block/ancient_crystal'},
  elements
};
const output = path.join(root,'src/main/resources/assets/deep_end/models/block/awakened_shrine.json');
fs.writeFileSync(output, JSON.stringify(model,null,2)+'\n');
console.log('Generated unified shrine model: '+elements.length+' elements.');
