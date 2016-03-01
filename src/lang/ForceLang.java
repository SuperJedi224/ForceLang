package lang;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import lang.Namespace;
import lang.StdLib;
import lang.exceptions.*;
import lang.exceptions.IllegalArgumentException;

import java.lang.Exception;


public final class ForceLang{
	public static final Scanner input=new Scanner(System.in);
	public static String stringify(Object o){
		if(o==null)return "<nil>";
		return o.toString();
	}
	public static Namespace root=new Namespace("root",null){{set("root",this);immutableFields.add("root");}};
	public static FObj parse(String x){
		if(x.startsWith("!")){
			FObj o=parse(x.substring(1));
			return FBool.valueOf(!o.isTruthy());
		}
		if(x.equals("{}"))return new FObj();
		if(x.equals("[]"))return new FList();
		if(x.equals("nil"))return null;
		if(x.matches("\"[^\"]+\"")){
			return new FString(x.replaceAll("^.|.$",""));
		}
		if(x.contains(" ")){
			x="root."+x;
			int j=x.indexOf(" ");
			FObj o=parse(x.substring(0,j));
			
			if(o!=null&&o instanceof Function){
				return ((Function)o).apply(x.substring(j+1),parse(x.substring(0,x.substring(0,j).lastIndexOf("."))));
			}else if(o!=null&&o instanceof Module){
				FObj o2=o.get("._invoke");
				if(o2==null||!(o2 instanceof Function))throw new IllegalInvocationException("This module is non-invocable");
				return ((Function)o2).apply(x.substring(j+1),o);
			}else{
				throw new IllegalInvocationException(x.substring(0,j)+" is not a function.");
			}
		}
		if(x.endsWith("]")){
			int i=x.indexOf("[");
			FObj o1=parse(x.substring(0,i)),o2=parse(x.substring(i+1,x.length()-1));
			if(!(o2 instanceof FNum))throw new IllegalArgumentException("Cannot use a non-numeric index"+x.substring(0,i));
			if(!(o1 instanceof FList))throw new IllegalInvocationException("Cannot index a non-list");
			try{return ((FList)o1).list.get(((FNum)o2).intValue());}catch(IndexOutOfBoundsException e){return null;}
		}
		if(x.contains("=")){
			int n=x.indexOf("=");
			return FBool.valueOf(Objects.equals(parse(x.substring(0,n)),parse(x.substring(n+1))));
		}
		if(x.indexOf("\"")==-1&&x.indexOf("+")!=-1){
			String[]t=x.split("\\+");
			FObj o=ForceLang.parse(t[0]);
			for(int i=1;i<t.length;i++)o=o.add(ForceLang.parse(t[1]));
			return o;
		}
		if(x.indexOf("\"")>x.indexOf("+")&&x.indexOf("+")!=-1){
			int i=x.indexOf("+");
			return ForceLang.parse(x.substring(0,i)).add(ForceLang.parse(x.substring(i+1)));
		}
		if(x.lastIndexOf("\"")<x.lastIndexOf("+")){
			int i=x.lastIndexOf("+");
			return ForceLang.parse(x.substring(0,i)).add(ForceLang.parse(x.substring(i+1)));
		}
		try{return new FNum(x);}catch(Exception e){};
		if(x.endsWith("()")){
			x="root."+x;
			x=x.substring(0,x.length()-2);
			FObj o=parse(x);
			if(o==null){throw new IllegalInvocationException("null is not a function.");}
			else if(o instanceof Function){
				return ((Function)o).apply(null,parse(x.substring(0,x.lastIndexOf("."))));
			}else if(o instanceof Module){
				FObj o2=o.get("._invoke");
				if(o2!=null&&o2 instanceof Function){
					return ((Function)o2).apply(null,o);
				}
				throw new IllegalInvocationException("This module is non-invocable.");
			}else{
				throw new IllegalInvocationException(x+" is not a function.");
			}
		}
		String[]nms=x.split("\\.");
		FObj n=Namespace.byName("root");
		for(int k=0;k<nms.length;k++){
			n=n.get(nms[k]);
		}
		return n;
	}
	public static final List<String>prog=new ArrayList<>();
	public static int iPointer;
	public static void main(String[]args) throws FileNotFoundException{
		StdLib.load();
		String fname="";
		if(args.length>0){
			fname=args[0];
		}else{
			fname=input.nextLine();
		}
		
		Scanner freader=new Scanner(new File(fname));
		while(freader.hasNext())prog.add(freader.nextLine().replaceAll("^\\s+",""));
		freader.close();
		iPointer=0;
		while(iPointer<prog.size()){
			parse(prog.get(iPointer));
			iPointer++;
		}
	}
}
