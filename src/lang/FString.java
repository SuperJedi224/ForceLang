package lang;

import java.util.Scanner;

import lang.exceptions.IllegalArgumentException;

public class FString extends FObj {
	final String val;
	public FString(String s){
		val=s;
		this.set("len",new FNum(s.length()));
		this.set("charAt",new Function(a->{
			FObj o=ForceLang.parse(a);
			try{return new FString(""+val.charAt(((FNum)o).intValue()));}catch(Exception e){
				throw new IllegalArgumentException("Expected Number.");
			}	
		}));
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
