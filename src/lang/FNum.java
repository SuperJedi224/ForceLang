package lang;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * An implementation of arbitrary-precision rational numbers, a more powerful alternative to java's built-in BigDecimal
 * @author Johnathan Waugh
 */
public class FNum extends FObj implements Comparable<FNum>{
	public FNum floor(){
		return new FNum(n.divide(d));
	}
	public boolean equals(Object o){
		if(o instanceof FNum){
			FNum num=(FNum)o;
			return n.equals(num.n)&&d.equals(num.d);
		}
		return false;
	}
	public boolean isTruthy(){
		return !equals(ZERO);
	}
	public FObj add(FObj o){
		if(o==null)return this;
		if(o instanceof FNum){
			return add((FNum)o);
		}
		if(o instanceof FBool){
			return add(o.isTruthy()?ONE:ZERO);
		}
		return new FString(ForceLang.stringify(this)+new FString(ForceLang.stringify(o)));
	}
	private BigInteger sqrt(BigInteger n){
		if(n.signum()<0)throw new ArithmeticException();
		int i=n.bitLength()/2;
		if(n.equals(BigInteger.ZERO))return n;
		if(n.compareTo(BigInteger.valueOf(4))<0)return BigInteger.ONE;
		final BigInteger TWO=BigInteger.valueOf(2);
		BigInteger a=TWO.pow(i-1);
		BigInteger b=TWO.pow(i+1);
		while(b.multiply(b).compareTo(n)>0){
			b=b.add(a).divide(TWO);
		}
		if(b.multiply(b).equals(n))return b;
		while(i>=0){
			BigInteger c=b.add(TWO.pow(i--));
			if(c.multiply(c).compareTo(n)<=0){
				b=c;
			}
		}
		return b;
	}
	public static final FNum ONE=new FNum(1);
	public static final FNum TEN=new FNum(10);
	public static final FNum ZERO=new FNum(0);
	public static final FNum NaN=new FNum(0,0);
	public static final FNum ONE_HALF=new FNum(1,2);
	public static final FNum ONE_THIRD=new FNum(1,3);
	public static final FNum ONE_FIFTH=new FNum(1,5);
	public static final FNum ONE_TENTH=new FNum(1,10);
	public static final FNum INFINITY=new FNum(1,0);
	public static final FNum MINUS_INFINITY=new FNum(-1,0);
	
