import java.util.*;
import java.io.*;

public class Script{

int oALenMax;
int oPLenMax;
double ans;
Hashtable var_name,order,level,func_name;
Vector var_value,f,func_wA,args,num_stack;
boolean isDeg;

	public Script() {
		isDeg=false;
ans=0;
oALenMax=6;
oPLenMax=2;
var_name=new Hashtable();
order=new Hashtable();
level=new Hashtable();
func_name=new Hashtable();
f=new Vector(100,100);
func_wA=new Vector();
args=new Vector();
num_stack=new Vector(100,100);
var_value=new Vector();
try{
	InputStream is= getClass().getResourceAsStream("/func.txt");
byte[] b =new byte[is.available()];
is.read(b);
is.close();
//String b="+ 1 6 - 2 6 * 3 5 / 4 5 ^ 5 3 sin 6 4 deg 7 2 ceil 8 4 floor 9 4 abs 10 4 == 11 7 != 12 7 >= 13 7 <= 14 7 > 15 7 < 16 7 sqrt 17 4 & 18 8 | 19 8 ? 20 8 ans 21 1 cos 22 4 tan 23 4 = 24 9 ; 25 10 exp 26 4 ln 27 4 lg 28 4 pi 29 1 e 30 1 arcsin 31 4 arccos 32 4 arctan 33 4 sinh 34 4 cosh 35 4 tanh 36 4 rand 37 1 max 38 4 sum 39 4 ! 42 2 args 47 4 if 58 4 do 59 4 arg 47 4 while 60 4 argnum 61 1 cot 62 4 arccot 63 4 arsinh 64 4 arcosh 65 4 artanh 66 4 cbrt 67 4 log 68 4 ranInt 70 4 min 71 4 gcd 72 4 lcm 73 4 mod 74 4 !! 76 4 % 77 4 prod 78 4 diff 79 4 solve 80 4 mod 74 4";
String[] o=split(new String(b)," ");
for(int k=0;k<o.length/3;k++){
level.put(new Integer(Integer.parseInt(o[3*k+1])),new Integer(Integer.parseInt(o[3*k+2])));
order.put(o[3*k],new Integer(Integer.parseInt(o[3*k+1])));
}
}catch(Exception e){}
for(int i=97;i<123;i++){
if(orderCode(chr(i))==0){
	var_name.put(chr(i),new Integer(i-97));
var_value.addElement(new Double(0.0));
}
}
}
	public int calc(int fn,int s,int e){
 int o=0;
 int ol=0;
 int op=-1;
Vector canshu =new Vector();
canshu.addElement(new Integer(s+1));
 for(int i=s;i<e;i++){
 if(f(fn,i)==40){
  int n=0;
boolean find=false;
  for(int j=i+1;j<e;j++){
 if(f(fn,j)==44&&n==0){
canshu.addElement(new Integer(j+1));
continue;
}
   if(f(fn,j)==40){
    n++;
   }else{
    if(f(fn,j)==41){
     if(n<=0){
      i=j;
      find=true;
      break;
     }else{
      n--;
     }
    }
   }
  }
  if(find){
   continue;
  }
  if(f(fn,s)==40){
 if(canshu.size()==1){
 return calc(fn,s+1,e);
 }else{
 canshu.addElement(new Integer(e+1));
 num_stack.addElement(canshu);
 return -num_stack.size();
 }
  }
  break;
 }
 if(level(f(fn,i))>ol){
  ol=level(f(fn,i));
  op=i;
  o=f(fn,i);
 }else{
 if(level(f(fn,i))==ol&&(ol==5||ol==6)){
  ol=level(f(fn,i));
  op=i;
  o=f(fn,i);
}
}
}
 if(f(fn,s)==40&&op<0){
 if(canshu.size()==1){
 return calc(fn,s+1,e-1);
 }else{
 canshu.addElement(new Integer(e));
 num_stack.addElement(canshu);
 return -num_stack.size();
 }
 }
double result = 0;
Vector cs;
switch(o){
case 0:
try{
StringBuffer sb=new StringBuffer();
for(int i=s;i<e;i++){
sb.append(chr(f(fn,i)));
}
result = Double.parseDouble(sb.toString());
break;
}catch(Exception exc){
throw new RuntimeException("数字格式错误");
}
case 1:
result = double_calc(fn,s,op)+double_calc(fn,op+1,e);
break;
case 2:
if(op==s){
result=-double_calc(fn,op+1,e);
}else{
result = double_calc(fn,s,op)-double_calc(fn,op+1,e);
}
break;
case 3:
result = double_calc(fn,s,op)*double_calc(fn,op+1,e);
break;
case 4:
result = double_calc(fn,s,op)/double_calc(fn,op+1,e);
break;
case 5:
result = iMath.pow(double_calc(fn,s,op),double_calc(fn,op+1,e));
break;
case 6:
result = iMath.sin(double_calc(fn,op+1,e),isDeg);
break;
case 7:
if(isDeg){
	result=double_calc(fn,s,op);
}else{
	result = Math.toRadians(double_calc(fn,s,op));
}
break;
case 8:
result = Math.ceil(double_calc(fn,op+1,e));
break;
case 9:
result = Math.floor(double_calc(fn,op+1,e));
break;
case 10:
result = Math.abs(double_calc(fn,op+1,e));
break;
case 11:
result = booleanToDouble(double_calc(fn,s,op)==double_calc(fn,op+1,e));
break;
case 12:
result = booleanToDouble(double_calc(fn,s,op)!=double_calc(fn,op+1,e));
break;
case 13:
result = booleanToDouble(double_calc(fn,s,op)>=double_calc(fn,op+1,e));
break;
case 14:
result = booleanToDouble(double_calc(fn,s,op)<=double_calc(fn,op+1,e));
break;
case 15:
result = booleanToDouble(double_calc(fn,s,op)>double_calc(fn,op+1,e));
break;
case 16:
result = booleanToDouble(double_calc(fn,s,op)<double_calc(fn,op+1,e));break;
case 17:
result = Math.sqrt(double_calc(fn,op+1,e));
break;
case 18:
result = (double)((int)double_calc(fn,s,op)&(int)double_calc(fn,op+1,e));
break;
case 19:
result = (double)((int)double_calc(fn,s,op)|(int)double_calc(fn,op+1,e));
break;
case 20:
result = ~(int)double_calc(fn,op+1,e);
break;
case 21:
result = this.ans;
break;
case 22:
result = iMath.cos(double_calc(fn,op+1,e),isDeg);
break;
case 23:
result = iMath.tan(double_calc(fn,op+1,e),isDeg);
break;
case 24:
double k=double_calc(fn,op+1,e);
var_value.setElementAt(new Double(k),f(fn,op-1)-10000);
result = k;
break;
case 25:
result = double_calc(fn,s,op)*0+double_calc(fn,op+1,e);
break;
case 26:
result = iMath.exp(double_calc(fn,op+1,e));
break;
case 27:
result=iMath.ln(double_calc(fn,op+1,e));
break;
case 28:
result=iMath.ln(double_calc(fn,op+1,e))/iMath.ln10;
break;
case 29:
result=iMath.PI;
break;
case 30:
result=iMath.E;
break;
case 31:
result = iMath.arcsin(double_calc(fn,op+1,e),isDeg);
break;
case 32:
result = iMath.arccos(double_calc(fn,op+1,e),isDeg);
break;
case 33:
result = iMath.arctan(double_calc(fn,op+1,e),isDeg);
break;
case 34:
	result=iMath.sinh(double_calc(fn,op+1,e));
	break;
case 35:
	result=iMath.cosh(double_calc(fn,op+1,e));
	break;
case 36:
	result=iMath.tanh(double_calc(fn,op+1,e));
	break;
case 37:
result = (new Random()).nextDouble();
break;
case 38:
cs=getVector(calc(fn,op+1,e));
result=getNum(fn,cs,0);
for(int i=1;i<cs.size()-1;i++){
result=Math.max(result,getNum(fn,cs,i));
}
break;
case 39:
cs = getVector(calc(fn,op+1,e),3);
double start =getNum(fn,cs,1);
double end =getNum(fn,cs,2);
for(double i=start;i<=end;i++){
var_value.setElementAt(new Double(i),0);
result+=getNum(fn,cs,0);
}
break;
case 42:
	result=iMath.fact(double_calc(fn,s,op));
	break;
case 47:
if(args.size()==3){
if(double_calc(fn,op+1,e)==0){
result=((Double)args.elementAt(0)).doubleValue();
}else{
throw new ArrayIndexOutOfBoundsException();
}
}else{
result=getNum(((Integer)args.elementAt(args.size()-1)).intValue(),args,(int)double_calc(fn,op+1,e));
}
break;
case 58:
cs=getVector(calc(fn,op+1,e),3);
if(getNum(fn,cs,0)==1){
result=getNum(fn,cs,1);
}else{
result=getNum(fn,cs,2);
}
break;
case 59:
cs=getVector(calc(fn,op+1,e),2);
do{
getNum(fn,cs,0);
result++;
}while(getNum(fn,cs,1)==1);
break;
case 60:
cs=getVector(calc(fn,op+1,e),2);
while(getNum(fn,cs,0)==1){
getNum(fn,cs,1);
result++;}
break;
case 61:
result=(double)args.size()-2;
break;
case 62:
	result=iMath.cot(double_calc(fn,op+1,e),isDeg);
	break;
case 63:
	result=iMath.arccot(double_calc(fn,op+1,e),isDeg);
	break;
case 64:
	result=iMath.arsinh(double_calc(fn,op+1,e));
	break;
case 65:
	result=iMath.arcosh(double_calc(fn,op+1,e));
	break;
case 66:
	result=iMath.artanh(double_calc(fn,op+1,e));
	break;
case 67:
	result=iMath.cbrt(double_calc(fn,op+1,e));
	break;
case 68:
	cs=getVector(calc(fn,op+1,e),2);
	result=iMath.log(getNum(fn,cs,1),getNum(fn,cs,0));
	break;
case 70:
	cs=getVector(calc(fn,op+1,e),2);
	result=iMath.ranInt((int)getNum(fn,cs,1),(int)getNum(fn,cs,0));
	break;
case 71:
cs=getVector(calc(fn,op+1,e));
result=getNum(fn,cs,0);
for(int i=1;i<cs.size()-1;i++){
result=Math.min(result,getNum(fn,cs,i));
}
break;
case 72:
	cs=getVector(calc(fn,op+1,e),2);
	result=iMath.gcd(getNum(fn,cs,0),getNum(fn,cs,1));
	break;
case 73:
	cs=getVector(calc(fn,op+1,e),2);
	result=iMath.lcm(getNum(fn,cs,0),getNum(fn,cs,1));
	break;
case 74:
cs=getVector(calc(fn,op+1,e),2);
result=getNum(fn,cs,0)%getNum(fn,cs,1);
break;
case 76:
	result=iMath.doubleFact(double_calc(fn,s,op));
break;
case 77:
result=double_calc(fn,s,op)/100;
break;
case 78:
cs = getVector(calc(fn,op+1,e),3);
double start1 =getNum(fn,cs,1);
double end1 =getNum(fn,cs,2);
result=1;
for(double i=start1;i<=end1;i++){
var_value.setElementAt(new Double(i),0);
result*=getNum(fn,cs,0);
}
break;
case 79:
cs = getVector(calc(fn,op+1,e),3);
double x0=getNum(fn,cs,1);
var_value.setElementAt(new Double(x0-1E-8),f(fn,((Integer)cs.elementAt(2)).intValue())-10000);
double f0=getNum(fn,cs,0);
var_value.setElementAt(new Double(x0+1E-8),f(fn,((Integer)cs.elementAt(2)).intValue())-10000);
result=(getNum(fn,cs,0)-f0)*5E7;
break;
case 80:
	cs = getVector(calc(fn,op+1,e),3);
	double x=getNum(fn,cs,1);
	double x1;
double fx0;
int i=0;
	do{
		i++;
		x1=x;
		var_value.setElementAt(new Double(x),f(fn,((Integer)cs.elementAt(2)).intValue())-10000);
	fx0=getNum(fn,cs,0);
		var_value.setElementAt(new Double(x+1E-6),f(fn,((Integer)cs.elementAt(2)).intValue())-10000);
		double diff=(getNum(fn,cs,0)-fx0)*1E6;
		x-=fx0/diff;
	}while(fx0!=0&&Math.abs((x-x1)/x1)>1E-15&&i<=1000);
	if(i<=1000){
		result=x;
	}else{
		result=i;
	}
	break;
case 81:
double xx=double_calc(fn,s,op);
result=xx*xx;
break;
case 82:
double xxx=double_calc(fn,s,op);
result=xxx*xxx*xxx;
break;
default:
if(o>=100000){
Vector fw=(Vector)func_wA.elementAt(o-100000);
int n=((Integer)fw.elementAt(fw.size()-1)).intValue();
if(n==-1){
args=getVector(calc(fn,op+1,e));
}else{
if(n>1){
args=getVector(calc(fn,op+1,e),n);
}else{
args.removeAllElements();
args.addElement(new Double(double_calc(fn,op+1,e)));
args.addElement(new Double(0));
}
}
args.addElement(new Integer(fn));
result=double_calc(o-100000,0,fw.size()-1);
}else{
if(o>=10000){
	result = ((Double)var_value.elementAt(o-10000)).doubleValue();
	}else{
throw new RuntimeException("未实现计算或逻辑异常，请联系作者:"+String.valueOf(o));
}
}
}
num_stack.addElement(new Double(result));
return num_stack.size()-1;
//throw new RuntimeException();
}
double double_calc(int fn,int s,int e){
int i=calc(fn,s,e);
if(i<0){
throw new RuntimeException("参数不合法");
}
return ((Double)num_stack.elementAt(i)).doubleValue();
}

int f(int fn,int i){
if(fn==-1){		
return ((Integer)f.elementAt(i)).intValue();
	}
return ((Integer)((Vector)func_wA.elementAt(fn)).elementAt(i)).intValue();
}
int level(int b){
if(level.containsKey(new Integer(b))){
return Integer.parseInt(level.get(new Integer(b)).toString());
}
if(b>=100000){
return 4;
}
if(b>=10000){
return 1;
}
return 0;
}
Vector getVector(int i,int n){
Vector r=getVector(i);
if(r.size()!=n+1){
throw new RuntimeException("参数不合法");
}
return r;
}
Vector getVector(int i){
if(i>=0){
throw new RuntimeException("参数不合法");
}
return (Vector)num_stack.elementAt(-i-1);
}
double getNum(int fn,Vector v,int i){
//try{
return double_calc(fn,Integer.parseInt(v.elementAt(i).toString()),Integer.parseInt(v.elementAt(i+1).toString())-1);
/*}catch(Exception e){
throw new RuntimeException(v.elementAt(i).toString()+" "+v.elementAt(i+1).toString());
}*/

}
int orderCode(String b){
if(order.containsKey(b)){
return Integer.parseInt(order.get(b).toString());
}
if(var_name.containsKey(b)){
return Integer.parseInt(var_name.get(b).toString())+10000;
}
if(func_name.containsKey(b)){
return Integer.parseInt(func_name.get(b).toString())+100000;
}
return 0;
}
Vector wordAnalyze(String a){
a=replace(replace(replace(replace(a,"}else{",","),"}while(",","),"){",","),"}\n",");").replace('{','(').replace('}', ')');
Vector v=new Vector();
for(int i=0;i<a.length();i++){
String b=a.substring(i,i+1);
if(b.equals("\n")|b.equals(" ")){
	continue;
}
if(isNum(b)|b.equals("(")|b.equals(")")|b.equals(",")){
v.addElement(new Integer(code(b)));
continue;
}
if(b.equals("E")){
v.addElement(new Integer(69));
if(a.substring(i+1,i+2).equals("+")||a.substring(i+1,i+2).equals("-")){
v.addElement(new Integer(code(a.substring(i+1,i+2))));
i++;
}
continue;
}
StringBuffer o=new StringBuffer("");
if(isAlpha(b)){
while(isAlpha(b)){
o.append(b);
i++;
if(i>=a.length()||o.length()>=oALenMax){
break;
}
b=a.substring(i,i+1);
}
String oo=o.toString();
while(orderCode(o.toString())==0){
	if(o.length()<=0){
		throw new RuntimeException("cmd "+oo+" cannot recogize");
	}
	o.deleteCharAt(o.length()-1);
i--;
}
v.addElement(new Integer(orderCode(o.toString())));
i--;
continue;
}
do{
o.append(b);
i++;
if(i>=a.length()||o.length()>=oPLenMax){
break;
}
b=a.substring(i,i+1);
}while(!isAlpha(b));
String oo=o.toString();
while(orderCode(o.toString())==0){
	if(o.length()<=0){
		throw new RuntimeException("运算指令 "+oo+" 不能识别");
	}
o.deleteCharAt(o.length()-1);
i--;
}
v.addElement(new Integer(orderCode(o.toString())));
i--;
}
return v;
}
boolean isAlpha(String b){
return (code(b)>64&&code(b)<91)||(code(b)>96&&code

(b)<123);
}
public boolean isNum(String b){
return (code(b)>47&&code(b)<58)||b.equals(".");
}
public boolean isVar(String b){
if(b.equals("")){
return false;
}
for(int i=0;i<b.length();i++){
if(!isAlpha(b.substring(i,i+1))){
return false;
}
}
return true;
}

public static int code(String b){
try{
byte[] r=b.getBytes("utf-8");
switch(r.length){
case 1:
return (int)(r[0]);
case 3:
return (int)(256+r[2]+(256+r[1])*256+(256+r[0])*65536);
}
return -1;
}catch(Exception e){
return -1;
}
}
public static int[] VectorToIntArray(Vector v){
int[] r=new int[v.size()];
for(int k=0;k<v.size();k++){
r[k]=Integer.parseInt(v.elementAt(k).toString());
}
return r;
}

public String calculate(String a){
System.gc();
if(a.length()>=2){
if(a.substring(1,2).equals("#")){
switch(code(a.substring(0,1))){
case 66:
	f=wordAnalyze(a.substring(2));
	ans=double_calc(-1,0,f.size());
if(ans==1){return "对";}
if(ans==0){return "错";}
return String.valueOf(ans);
case 67:
	f=wordAnalyze(a.substring(2));
	return chr((int)double_calc(-1,0,f.size()));
case 68:
	isDeg=true;
	return "角度制";
case 70:
String[] s =split(a.substring(2)," ");
if(isVar(s[0])&&orderCode(s[0])==0){
Vector fw=wordAnalyze(s[2]);
if(s[1].equals("?")){
fw.addElement(new Integer(-1));
}else{
fw.addElement(new Integer(Integer.parseInt(s[1])));
}
func_name.put(s[0],new Integer(func_wA.size()));
func_wA.addElement(fw);
return "函数定义成功";
}
throw new RuntimeException("函数名不合法或冲突");
case 82:
	isDeg=false;
	return "弧度制";
case 86:
if(isVar(a.substring(2))&&orderCode(a.substring(2))==0){
var_name.put(a.substring(2),new Integer(var_value.size()));
var_value.addElement(new Double(0.0));
oALenMax=Math.max(oALenMax,a.substring(2).length());
return "变量定义成功";
}else{
return "变量名不合法或冲突";
}
case 85:
return String.valueOf(code(a.substring(2)));
case 79:
return String.valueOf(orderCode(a.substring(2)));
case 76:
return String.valueOf(level(orderCode(a.substring(2))));
case 87:
	int[] i=VectorToIntArray(wordAnalyze(a.substring(2)));
	String s1="";
	for(int k=0;k<i.length;k++){
		s1+=String.valueOf(i[k])+" ";
	}
	return s1;
case 102:
	int[] i2=VectorToIntArray((Vector)func_wA.elementAt(((Integer)func_name.get(a.substring(2))).intValue()));
	String s2="";
	for(int k=0;k<i2.length;k++){
		s2+=String.valueOf(i2[k])+" ";
	}
	return s2;
case 116:
long t0=System.currentTimeMillis();
f=wordAnalyze(a.substring(2));
ans=double_calc(-1,0,f.size());
return "Time:"+String.valueOf(System.currentTimeMillis()-t0)+"\n"+String.valueOf(ans);
case 114:
	return replace(replace(replace(replace(a,"}else{",","),"}while(",","),"){",","),"}",");").replace('{','(').replace('\n','\u0000').replace(' ','\u0000');
default:
return "unknown";
}
}
}
f=wordAnalyze(a);
ans=double_calc(-1,0,f.size());
long answer=(long)ans;
if(ans==(double)answer){
return String.valueOf(ans)+"\n整数形式:"+String.valueOf(answer);
}
if(ans==Math.floor(ans)){
return String.valueOf(ans);
}
long[] FENGSHU=iMath.toFraction(ans);
if(FENGSHU.length==2){
return String.valueOf(ans)+"\n分数形式:"+String.valueOf(FENGSHU[0])+"/"+String.valueOf(FENGSHU[1]);
}else{
return String.valueOf(ans)+"\n带分数形式:"+String.valueOf(FENGSHU[0])+"+"+String.valueOf(FENGSHU[1])+"/"+String.valueOf(FENGSHU[2]);
}
}

double booleanToDouble(boolean b){
	if(b){return 1.0;}
	return 0.0;
}
public static String chr(int c){
byte[] b={(byte)c};
try{
	return new String(b,"utf-8");
}catch(Exception e){
	return "";
}
}

public static String[] split(String str, String separator){
Vector v = new Vector();
while (str.indexOf(separator)>=0) {
v.addElement(str.substring(0,str.indexOf(separator)));
str=str.substring(str.indexOf(separator)+1);
}
v.addElement(str);
String[] s=new String[v.size()];
v.copyInto(s);
return s;
}
public static void connectVector(Vector a,Vector b){
	for(int i=0;i<b.size();i++){
		a.addElement(b.elementAt(i));
	}
}
public static String replace(String a,String b,String c){
	StringBuffer sb=new StringBuffer(a);
	int i=a.indexOf(b);
	while(i>=0){
		sb=sb.delete(i, i+b.length()).insert(i, c);
		i=sb.toString().indexOf(b);
	}
	return sb.toString();
}
public void setVar(String var,double value){
	var_value.setElementAt(new Double(value),((Integer)var_name.get(var)).intValue());
}
public void word_analyze(String s){
f=wordAnalyze(s);
}
public double double_calc(){
ans=double_calc(-1,0,f.size());
return ans;
}
public double varValue(String varName){
	return ((Double)var_value.elementAt(((Integer)var_name.get(varName)).intValue())).doubleValue();
}
}