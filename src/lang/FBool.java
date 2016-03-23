package lang;

public class FBool extends FObj {
	private FBool(String t){
		Namespace.byName("root").setConstant(t, this);;
		set("toString",new Function(a->new FString(t)));
		this.setImmutable();
	}
	public static final FBool TRUE=new FBool("TRUE");
	public static final FBool FALSE=new FBool("FALSE");
	public boolean equals(Object o){
		return this==o;
	}
	public static FBool valueOf(boolean b){
		return b?TRUE:FALSE;
	}
	public static FBool valueOf(FObj o){
		if(o==null)return FALSE;
		return valueOf(o.isTruthy());
	}
	public boolean isTruthy(){
		return this==TRUE;
	}
	
}
