// Reference-driven ancient shrine. World coordinates packed into one 18-block display.
// Existing raster textures only; luminous strokes sample their colour swatches.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const scale=16/18, elements=[];
const point=([x,y,z])=>[(x+7)*scale,y*scale,(z+7)*scale];
const swatches={cyan:[5.56,.56,5.94,.94],violet:[9.06,2.06,9.44,2.44]};
function box(from,to,texture='stone',rotation=null,emission=0){
  const uv=swatches[texture]||[0,0,16,16];
  const e={from:point(from),to:point(to),faces:Object.fromEntries(
    ['down','up','north','south','east','west'].map(f=>[f,{uv,texture:'#'+texture}]))};
  if(rotation)e.rotation={origin:point(rotation.origin),axis:rotation.axis,angle:rotation.angle,rescale:false};
  if(emission)e.light_emission=emission;
  elements.push(e);
}
function glyph(x,y,z,h=.65,rotation=null){
  // Interrupted angular script rather than a repeated cross.
  const w=h*.48,s=h*.065;
  for(const[a,b,c,d]of[[0,0,s,h],[0,h-s,w,h],[w-s,h*.52,w,h],
    [s,h*.48,w,h*.48+s],[s,0,w*.70,s]])
    box([x+a,y+b,z],[x+c,y+d,z+.018],'cyan',rotation,15);
}
function crystal(x,y,z,height,width,tilt=0){
  const rotation={origin:[x,y+height*.5,z],axis:tilt?'z':'y',angle:tilt||45};
  for(let i=0;i<12;i++){
    const a=i/12,b=(i+1)/12,half=width*(.055+.445*Math.sin(Math.PI*(a+b)/2));
    box([x-half,y+height*a,z-half*.7],[x+half,y+height*b,z+half*.7],'crystal',rotation,9);
  }
  box([x-width*.11,y+height*.37,z-width*.37],
    [x+width*.11,y+height*.63,z-width*.37+.04],'violet',rotation,15);
}
// Keep the existing recipe's walkable floor heights and footprint.
for(let z=-7;z<8;z++)for(let x=-7;x<8;x++){
  if(Math.abs(x)===7&&Math.abs(z)===7)continue;
  box([x+.018,0,z+.018],[x+.982,1,z+.982]);
  const edge=Math.max(Math.abs(x),Math.abs(z));
  if(edge<=5)box([x+.018,1,z+.018],[x+.982,2,z+.982]);
  else if(edge===6)box([x+.018,1,z+.018],[x+.982,1.5,z+.982]);
}
box([0,2,-3],[1,3,-2]); // support beneath the real interactive pedestal
box([.465,2.006,-5],[.535,2.022,3],'cyan',null,15);
box([-4.5,2.006,2.465],[5.5,2.022,2.535],'cyan',null,15);
for(const sign of[-1,1])box([-1.25,2.006,.46],[2.25,2.022,.54],'cyan',
  {origin:[.5,2.023,.5],axis:'y',angle:sign*45},15);
