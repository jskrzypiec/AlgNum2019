package z2_2;

import java.math.BigInteger;

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//klasa na potrzeby testów wariantu z w³asnym typem danych (TC) - U³amek
//którego licznik(x) i mianownik(y) s¹ typami BigInteger
//W teorii je¿eli nie wykonujemy ¿adnych zaokr¹leñ, dok³adnoœæ rozwi¹zania powinna byæ idealna, a b³¹d zerowy
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

public class Ulamek extends Number{
	public BigInteger x; //x - licznik
	public BigInteger y; //y - mianownik
	
	public Ulamek(BigInteger x, BigInteger y) {
		this.x = x;
		this.y = y;
		//System.out.println(this);
	}
	public Ulamek(int x, int y) {
		this.x = new BigInteger(Integer.toString(x) );
		this.y = new BigInteger(Integer.toString(y) );
	}
	public Ulamek() {
		this.x = new BigInteger("0");
		this.y = new BigInteger("1");
	}
	
	//redukcja u³amka
	public void redukujUlamek() {
		BigInteger nwd = this.x.gcd(this.y);
		this.x = this.x.divide(nwd);
		this.y = this.y.divide(nwd);
		//je¿eli u³amek ma postaæ [a/-b] to zamiana na postaæ [-a/b]
		if(this.x.compareTo(new BigInteger("0"))==1 && this.y.compareTo(new BigInteger("0"))==-1) {
			this.x = this.x.negate();
			this.y = this.y.negate();
		}
		//je¿eli u³amek ma postaæ [-a/-b] to zamiana na postaæ [a/b]
		else if(this.x.compareTo(new BigInteger("0"))==-1 && this.y.compareTo(new BigInteger("0"))==-1) {
			this.x = this.x.negate();
			this.y = this.y.negate();
		}
	}
	
	public static Ulamek dodaj(Ulamek u1, Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		//System.out.println("-przed dzia³aniem-:" + u1 + "\t" + u2);
		u1.redukujUlamek();
		u2.redukujUlamek();
		if(u1.y.compareTo(u2.y)==0) {
			newUlamek.x = u1.x.add(u2.x);
			newUlamek.y = u1.y;
		}
		else {
			newUlamek.x = (u1.x.multiply(u2.y)).add(u1.y.multiply(u2.x));
			newUlamek.y = u1.y.multiply(u2.y);
		}
		newUlamek.redukujUlamek();
		//System.out.println("-po dzia³aniu-:" + u1 + "\t" + u2);
		return newUlamek;
	}
	public static Ulamek odejmij(Ulamek u1, Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		//System.out.println("-przed dzia³aniem-:" + u1 + "\t" + u2);
		u1.redukujUlamek();
		u2.redukujUlamek();
		if(u1.y.compareTo(u2.y)==0) { //BigInteger.compareTo zwaraca 0 jezeli s¹ równe
			newUlamek.x = u1.x.subtract(u2.x);
			newUlamek.y = u1.y;
		}
		else {
			newUlamek.x = (u1.x.multiply(u2.y)).subtract(u1.y.multiply(u2.x));
			newUlamek.y = u1.y.multiply(u2.y);
			newUlamek.redukujUlamek();
		}
		//System.out.println("-po dzia³aniu-:" + u1 + "\t" + u2);
		return newUlamek;
	}
	
	public static Ulamek mnoz(Ulamek u1, Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		//System.out.println("-przed dzia³aniem-:" + u1 + "\t" + u2);
		u1.redukujUlamek();
		u2.redukujUlamek();
		newUlamek.x = u1.x.multiply(u2.x);
		newUlamek.y = u1.y.multiply(u2.y);
		newUlamek.redukujUlamek();
		//System.out.println("-po dzia³aniu-:" + u1 + "\t" + u2);
		return newUlamek;
	}
	
	public static Ulamek podziel(Ulamek u1, Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		//System.out.println("-przed dzia³aniem-:" + u1 + "\t" + u2);
		u1.redukujUlamek();
		u2.redukujUlamek();
		newUlamek.x = u1.x.multiply(u2.y);
		newUlamek.y = u1.y.multiply(u2.x);
		newUlamek.redukujUlamek();
		//System.out.println("-po dzia³aniu-:" + u1 + "\t" + u2);
		return newUlamek;
	}
	
