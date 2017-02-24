public class BigNumber{
public StringBuffer num;
public boolean isNeg;
public static BigNumber one=new BigNumber(1);
public static BigNumber zero=new BigNumber();
public int length(){
return num.length();
}
public BigNumber neg(){
return new BigNumber(num,!isNeg);
}
public boolean isNeg(){
return isNeg;
}
public byte num(int index){
return (byte)((byte)num.charAt(index)-48);
}
public void setNum(int index,byte b){
num.setCharAt(index,(char)(b+48));
}
public static byte num(StringBuffer sb,int index){
return (byte)((byte)sb.charAt(index)-48);
}
public static void setNum(StringBuffer sb,int index,byte b){
sb.setCharAt(index,(char)(b+48));
}
public BigNumber(StringBuffer b,boolean n){
num=new StringBuffer(b.toString());
isNeg=n;
}
public BigNumber(int i){
	setNumber(String.valueOf(i));
}
public BigNumber plus(BigNumber bn){
if(bn.isNeg){
if(isNeg){
return neg().plus(bn.neg()).neg();
}else{
return miner(bn.neg());
}
}else{
if(isNeg){
return bn.miner(neg());
}
}
if(length()<bn.length()){
return bn.plus(this);
}
StringBuffer b=new StringBuffer();
byte sum;
byte tmp=0;
for(int i=0;i<bn.length();i++){
sum=(byte)(bn.num(i)+num(i)+tmp);
if(sum>=10){
b.append(sum-10);tmp=1;
}else{
b.append(sum);tmp=0;
}}
if(length()>bn.length()){
b.append(num.toString().substring(bn.length()));
}
if(tmp>0){
if(length()==bn.length()){
b.append(1);
}else{
/*setNum(b,i,(byte)(num(b ,i)+tmp));
}else{
i--;
if(tmp==1){
BigNumber.setNum(b,i,(byte)(num(b,i)+10));
}
}*/
int i=bn.length();
while(i<length()-1){
if(num(i)>=9){
BigNumber.setNum(b,i,(byte)0);
}else{
BigNumber.setNum(b,i,(byte)(num(i)+1));
tmp=0;
break;
}
//BigNumber.setNum(b,i+1,(byte)(num(i+1)+1));
i++;
}
if(tmp>0){
if(num(b,b.length()-1)>=9){
BigNumber.setNum(b,length()-1,(byte)0);
b.append(1);
}else{
BigNumber.setNum(b,b.length()-1,(byte)(num(b,b.length()-1)+1));
}
}
}
}
return new BigNumber(b,false);
}
public BigNumber plus(){
return plus(this);
}
public BigNumber prodByte(byte m){
BigNumber b=new BigNumber();
for(byte i=0;i<m;i++){
b=b.plus(this);
}
return b;
}

public BigNumber prod(byte b){
switch(b){
case 0:
return new BigNumber();
case 1:
return this;
case 2:
return plus();
case 3:
return plus(plus());
case 4:
return plus().plus();
case 5:
return plus(plus().plus());
case 6:
return prod((byte)3).plus();
case 7:
return prod((byte)6).plus(this);
case 8:
return plus().plus().plus();
case 9:
return plus().plus().plus().plus(this);
case 10:
BigNumber b0=plus().plus();
return b0.plus().plus(b0);
default:
throw new RuntimeException();
}
}
public BigNumber prod(BigNumber bn){
BigNumber b=bn.prodByte(num(length()-1));
for(int i=length()-2;i>=0;i--){
b=b.prodByte((byte)10);
b=b.plus(bn.prodByte(num(i)));
}
if(isNeg!=bn.isNeg){
b.isNeg=true;
}
return b;
}
public BigNumber(){
num=new StringBuffer("0");
isNeg=false;
}
public BigNumber miner(BigNumber bn){
if(bn.isNeg){
	if(isNeg){
		return bn.neg().miner(neg());
	}else{
		return plus(bn.neg());
	}
}else{
	if(isNeg){
		return neg().plus(bn).neg();
	}
}
if(bn.bigThan(this)){
	return bn.miner(this).neg();
}StringBuffer b=new StringBuffer();
byte sum;
byte tmp=0;
for(int i=0;i<bn.length();i++){
sum=(byte)(num(i)-bn.num(i)+tmp);
if(sum<0){
b.append(sum+10);tmp=-1;
}else{
b.append(sum);tmp=0;
}}
if(length()>bn.length()){
b.append(num.toString().substring(bn.length()));
}
/*else{
i--;
if(tmp==-1){
BigNumber.setNum(b,i,(byte)(num(b,i)-10));
}
}
while(i<length()-1&&num(i)<0){
BigNumber.setNum(b,i,(byte)9);
BigNumber.setNum(b,i+1,(byte)(num(i+1)-1));
i++;
}*/
if(tmp<0){
int i=bn.length();
while(i<length()){
if(num(i)>=9){
BigNumber.setNum(b,i,(byte)0);
}else{
BigNumber.setNum(b,i,(byte)(num(i)-1));
tmp=0;
break;
}
//BigNumber.setNum(b,i+1,(byte)(num(i+1)+1));
i++;
}
}
	while(b.length()>1&&b.charAt(b.length()-1)=='0'){
		b.deleteCharAt(b.length()-1);
	}
return new BigNumber(b,false);
}
public BigNumber divideBy(BigNumber bn0){
	BigNumber[] r=divideByWithRaminer(bn0);
	return r[0];
}
	public BigNumber[] divideByWithRaminer(BigNumber bn0){
	if(bn0.equals(new BigNumber())){
throw new ArithmeticException("Not a number");
}
BigNumber bn=new BigNumber(bn0.abs());
if(abs().smallThan(bn0)){
	BigNumber[] result={new BigNumber(),new BigNumber()};
	return result;
}
BigNumber b=new BigNumber(abs());
StringBuffer sb=new StringBuffer();
BigNumber[] bnm=new BigNumber[15];
bnm[0]=bn;
int bnm_max=0;
BigNumber r;
int start;
if(bn.length()<=1){
	r=b.subNum(0,1);
	start=1;
	if(r.bigOrEquals(bn)){
		r=new BigNumber();
		start=0;
	}
}else{
r=b.subNum(0,bn.length());
if(r.bigOrEquals(bn)){
	r=b.subNum(0,bn.length()-1);
}
start=r.length();
}
System.out.println(r.toString());
for(int i=start;i<b.length();i++){
try{
	r.append(b.num.charAt(length()-i-1));
System.out.println(r.toString());
}catch(Exception exc){
throw new RuntimeException("error0:"+exc);
}
int k=0;
while(k<9&&r.bigOrEquals(bnm[k])){
k++;
if(k>bnm_max){
try{
bnm[k]=bnm[k-1].plus(bn);
}catch(Exception exc){
throw new RuntimeException("error1:"+exc);
}
bnm_max++;
}
}
sb.insert(0,(char)(k+48));
System.out.println(sb.toString());
if(k>0){
r=r.miner(bnm[k-1]);
}
System.out.println(i);
}
BigNumber[] result={new BigNumber(sb,isNeg()!=bn0.isNeg()),r};
return result;
/*
StringBuffer r=new StringBuffer();
do{
BigNumber a=new BigNumber(new StringBuffer(b.num.toString().substring(length()-bn.length())),false);
if(a.smallThan(bn)){
a=new BigNumber(new StringBuffer(b.num.toString().substring(0,length()-bn.length()-1)),false);
}
int i=0;
BigNumber d=new BigNumber(bn);
BigNumber d0;
do{
d0=new BigNumber(d);
d=d.plus(bn);
i++;
}while(!a.smallThan(d));
b=b.miner(d0.exp(b.length()));
r.insert(0,i);
}while(bn.smallThan(b));
return new BigNumber(r,isNeg()!=bn.isNeg());
*/
}
	public BigDecimal divideBy(BigNumber bn0,int n){
		if(bn0.equals(new BigNumber())){
			throw new ArithmeticException("Not a number");
			}
			BigNumber bn=new BigNumber(bn0.abs());
/*			if(abs().smallThan(bn0)){
				BigNumber[] result={new BigNumber(),new BigNumber()};
				return result;
			}*/
			BigNumber b=new BigNumber(abs());
			StringBuffer sb=new StringBuffer();
			BigNumber[] bnm=new BigNumber[10];
			bnm[0]=bn;
			int bnm_max=0;
			BigNumber r;
			int start;
			if(bn.length()<=1||this.length()<=1){
				r=b.subNum(0,1);
				start=1;
				if(r.bigOrEquals(bn)){
					r=new BigNumber();
					start=0;
				}
			}else{
			r=b.subNum(0,bn.length());
			if(r.bigOrEquals(bn)){
				r=b.subNum(0,bn.length()-1);
			}
			start=r.length();
			}
			//Runrec.rec(r.toString());
			for(int i=start;i<n;i++){
				if(i<b.length()){
				r.append(b.num.charAt(length()-i-1));
				}else{
					r.append(0);
				}
			//Runrec.rec(r.toString());
			int k=0;
			while(k<9&&r.bigOrEquals(bnm[k])){
			k++;
			if(k>bnm_max){
			bnm[k]=bnm[k-1].plus(bn);
			bnm_max++;
			}
			}
			sb.insert(0,(char)(k+48));
						//Runrec.rec(sb.toString());
			if(k>0){
			//Runrec.rec(bnm[k-1].toString());
			r=r.miner(bnm[k-1]);
			}
			//Runrec.rec(r.toString());
			if(r.equals(BigNumber.zero)){
				break;
			}
						//Runrec.rec(i);
			}
			BigDecimal result=new BigDecimal(sb,new BigNumber(length()-bn0.length()),isNeg()!=bn0.isNeg());
			return result;	
	}
public boolean bigThan(BigNumber bn){
if(isNeg!=bn.isNeg()){
return bn.isNeg();
}
boolean b=false;
	if(length()==bn.length()){
		for(int i=length()-1;i>=0;i--){
			if(num(i)!=bn.num(i)){
				b= num(i)>bn.num(i);
				break;
			}
		}
	}else{
	b=length()>bn.length();
	}
	if(isNeg){
	b=!b;
	}
	return b;
}
public boolean smallThan(BigNumber bn){
if(isNeg!=bn.isNeg()){
return isNeg();
}
boolean b=false;
	if(length()==bn.length()){
		for(int i=length()-1;i>=0;i--){
			if(num(i)!=bn.num(i)){
				b= num(i)<bn.num(i);
				break;
			}
		}
	}else{
	b=length()<bn.length();
	}
	if(isNeg){
	b=!b;
	}
	return b;
}
public boolean bigOrEquals(BigNumber bn){
return !smallThan(bn);
}
public boolean equals(BigNumber bn){
return isNeg==bn.isNeg()&&num.toString().equals(bn.num.toString());
}
public void append(char c){
	if(equals(new BigNumber())){
		num.setCharAt(0, c);
		return;
		}
		num.insert(0,c);
}
public void append(int k){
append((char)(k+48));
}
public BigNumber subNum(int index,int endIndex){
return new BigNumber(new StringBuffer(num.toString().substring(num.length()-endIndex,num.length()-index)),false);
}
public BigNumber exp(int i){
StringBuffer s=new StringBuffer(num.toString());
while((i--)>0){
s.insert(0,0);
}
return new BigNumber(s,isNeg);
}
public BigNumber abs(){
return new BigNumber(new StringBuffer(num.toString()),false);
}
public BigNumber(BigNumber bn){
num=new StringBuffer(bn.num.toString());
isNeg=bn.isNeg();
}
public BigNumber(String str){
	setNumber(str);
}
public void setNumber(String str){
	StringBuffer s=new StringBuffer(str);
	isNeg=false;
	if(s.charAt(0)=='-'){
		isNeg=true;
		s.deleteCharAt(0);
	}
	while(s.length()>1&&s.charAt(0)=='0'){
		s.deleteCharAt(0);
	}
	num=new StringBuffer();
	for(int i=s.length()-1;i>=0;i--){
		byte n=(byte)((byte)s.charAt(i)-48);
		if(n<0||n>9){throw new NumberFormatException();}
	num.append(n);
}
}
public static StringBuffer plus(StringBuffer sb1,StringBuffer sb2){
	return new BigNumber(sb1,false).plus(new BigNumber( sb2,false)).num;
}
public static StringBuffer miner(StringBuffer sb1,StringBuffer sb2){
BigNumber r=new BigNumber(sb1,false).miner(new BigNumber( sb2,false));
StringBuffer s=r.num;
if(r.isNeg()){
s.insert(0,'-');
}
return s;
}
public static StringBuffer prod(StringBuffer sb1,StringBuffer sb2){
	return new BigNumber(sb1,false).prod(new BigNumber( sb2,false)).num;
}
public static StringBuffer divide(StringBuffer sb1,StringBuffer sb2,int n){
	return new BigNumber(sb1,false).divideBy(new BigNumber( sb2,false),n).num;
}
public int intValue(){
	return Integer.parseInt(toString());
}
public String toString(){
StringBuffer r=new StringBuffer();
if(isNeg){
r.append('-');
}
for(int i=num.length()-1;i>=0;i--){
r.append(num.charAt(i));
}
return r.toString();
}
}