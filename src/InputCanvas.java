import javax.microedition.lcdui.*;
import java.util.Vector;
public class InputCanvas extends Canvas implements CommandListener{
int startLine,selectedLine,lineNumES;
TextBox editBox;
Vector v;
Display display;
CanvasBox editcb;
Command ok,back,calc;
Script script;
Displayable displayable;

public InputCanvas(Display d){
	setFullScreenMode(true);
		startLine=0;
	selectedLine=0;
	lineNumES=getHeight()/(Font.getDefaultFont().getHeight()+2);
	editBox=new TextBox("编辑","",1024,TextField.ANY);
	ok=new Command("确定",4,0);
	back=new Command("返回",4,1);
	calc=new Command("计算",4,0);
	editBox.addCommand(ok);
	editBox.addCommand(back);
	editBox.setCommandListener(this);
	v=new Vector();
	display=d;
	editcb=new CanvasBox("1+1",getWidth());
	v.addElement(editcb);
	script=new Script();
/*	this.addCommand(back);
	this.addCommand(calc);
this.setCommandListener(this);*/
}
public void paint(Graphics g){
g.setColor(255,255,0);
g.fillRect(0,0,getWidth(),getHeight());
g.setColor(0, 255, 0);
g.drawString(getMessage(),0,0, Graphics.TOP|Graphics.LEFT);
int y=15;
for(int i=startLine;i<startLine+lineNumES&&i<lineNum();i++){
CanvasLine cl=getLineAt(i);
g.setColor(cl.getColor());
if(cl.isLeft()){
g.drawString(cl.getString(),0,y,Graphics.TOP|Graphics.LEFT);
}else{
g.drawString(cl.getString(),getWidth(),y,Graphics.TOP|Graphics.RIGHT);
}
y+=20;
}
}
public void keyPressed(int key){
switch(key){
case -1:
selectedLine--;
if(selectedLine<startLine){
startLine--;
}
if(startLine<0){
startLine =0;
selectedLine =0;
}
repaint();
break;
case -2:
selectedLine ++;
if(selectedLine>=lineNum()){
selectedLine--;
}
if(selectedLine>=startLine+lineNumES-1){
startLine++;
}
repaint();
break;
case -5:
editBox.setString(CanvasBoxAtLine(selectedLine).getString());
display.setCurrent(editBox);
break;
case -6:
commandAction(calc,this);
break;
case -7:
commandAction(back,this);
break;
}
}
CanvasLine getLineAt(int i){
	int k=0;
	CanvasBox cb=new CanvasBox();
	int n=0;
	while(k<=i){
	cb=(CanvasBox)v.elementAt(n);
	k+=cb.lineNum();
	n++;
	}
	int c=cb.getColor();
	int l=i+cb.lineNum()-k;
	String s=cb.getStringAtLine(l);
	if(i==selectedLine){
		c=0x00ff0000;
	}
	return new CanvasLine(s,c,cb.isEditable());
}
CanvasBox CanvasBoxAtLine(int i){
int k=0;
CanvasBox cb=new CanvasBox();
int n=0;
while(k<=i){
cb=(CanvasBox)v.elementAt(n);
k+=cb.lineNum();
n++;
}
return cb;
}
public void commandAction(Command cd,Displayable disp){
if(cd==ok){
editcb.setString(editBox.getString());
selectedLine =lineNum()-1;
if(selectedLine <lineNumES-3){
startLine =0;
}else{
startLine =selectedLine -(lineNumES-4);
}
repaint();
display.setCurrent(this);
return;
}
if(cd==calc){
	try{
	v.addElement(new CanvasBox(script.calculate(editcb.getString()),getWidth(),false));
	}catch(Exception exc){
		v.addElement(new CanvasBox(exc.toString(),getWidth(),false));
	}
	editcb=new CanvasBox(editcb.getString(),getWidth());
	v.addElement(editcb);
	selectedLine =lineNum()-1;
	if(selectedLine <lineNumES-3){
	startLine =0;
	}else{
	startLine =selectedLine -(lineNumES-4);
	}
	repaint();
    return;
}
if(cd==back){
	if(disp==this){
		display.setCurrent(displayable);
		return;
	}
	if(disp==editBox){
		display.setCurrent(this);
		return;
	}
}
}
public int lineNum(){
int ln=0;
for(int i=0;i<v.size();i++){
ln+=((CanvasBox)v.elementAt(i)).lineNum();
}
return ln;
}
public void setNextDisplayable(Displayable d){
	displayable=d;
}
public String getMessage(){
	String s="计算器         ";
	if(script.isDeg){
		s+="#角度制";
	}else{
		s+="#弧度制";
	}
	return s;
}
}

