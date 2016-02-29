package lang;

import java.util.*;

import lang.exceptions.IllegalAssignmentException;

public class FObj {
	private boolean m=true;
	protected void setImmutable(){
		m=false;
	}
	public boolean equals(Object o){
		return this==o;
	}
	public String toString(){
		if(fields.containsKey("toString")){
			return ((Function)fields.get("toString")).apply(null,this).toString();
		}
		return super.toString();
	}
	protected final List<String>immutableFields=new ArrayList<>();
	private final Map<String,FObj>fields=new HashMap<>();
	public final FObj get(String name){
		return fields.get(name);
	}
	public final void set(String name,FObj value){
		if(!m)throw new IllegalAssignmentException("Object "+this+" is immutable.");
		if(immutableFields.contains(name))throw new IllegalAssignmentException("Field "+name+" is immutable.");
		fields.put(name,value);
	}
	public boolean isTruthy(){
		return true;
	}
	public FObj add(FObj o){
		return new FString(ForceLang.stringify(this)+new FString(ForceLang.stringify(o)));
	}
}