function crack(x,y,z,length,seed=0){
  for(let i=0;i<5;i++){
    const offset=(i+seed)%2?.07:-.05;
    box([x+offset,y+i*length/5,z],[x+offset+.035,y+(i+1)*length/5+.04,z+.018],
      'violet',null,7);
  }
}
// Asymmetric rear towers: masonry courses, broken buttresses, chipped crowns.
for(const side of[-1,1]){
  const x=side*5,top=side<0?16.7:14.5,outer=side<0?x-1:x+1;
  for(let y=2;y<top;y+=.75){
    const last=Math.min(y+.72,top);
    box([x+.025,y,3.025],[x+.975,last,3.975]);
    if(y<top-2.5)box([x+.025,y,4.025],[x+.975,last,4.975]);
    if(y<top-5.5)box([outer+.025,y,3.025],[outer+.975,last,3.975]);
    if(y<top-8)box([outer+.025,y,4.025],[outer+.975,last,4.975]);
  }
  for(let i=0;i<8;i++){
    const y=2.4+i*1.35;if(y>top-1)break;
    const bx=x+(i%2?.68:-.12);
    box([bx,y,2.80],[bx+.40,y+.55,3.12]);
  }
  for(let y=3;y<top-1;y+=1.0)glyph(x+.34,y,2.99,.62);
  crack(x+.12,4,2.985,2.3);crack(x+.78,top-5,2.985,2.5,1);
  box([x+.10,top,3.12],[x+.44,top+.62,3.88]);
  box([x+.60,top-.45,3.17],[x+.92,top+.20,3.83]);
  for(const z of[-4,5]){
    const tip=z<0?6:8;
    for(let y=2;y<tip;y+=.65){
      const taper=y>tip-1.5?.16:0;
      box([x+taper+.02,y,z+taper+.02],
        [x+1-taper-.02,Math.min(y+.62,tip),z+1-taper-.02]);
    }
    for(let y=2.8;y<tip-1;y+=.85)glyph(x+.34,y,z-.015,.55);
    crack(x+.12,3,z-.02,1.2);
    crystal(x+.5,tip,z+.5,1.15,.42);
  }
}
// Four thick, carved diamond arms with the reference's open upper/lower tips.
function arm(ax,ay,bx,by){
  const length=Math.hypot(bx-ax,by-ay);
  const rotation={origin:[ax,ay,.5],axis:'z',angle:by>ay?45:-45};
  box([ax,ay-.29,.13],[ax+length,ay+.29,.87],'stone',rotation);
  for(let t=.15;t<length-.2;t+=.72){
    box([ax+t,ay-.36,.08],[ax+Math.min(t+.12,length),ay+.36,.92],'stone',rotation);
    if(t+.43<length)glyph(ax+t+.20,ay-.17,.105,.31,rotation);
  }
}
arm(-3.8,9.5,-.15,13.15);arm(1.15,13.15,4.8,9.5);
arm(-3.8,9.5,-.15,5.85);arm(1.15,5.85,4.8,9.5);
// Floating cluster: pointed layered shards, bright core, suspended axial stones.
crystal(.5,7.1,.5,4.8,1.25);
crystal(-.20,7.9,.45,2.8,.62,-22.5);crystal(1.20,7.9,.55,2.8,.62,22.5);
crystal(.5,8.4,-.04,2.2,.60);
box([.475,6.45,.475],[.525,7.10,.525],'cyan',null,15);
for(const y of[6.15,12.5,13.45,14.4]){
  box([.23,y,.23],[.77,y+.48,.77]);
  box([.45,y+.13,.215],[.55,y+.36,.23],'violet',null,12);
}
box([.48,3.95,-2.52],[.52,6.15,-2.48],'cyan',null,15);
// Check rotated corners as well as unrotated bounds before generating assets.
for(const e of elements){
  const r=e.rotation;
  for(let mask=0;mask<8;mask++){
    let v=e.from.map((a,i)=>mask&(1<<i)?e.to[i]:a);
    if(r){
      const axis={x:0,y:1,z:2}[r.axis],i=(axis+1)%3,j=(axis+2)%3;
      const a=v[i]-r.origin[i],b=v[j]-r.origin[j],rad=r.angle*Math.PI/180;
      v[i]=r.origin[i]+a*Math.cos(rad)-b*Math.sin(rad);
      v[j]=r.origin[j]+a*Math.sin(rad)+b*Math.cos(rad);
    }
    if(v.some(c=>c<0||c>16))throw new Error('Rotated shrine element outside display envelope');
  }
}
const model={parent:'minecraft:block/block',textures:{
  particle:'deep_end:block/ancient_shrine_stone',stone:'deep_end:block/ancient_shrine_stone',
  cyan:'deep_end:block/ancient_rune_inlay',violet:'deep_end:block/ancient_crystal',
  crystal:'deep_end:block/ancient_crystal'},elements};
fs.writeFileSync(path.join(root,'src/main/resources/assets/deep_end/models/block/awakened_shrine.json'),
  JSON.stringify(model,null,2)+'\n');
console.log(`Generated reference shrine: ${elements.length} elements, ${elements.filter(e=>e.light_emission===15).length} fullbright details.`);
