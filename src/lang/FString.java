package lang;

import java.util.Scanner;

public class FString extends FObj {
	final String val;
	public FString(String s){
		val=s;
		this.set("len",new FNum(s.length()));
		this.setImmutable();
	}
	public FString(Scanner s){
		this(s.nextLine());
	}
	public String toString(){
		return val;
	}
	public boolean equals(Object o){
		return (o instanceof FString) && ((FString)o).val.equals(val);
	}
	public boolean isTruthy(){
		return !val.isEmpty();
	}
}
