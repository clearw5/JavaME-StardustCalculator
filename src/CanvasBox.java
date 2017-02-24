import javax.microedition.lcdui.Font;
import java.util.Vector;

public class CanvasBox{
String text;
int sw,color;
String[] line;
Font font;
boolean isEditable;
public CanvasBox(String s,int c,Font f,int sw,boolean b){
	text=s;
	color=c;
	font=f;
	this.sw=sw;
	setString(s);
}
public CanvasBox(){
}
public CanvasBox(String s,int sw){
	this.sw=sw;
	font=Font.getDefaultFont();
	color=0x0087cefa;
	setString(s);
	isEditable=true;
}
public CanvasBox(String s,int sw,boolean b){
	this.sw=sw;
	font=Font.getDefaultFont();
	color=0;
	isEditable=b;
	setString(s);
}
public String getString(){
return text;
}
public int lineNum(){
return line.length;
}
public String getStringAtLine(int i){
	return line[i];
}
public int getColor(){
	return color;
}
public boolean isEditable(){
	return isEditable;
}
public void setString(String s){
	text=s;
	if(s.equals("")){
		String[] tmp={""};
		line=tmp;
		return;
	}
	Vector v=new Vector();
	StringBuffer sb=new StringBuffer();
	if(isEditable){
	for(int i=0;i<s.length();i++){
		sb.append(s.charAt(i));
		if(font.stringWidth(sb.toString())>sw||s.charAt(i)=='\n'){
			v.addElement(sb.deleteCharAt(sb.length()-1).toString());
			i--;
			sb=new StringBuffer();
		}
	}
	if(sb.length()>0){
	v.addElement(sb.toString());
	}
	line=new String[v.size()];
	for(int i=0;i<v.size();i++){
	line[i]=(String)v.elementAt(i);	
	}
	}else{
		for(int i=s.length()-1;i>=0;i--){
			sb.insert(0,s.charAt(i));
			if(font.stringWidth(sb.toString())>sw||s.charAt(i)=='\n'){
				v.addElement(sb.deleteCharAt(sb.length()-1).toString());
				sb=new StringBuffer();
			}
		}
		if(sb.length()>0){
		v.addElement(sb.toString());
		}
		line=new String[v.size()];
		for(int i=0;i<v.size();i++){
			line[i]=(String)v.elementAt(v.size()-i-1);	
			}
	}
}
}