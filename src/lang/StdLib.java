package lang;

import java.io.File;
import java.math.BigInteger;
import java.text.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lang.exceptions.*;
import lang.exceptions.IllegalArgumentException;
import lang.graphics.FCanvas;

import java.lang.Exception;

public class StdLib {
	private static Random rand=new Random();
	public static void load(){
		Namespace root=Namespace.byName("root");
		Namespace math=Namespace.byName("root.math");
		Namespace number=Namespace.byName("root.number");
		Namespace io=Namespace.byName("root.io");
		Namespace random=Namespace.byName("root.random");
		Namespace string=Namespace.byName("root.string");
		Namespace gui=Namespace.byName("root.gui");
		Namespace timer=Namespace.byName("root.timer");
		Namespace graphics=Namespace.byName("root.graphics");
		Namespace datetime=Namespace.byName("root.datetime");
		string.setMethod("builder",a->{
			FStringBuilder s=new FStringBuilder();
			if(a!=null)s.add(ForceLang.parse(a));
			return s;
			
		});
		string.setMethod("rev",a->{
			if(a==null)throw new IllegalInvocationException("string.rev is not nulladic.");
			FObj o=ForceLang.parse(a);
			if(o instanceof FString){
				return new FString(new StringBuilder(o.toString()).reverse().toString());
			}else if(o instanceof FStringBuilder){
				FStringBuilder s=new FStringBuilder();
				s.add(new FString(new StringBuilder(o.toString()).reverse().toString()));
				return s;
			}else{
				throw new IllegalArgumentException("string.rev cannot be called with this argument.");
			}
			
		});
		string.setMethod("char",a->{
			if(a==null)throw new IllegalInvocationException("string.char is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{return new FString(""+(char)((FNum)o).longValue());}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		string.setMethod("toCharArray",a->{
			if(a==null)throw new IllegalInvocationException("string.toCharArray is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{String s=((FString)o).toString();
			FList l=new FList();
			for(char c:s.toCharArray()){
				l.append(new FNum((int)c));
			}
			return l;
			}catch(Exception e){
				throw new IllegalArgumentException("Expected String.");
			}
		});
		random.setMethod("rand",a->{
			if(a==null)a="80";
			try{
				FNum b=(FNum)ForceLang.parse(a);
				if(b.denominator().equals(BigInteger.ONE)){
					StringBuilder s=new StringBuilder();
					for(int i=0;i<b.numerator().intValue();i++){
						s.append(rand.nextInt(2));
					}
					return new FNum(new BigInteger(s.toString(),2),BigInteger.valueOf(2).pow(s.length()));
				}else{
					throw new IllegalArgumentException(b+" is not an integer.");
				}
			}catch(Exception e){
				if(e instanceof IllegalArgumentException)throw e;
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		random.setMethod("seed",a->{
			if(a==null)throw new IllegalInvocationException("random.seed is not nulladic.");
			FObj o=ForceLang.parse(a);
			if(o instanceof FNum){
				if(o.equals(new FNum(((FNum)o).longValue()))){
					long l=((FNum)o).longValue();
					rand=new Random(l);
				}else{
					throw new IllegalArgumentException(o+" is not an integer, or is out of range.");
				}
			}else{
				throw new IllegalArgumentException("Expected Number.");
			}
			return null;
		});
		math.setConstant("pi",new FNum("245850922/78256779"));
		math.setConstant("e",new FNum("268876667/98914198"));
		math.setConstant("phi",new FNum("63245986/39088169"));
		math.setMethod("sqrt",a->{
			if(a==null)throw new IllegalInvocationException("math.sqrt is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{return ((FNum)o).sqrt();}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		math.setMethod("ln",a->{
			if(a==null)throw new IllegalInvocationException("math.ln is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{return ((FNum)o).ln();}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		math.setMethod("log",a->{
		    if(a==null)throw new IllegalInvocationException("math.log is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{return ((FNum)o).ln().multiply(new FNum("4885743/11249839"));}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		math.setMethod("floor",a->{
			if(a==null)throw new IllegalInvocationException("math.floor is not nulladic.");
			FObj o=ForceLang.parse(a);
			try{return ((FNum)o).floor();}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}
		});
		io.setMethod("write",a->{
			if(a==null)throw new IllegalInvocationException("io.write is not nulladic.");
			System.out.print(ForceLang.stringify(ForceLang.parse(a)));
			return null;
		});
		io.setMethod("writeln",a->{
			System.out.println(a==null?"":ForceLang.stringify(ForceLang.parse(a)));
			return null;
		});
		io.setMethod("readln",a->{
			return new FString(ForceLang.input);
		});
		io.setMethod("readnum",a->{
			return new FNum(ForceLang.input.nextLine());
		});
		io.setMethod("freader",a->{
			Object o=ForceLang.parse(a);
			if(o instanceof FString){
				return new FileReader(""+o);
			}else{
				throw new IllegalArgumentException("Expected String");
			}
		});
		root.setConstant("nil",null);
		root.setMethod("set",a->{
			int i=a.indexOf(" ");
			String s2=a.substring(0,i);
			FObj x=ForceLang.parse(a.substring(i+1)),y=Namespace.byName("root");
			if(s2.indexOf("[")!=-1){
				int j=s2.indexOf("[");
				FObj o1=ForceLang.parse(s2.substring(0,j)),o2=ForceLang.parse(s2.substring(j+1,s2.length()-1));
				if(!(o2 instanceof FNum))throw new IllegalArgumentException("Cannot use a non-numeric index");
				if(!(o1 instanceof FList))throw new IllegalInvocationException("Cannot index a non-list");
				int ind=((FNum)o2).intValue();
				List<FObj>l=((FList)o1).list;
				while(l.size()<ind+1)l.add(null);
				l.set(ind,x);
				
			}else{String[]nms=s2.split("\\.");
			for(int z=0;z<nms.length-1;z++){
				y=y.get(nms[z]);
			}
			y.set(nms[nms.length-1],x);}
			return x;
			
		});
		root.setMethod("exit",a->{
			if(a!=null){
				System.err.println(ForceLang.stringify(ForceLang.parse(a)));
			}
			System.exit(0);
			return null;
		});
		root.setMethod("cons",a->{
			int i=a.indexOf(" ");
			String s="root."+a.substring(0,i),t=s.substring(0,s.lastIndexOf(".")),u=s.substring(1+s.lastIndexOf("."));
			if(!Namespace.exists(t))throw new NamespaceNotFoundException(t);
			Namespace.byName(t).setConstant(u,ForceLang.parse(a.substring(i+1)));
			return null;
		});
		root.setMethod("label",a->{
			if(a.isEmpty())throw new IllegalArgumentException("The empty string is not a valid label.");
			return null;
		});
		root.setMethod("goto",a->{
			for(int i=0;i<ForceLang.prog.size();i++){
				if(ForceLang.prog.get(i).equals("label "+a)){
					ForceLang.iPointer=i;
					return null;
				}
			}
			throw new NoSuchLabelException(a);
		});
		root.setMethod("gotoex",a->{
			a=ForceLang.stringify(ForceLang.parse(a));
			for(int i=0;i<ForceLang.prog.size();i++){
				if(ForceLang.prog.get(i).equals("label "+a)){
					ForceLang.iPointer=i;
					return null;
				}
			}
			throw new NoSuchLabelException(a);
		});
		root.setMethod("require",a->{
			try{Module object=new Module();
			Class<?>clazz=null;
			try{clazz=Class.forName(a);}catch(Exception e){clazz=Class.forName(a+".Main");}
			clazz.getMethod("load",Module.class).invoke(null,object);
			object.setImmutable();
			return object;}catch(Exception e){
				throw new NoSuchModuleException(a);
			}
		});
		root.setMethod("if",a->{
			if(!FBool.valueOf(ForceLang.parse(a)).isTruthy())ForceLang.iPointer++;
			return null;
		});
		root.setMethod("def",a->{
			int i=a.indexOf(" ");
			ForceLang.defs.put(a.substring(0,i),a.substring(i+1));
			return null;
		});
		root.setMethod("undef",a->{
			ForceLang.defs.remove(a);
			return null;
		});
		gui.setMethod("show",a->{
			JFrame j=new JFrame();
			j.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(j,ForceLang.stringify(ForceLang.parse(a)),"",JOptionPane.INFORMATION_MESSAGE);
			return null;
		});
		gui.setMethod("warn",a->{
			JFrame j=new JFrame();
			j.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(j,ForceLang.stringify(ForceLang.parse(a)),"",JOptionPane.WARNING_MESSAGE);
			return null;
		});
		gui.setMethod("prompt",a->{
			JFrame j=new JFrame();
			j.setAlwaysOnTop(true);
			return new FString(JOptionPane.showInputDialog(j, a==null?"":ForceLang.stringify(ForceLang.parse(a))));
		});
		timer.setMethod("new",a->{
			System.err.println("timer.new is deprecated, use datetime.timer instead");
			return new FTimer();	
		});
		datetime.setMethod("timer",a->new FTimer());
		datetime.setMethod("now",a->new FNum(System.currentTimeMillis()));
		datetime.setMethod("toTimeString",a->{
			DateFormat df = DateFormat.getTimeInstance();
			return new FString(df.format(new Date(((FNum)ForceLang.parse(a)).longValue())));
		});
		datetime.setMethod("toDateString",a->{
			DateFormat df = DateFormat.getDateInstance();
			return new FString(df.format(new Date(((FNum)ForceLang.parse(a)).longValue())));
		});
		datetime.setMethod("wait",a->{
			long f=((FNum)ForceLang.parse(a)).longValue();
			long t=System.nanoTime();
			try{Thread.sleep(f);}catch(Exception e){};
			return new FNum((System.nanoTime()-t)/100000);
		});
		graphics.setMethod("canvas",a->{
			FObj o=ForceLang.parse(a);
			if(o instanceof FString){
				try {
					return new FCanvas(ImageIO.read(new File(a.toString())));
				} catch (Exception e) {
					throw new InvalidFileException("Not found, or not an image.");
				}
			}
			return new FCanvas(o);
		});
		number.setMethod("parse",a->new FNum(ForceLang.stringify(ForceLang.parse(a))));
		math.setImmutable();random.setImmutable();io.setImmutable();string.setImmutable();gui.setImmutable();timer.setImmutable();datetime.setImmutable();
		graphics.setImmutable();number.setImmutable();
	}
}
