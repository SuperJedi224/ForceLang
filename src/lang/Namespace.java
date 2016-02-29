package lang;
import lang.exceptions.NamespaceNotFoundException;

public class Namespace extends FObj{
	public final String name;
	public final String fullName;
	public Namespace(String name,Namespace _super){
		this.name=name;
		this.fullName=_super==null?this.name:_super.fullName+"."+this.name;
		if(_super!=null){
			_super.set(name,this);
			_super.immutableFields.add(name);
		}
	}
	public void setVariable(String name,FObj value){
		set(name,value);
	}
	public void setConstant(String name,FObj value){
		set(name,value);
		immutableFields.add(name);
	}
	public String toString(){
		return fullName;
	}
	public static boolean exists(String path){
		try{
			String[]a=path.split("\\.");
			Namespace n=ForceLang.root;
			for(int i=0;i<a.length;i++){
				n=n.getSubNamespace(a[i]);
			}
			return true;}
			catch(Exception e){
				return false;
						
			}
	}
	public void setMethod(String name,java.util.function.Function<String,FObj> value){
		setConstant(name,new Function(value));
	}
	public static Namespace byName(String fullname){
		try{
		String[]a=fullname.split("\\.");
		Namespace n=ForceLang.root;
		for(int i=0;i<a.length;i++){
			n=n.getSubNamespace(a[i]);
		}
		return n;}
		catch(Exception e){
			int i=fullname.lastIndexOf(".");
			return new Namespace(fullname.substring(i+1),byName(fullname.substring(0,i)));
					
		}
	}
	public Namespace getSubNamespace(String name){
		FObj o=get(name);
		if(o!=null&&o instanceof Namespace){
			return (Namespace)o;
		}else{
			throw new NamespaceNotFoundException(this.fullName+"."+name);
		}
	}
}