	public static final FNum PI=new FNum(245850922,78256779);
	public static final FNum E=new FNum(268876667,98914198);
	public FNum cos(){
		FNum f=this;
		FNum s=ZERO;
		FNum fac=ONE;
		final FNum TWOPI=FNum.PI.multiply(new FNum(2));
		while(f.compareTo(FNum.ZERO)<0){
			f=f.add(TWOPI);
		}
		while(f.compareTo(TWOPI)>0){
			f=f.subtract(TWOPI);
		}
		for(int i=0;i<24;i++){
			FNum n=new FNum(-1).pow(i).multiply(f.pow(2*i));
			if(i!=0)fac=fac.multiply(new FNum(2*i-1)).multiply(new FNum(2*i));
			s=s.add(n.divide(fac));
		}
		return s;
	}
	/**
	 * The BigRational n/d.
	 * @param n The numerator as a BigInteger.
	 * @param d The denominator as a BigInteger.
	 */
	public FNum(BigInteger n, BigInteger d) {
		if(d.compareTo(BigInteger.ZERO)<0){
			n=n.negate();
			d=d.negate();
		}
		BigInteger r=n.gcd(d);
		if(!r.equals(BigInteger.ZERO)){n=n.divide(r);d=d.divide(r);}
		this.n=n;
		this.d=d;
	}
	{
		set("toString",new Function(a->{
			if(a==null)return new FString(toString());
			return new FString(toString(((FNum)ForceLang.parse(a)).intValue()));
		}));
		set("mult",new Function(a->{
			FObj o=ForceLang.parse(a);
			if(o instanceof FNum){
				return this.multiply((FNum)o);
			}
			throw new IllegalArgumentException("");
		}));
		set("pow",new Function(a->{
			FObj o=ForceLang.parse(a);
			if(o instanceof FNum){
				return this.pow(((FNum)o).intValue());
			}
			throw new IllegalArgumentException("");
		}));
		set("toHexString",new Function(a->{
			if(isNaN()||denominator().equals(BigInteger.ZERO))return new FString(toString());
			return new FString(this.numerator().divide(denominator()).toString(16));
		}));
	}
	public String toString(){
		if(d.equals(BigInteger.ONE))return n.toString();
		if(isNaN())return "NaN";
		if(denominator().equals(BigInteger.ZERO)){
			return numerator().compareTo(BigInteger.ZERO)>0?"Infinity":"-Infinity";
		}
		return this.n+"/"+this.d;
	}
	/**
	 * The BigRational val/1=val.
	 * @param val The value as a long
	 */
	public FNum(long val) {
		this(val,1L);
	}
	/**
	 * The BigRational n/d.
	 * @param n The numerator as a long.
	 * @param d The denominator as a long.
	 */
	public FNum(long n,long d){
		this(BigInteger.valueOf(n),BigInteger.valueOf(d));
	}
	/**
	 * The BigRational representation of the value represented by a string.
	 * The string may be formatted as a fraction, an integer percentage, a terminating decimal, or a whole number.
	 * @param val The string representation of the value.
	 */
	public FNum(String val) {
		String[] i=val.split("/");
		FNum x,y;
		if(i.length==1){
			if(val.startsWith("0x")){
				x=new FNum(new BigInteger(val.substring(2),16));
				y=ONE;
			}else if(val.indexOf("%")!=-1){
				x=new FNum(new BigInteger(i[0].substring(0,i[0].length()-1)));
				y=new FNum(BigInteger.valueOf(100));
			}else if(val.indexOf("e")!=-1){
				x=new FNum(val.split("e")[0]);
				y=TEN.pow(-Integer.parseInt(val.split("e")[1]));
			}else if(val.indexOf(".")!=-1){
				BigDecimal v=new BigDecimal(val);
				x=new FNum(v.unscaledValue());
				y=TEN.pow(v.scale());
			}else{
				x=new FNum(new BigInteger(i[0]));
				y=ONE;
			}
		}else{
			x=new FNum(new BigInteger(i[0]));
			y=new FNum(new BigInteger(i[1]));
		}
		FNum z=x.divide(y);
		n=z.n;
		d=z.d;
	}
	public FNum(BigInteger value) {
		this(value,BigInteger.ONE);
	}
	private final BigInteger n,d;
	/**
	 * Find and return the sum of this and another BigRational.
	 * @param v The BigRational value to be added.
	 * @return The sum, as a third BigRational.
	 */
	public FNum add(FNum v){
		return new FNum(n.multiply(v.d).add(v.n.multiply(d)),d.multiply(v.d));
	}
	/**
	 * @see #compareTo(FNum)
	 */
	public int compareTo(BigDecimal o){
		return compareTo(valueOf(o));
	}
	
