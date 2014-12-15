var canvas, ctx, sel_obj, t_inside;
var current = 200;

function refresh() {
  ctx.fillStyle='white';
  ctx.fillRect(0,0,canvas.width,canvas.height);
  drawGrid();
  drawCurrent();
}

function init() {
  canvas = document.getElementById('id_canvas');
  ctx = canvas.getContext('2d');

  current = 150 + canvas.w
  resizeCanvas();
  drawGrid();
  drawcurrent();
}

function resizeCanvas() {
  canvas.width = window.innerWidth;
  canvas.height = 300;
  drawGrid();
  drawCurrent();
}


function drawGrid() {

  ctx.strokeStyle='#D3D3D3';
  for( var i = 1; i<7;i++){
    ctx.beginPath();
    ctx.moveTo(50, (15*i)+3.5);
    ctx.lineTo(canvas.width - 50, (15*i)+3.5);
    ctx.closePath();
    ctx.lineWidth = 1;
    ctx.stroke();
  }

  ctx.beginPath();
  ctx.moveTo(150, 25.5);
  ctx.lineTo(150, 250.5);
  ctx.closePath();
  ctx.lineWidth = 1;
  ctx.stroke();

  var img=new Image();
  img.onload=function(){
    ctx.drawImage(img,0,0,img.width,img.height,80,5,40,105);
  }
  img.src="res/cledesol.png";

  ctx.strokeStyle='black';
  ctx.lineWidth='1';

  // Draw the standard grid
  for( var i = 1; i<6;i++){
    ctx.beginPath();
    ctx.moveTo(50, (15*i)+10.5);
    ctx.lineTo(canvas.width - 50, (15*i)+10.5);
    ctx.closePath();
    ctx.lineWidth = 1;
    ctx.stroke();
  }

  // draw the tab grid
  for( var i = 1; i<7;i++){
    ctx.beginPath();
    ctx.moveTo(50, (25*i)+100.5);
    ctx.lineTo(canvas.width - 50 , (25*i)+100.5);
    ctx.closePath();
    ctx.lineWidth = 1;
    ctx.stroke();
  }

  ctx.beginPath();
  ctx.moveTo(50, 25.5);
  ctx.lineTo(50, 250.5);
  ctx.closePath();
  ctx.lineWidth = 3;
  ctx.stroke();



  ctx.beginPath();
  ctx.moveTo(canvas.width - 50, 25.5);
  ctx.lineTo(canvas.width - 50, 250.5);
  ctx.closePath();
  ctx.lineWidth = 3;
  ctx.stroke();

  ctx.beginPath();
  ctx.moveTo(canvas.width/2 + 50, 25.5);
  ctx.lineTo(canvas.width/2 + 50, 250.5);
  ctx.closePath();
  ctx.lineWidth = 1;
  ctx.stroke();

}

// DARK MAGIC AHEAD
function touch(e) {
  current += 200;
  refresh();
}


function drawCurrent() {
  ctx.strokeStyle='red';
  ctx.beginPath();
  ctx.moveTo(current, 15.5);
  ctx.lineTo(current, 260.5);
  ctx.closePath();
  ctx.lineWidth = 1;
  ctx.stroke();
}

function removeLast() {

}