	import java.util.*;
	import java.io.*;
public class BigDecimalScript {


	int oALenMax;
	int oPLenMax;
	int limitDig;
	BigDecimal ans;
	Hashtable var_name,order,level,func_name;
	Vector var_value,f,func_wA,args,num_stack;
	boolean isDeg;

		public BigDecimalScript() {
			limitDig=10;
			isDeg=false;
	ans=new BigDecimal();
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
	 }
	else{
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
	BigDecimal result = new BigDecimal();
	//Vector cs;
	switch(o){
	case 0:
	try{
	StringBuffer sb=new StringBuffer();
	for(int i=s;i<e;i++){
	sb.append(chr(f(fn,i)));
	}
	result = new BigDecimal(sb.toString());
	break;
	}catch(Exception exc){
	throw new RuntimeException("hh");
	}
		case 1:
	result = bn_calc(fn,s,op).plus(bn_calc(fn,op+1,e));
	break;
	case 2:
	result = bn_calc(fn,s,op).miner(bn_calc(fn,op+1,e));
	break;
	/*case 1:
	result = bn_calc(fn,s,op).plus(bn_calc(fn,op+1,e));
	break;
	case 2:
	if(op==s){
	result=bn_calc(fn,op+1,e).neg();
	}else{
	result = bn_calc(fn,s,op).miner(bn_calc(fn,op+1,e));
	}
	break;*/
	case 3:
	result = bn_calc(fn,s,op).prod(bn_calc(fn,op+1,e));
	break;

	case 4:
	result = bn_calc(fn,s,op).divideBy(bn_calc(fn,op+1,e),limitDig);
	break;
	case 26:
		result =BigDecimal.exp(bn_calc(fn,op+1,e),limitDig);
		break;
/*	case 11:
	result = booleanToBigDecimal(bn_calc(fn,s,op).equals(bn_calc(fn,op+1,e)));
	break;
	case 12:
	result = booleanToBigDecimal(!bn_calc(fn,s,op).equals(bn_calc(fn,op+1,e)));
	break;
	case 13:
	result = booleanToBigDecimal(!bn_calc(fn,s,op).smallThan(bn_calc(fn,op+1,e)));
	break;
	case 14:
	result = booleanToBigDecimal(!bn_calc(fn,s,op).bigThan(bn_calc(fn,op+1,e)));
	break;
	case 15:
	result = booleanToBigDecimal(bn_calc(fn,s,op).bigThan(bn_calc(fn,op+1,e)));
	break;
	case 16:
	result = booleanToBigDecimal(bn_calc(fn,s,op).smallThan(bn_calc(fn,op+1,e)));
	break;
*/
	case 42:
		result=BigDecimal.fact(bn_calc(fn,s,op));
		break;
/*	case 47:
	if(args.size()==3){
	if(bn_calc(fn,op+1,e).equals(new BigDecimal())){
	result=(BigDecimal)args.elementAt(0);
	}else{
	throw new ArrayIndexOutOfBoundsException();
	}
	}else{
	result=getNum(((Integer)args.elementAt(args.size()-1)).intValue(),args,bn_calc(fn,op+1,e).intValue());
	}
	break;
	case 60:
	cs=getVector(calc(fn,op+1,e),2);
	int sumnum=0;
	BigDecimal one=new BigDecimal(1);
	while(getNum(fn,cs,0).equals(one)){
	getNum(fn,cs,1);
	sumnum++;}
	result=new BigDecimal(sumnum);
	break;
	case 61:
	result=new BigDecimal(args.size()-2);
	break;
	case 78:
	cs = getVector(calc(fn,op+1,e),3);
	int start1 =getNum(fn,cs,1).intValue();
	int end1 =getNum(fn,cs,2).intValue();
	result=new BigDecimal(1);
	for(int i=start1;i<=end1;i++){
	var_value.setElementAt(new Double(i),0);
	result=result.prod(getNum(fn,cs,0));
	}
	break;
	*/
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
	args.addElement(bn_calc(fn,op+1,e));
	args.addElement(new BigDecimal());
	}
	}
	args.addElement(new Integer(fn));
	result=bn_calc(o-100000,0,fw.size()-1);
	}else{
	if(o>=10000){
		result = (BigDecimal)var_value.elementAt(o-10000);
		}else{
	throw new RuntimeException(""+String.valueOf(o));
	}
	}
	}
	num_stack.addElement(result);
	return num_stack.size()-1;
	//throw new RuntimeException();
	}
	BigDecimal bn_calc(int fn,int s,int e){
	int i=calc(fn,s,e);
	if(i<0){
	throw new RuntimeException("  ");
	}
	return (BigDecimal)num_stack.elementAt(i);
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
	throw new RuntimeException("");
	}
	return r;
	}
	Vector getVector(int i){
	if(i>=0){

	throw new RuntimeException(" ");
	}
	return (Vector)num_stack.elementAt(-i-1);
	}
	BigDecimal getNum(int fn,Vector v,int i){
	//try{
	return bn_calc(fn,Integer.parseInt(v.elementAt(i).toString()),Integer.parseInt(v.elementAt(i+1).toString())-1);
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
	while(orderCode(o.toString())==0){
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
	while(orderCode(o.toString())==0){
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
		ans=bn_calc(-1,0,f.size());
	if(ans==new BigDecimal("1")){return "";}
	if(ans.equals(new BigDecimal())){return "";}
	return String.valueOf(ans);
	case 67:
		f=wordAnalyze(a.substring(2));
		return chr(Integer.parseInt(bn_calc(-1,0,f.size()).toString()));
	case 68:
		isDeg=true;
		return "";
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
	return "";
	}
	throw new RuntimeException("");
	case 82:
		isDeg=false;
		return "";
	case 86:
	if(isVar(a.substring(2))&&orderCode(a.substring(2))==0){
	var_name.put(a.substring(2),new Integer(var_value.size()));
	var_value.addElement(new Double(0.0));
	oALenMax=Math.max(oALenMax,a.substring(2).length());
	return "";
	}else{
	return "";
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
case 111:
Runrec.writeRec();
return "record.";
	case 116:
	long t0=System.currentTimeMillis();
	f=wordAnalyze(a.substring(2));
	ans=bn_calc(-1,0,f.size());
	return "Time:"+String.valueOf(System.currentTimeMillis()-t0)+"\n"+String.valueOf(ans);
	case 114:
return "runrec:\n"+Runrec.rec();
	case 78:
		limitDig=Integer.parseInt(a.substring(2));
		return "SetLimitDig succeed";
	default:
	return "unknown";
	}
	}
	}
	f=wordAnalyze(a);
	ans=bn_calc(-1,0,f.size());
	return ans.toString();
	}

	BigDecimal booleanToBigDecimal(boolean b){
		if(b){return new BigDecimal("1");}
		return new BigDecimal();
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
	public BigDecimal bn_calc(){
	ans=bn_calc(-1,0,f.size());
	return ans;
	}
	public double varValue(String varName){
		return ((Double)var_value.elementAt(((Integer)var_name.get(varName)).intValue())).doubleValue();
	}
/*	public static BigDecimal fact(BigDecimal bn){
		if(bn.smallThan(new BigDecimal())){
			throw new IllegalArgumentException("阶乘只能是正整数");
		}
		int n=Integer.parseInt(bn.toString());
		BigDecimal b=new BigDecimal(1);
		for(int i=2;i<=n;i++){
			b=b.prod(new BigDecimal(i));
		}
		return b;
	}
	}

*/

}
