package lang;

import java.util.Scanner;

import lang.exceptions.IllegalArgumentException;

public class FString extends FObj {
	final String val;
	public FString(String s){
		val=s;
		this.set("len",new FNum(s.length()));
		this.set("repeat",new Function(a->{
			FObj o=ForceLang.parse(a);
			StringBuilder f=new StringBuilder();
			try{int l=((FNum)o).intValue();
			for(;l-->0;)f.append(val);
			return new FString(f.toString());}catch(Exception e){throw new IllegalArgumentException("Expected Number.");}
		}));
		this.set("charAt",new Function(a->{
			FObj o=ForceLang.parse(a);
			try{return new FString(""+val.charAt(((FNum)o).intValue()));}catch(Exception e){
				throw new IllegalArgumentException("Input given is non-numeric or out of bounds.");
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
