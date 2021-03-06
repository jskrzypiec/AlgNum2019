package z3;

//klasa Użytkownik reprezentująca użytkowników oceniających produkty

//klasa implementuje Comparable, a odpowiednio zdefiniowana metoda compareTo umożliwia
//porównywanie obiektów typu 'Uzytkownik' po polu 'iloscOcen'
//porownanie jest wykorzystane w metodzie 'dajMacierz(...)' klasy 'Parser' celem sortowania 
//uzytkoniwkow malejąco na podstawie ilości ocen

public class Uzytkownik implements Comparable<Uzytkownik>{
	public String id;
	public int iloscOcen;
	
	
	public Uzytkownik(String id, int iloscOcen) {
		this.id = id;
		this.iloscOcen = iloscOcen;
	}
	public Uzytkownik(String id) {
		this.id = id;
		this.iloscOcen = 0;
	}
	public Uzytkownik() {}
	
	
	@Override
	public String toString() {
		return "Uzytkownik [id=" + id + ", iloscOcen=" + iloscOcen + "]";
	}
	
	@Override
	public int compareTo(Uzytkownik arg0) {
		if(this.iloscOcen < arg0.iloscOcen) {
			return 1;
		}
		else if(this.iloscOcen > arg0.iloscOcen) {
			return -1;
		}
		else if(this.iloscOcen == arg0.iloscOcen) {
			return 0;
		}
		return 0;
	}
	
	
}
