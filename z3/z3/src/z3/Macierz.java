package z3;

//klasa definiuj¹ca macierze, podstawowe operacje na macierzach, metodê Gaussa oraz metodê ALS wykorzysywan¹ w systemie rekomendacji

//klasa macierz posiada pola:
//nazwa - pojedynczy znak, np 'A'
//n - iloœæ wierszy; 
//m - iloœæ kolumn
//A[][] - tablica rozmiaru nXm przechowuj¹ca wartoœci macierzy
//rozw[] - tablica przechowuj¹ca wektor rozwi¹zania macierzy rozszerzonej - wykorzystanie w metodzie Gaussa

//klasa implementuje ró¿nego rodzaju operacje na macierzach, np. dodawanie, mno¿enie, transpozycja macierzy,
//mno¿enie macierzy przez liczbê, uzyskanie danej kolumny macierzy, 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Macierz {
	//rozmiar macierzy
	char nazwa;
	int n; 
	int m;
	double[][] A;
	double[] rozw; //do gaussa
	
	//konstruktory
	//normalna macierz
	public Macierz(char nazwa, int n, int m) {
		this.nazwa = nazwa;
		this.n = n;
		this.m = m;
		A = new double[n][m];
	}
	//macierz E - jednostkowa
	public Macierz(int n) {
		this.nazwa = 'E';
		this.n = n;
		this.m = n;
		this.A = new double[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i==j) {
					this.A[i][j] = 1.0;
				} 
				else {
					this.A[i][j] = 0.0;
				}
			}
		}
	}
	
	//metody
	//funkcja czytaj¹ca macierz - do testow na prostych przykladach
	public void czytajMac() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Podaj wartoœci do macierzy [" + n + "x" + m + "]:");
		for(int i=0; i<n; i++) 
		{
			System.out.print(i + " wiersz: ");
			for(int j=0; j<m; j++) 
			{
					this.A[i][j] = reader.nextDouble();
			}
		}
		reader.close();
	}
	//metoda losuj¹ca macierz
	public void losujMac() {
		int r;
		Random rand = new Random();
		for(int i=0; i<this.n; i++)
		{
			for(int j=0; j<this.m; j++)
			{		
				r = rand.nextInt(65536) + 65536 ; //(max-min)+min, nextInt(int n) zwraca [0;n) //losowanie z przedzialu [1,2)
				A[i][j] = (double)r/65536;
			}
		}
	}
	//funkcja wyliczaj¹ca min i max wyrazy w macierzy - do testowania wylosowanych wartoœci
	public void minMax() {
		double min=10.0, max=0.0;
		min = this.A[0][0];
		max = this.A[0][0];
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				if(this.A[i][j]>max) {
					max = this.A[i][j];
				}
				if(this.A[i][j]<min) {
					min = this.A[i][j];
				}
			}
		}
		System.out.println("min:"+min + ", max:"+max);
	}
	//wypisz mac
	public void wypiszMac() {
		System.out.println("Macierz " + this.nazwa + ":");
		for(int i=0; i<n; i++) 
		{
			for(int j=0; j<m; j++)
				System.out.print(A[i][j] + "\t");
			System.out.println();
		}
		System.out.println();
	}
	//wypisz rozw
	public void wypiszRozw() {
		System.out.print("wektor 'rozw' macierzy " + this.nazwa + ": [");
		for(int i=0; i<n; i++)
		{
			System.out.print(this.rozw[i] + "\t");
		}
		System.out.println("]\n");
	}
	
	//zwraca macierz w postaci danej kolmny
	public Macierz dajKolumne(int kol) {
		Macierz nowaMacierz = new Macierz('K', this.n, 1);
		for(int i=0; i<this.n; i++) {
			nowaMacierz.A[i][0] = this.A[i][kol];
		}
		return nowaMacierz;
	}
	//tworzy macierz Transponowan¹, zwraca now¹ Macierz
	public Macierz transponuj() {
		Macierz nowaMacierz = new Macierz('N', this.m, this.n);
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				nowaMacierz.A[j][i] = this.A[i][j];
			}
		}
		return nowaMacierz;
	}
	
	//dodanie macierzy do macierzy B, zwraca now¹ Macierz 
	public Macierz dodajMacierz(Macierz B) {
		Macierz nowaMacierz = new Macierz('N', this.n, B.m);
		for(int i=0; i<nowaMacierz.n; i++) {
			for(int j=0; j<nowaMacierz.m; j++) {
				nowaMacierz.A[i][j] = this.A[i][j] + B.A[i][j];
			}
		}
		return nowaMacierz;
	}
	//mno¿enie macierzy przez macierz B, zwraca now¹ Macierz
	public Macierz mnozMacierz(Macierz B) {
		Macierz nowaMacierz = new Macierz('N', this.n, B.m);
		for(int i=0; i<nowaMacierz.n; i++) {
			for(int j=0; j<nowaMacierz.m; j++) {
				for(int k=0; k<this.m; k++) {//tyle razy ile kolumn w pierwszej macierzy
					nowaMacierz.A[i][j] += this.A[i][k] * B.A[k][j];
				}
			}
		}
		return nowaMacierz;
	}
	//mnozenie macierzy przez liczbe, zwraca now¹ Macierz
	public Macierz mnozPrzezLiczbe(double liczba) {
		Macierz nowaMacierz = new Macierz('N', this.n, this.m);
		for(int i=0; i<n; i++) 
		{
			for(int j=0; j<m; j++) {
				if(this.A[i][j]!=0.0) {
					nowaMacierz.A[i][j] = this.A[i][j]*liczba;
				}
			}
		}
		return nowaMacierz;
	}
	
	//zwraca liste indeksów kolumn danego wiersza, gdzie wyrazy macierzy s¹ znane
	public List<Integer> listKol(int wiersz){
		List<Integer> Iu = new ArrayList<>();
		for(int j=0; j<this.m; j++) {
			if(this.A[wiersz][j]!=0.0) {
				Iu.add(j);
			}
		}
		return Iu;
	}
	//zwraca liste indeksów wierszy danej kolumny, gdzie wyrazy macierzy s¹ znane
	public List<Integer> listWier(int kol){
		List<Integer> Iu = new ArrayList<>();
		for(int i=0; i<this.n; i++) {
			if(this.A[i][kol]!=0.0) {
				Iu.add(i);
			}
		}
		return Iu;
	}
	//tworzy macierz z danych w liscie kolumn
	public Macierz dajMacierzKolumny(List<Integer> Iu) {
		Macierz nowaMacierz = new Macierz('N', this.n, Iu.size());
		for(int i=0; i<nowaMacierz.n; i++) {
			for(int j=0; j<nowaMacierz.m; j++) {
				nowaMacierz.A[i][j] = this.A[i][Iu.get(j)];
			}				
		}
		return nowaMacierz;
	}
	//tworzy now¹ macierz na podstawie macierzy A i wektora V w postaci macierzy [A|V]
	public Macierz nowaMacierzRozszerzona(Macierz V) {
		Macierz nowaMacierz = new Macierz('N', this.n, this.m+V.m);
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				nowaMacierz.A[i][j] = this.A[i][j];
			}
			nowaMacierz.A[i][this.m] = V.A[i][0];
		}
		return nowaMacierz;
	}
	//funka przypisuj¹ca wektor 'rozw' danej kolumnie 'kol'
	public void setKolumna(int kol, double[] rozw) {
		for(int i=0; i<this.n; i++) {
			this.A[i][kol] = rozw[i];
		}		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//do gaussa
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//uzyksanie rozwi¹zania
	public void uzyskajRozw() {
		this.rozw = new double[this.n];
		for(int i=0; i<n; i++)
		{
			rozw[i] = A[i][n]/A[i][i];
		}
	}
	//dalsza czêœæ redukcji Gaussa (gdy jesteœmy w 1 pêtli, tutaj najczêœciej i)
	public void dalszaCzRedGaussa(int i, double ratio) {
		for(int j=0; j<n; j++) 
		{
			if(i!=j) 
			{
				ratio = A[j][i]/A[i][i] ;
				for(int k=i; k<n+1; k++) 
				{
					A[j][k] = A[j][k] - ratio*A[i][k];
				}
			}
		}
	}
	//zamiana wierszy
	public void zamienWiersze(int w1, int w2, int odKtoregoInd) {
		double temp = 0.0;
		for(int q=odKtoregoInd; q<n+1; q++) {
			temp = A[w1][q];
			A[w1][q] = A[w2][q];
			A[w2][q] = temp;
		}
	}
	//gauss
	public void gauss() {
		//redukcja Gaussa
		double ratio = 0.0;
		for(int i=0; i<n; i++) 
		{
			if(A[i][i]==0.0 && i<n-1) //je¿eli na g³ównej diagonalnej jest 0 w obecnym miejscu to zamien wiersze z wierszem ni¿ej
			{ 
				//zamiana wierszy
				this.zamienWiersze(i, i+1, i);
			}
			//dalsza czêœæ redukcji Gaussa
			this.dalszaCzRedGaussa(i, ratio);
		}
		//uzyskanie rozwi¹zania
		this.uzyskajRozw();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//system rekomendacji
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//wyliczenie U
	public void wyliczU(Macierz R, Macierz P, double lambda) {
		for(int u=0; u<this.m; u++)
		{
		//Iu
		List<Integer> Iu = R.listKol(u);
		//System.out.println(Iu);
		//PIu		
		Macierz PIu = P.dajMacierzKolumny(Iu);
		//System.out.println("PIu:::");
		//PIu.wypiszMac();
		//Vu
		//
		Macierz Vu= new Macierz('V', P.n, 1);
		for(int i=0; i<Iu.size(); i++) {
			Vu = Vu.dodajMacierz( P.dajKolumne(Iu.get(i)).mnozPrzezLiczbe(R.A[u][Iu.get(i)]) );
		}
		//
		//System.out.println("Vu:::");
		//Vu.wypiszMac();
		//Au
		Macierz Au = PIu.mnozMacierz(PIu.transponuj());
		//System.out.println("Au:::");
		//Au.wypiszMac();
		//dalej Au -> dodanie E
		Macierz E = new Macierz(Au.n).mnozPrzezLiczbe(lambda);
		Au = Au.dodajMacierz(E);
		//System.out.println("Au = Au+E:::");
		//Au.wypiszMac();
		//wyliczenie Uu
		Macierz AuVu = Au.nowaMacierzRozszerzona(Vu);
		//System.out.println("AuVu:::");
		//AuVu.wypiszMac();
		
		///gauss
		AuVu.gauss();
		//AuVu.wypiszMac();
		//AuVu.wypiszRozw();
		
		this.setKolumna(u, AuVu.rozw);
		//U.A[0][0] = AuVu.rozw[0]; U.A[1][0] = AuVu.rozw[1]; U.A[2][0] = AuVu.rozw[2];
		//this.wypiszMac();
		}
	}	
	
	//wyliczenie P
	public void wyliczP(Macierz R, Macierz U, double lambda) {
		//Ip
		//double lambda = 0.1;
		for(int p=0; p<this.m; p++)
		{
			List<Integer> Ip = R.listWier(p);
			//System.out.println(Ip);
			//UIp		
			Macierz UIp = U.dajMacierzKolumny(Ip);
			//System.out.println("UIp:::");
			//UIp.wypiszMac();
			//Wp
			//
			Macierz Wp = new Macierz('W', U.n, 1);
			for(int i=0; i<Ip.size(); i++) {
				Wp = Wp.dodajMacierz( U.dajKolumne(Ip.get(i)).mnozPrzezLiczbe(R.A[Ip.get(i)][p]) );
			}
			//
			//System.out.println("Wp:::");
			//Wp.wypiszMac();
			//Bp
			Macierz Bp = UIp.mnozMacierz(UIp.transponuj());
			//System.out.println("Bp:::");
			//Bp.wypiszMac();
			//dalej Bp -> dodanie E
			Macierz E = new Macierz(Bp.n).mnozPrzezLiczbe(lambda);
			Bp = Bp.dodajMacierz(E);
			//System.out.println("Bp = Bp+E:::");
			//Bp.wypiszMac();
			//wyliczenie Pp
			Macierz BpWp = Bp.nowaMacierzRozszerzona(Wp);
			//System.out.println("BpWp:::");
			//BpWp.wypiszMac();
			///gauss
			BpWp.gauss();
			//BpWp.wypiszMac();
			//BpWp.wypiszRozw();
				
			this.setKolumna(p, BpWp.rozw);
			//P.A[0][0] = BpWp.rozw[0]; P.A[1][0] = BpWp.rozw[1]; P.A[2][0] = BpWp.rozw[2];
			//this.wypiszMac();	
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//funkcja ALS
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//metoda ALS - wywo³ywana na macierzy R
	public Macierz ALS(int d, double lambda) {
		Macierz nowaMacierz = new Macierz('R', this.n, this.m);
		Macierz P = new Macierz('P',d, this.m);
		Macierz U = new Macierz('U',d, this.n);
		//SZYBKOSC ZBIEZNOSCI
		double suma_i = 0.0;
		//JAKOSC REKOMDNACJI
		double suma_f_celu = 0.0;
		//CZAS OBLICZEN
		double suma_czasu=0.0;
		long start=0, koniec=0, czas=0;
		int ileProb=2;
		for(int z=0; z<ileProb; z++) {
			P.losujMac();
			U.losujMac();
			Macierz Rtemp = new Macierz('T', this.n, this.m);
			//funkcja celu
			double f_celu1 = 0.0; //obecna f_celu
			double f_celu2 = 0.0; //f_celu z poprzedniej iteracji
			double roznica = 0.0;
		
			//wykonuje obliczenia dan¹ iloœc razy, np. 1000000
			for(int i=0; i<1000000; i++) {
				//czas
				start = System.currentTimeMillis();
				//
				U.wyliczU(this, P, 0.1);
				//U.wypiszMac();
				
				P.wyliczP(this, U, 0.1);
				//P.wypiszMac();
				
				Rtemp = U.transponuj().mnozMacierz(P);
				//Rtemp.wypiszMac();
				//
				//czas
				koniec = System.currentTimeMillis();
				czas = koniec-start;
				
				//wyliczenie funkcji celu
				f_celu2 = f_celu1;
				f_celu1 = 0.0;
				//pierwszy sk³adnik (ró¿nica R i Rtemp)
				for(int n=0; n<this.n; n++) {
					for(int m=0; m<this.m; m++) {
						if(this.A[n][m]!=0.0) {
							f_celu1 += Math.pow(this.A[n][m]-Rtemp.A[n][m],2);
						}
					}
				}
				//drugi sk³adnik (regularyzacja)
				double reg = 0.0;
				for(int n=0; n<U.n; n++) {
					for(int m=0; m<U.m; m++) {
						reg += Math.pow(U.A[n][m],2);
					}
				}
				for(int n=0; n<P.n; n++) {
					for(int m=0; m<P.m; m++) {
						reg += Math.pow(P.A[n][m],2);
					}
				}
				reg = lambda*reg;
				//
				f_celu1 += reg;
				//roznica
				roznica = Math.abs(f_celu1-f_celu2);
				//System.out.println("i:"+i+ ", roznica:"+roznica + ", f_celu1:"+f_celu1 + ", f_celu2:"+f_celu2); //testowe wypisanie
				if(roznica<0.01) {
					//System.out.println("i:"+i+ ", roznica:"+roznica + ", f_celu1:"+f_celu1 + ", f_celu2:"+f_celu2); //testowe wypisanie
					suma_i += i;
					suma_f_celu += f_celu1;
					suma_czasu += (koniec-start);
					break;
				}
			}
		}
		double srednia_i = suma_i/ileProb;
		System.out.println("suma_i:"+suma_i +", srednia_i:"+srednia_i);
		double srednia_f_celu = suma_f_celu/ileProb;
		System.out.println("srednia_f_celu:"+srednia_f_celu);
		double srednia_czasu = suma_czasu/ileProb;
		System.out.println("srednia_czasu:"+srednia_czasu);
		//SZYBKOSC ZBIEZNOSCI
		//
		//Rtemp.wypiszMac();
		nowaMacierz = U.transponuj().mnozMacierz(P);
		return nowaMacierz;
	}
	
	//funkcja do zaokr¹gleñ
	public void round() {
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				if(this.A[i][j]<=1.49 && this.A[i][j]>=0.5)
					this.A[i][j]=1.0;
				else if(this.A[i][j]<=2.49 && this.A[i][j]>=1.5)
					this.A[i][j]=2.0;
				else if(this.A[i][j]<=3.49 && this.A[i][j]>=2.5)
					this.A[i][j]=3.0;
				else if(this.A[i][j]<=4.49 && this.A[i][j]>=3.5)
					this.A[i][j]=4.0;
				else if(this.A[i][j]<=5.49 && this.A[i][j]>=4.5)
					this.A[i][j]=5.0;
			}
		}
	}
}
