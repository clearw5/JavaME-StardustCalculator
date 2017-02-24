import javax.microedition.midlet.*;
import javax.microedition.io.*;
//import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;

import java.io.*;
public class Midlet extends MIDlet implements CommandListener{
Display d;
 Command s;
Command e,update;
Command back,g;
TextField p,fx,xmin,xmax;
TextField tf;
Form f,about,fxgra,exit;
 Script script;
wel w;
graph gra;
InputCanvas ic;
BNIC bnic;
BDIC bdic;
 public Midlet(){
	 update=new Command("检查更新", 4, 0);
	 s = new Command("计算", 4, 0);
	 g = new Command("图像", 4, 0);
	 e = new Command("退出", 4, 1);
back = new Command("返回", 4, 2);
	 p = new TextField("表达式:", "", 1024, 0);
	 xmin = new TextField("X最小值：", "-10", 1024, 0);
	 xmax = new TextField("X最大值：", "10", 1024, 0);
	 fx = new TextField("函数:y=", "", 1024, 0);
	 tf = new TextField("结果:", "", 1024, 0);
	 f = new Form("TestMode");
	 exit = new Form("退出？");
	 fxgra = new Form("函数图像");
         about = new Form("关于");
	 try{
		 script=new Script();
	 }catch(Exception e){
		 f.append(e.toString());
	 }
 }
public void startApp() {
	d=Display.getDisplay(this);
  f.addCommand(s);
  fxgra.addCommand(g);
  fxgra.addCommand(back);
  exit.addCommand(back);
  exit.addCommand(e);
f.addCommand(back);
  f.setCommandListener(this);
  exit.setCommandListener(this);
  fxgra.setCommandListener(this);
  f.append(p);
fxgra.append(fx);
fxgra.append(xmin);
fxgra.append(xmax);
  f.append(tf);
exit.append("是否退出？");
about.addCommand(back);
about.addCommand(update);
about.setCommandListener(this);
about.append("  软件作者：星尘幻影\n  QQ:946994919\n  版本：BUG自测版0.1.1");
ic=new InputCanvas(d);
bnic=new BNIC(d);
bdic=new BDIC(d);
String[] m={"计算器","函数图像","逻辑判断","高精度计算","大数计算","关于","退出"};
Displayable[] da={ic,fxgra,f,bdic,bnic,about,exit,f};
w=new wel(d,da,m);
ic.setNextDisplayable(w);
bnic.setNextDisplayable(w);
bdic.setNextDisplayable(w);
  d.setCurrent(w);

}

 public void pauseApp() {
 }
/*public int c(int n,int r){if(n==r||r==0){return 1;}else{return c(n-1,r)+c(n-1,r-1);}}
 public void destroyApp(boolean unconditional) {
 }*/
 public void commandAction(Command cd, Displayable de) {
if(cd==s){
		try{
String s=p.getString();
if(w.sn==2){
	s="B#"+s;
}
tf.setString(script.calculate(s));

		}catch(Exception e){
		 tf.setString("出错啦！\n"+e.toString());
}
}else{
if(cd==back){
 if(de!=gra){
 d.setCurrent(w);
}else{
d.setCurrent(fxgra);
}
}else{
	if(cd==g){
try{

			gra=new graph(fx.getString(),this,back,Double.parseDouble(xmin.getString()),Double.parseDouble(xmax.getString()));
		d.setCurrent(gra);
	}catch(Exception e){
			fxgra.append(e.toString());
d.setCurrent(fxgra);

}
	}else{
		if(cd==update){
			try{
			HttpConnection http = (HttpConnection) Connector.open("http://www.baidu.com");
			//http.setRequestMethod(HttpConnection.POST);
			DataInputStream dis = http.openDataInputStream();
			int ch;
			StringBuffer message=new StringBuffer();
			while ( ( ch = dis.read() ) != -1 ) {
			message = message.append( ( char ) ch );
			}
			about.append(message.toString());
			}catch(Exception e){
				 about.append("出错啦！\n"+e.toString());
		}
		}else{
notifyDestroyed();
try {
	destroyApp(true);
} catch (MIDletStateChangeException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		}
}
}
}
}
protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	// TODO Auto-generated method stub
	
}
}



