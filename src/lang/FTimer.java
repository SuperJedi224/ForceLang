package lang;

public class FTimer extends FObj {
	long t;
	public FTimer(){
		this.set("poll",new Function(a->new FNum((System.nanoTime()-t)/1000)));
		this.set("reset",new Function(a->{t=System.nanoTime();return null;}));
		this.setImmutable();
		t=System.nanoTime();
	}
}
