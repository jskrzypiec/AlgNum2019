package z3;

//klasa s³u¿aca do przetwarzania pliku udostêpnionego w ramach projektu SNAP firmy Amazon
//(https://snap.stanford.edu/data/amazon-meta.html)

//konstruktor klasy 'Parser' skanuje plik w poszukiwaniu Produktów, tworzy listê obiektów typu Produkt, 
//do której dodaje wszystkie Produkty posiadaj¹ce id
//tworzonych jest 548552 produktów

//lista Produktów jest skanowana w poszukiwaniu produktów, które posiadaj¹ mniej ni¿ 70 ocen
//obiekty te s¹ kasowane z listy Produktów - pozostaje 19207 Produktów w liœcie które maj¹ 70 lub wiêcej ocen


//metoda dajPodliste(int ileProduktow) tworzy mniejsz¹ listê o rozmiaerze 'ileProduktow' Produktów na podstawie listy 19207 Produktów


//metoda 'dajMacierz(List<produkt> podlista)’ tworzy macierz na podstawie listy Produktów
//metoda dobiera u¿ytkowników, którzy ocenili jak najwiêcej produktów z listy 'podlista'

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
	//lista produktów
	public List<Produkt> produkty = new ArrayList<>();
	
	//parser uruchamiany przy wywo³aniu konstruktora
	public Parser() throws IOException {
		File file = new File("C:\\Users\\Jakub Skrzypiec\\Desktop\\5sem\\AN\\lab\\zad3\\amazon-meta.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));		
		String line;
		String[] wyrazy;
		
		int i = 0; //który produkt
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
						//dodanie ka¿dego podobnego produktu
						produkty.get(i).getSimilar().add(wyrazy[j]);
					}
				}
			}
			//oceny i u¿ytkownicy, którzy oceniali
			//np. custId.get(7) oceni³ produkt ocen¹ custRating.get(7)
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
				//przeiterowanei do nastêpnego produktu
				i++;
			}
			//przeitrerowanie do nastêpnego produktu w przypadku wycofanego produktu
			else if(line.length()>21 && line.substring(0, 22).equals("  discontinued product")){
				i++;
			}
		}
		System.out.println("INFO: ukoñczono skanowanie pliku, utworzono " + produkty.size() + " produktów");
		br.close();
	
		////////////////////////////////////////////////////////////////////////
		//wyrzucenie z listy produktów produkty o liczbie ocen mniejszej ni¿ 2//
		////////////////////////////////////////////////////////////////////////
		//
		//lista id do skasowania - produkty z liczb¹ ocen mniejsz¹ ni¿ 70
		List<Integer> doSkasowania = new ArrayList<>();
		for(int j=0; j<produkty.size(); j++) {
			if(produkty.get(j).getCustId().size()<70 || produkty.get(j).getCustRating().size()<70) {
				doSkasowania.add(j);
			}
		}
		System.out.println("INFO: wyszukano "+ doSkasowania.size() +" produktów do skasowania");
		//odwrócenie listy idDoSkasowania
		Collections.reverse(doSkasowania);
		//skasowanie produktów z liczb¹ ocen mniejsz¹ ni¿ 2
		for(Integer id_del : doSkasowania) {
			produkty.remove(id_del.intValue());
		}
		//ile produktów zostalo po skasowaniu
		System.out.println("INFO: po skasowaniu zosta³o "+ produkty.size() +" produktów");
	}
	
	//metoda tworz¹ca listê Produktów 'podlista' na podstawie listy Produktów 'produkty'
	//przyjêty argument 'ileProduktow' jest d³ugoœci¹ tworzonej 'podlisty' Produktów
	public List<Produkt> dajPodliste(int ileProduktow){
		//podlista Produktów
		List<Produkt> podlista = new ArrayList<>();
		Produkt prod_temp;
		int i=0;
		//dopóki rozmiar 'podlisty' Produktów jest mniejszy ni¿ powinien byæ
		while(podlista.size()<ileProduktow) {
			//wczytaj nastêpny produktu 'prod_temp'
			prod_temp = this.produkty.get(i);
			//dla ka¿dego Produktu 'prod' w 'produkty'
			for(Produkt prod:this.produkty) {
				//dla ka¿dego Produktu podobnego do danego Produktu 'prod_temp'
				for(int k=0; k<prod_temp.getSimiliar_count(); k++) {
					//dla ka¿dego Produktu  podobnego do danego Produktu 'prod'
					for(int l=0; l<prod.getSimiliar_count(); l++) {
						//sprawdz czy podobne Produkty s¹ sobie równe
						if(prod_temp.getSimilar().get(k).contentEquals(prod.getSimilar().get(l))) {
							//je¿eli tak to dodaj, o ile ju¿ takiego nie ma w liœcie
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
						//je¿eli tak to dodaj, o ile ju¿ takiego nie ma w liœcie
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
		System.out.println("INFO: utworzono podliste Przedmiotów o wielkoœci "+ podlista.size());
		produkty.clear();
		return podlista;
	}
	
	//funkcja tworz¹ca macierz na podstawie argumentu 'podlista' - podlisty Produktów z listy 'produkty'
	//macierz jest rozmiaru NxM, gdzie N to iloœæ elementów 'podlista', a M to ustalona dalej w kodzie metody krotnoœæ N, np 2-krotnoœæ
	public Macierz dajMacierz(List<Produkt> podlista) {
		//podlista - lista produktow 
		//lista unikatowych u¿ytkowników
		List<Uzytkownik> uzytkownicy = new ArrayList<>();
		//dodanie pierwszego u¿ytkownika
		uzytkownicy.add( new Uzytkownik(podlista.get(0).getCustId().get(0)) );
		//dla ka¿dego PRODUKKTU
		for(Produkt prod:podlista) {
			//dla ka¿dej oceny PRODUKTU dodaj u¿ytkownika do listy 'uzytkownicy' je¿eli jeszcze go tam nie ma
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
		System.out.println("INFO: utworzono liste U¿ytkowników o wielkoœci "+ uzytkownicy.size());
		//dla kazdego PRZEDMIOTU w liscie 'podlista'
		for(int i=0; i<podlista.size(); i++) {
			Produkt produktTemp = podlista.get(i);
			//dla kazdego U¯YTKOWNIKA w liœcie 'uzytkownicy'
			for(int j=0; j<uzytkownicy.size(); j++) {
				//dla kazdej OCENY danego PRZEDMIOTU
				for(int k=0; k<produktTemp.getCustRating().size(); k++) {
					//sprawdz czy ID U¯YTKOWNIKA oceniaj¹cego produkt jest równe z ID U¯YTKOWNIKA
					//zliczenie ile ocen ma ka¿dy z u¿ytkowników
					if(produktTemp.getCustId().get(k).equals(uzytkownicy.get(j).id)) {
						uzytkownicy.get(j).iloscOcen ++;
					}
				}
			}
		}
		//sortowanie U¯YTKOWNIKÓW po iloœæ ocen malej¹co
		Collections.sort(uzytkownicy);
		//usuniêcie pierwszego u¿ytkownika z listy - jest to u¿ytkownik, który oceni³ wszystkie lub prawie wszystkie produkty
		//pozbywamy siê go
		uzytkownicy.remove(0);
		//utworzenie podlisty U¯YTKOWNIKÓW wielkoœci podlisty PRODUKTÓW
		//lub K krotnie wiêksze
		List<Uzytkownik> podlista_uzytkownicy = new ArrayList<>();
		for(int i=0; i<podlista.size(); i++) { //podliste.size()*K
			podlista_uzytkownicy.add(uzytkownicy.get(i));
		}
		System.out.println("INFO: utworzono podliste U¿ytkowników o wielkoœci "+ podlista_uzytkownicy.size());
		uzytkownicy.clear();
		//utworzenie macierzy
		Macierz newMacierz = new Macierz('R', podlista_uzytkownicy.size(), podlista.size());
		System.out.println("INFO: utworzono macierz rozmiarów ["+newMacierz.n+"x"+newMacierz.m +"]");
		//dla kazdego PRZEDMIOTU w liscie 'podlista'
		for(int i=0; i<podlista.size(); i++) {
			Produkt produktTemp = podlista.get(i);
			//dla kazdego U¯YTKOWNIKA w liœcie 'uzytkownicy'
			for(int j=0; j<podlista_uzytkownicy.size(); j++) {
				//dla kazdej OCENY danego PRZEDMIOTU
				for(int k=0; k<produktTemp.getCustRating().size(); k++) {
					//sprawdz czy ID U¯YTKOWNIKA oceniaj¹cego produkt jest równe z ID U¯YTKOWNIKA
					//zliczenie ile ocen ma ka¿dy z u¿ytkowników
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