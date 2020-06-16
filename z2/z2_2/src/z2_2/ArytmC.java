package z2_2;

import java.math.BigInteger;
import java.util.Random;

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//klasa potrzebna do wykonywania generycznych operacji na podstawie typu zmiennej
//zaimplementowa³em m.in. operacje arytmetyczne (dodaj,usuñ,podziel,mnoz),
//funkcje do porównañ wartoœci (czyRówne), wartoœæ bezwzglêdna (Absolute),
//nextRandom do losowania oraz typ do zwracania typu
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

public class ArytmC<T extends Number>{
	Class<T> typGen;
	
	public ArytmC(Class<T> typGen) {
		super();
		this.typGen = typGen;
	}
	
	public int czyRowne(Object a, Object b) {
		if(this.typGen.equals(Double.class) && (double)a>(double)b || this.typGen.equals(Float.class) && (float)a>(float)b)
			return 1;
		else if(this.typGen.equals(Double.class) && (double)a<(double)b || this.typGen.equals(Float.class) && (float)a<(float)b)
			return -1;
		else if(this.typGen.equals(Double.class) && (double)a==(double)b || this.typGen.equals(Float.class) && (float)a==(float)b)
			return 0;
		else if(this.typGen.equals(Ulamek.class))
			return Ulamek.czyRowne((Ulamek)a, (Ulamek)b);
		return -2;
	}
	
	public Object Absolute(Object x) {
		if(this.typGen.equals(Double.class) && (double)x<0.0) {
			return Math.abs((double)x);
		}
		else if(this.typGen.equals(Float.class) && (float)x<0.0) {
			return Math.abs((float)x);
		}
		else if(this.typGen.equals(Ulamek.class) && Ulamek.czyRowne((Ulamek)x, new Ulamek())==-1) {
			Ulamek xUlamek = (Ulamek)x;
			Ulamek newUlamek = new Ulamek(xUlamek.x, xUlamek.y);
			newUlamek.x = newUlamek.x.abs();
			newUlamek.y = newUlamek.y.abs();
			return newUlamek;
		}
		else if(x.equals(-0) || x.equals(+0))
			return +0;
		else if(x.equals(Double.POSITIVE_INFINITY) || x.equals(Double.NEGATIVE_INFINITY) )
			return Double.POSITIVE_INFINITY;
		else if(x.equals(Float.POSITIVE_INFINITY) || x.equals(Float.NEGATIVE_INFINITY) )
			return Float.POSITIVE_INFINITY;
		return x;
	}

	public Object dodaj(Object object, Object object2) {
		if(this.typGen.equals(Double.class)) {
			return (double)object + (double)object2;
		}
		else if(this.typGen.equals(Float.class)) {
			return (float)object + (float)object2;
		}
		else if(this.typGen.equals(Ulamek.class)) {		
			return Ulamek.dodaj((Ulamek)object , (Ulamek)object2);
		}
		return null;
	}
	
	public Object odejmij(Object object, Object object2) {
		if(this.typGen.equals(Double.class)) {
			return (double)object - (double)object2;
		}
		else if(this.typGen.equals(Float.class)) {
			return (float)object - (float)object2;
		}
		else if(this.typGen.equals(Ulamek.class)) {
			return Ulamek.odejmij((Ulamek)object , (Ulamek)object2);
		}
		return null;
	}
	
	public Object mnoz(Object object, Object object2) {
		if(this.typGen.equals(Double.class)) {
			return (double)object * (double)object2;
		}
		else if(this.typGen.equals(Float.class)) {
			return (float)object * (float)object2;
		}
		else if(this.typGen.equals(Ulamek.class)) {
			return Ulamek.mnoz((Ulamek)object , (Ulamek)object2);
		}
		return null;
	}
	
	public Object podziel(Object object, Object object2) {
		if(this.typGen.equals(Double.class)) {
			return (double)object / (double)object2;
		}
		else if(this.typGen.equals(Float.class)) {
			return (float)object / (float)object2;
		}
		else if(this.typGen.equals(Ulamek.class)) {
			return Ulamek.podziel((Ulamek)object , (Ulamek)object2);
		}
		return null;
	}
	
	public Object nextRandom(int x, int y) {
		Random rand = new Random();
		if(this.typGen.equals(Double.class))
			return (double)rand.nextInt(x)-(double)y;
		else if(this.typGen.equals(Float.class))
			return (float)rand.nextInt(x)-(float)y;
		else if(this.typGen.equals(Ulamek.class))
			return Ulamek.odejmij(new Ulamek(rand.nextInt(x),1), new Ulamek(y,1));
		return null;
	}
	
	public Object typ(int x) {
		if(this.typGen.equals(Double.class)) {
			return (double)x;
		}
		else if(this.typGen.equals(Float.class)) {
			return (float)x;
		}
		else if(this.typGen.equals(Ulamek.class)) {
			return new Ulamek(x,1);
		}
		return null;
	}
	

}
