package lang;


public class Function extends FObj{
	private final java.util.function.Function<String,FObj>f;
	public FObj apply(String s){
		return f.apply(s);
	};
	public Function(java.util.function.Function<String,FObj>f){
		this.f=f;
	}
}