	public FNum ln(){
		if(isNaN())return NaN;
		if(signum()<0)return NaN;
		if(n.equals(BigInteger.ZERO))return MINUS_INFINITY;
		FNum m=this,n=ZERO,t=ONE;
		if(ONE.compareTo(m)>0){
			m=ONE.divide(m);
			t=new FNum(-1);
		}
		if(m.compareTo(E)>0){
			int i=0;
			while(m.compareTo(E)>0){
				m=m.divide(E);
				i++;
			}
			return new FNum(i).add(m.ln()).multiply(t);
		} 
		m=m.subtract(ONE).divide(m);
		for(int i=1;i<30;i++){
			n=n.add(ONE.divide(new FNum(i)).multiply(m.pow(i)));
		}
		return n.multiply(t);
	}
	private int signum() {
		if(d.signum()==0)return 0;
		return n.signum();
	}
	/**
	 * @see #compareTo(FNum)
	 */
	public int compareTo(BigInteger o){
		BigInteger[] k=((BigInteger)n).divideAndRemainder(d);
		int q=k[0].compareTo(o);
		if(q==0&&!k[1].equals(BigInteger.ZERO))q=1;
		return q;
	}
	/**
	 * @see #compareTo(FNum)
	 */
	public int compareTo(FNum o){
		if(isNaN())return o.isNaN()?0:-1;
		if(d.equals(BigInteger.ZERO)){
			if(toString().equals("Infinity"))return o.toString().equals("Infinity")?0:1;
			if(toString().equals("-Infinity"))return o.toString().equals("-Infinity")?0:-1;
		}
		return n.multiply(o.d).compareTo(o.n.multiply(d));
	}
	/**
	 * Find and return the quotient of this and another BigRational.
	 * @param v The BigRational value to be added.
	 * @return The quotient, as a third BigRational.
	 */
	public FNum divide(FNum v){
		return new FNum(n.multiply(v.d),d.multiply(v.n));
	}
	/**
	 * @see java.lang.Number#doubleValue()
	 */
	public double doubleValue() {
		return doubleValue(16);
	}
	/**
	 * The BigRational representation of a BigDecimal object.
	 * @param val The BigDecimal object whose numeric value the returned BigRational represents.
	 * @return The BigRational object having the same numeric value as the passed BigDecimal.
	 */
	public static FNum valueOf(BigDecimal val){
		BigInteger x,y;
		if(val.scale()<0){
			x=val.toBigIntegerExact();
			y=BigInteger.ONE;
		}else{
			x=val.unscaledValue();
			y=BigInteger.TEN.pow(val.scale());
		}
		return new FNum(x,y);
	}
	/**
	 * The double value of this BigRational to a specified number of decimal places. Returns NaN if this BigRational represents the ratio 0/0.
	 * @param decimalPlaces
	 * @return The double value of the represented rational number, truncated to the specified number of decimal places.
	 */
	public double doubleValue(int decimalPlaces) {
		if(isNaN())return Double.NaN;
		if(d.equals(BigInteger.ZERO))return n.compareTo(BigInteger.ZERO)>0?Double.POSITIVE_INFINITY:Double.NEGATIVE_INFINITY;
		BigInteger v=n;
		BigInteger[] k=n.divideAndRemainder(d);
		double c=k[0].longValue();
		double f=0.1;
		for(int i=0;i<decimalPlaces;i++){
			v=k[1].multiply(BigInteger.TEN);
			k=v.divideAndRemainder(d);
			c+=k[0].longValue()*f;
			f*=0.1;
		}
		return c;
	}
	/**
	 * @see java.lang.Number#floatValue()
	 */
	public float floatValue() {
		return (float)doubleValue();
	}
	/**
	 * @throws ArithmeticException if this BigRational represents the ratio 0/0 
	 * @see java.lang.Number#intValue()
	 */
	public int intValue() {
		return n.divide(d).intValue();
	}
	/**
	 * Returns true if this BigRational represents the ratio 0/0
	 */
	public boolean isNaN(){
		return n.equals(BigInteger.ZERO)&&d.equals(BigInteger.ZERO);
	}
	/**
	 * @throws ArithmeticException if this BigRational represents the ratio 0/0
	 * @see java.lang.Number#longValue()
	 */
	public long longValue() {
		return n.divide(d).longValue();
	}
	/**
	 * @see java.math.BigInteger#negate()
	 */
	public FNum negate(){
		return new FNum(n.negate(),d);
	}
	/**
	 * Raises this BigRational to an integer power
	 * @param exp The power to raise it to
	 * @return The result, as another BigRational
	 */
	public FNum pow(int exp){
		if(exp==0)return FNum.ONE;
		if(exp<0)return new FNum(d.pow(-exp),n.pow(-exp));
		return new FNum(n.pow(exp),d.pow(exp));
	}
	/**
	 * Find and return the difference of this and another BigRational.
	 * @param v The BigRational value to be subtracted.
	 * @return The difference, as a third BigRational.
	 */
	public FNum subtract(FNum v){
		return add(v.negate());
	}
	/**
	 * Find and return the product of this and another BigRational.
	 * @param v The BigRational value to be multiplied by.
	 * @return The product, as a third BigRational.
	 */
	public FNum multiply(FNum v){
		return new FNum(n.multiply(v.n),d.multiply(v.d));
	}
	/**
	 * Returns a string containing a decimal approximation of this BigRational's value. Truncates to a specified number of decimal places.
	 * @param decimalPlaces The number of decimal places to use
	 * @return A string representation as described above
	 */
	public String toString(int decimalPlaces){
		if(compareTo(ZERO)<0)return "-"+multiply(new FNum(-1)).toString(decimalPlaces);
		BigInteger v=n;
		BigInteger[] k=n.divideAndRemainder(d);
		String c=k[0].longValue()+".";
		for(int i=0;i<decimalPlaces;i++){
			v=k[1].multiply(BigInteger.TEN);
			k=v.divideAndRemainder(d);
			c+=k[0].longValue();
		}
		return c;
	}
	public BigInteger numerator(){
		return n;
	}
	public BigInteger denominator(){
		return d;
	}
	public FNum sqrt(){
		FNum a=new FNum(sqrt(n.divide(d)).add(BigInteger.ONE));
		for(int i=0;i<9;i++){
			a=a.add(this.divide(a)).multiply(ONE_HALF);
		}
		return a;
	}
}