	public static int czyRowne(Ulamek uu1, Ulamek uu2) {
		Ulamek u1 = new Ulamek(uu1.x, uu1.y);
		Ulamek u2 = new Ulamek(uu2.x, uu2.y);

		if (u1.x.compareTo(new BigInteger("0"))==1 && u2.x.compareTo(new BigInteger("0"))==0) {
			return 1;
		}
		else if (u1.x.compareTo(new BigInteger("0"))==-1 && u2.x.compareTo(new BigInteger("0"))==0) {
			return -1;
		}
		else if (u1.x.compareTo(new BigInteger("0"))==0 && u2.x.compareTo(new BigInteger("0"))==1) {
			return -1;
		}
		else if (u1.x.compareTo(new BigInteger("0"))==0 && u2.x.compareTo(new BigInteger("0"))==-1) {
			return 1;
		}
		else if (u1.x.compareTo(new BigInteger("0"))==0 && u2.x.compareTo(new BigInteger("0"))==0) {
			return 0;
		}
		else{
			Ulamek temp = new Ulamek(u1.x, u1.y);
			u1.x = u1.x.multiply(u2.y);
			u1.y = u1.y.multiply(u2.y);
			u2.x = u2.x.multiply(temp.y);
			u2.y = u2.y.multiply(temp.y);
		}
		//System.out.println(u1 + "," + u2);
		
		return u1.x.compareTo(u2.x);
	}
	
	///////////////
	//wczesniejsze wersje - nie statyczne 
	/*public Ulamek dodaj(Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		System.out.println("-przed dzia³aniem-:" + this + "\t" + u2);
		this.redukujUlamek();
		u2.redukujUlamek();
		if(this.y.compareTo(u2.y)==0) {
			newUlamek.x = this.x.add(u2.x);
			newUlamek.y = this.y;
		}
		else {
			newUlamek.x = (this.x.multiply(u2.y)).add(this.y.multiply(u2.x));
			newUlamek.y = this.y.multiply(u2.y);
		}
		newUlamek.redukujUlamek();
		System.out.println("-po dzia³aniu-:" + this + "\t" + u2);
		return newUlamek;
	}
	
	public Ulamek odejmij(Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		System.out.println("-przed dzia³aniem-:" + this + "\t" + u2);
		this.redukujUlamek();
		u2.redukujUlamek();
		if(this.y.compareTo(u2.y)==0) { //BigInteger.compareTo zwaraca 0 jezeli s¹ równe
			newUlamek.x = this.x.subtract(u2.x);
			newUlamek.y = this.y;
		}
		else {
			newUlamek.x = (this.x.multiply(u2.y)).subtract(this.y.multiply(u2.x));
			newUlamek.y = this.y.multiply(u2.y);
			newUlamek.redukujUlamek();
		}
		System.out.println("-po dzia³aniu-:" + this + "\t" + u2);
		return newUlamek;
	}
	
	public Ulamek mnoz(Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		System.out.println("-przed dzia³aniem-:" + this + "\t" + u2);
		this.redukujUlamek();
		u2.redukujUlamek();
		newUlamek.x = this.x.multiply(u2.x);
		newUlamek.y = this.y.multiply(u2.y);
		newUlamek.redukujUlamek();
		System.out.println("-po dzia³aniu-:" + this + "\t" + u2);
		return newUlamek;
	}
	
	public Ulamek podziel(Ulamek u2) {
		Ulamek newUlamek = new Ulamek();
		System.out.println("-przed dzia³aniem-:" + this + "\t" + u2);
		this.redukujUlamek();
		u2.redukujUlamek();
		newUlamek.x = this.x.multiply(u2.y);
		newUlamek.y = this.y.multiply(u2.x);
		newUlamek.redukujUlamek();
		System.out.println("-po dzia³aniu-:" + this + "\t" + u2);
		return newUlamek;
	}*/
	
	@Override
	public String toString() {
		return "["+x + "/" + y+"]";
	}
	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
