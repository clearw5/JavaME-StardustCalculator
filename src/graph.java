//import com.chen.javax.microedition.lcdui.map.KeyMap;
import javax.microedition.lcdui.*;

public class graph extends Canvas{
Script script;
int sw,sh;
double[] y;
Command back;
Midlet mid;
double xmax,xmin,dy,dx;
Graphics gra;String fx;int ox,oy;
	public graph(String fx,Midlet midlet,Command b,double min,double max){		xmax=max;
		xmin=min;
		back = b;
/*		this.addCommand(back);
		this.setCommandListener(midlet);
*/
this.mid=midlet;
		script=new Script();
		sw=getWidth();
		sh=getHeight();		y=new double[sw];		this.fx=fx;
oy=sh/2;
setFullScreenMode(true);
		draw(fx);
	}
	public void paint(Graphics g) {
g.translate(0,0);
			g.setColor(255,255,255);
	g.fillRect(0,0,sw, sh);
		g.setColor(0,0,0);
g.drawLine(ox,0,ox,10000);
g.drawLine(0,oy,10000,oy);
g.drawString(String.valueOf((float)xmin),0,oy+2,20);
g.drawString(String.valueOf((float)xmax),sw,oy+2,24);
g.drawString(String.valueOf((float)(oy*dx)),ox+2,0,20);
g.drawString(String.valueOf((float)((oy-sh)*dx)),ox+2,sh,36);
g.translate(ox,oy);
		g.setColor(255,0,0);
		for(int i=0;i<sw-1;i++){
if(isNormalNum(y[i])&&isNormalNum(y[i+1])){
int y1=(int)(dy*y[i]);
int y2=(int)(dy*y[i+1]);if(Math.abs(y1-y2)<=sh){
		  g.drawLine(i-ox,-y1,i+1-ox,-y2);}
			}
	  }
	}
	public void draw(String f){
		dx = (xmax-xmin)/((double)sw);
	double x=xmin;
script.word_analyze(f);
	for(int i=0;i<sw;i++){
		script.setVar("x",x);
		y[i]=script.double_calc();
		x+=dx;
	}
	dy=sw/(xmax-xmin);
ox=(int)(-xmin*dy);
		repaint();
	}
	 public void commandAction(Command cd, Displayable de) {
		 
	 }	
 public void keyPressed(int key){	
	 if(key==-4){	for(int i=sw/10;i<sw;i++){		y[i-sw/10]=y[i];	}	for(int i=9*sw/10;i<sw;i++){		script.setVar("x",xmax);		y[i]=script.double_calc();		xmax+=dx;	};
		 xmin+=sw/10*dx;		 ox=(int)(-xmin*dy);			 repaint();
			 return;
		 }
		 if(key==-3){
			 for(int i=sw*9/10-1;i>=0;i--){	
				y[i+sw/10]=y[i];		
		}		
		for(int i=sw/10-1;i>=0;i--){	
				xmin-=dx;			
				script.setVar("x",xmin);
					y[i]=script.double_calc();	
	};		
			 xmax-=sw/10*dx;	
				 ox=(int)(-xmin*dy);
						 repaint();		
				 return;	
	 }		 
if(key==-2){
			 oy-=sh/10;			 repaint();			 return;		 }	
if(key==-1){
			 oy+=sh/10;			 repaint();			 return;		 }	
	 if(key==42){	
double d=(xmax-xmin)*0.5;
		 xmin -=d;			 xmax+=d;	
		 draw(fx);			
 return;		 }	
	 if(key==35){		
double d=(xmax-xmin)*0.25;
		 xmin +=d;			 xmax-=d;	
		 draw(fx);			
			 return;
		 }
if(key==-7){
mid.commandAction(back,this);
return;
}
	 }
boolean isNormalNum(double x){
return !(Double.isNaN(x)||Double.isInfinite(x));//&&x!=Double.POSITIVE_INFINITY&&x!=Double.NEGATIVE_INFINITY;
}
}
