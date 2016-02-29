package lang;


public class Function extends FObj{
	private final java.util.function.BiFunction<String,FObj,FObj>f;
	public FObj apply(String s,FObj o){
		return f.apply(s,o);
	};
	public Function(java.util.function.Function<String,FObj>f){
		this((a,b)->f.apply(a));
	}
	public Function(java.util.function.BiFunction<String,FObj,FObj>f){
		this.f=f;
		this.setImmutable();
	}
}
