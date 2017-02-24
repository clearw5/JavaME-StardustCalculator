
public class BigDecimal {
public StringBuffer num;
public BigNumber exp;
public boolean isNeg;
public static BigDecimal one=new BigDecimal("1");
public BigDecimal(String s){
	 BigNumber n=new BigNumber();
	 int i=s.indexOf("E");
	 if(i>=0){
	 n=new BigNumber(s.substring(i+1));
	 s=s.substring(0,i);
	 }
	 i=s.indexOf(".");
	 StringBuffer sb=new StringBuffer(s);
	 if(i>=0){
	 n=n.plus(new BigNumber(i-1));
	 sb=sb.deleteCharAt(i);
	 }
	else{
		n=n.plus(new BigNumber(s.length()-1));
	 }
	if(sb.charAt(0)=='+'||sb.charAt(0)=='-'){
		isNeg=(sb.charAt(0)=='-');
		sb.deleteCharAt(0);
	}
	 while(sb.charAt(0)=='0'){
	sb=sb.deleteCharAt(0);
	n=n.miner(BigNumber.one);
	 }
	 while(sb.charAt(sb.length()-1)=='0'){
	sb=sb.deleteCharAt(sb.length()-1);
	 }
	 num=new BigNumber(sb.toString()).num;;
	 exp=n;
}
public BigDecimal(StringBuffer b,BigNumber e,boolean n){
num=new StringBuffer(b.toString());
exp=e;
isNeg=n;
}
public BigDecimal(){
	num=new StringBuffer("0");
	exp=new BigNumber(0);
	isNeg=false;
}
public BigDecimal(BigDecimal bd){
	num=new StringBuffer(bd.num.toString());
	exp=new BigNumber(bd.exp);
	isNeg=bd.isNeg;
}
public BigDecimal(double d){
	String s=String.valueOf(d);
	BigNumber n=new BigNumber();
	 int i=s.indexOf("E");
	 if(i>=0){
	 n=new BigNumber(s.substring(i+1));
	 s=s.substring(0,i);
	 }
	 i=s.indexOf(".");
	 StringBuffer sb=new StringBuffer(s);
	 if(i>=0){
	 n=n.plus(new BigNumber(i-1));
	 sb=sb.deleteCharAt(i);
	 }
	else{
		n=n.plus(new BigNumber(s.length()-1));
	 }
	if(sb.charAt(0)=='+'||sb.charAt(0)=='-'){
		isNeg=(sb.charAt(0)=='-');
		sb.deleteCharAt(0);
	}
	 while(sb.charAt(0)=='0'){
	sb=sb.deleteCharAt(0);
	n=n.miner(BigNumber.one);
	 }
	 while(sb.charAt(sb.length()-1)=='0'){
	sb=sb.deleteCharAt(sb.length()-1);
	 }
	 num=new BigNumber(sb.toString()).num;;
	 exp=n;	
}
public BigDecimal neg(){
return new BigDecimal(num,exp,!isNeg);
}
public BigDecimal abs(){
	return new BigDecimal(num,exp,false);
}
public int length(){
return num.length();
}
/*public BigNumber subNum(int start,int end){
return new BigNumber*/
public String toString(){
	StringBuffer r=new StringBuffer();
	if(isNeg){
	r.append('-');
	}
	for(int i=num.length()-1;i>=0;i--){
	r.append(num.charAt(i));
	}
	if(r.length()>1){
	r.insert(1, '.');
	}
	r.append("E"+exp.toString());
	return r.toString();
}
public BigNumber exp(){
return exp;
}
public BigDecimal plus(BigDecimal bd){
if(bd.isNeg){
if(isNeg){
return neg().plus(bd.neg()).neg();
}else{
return miner(bd.neg());
}
}else{
if(isNeg){
return bd.miner(neg());
}
}
if(length()-exp().intValue()<bd.length()-bd.exp().intValue()){
return bd.plus(this);
}
int k=bd.length()-bd.exp().intValue()+exp().intValue();
StringBuffer a=new StringBuffer(num.toString().substring(length()-k));
//Runrec.rec(num.toString().substring(length()-k));
//Runrec.rec(k);
int e=Math.max(exp().intValue(),bd.exp().intValue());
StringBuffer r=BigNumber.plus(a,bd.num);
if(r.length()>a.length()){
e++;
}
if(k<length()){
r.insert(0,num.toString().substring(0,length()-k));
//Runrec.rec(num.toString().substring(0,length()-k));
}
return new BigDecimal(r,new BigNumber(e),false);
}
public BigDecimal miner(BigDecimal bd){
if(bd.isNeg){
	if(isNeg){
		return bd.neg().miner(neg());
	}else{
		return plus(bd.neg());
	}
}else{
	if(isNeg){
		return neg().plus(bd).neg();
	}
}
if(length()-exp().intValue()<bd.length()-bd.exp().intValue()){
return bd.miner(this);
}
int k=bd.length()-bd.exp().intValue()+exp().intValue();
StringBuffer a=new StringBuffer(num.toString().substring(length()-k));
int e=Math.max(exp().intValue(),bd.exp().intValue());
StringBuffer r=BigNumber.miner(a,bd.num);
boolean isneg=false;
if(r.charAt(0)=='-'){
isneg=true;
r.deleteCharAt(0);
}
if(k<length()){
	r.insert(0,num.toString().substring(0,length()-k));
}
return new BigDecimal(r,new BigNumber(e),isneg);
}
public BigDecimal prod(BigDecimal bd){
	StringBuffer p=BigNumber.prod(num,bd.num);
	int e=exp.intValue()+bd.exp.intValue();
	if(p.length()>e+1){
		e++;
	}
	return new BigDecimal(p,new BigNumber(e),isNeg||bd.isNeg);
}
public BigDecimal divideBy(BigDecimal bd,int n){
	return new BigDecimal(BigNumber.divide(num,bd.num,n),exp.miner(bd.exp).miner(BigNumber.one),isNeg||bd.isNeg);
}
public static BigDecimal exp(BigDecimal x,int n){
	BigDecimal s=new BigDecimal(1.0);
	BigDecimal m=new BigDecimal(1.0);
	//BigDecimal s0=new BigDecimal(1.0);
	double i=1;
	 do{
	 //s0=new BigDecimal(s);
	 m=m.prod(x.divideBy(new BigDecimal(i),n));
	 Runrec.rec(m.toString());
	 s=s.plus(m);
	 i++;
	 }
	while(i<n);//s.miner(s0).abs().divideBy(s0,n).num.(1E-17);
	 return s;
	 }
public static double fact(double x){
	 double r=1;
	 while(x>0){
		 r*=x;
		 x--;
	 }
	 return r;
	 }
public static BigDecimal fact(BigDecimal x){
	 BigDecimal r=new BigDecimal(1.0);
	 while(Double.parseDouble(x.toString())>0){
		 r=r.prod(x);
		 x=x.miner(BigDecimal.one);
	 }
	 return r;
	 }
}
