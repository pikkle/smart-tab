var canvas, ctx, sel_obj, t_inside;

function refresh() {
  ctx.fillStyle='white';
  ctx.fillRect(0,0,canvas.width,canvas.height);
}

function init() {
  canvas = document.getElementById('id_canvas');
  ctx = canvas.getContext('2d');

  resizeCanvas();
  drawGrid();
}

function resizeCanvas() {
  canvas.width = window.innerWidth;
  canvas.height = 500;
  drawGrid();
}

function draw() {

  ctx.beginPath();
  ctx.moveTo(100, 150);
  ctx.lineTo(100, 500);

  // set line color
  ctx.strokeStyle = '#ff00000';
  ctx.stroke();
  ctx.end();
}

function drawGrid() {

  var img=new Image();
  img.onload=function(){
    ctx.drawImage(img,0,0,img.width,img.height,60,5,50,136);
    ctx.drawImage()
  }
  img.src="res/cledesol.png";

  ctx.strokeStyle='black';
  ctx.lineWidth='1';

  for( var i = 1; i<6;i++){
    ctx.beginPath();
    ctx.moveTo(50, (20*i)+10.5);
    ctx.lineTo(canvas.width, (20*i)+10.5);
    ctx.closePath();
    ctx.lineWidth = 1;
    ctx.stroke();
  }

  for( var i = 1; i<7;i++){
    ctx.beginPath();
    ctx.moveTo(50, (20*i)+130.5);
    ctx.lineTo(canvas.width, (20*i)+130.5);
    ctx.closePath();
    ctx.lineWidth = 1;
    ctx.stroke();
  }

  ctx.beginPath();
  ctx.moveTo(50, 30.5);
  ctx.lineTo(50, 250.5);
  ctx.closePath();
  ctx.lineWidth = 3;
  ctx.stroke();


}

// DARK MAGIC AHEAD
function touch(e) {

}