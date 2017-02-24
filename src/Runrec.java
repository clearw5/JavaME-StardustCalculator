import java.io.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
public class Runrec{
	public static boolean isPC=true;
static StringBuffer rec=new StringBuffer();
public Runrec(){};
public static void rec(String str){
rec.append(str+"\n");
if(isPC){
	System.out.println(str);
}
}
public static void rec(int i){
rec(String.valueOf(i));
}
public static void writeRec(){
	if(isPC){
		System.out.println(rec.toString());
	}else{
try{
FileConnection fc =(FileConnection)Connector.open("file:///sdcard/runrec.txt",Connector.READ_WRITE);
if(fc.exists()){
fc.delete();}
fc.create();
DataOutputStream dos=fc.openDataOutputStream();
dos.write(rec.toString().getBytes());
dos.close();
fc.close();
}catch(Exception e){
throw new RuntimeException(e.toString());
}
}
}
public static String rec(){
return rec.toString();
}
}