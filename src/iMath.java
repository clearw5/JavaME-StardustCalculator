import java.util.Random;
public class iMath {
 public static final double ln10=2.302585092994044;
 public static final double PI=3.14159265358979323;
 public static final double  E=2.71828182845904523;
 public static double exp(double x){
 double s=1.0;
 double m=1.0;
 double i=1.0;
 double s0;
 do{
 s0=s;
 m*=x/i;
 s=s+m;
 i++;
 }
while(Math.abs(s-s0)/s0>1E-17);
 return s;
 }
 public static double arctan(double x,boolean isDeg){
 double s=x;
 double m=x;
 double i=3.0;
 double s0;
 double sgn=1;
 do{
 s0=s;
 m*=x*x;
 sgn=-sgn;
 s=s+m/i*sgn;
 i+=2;
 }
while(i<1000&&Math.abs((s-s0)/s0)>1E-17);
 if(isDeg){
	 return s/PI*180;
 }
 return s;
 }

public static double log(double m,double base) {
 return ln(m)/ln(base);
 }
 public static double pow(double a,double b){
	 if(a==0&&b==0){
		 return Double.NaN;
	 }
	 if(isInt(b)&&Math.abs(b)<=100){
		 double s=1;
		 for(int i=1;i<=b;i++){
			 s*=a;
		 }
		 if(b<0){
			 s=1/s;
		 }
		 return s;
	 }
 if(a<0){
if(isInt(b)){
if(isInt(b/2)){
return exp(b*ln(-a));
}else{
return -exp(b*ln(-a));
}
}else{
long[] s=toFraction(fpart(b));
if(isInt(((double)s[1])/2)){
return Double.NaN;
}else{
if(isInt(((double)s[0])/2)){
return exp(b*ln(-a));
}else{
return -exp(b*ln(-a));
}
}
}
}
return exp(b*ln(a));
 }
 public static double[] sciForm(double d){
 String s=String.valueOf(d);
 double n=0;
 int i=s.indexOf("E");
 if(i>=0){
n=Double.parseDouble(s.substring(i+1));
 s=s.substring(0,i-1);
 }
 i=s.indexOf(".");
 StringBuffer sb=new StringBuffer(s);
 if(i>=0){
 n+=i-1;
 sb=sb.deleteCharAt(i);
 }
else{
 n+=s.length()-1;
 }
if(sb.charAt(0)=='+'||sb.charAt(0)=='-'){
while(sb.length()>1&&sb.charAt(1)=='0'){
sb=sb.deleteCharAt(1);
 n--;
 }
 }
else{
 while(sb.charAt(0)=='0'){
sb=sb.deleteCharAt(0);
 n--;
 }
 }
 while(sb.charAt(sb.length()-1)=='0'){
sb=sb.deleteCharAt(sb.length()-1);
 }
 double m=Double.parseDouble(sb.insert(1, ".").toString());
 double[] r={m,n};
 return r;
 }
 public static double artanh(double x){
 double s=x;
 double m=x;
 double i=1;
 double s0;
 do{
 s0=s;
 m*=x*x;
 i+=2;
 s+=m/i;
 }
while(Math.abs((s-s0)/s0)>1E-17);
 return s;
 }
public static double arsinh(double x){
return ln(x+Math.sqrt(x*x+1));
}
public static double arcosh(double x){
return ln(x+Math.sqrt(x*x-1));
}
 public static double ln(double x){
 if(x==0){
 return Double.NEGATIVE_INFINITY;
 }
 if(x<0){
 return Double.NaN;
 }
 double[] s=sciForm(x);

if(s[0]>3.162){

s[1]++;

s[0]=s[0]/10; }

double r=2*artanh((s[0]-1)/(s[0]+1));

r+=s[1]*ln10;

return r;
}
 public static double arcsin(double x,boolean isDeg)
 {
if(x*x==1&&x>0){return PI/2;}
if(x*x==1&&x<0){return -PI/2;}
	 return arctan(x/Math.sqrt(1-x*x),isDeg);
 }
 public static double arccos(double x,boolean isDeg){
	 return arctan(Math.sqrt(1-x*x)/x,isDeg);
 }
public static double arccot(double x,boolean isDeg){
	 return arctan(1/x,isDeg);
 }
  public static double sin(double x,boolean isDeg){
	 if(isDeg){
		 x=Math.toRadians(x);
	 }
	 return Math.sin(x);
 }
 public static double cos(double x,boolean isDeg){
	 if(isDeg){
		 x=Math.toRadians(x);
 }
	 return Math.cos(x);
 }
 public static double tan(double x,boolean isDeg){
	 if(isDeg){
		 x=Math.toRadians(x);
	 }
	 return Math.tan(x);
 }
 public static double cot(double x,boolean isDeg){
	 if(isDeg){
		 x=Math.toRadians(x);
	 }
	 return 1/Math.tan(x);
 }

 public static int ranInt(int a,int b){
	 if(a>b){
		 int tmp=a;
		 a=b;
		 b=tmp;
	 }
	 return a+(int)Math.floor(new Random().nextDouble()*(b-a+1)/0.9999999999999999);
 }
 public static double sinh(double x){
	 return (exp(x)-exp(-x))/2;
 }
 public static double cosh(double x){
	 return (exp(x)+exp(-x))/2;
 }
 public static double tanh(double x){
	 return sinh(x)/cosh(x);
 }
 public static double fact(double x){
	 double r=1;
	 while(x>0){
		 r*=x;
		 x--;
	 }
	 return r;
	 }
 public static double doubleFact(double x){
	 double r=1;
	 while(x>0){
		 r*=x;
		 x-=2;
	 }
	 return r;
	 }
public static long[] toFraction(double x){
if(x==Math.floor(x)){
throw new RuntimeException("Cannot convey to Fraction");
}
boolean isNeg=(x<0);
x=Math.abs(x);
long[] r;
if(x<=1){
r=toFrac(x);
}else{
double y=Math.floor(x);
r=toFrac(x-y);
double fz=r[0]+r[1]*y;
if(fz>1E+18){
long[] tmp={(long)y,r[0],r[1]};
r=tmp;
}else{
long[] tmp={(long)fz,r[1]};
r=tmp;
}
}
if(isNeg){r[0]=-r[0];}
return r;
}
public static long[] toFrac(double x){
if(x==Math.floor(x)){
throw new RuntimeException("Cannot convey to Fraction");
}
x=x*1E+14;
double m=x;
double n=9.99999999999999E13;
double t;
double y=n;
do{
t=n%m;
n=m;
m=t;
}while(t/y>1E-12);
long[] r={(long)(x/n),(long)(y/n)};
return r;
}
public static boolean isInt(double x){
return x==Math.ceil(x);
}
public static double fpart(double x){
x=Math.abs(x);
return x-Math.floor(x);
}
public static double cbrt(double x){
	if(x==0){
		return 0;
	}
	double y=x/3;
	double y0;
	do{
		y0=y;
		y=(2*y*y*y+x)/(3*y*y);
	}while(Math.abs((y-y0)/y0)>=1E-15);
	return y;
}
public static double gcd(double x,double y){
	if(isInt(x)&&isInt(y)){
		double mod;
		do{
			mod=x%y;
			x=y;
			y=mod;
		}while(Math.abs(mod/x)>=1E-14);
		return x;
	}
	throw new IllegalArgumentException("");
}
public static double lcm(double x,double y){
	return x*y/gcd(x,y);
}
}
