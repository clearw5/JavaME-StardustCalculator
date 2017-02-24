import javax.microedition.lcdui.*;

public class wel extends Canvas {
Display d;
Displayable[] da;
Image img;
public int sn;
boolean init;
String[] menu;
public wel(Display d,Displayable[] da,String[] m){
this.d=d;
this.da=da;
menu=m;
sn=0;
init=true;
/*try{
if(getWidth()==240){
img=Image.createImage("/wel_240_320.jpg");
}else{
img=Image.createImage("/wel_320_480.jpg");
}
}catch(Exception e){}
*/
	int sw=getWidth();
	int sh=getHeight();
try{
Image im=Image.createImage("/wel.jpg");
/*int[] rgb=new int[sw*sh];
im.getRGB(rgb,0,sw,0,0,sw,sh);
img=Image.createRGBImage(rgb,sw,sh,true);*/
img=scaleImage(im,sw,sh);
}catch(Exception e){}
}
public void paint(Graphics g){
if(img!=null){	g.drawImage(img,0,0,20);}
if(!init){
int x=30;
int y=x;
for(int i=0;i<menu.length;i++){
g.setColor(66,255,255);
if(i==sn){
g.setColor(138,43,226);
}
g.drawString(menu[i],x,y,20);
y+=20;
}
}
}
public void keyPressed(int key){
if(init){
if(key==48){
d.setCurrent(da[menu.length]);
}else{
init=false;
repaint();
}
return;
}
switch(key){
case -1:
if(sn>0){
sn--;
}
break;
case -2:
if(sn<menu.length-1){
sn++;
}
break;
case -5:
Displayable dab=da[sn];
d.setCurrent(dab);
break;
}
repaint();
try{
Thread.sleep(100);
}catch(Exception e){}
}
//public void pointerPressed(int x,int y){

public static Image scaleImage(Image src,int dstW, int dstH) {
 int srcW = src.getWidth();
  int srcH = src.getHeight();
   Image tmp = Image.createImage(dstW, srcH);
    Graphics g = tmp.getGraphics();
     int delta = (srcW << 16) / dstW; 
     int pos = delta / 2;
      for (int x= 0; x< dstW; x++) {
       g.setClip(x, 0, 1, srcH);
        g.drawImage(src, x- (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
         pos += delta; 
         } 
         Image dst = Image.createImage(dstW, dstH);
          g = dst.getGraphics();
          delta = (srcH << 16) / dstH;
           pos = delta / 2;
            for (int y = 0; y < dstH; y++) { 
            g.setClip(0, y, dstW, 1);
             g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP); pos += delta;
              } 
              return dst;
               }
}