public class CanvasLine{
String text;
int color;
boolean isLeft;
public CanvasLine(String s,int c,boolean b){
text=s;
color=c;
isLeft=b;
}
public String getString(){
return text;
}
public int getColor(){
return color;
}
public boolean isLeft(){
return isLeft;
}
}
