package z3;

//klasa s�u�aca do przetwarzania pliku udost�pnionego w ramach projektu SNAP firmy Amazon
//(https://snap.stanford.edu/data/amazon-meta.html)

//konstruktor klasy 'Parser' skanuje plik w poszukiwaniu Produkt�w, tworzy list� obiekt�w typu Produkt, 
//do kt�rej dodaje wszystkie Produkty posiadaj�ce id
//tworzonych jest 548552 produkt�w

//lista Produkt�w jest skanowana w poszukiwaniu produkt�w, kt�re posiadaj� mniej ni� 70 ocen
//obiekty te s� kasowane z listy Produkt�w - pozostaje 19207 Produkt�w w li�cie kt�re maj� 70 lub wi�cej ocen


//metoda dajPodliste(int ileProduktow) tworzy mniejsz� list� o rozmiaerze 'ileProduktow' Produkt�w na podstawie listy 19207 Produkt�w


//metoda 'dajMacierz(List<produkt> podlista)� tworzy macierz na podstawie listy Produkt�w
//metoda dobiera u�ytkownik�w, kt�rzy ocenili jak najwi�cej produkt�w z listy 'podlista'

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
	//lista produkt�w
	public List<Produkt> produkty = new ArrayList<>();
	
	//parser uruchamiany przy wywo�aniu konstruktora
	public Parser() throws IOException {
		File file = new File("C:\\Users\\Jakub Skrzypiec\\Desktop\\5sem\\AN\\lab\\zad3\\amazon-meta.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));		
		String line;
		String[] wyrazy;
		
		int i = 0; //kt�ry produkt
		while( (line = br.readLine()) != null) {
			//id
			if(line.length()>6 && line.substring(0, 6).equals("Id:   ")) {
				String id = line.substring(6, line.length());
				int idTemp = Integer.parseInt(id);
				produkty.add(new Produkt(idTemp));
			}
			//asin
			if(line.length()>6 && line.substring(0, 6).equals("ASIN: ")) {
				String asin = line.substring(6, line.length());
				//System.out.println("asin:|"+asin+"|");
				produkty.get(i).setAsin(asin);
			}
			//podobne
			if(line.length()>11 && line.substring(0,11).equals("  similar: ")) {
				line = line.replace("  ", " ");
				wyrazy = line.split(" ");
				int iloscPodobnych = Integer.parseInt(wyrazy[2]);
				if(iloscPodobnych!=0) {
					produkty.get(i).setSimiliar_count(iloscPodobnych);
					for(int j=3; j<iloscPodobnych+3; j++) {
						//dodanie ka�dego podobnego produktu
						produkty.get(i).getSimilar().add(wyrazy[j]);
					}
				}
			}
			//oceny i u�ytkownicy, kt�rzy oceniali
			//np. custId.get(7) oceni� produkt ocen� custRating.get(7)
			if(line.length()>9 && line.substring(0, 10).equals("  reviews:") ){
				wyrazy = line.split(" ");
				//System.out.println(line);
				int iloscRecenzji = Integer.parseInt(wyrazy[4]);
				if(iloscRecenzji>1 && iloscRecenzji!=593) {
					while( (line = br.readLine())!="" && line.length()>3) {
						if(line.substring(0,4).equals("    ")) {
							line = line.replace("    ", " ");
							line = line.replace("   ", " ");
							line = line.replace("  ", " ");
							wyrazy = line.split(" ");
							//dodanie id klienta
							produkty.get(i).getCustId().add(wyrazy[3]);
							//dodanie jego oceny
							produkty.get(i).getCustRating().add( Integer.parseInt(wyrazy[5]) );
						}
					}
				}
				//przeiterowanei do nast�pnego produktu
				i++;
			}
			//przeitrerowanie do nast�pnego produktu w przypadku wycofanego produktu
			else if(line.length()>21 && line.substring(0, 22).equals("  discontinued product")){
				i++;
			}
		}
		System.out.println("INFO: uko�czono skanowanie pliku, utworzono " + produkty.size() + " produkt�w");
		br.close();
	
		////////////////////////////////////////////////////////////////////////
		//wyrzucenie z listy produkt�w produkty o liczbie ocen mniejszej ni� 2//
		////////////////////////////////////////////////////////////////////////
		//
		//lista id do skasowania - produkty z liczb� ocen mniejsz� ni� 70
		List<Integer> doSkasowania = new ArrayList<>();
		for(int j=0; j<produkty.size(); j++) {
			if(produkty.get(j).getCustId().size()<70 || produkty.get(j).getCustRating().size()<70) {
				doSkasowania.add(j);
			}
		}
		System.out.println("INFO: wyszukano "+ doSkasowania.size() +" produkt�w do skasowania");
		//odwr�cenie listy idDoSkasowania
		Collections.reverse(doSkasowania);
		//skasowanie produkt�w z liczb� ocen mniejsz� ni� 2
		for(Integer id_del : doSkasowania) {
			produkty.remove(id_del.intValue());
		}
		//ile produkt�w zostalo po skasowaniu
		System.out.println("INFO: po skasowaniu zosta�o "+ produkty.size() +" produkt�w");
	}
	
	//metoda tworz�ca list� Produkt�w 'podlista' na podstawie listy Produkt�w 'produkty'
	//przyj�ty argument 'ileProduktow' jest d�ugo�ci� tworzonej 'podlisty' Produkt�w
	public List<Produkt> dajPodliste(int ileProduktow){
		//podlista Produkt�w
		List<Produkt> podlista = new ArrayList<>();
		Produkt prod_temp;
		int i=0;
		//dop�ki rozmiar 'podlisty' Produkt�w jest mniejszy ni� powinien by�
		while(podlista.size()<ileProduktow) {
			//wczytaj nast�pny produktu 'prod_temp'
			prod_temp = this.produkty.get(i);
			//dla ka�dego Produktu 'prod' w 'produkty'
			for(Produkt prod:this.produkty) {
				//dla ka�dego Produktu podobnego do danego Produktu 'prod_temp'
				for(int k=0; k<prod_temp.getSimiliar_count(); k++) {
					//dla ka�dego Produktu  podobnego do danego Produktu 'prod'
					for(int l=0; l<prod.getSimiliar_count(); l++) {
						//sprawdz czy podobne Produkty s� sobie r�wne
						if(prod_temp.getSimilar().get(k).contentEquals(prod.getSimilar().get(l))) {
							//je�eli tak to dodaj, o ile ju� takiego nie ma w li�cie
							boolean czyJest = false;
							for(Produkt pp:podlista) {
								if(pp.getAsin().contentEquals(prod.getAsin())) {
									czyJest = true;
								}
							}
							if(!czyJest) {
								podlista.add(prod);
							}
							if(podlista.size()>=ileProduktow)
								break;
						}
					}
					//i sprawdz czy sam Produkt 'prod' jest Produktem podobnym do Produktu 'prod_temp'
					if(prod.getAsin().equals(prod_temp.getSimilar().get(k))) {
						//je�eli tak to dodaj, o ile ju� takiego nie ma w li�cie
						boolean czyJest = false;
						for(Produkt pp:podlista) {
							if(pp.getAsin().contentEquals(prod.getAsin())) {
								czyJest = true;
							}
						}
						if(!czyJest) {
							podlista.add(prod);
						}
						if(podlista.size()>=ileProduktow)
							break;
					}
				}
				if(podlista.size()>=ileProduktow)
					break;
			}
			i++;
		}
		System.out.println("INFO: utworzono podliste Przedmiot�w o wielko�ci "+ podlista.size());
		produkty.clear();
		return podlista;
	}
	
	//funkcja tworz�ca macierz na podstawie argumentu 'podlista' - podlisty Produkt�w z listy 'produkty'
	//macierz jest rozmiaru NxM, gdzie N to ilo�� element�w 'podlista', a M to ustalona dalej w kodzie metody krotno�� N, np 2-krotno��
	public Macierz dajMacierz(List<Produkt> podlista) {
		//podlista - lista produktow 
		//lista unikatowych u�ytkownik�w
		List<Uzytkownik> uzytkownicy = new ArrayList<>();
		//dodanie pierwszego u�ytkownika
		uzytkownicy.add( new Uzytkownik(podlista.get(0).getCustId().get(0)) );
		//dla ka�dego PRODUKKTU
		for(Produkt prod:podlista) {
			//dla ka�dej oceny PRODUKTU dodaj u�ytkownika do listy 'uzytkownicy' je�eli jeszcze go tam nie ma
			for(int k=0; k<prod.getCustId().size(); k++) {
				boolean czyJest = false;
				for(Uzytkownik uzyt:uzytkownicy) {
					if(uzyt.id.contentEquals(prod.getCustId().get(k))) {
						czyJest = true;
					}
				}
				if(!czyJest) {
					uzytkownicy.add( new Uzytkownik(prod.getCustId().get(k)) );
				}
			}
		}
		System.out.println("INFO: utworzono liste U�ytkownik�w o wielko�ci "+ uzytkownicy.size());
		//dla kazdego PRZEDMIOTU w liscie 'podlista'
		for(int i=0; i<podlista.size(); i++) {
			Produkt produktTemp = podlista.get(i);
			//dla kazdego U�YTKOWNIKA w li�cie 'uzytkownicy'
			for(int j=0; j<uzytkownicy.size(); j++) {
				//dla kazdej OCENY danego PRZEDMIOTU
				for(int k=0; k<produktTemp.getCustRating().size(); k++) {
					//sprawdz czy ID U�YTKOWNIKA oceniaj�cego produkt jest r�wne z ID U�YTKOWNIKA
					//zliczenie ile ocen ma ka�dy z u�ytkownik�w
					if(produktTemp.getCustId().get(k).equals(uzytkownicy.get(j).id)) {
						uzytkownicy.get(j).iloscOcen ++;
					}
				}
			}
		}
		//sortowanie U�YTKOWNIK�W po ilo�� ocen malej�co
		Collections.sort(uzytkownicy);
		//usuni�cie pierwszego u�ytkownika z listy - jest to u�ytkownik, kt�ry oceni� wszystkie lub prawie wszystkie produkty
		//pozbywamy si� go
		uzytkownicy.remove(0);
		//utworzenie podlisty U�YTKOWNIK�W wielko�ci podlisty PRODUKT�W
		//lub K krotnie wi�ksze
		List<Uzytkownik> podlista_uzytkownicy = new ArrayList<>();
		for(int i=0; i<podlista.size(); i++) { //podliste.size()*K
			podlista_uzytkownicy.add(uzytkownicy.get(i));
		}
		System.out.println("INFO: utworzono podliste U�ytkownik�w o wielko�ci "+ podlista_uzytkownicy.size());
		uzytkownicy.clear();
		//utworzenie macierzy
		Macierz newMacierz = new Macierz('R', podlista_uzytkownicy.size(), podlista.size());
		System.out.println("INFO: utworzono macierz rozmiar�w ["+newMacierz.n+"x"+newMacierz.m +"]");
		//dla kazdego PRZEDMIOTU w liscie 'podlista'
		for(int i=0; i<podlista.size(); i++) {
			Produkt produktTemp = podlista.get(i);
			//dla kazdego U�YTKOWNIKA w li�cie 'uzytkownicy'
			for(int j=0; j<podlista_uzytkownicy.size(); j++) {
				//dla kazdej OCENY danego PRZEDMIOTU
				for(int k=0; k<produktTemp.getCustRating().size(); k++) {
					//sprawdz czy ID U�YTKOWNIKA oceniaj�cego produkt jest r�wne z ID U�YTKOWNIKA
					//zliczenie ile ocen ma ka�dy z u�ytkownik�w
					if(produktTemp.getCustId().get(k).equals(podlista_uzytkownicy.get(j).id)) {
						newMacierz.A[j][i] = produktTemp.getCustRating().get(k);
					}
				}
			}
		}
		//newMacierz.wypiszMac();
		return newMacierz;
	}
}