package lang;

public class FStringBuilder extends FObj{
	private final StringBuilder s=new StringBuilder();
	public FStringBuilder(){
		set("toString",new Function(a->{
			return new FString(s.toString());
		}));
		set("append",new Function(a->{
			s.append(ForceLang.stringify(ForceLang.parse(a)));
			return null;
		}));
		set("length",new Function(a->new FNum(s.length())));
	}
}
