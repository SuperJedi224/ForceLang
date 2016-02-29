package lang;

import java.util.*;

public class FList extends FObj{
	final List<FObj>list=new ArrayList<>();
	public FList(){
		set("toString",new Function(a->{
			return new FString(list.toString());
		}));
	}
}
