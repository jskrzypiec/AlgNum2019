package z2_2;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Random;

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//Generyczna klasa MojaMacierz - zawieraj¹ca m.in. rozmiar n, macierz A rozmiaru nxn, wektor rozwi¹zania - X,
//wektor wyliczanego rozwi¹zania - B, wektor b³êdu bezwzglêdnego miêdzy rozw. a wyliczonym rozw. - 'blad',
//wektor (0,1,...,n-1) przestawieñ kolumny potrzebny przy zamianie kolumn w metodzie Gaussa z pe³nym wyborem
//
//Najwa¿niejszymi metodami klasy MojaMacierz s¹ 3 warianty algorytmu Gaussa-Jordana:
//gauss() - metoda Gaussa bez wyboru elementu podstawowego - (P)
//gaussCzesciowy() - metoda Gaussa z czêœciowym wyborem elementu podstawowego - (PG)
//gaussPalny() - metoda Gaussa z pe³nym wyborem elementu podstawowego - (FG)
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

public class MojaMacierz<T extends Number> {
	Class<T> typGen;
	ArytmC<T> obl;
	char nazwa;
	int n; //rozmiar macierzy
	T[][] A; //macierz A
	T[] X; //wektor rozwi¹zania
	T[] B; //wektor wyliczonego rozwi¹zania
	T[] blad; //wektor b³êdu
	int[] przestaw; //wektor zmiennych (potrzebny do uzyskania rozw w wariancie FG)
	T suma_bledu;
	T zero;
	
	//konstruktor
	public MojaMacierz(int size, char nazwa, Class<T> typGen, T zero) {
		this.typGen = typGen;
		this.obl = new ArytmC<T>(typGen);
		this.nazwa = nazwa;
		this.n = size;
		this.A = (T[][]) Array.newInstance(typGen, size, size+1);  //n,n+1
		this.X = (T[]) Array.newInstance(typGen, size);
		this.B = (T[]) Array.newInstance(typGen, size);
		this.blad = (T[]) Array.newInstance(typGen, size);
		this.zero = zero;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size+1; j++) {
				this.A[i][j] = zero;
			}
			this.X[i] = zero;
			this.B[i] = zero;
			this.blad[i] = zero;
		}
		this.suma_bledu = zero;
	
		this.przestaw = new int[n]; //wektor przestawieñ zmiennych, u¿yty przy zamianie kolumn
		//zainicjowanie wartoœci wektorza 'przestaw' (0,1,2,...,n-1)
		for(int h=0; h<n; h++) {
			przestaw[h] = h;
		}	
	}
	
	//wypiszMac
	public void wypiszMac() {
		System.out.println("Macierz " + this.nazwa + ":");
		for(int i=0; i<n; i++) 
		{
			for(int j=0; j<n+1; j++)
				System.out.print(A[i][j] + "\t");
			System.out.println();
		}
		System.out.println();
	}
	//funkcja wypisuj¹ca rozwi¹zanie - X
	public void wypiszRozwMac() {
		System.out.print("wektor 'Rozw' macierzy " + this.nazwa + ": [");
		for(int i=0; i<n; i++)
		{
			System.out.print(X[i] + "\t");
		}
		System.out.println("]\n");
	}
	//funkcja wypisuj¹ca wektor 'B' (wektor wyliczonego rozwi¹zania)
	public void wypiszB() {
		System.out.print("wektor 'B' macierzy " + this.nazwa + ": [");
		for(int i=0; i<n; i++)
		{
			System.out.print(B[i] + "\t");
		}
		System.out.println("]\n");
	}
	//funkcja wypisuj¹ca wektor 'przestaw'
	public void wypiszWektPrzestaw() {
		System.out.print("wektor 'przestaw' macierzy " + this.nazwa + ": [");
		for(int i=0; i<n; i++)
		{
			System.out.print(przestaw[i] + ", ");
		}
		System.out.println("]\n");
	}
	//funkcja wypisuj¹ca wektor 'blad' - blad wzgledny obliczen
	public void wypiszWektBlad() {
		System.out.print("wektor 'blad' macierzy " + this.nazwa + ": [");
		for(int i=0; i<n; i++)
		{
			System.out.print(blad[i] + ", ");
		}
		System.out.println("]\n");
	}
	
	//funkcja dodaj¹ca
	public void dodajMac() {
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n+1; j++)
			{		
				A[i][j] = (T) obl.dodaj(A[i][j], A[i][j]);
			}
		}
	}
	
	//funkcja losuj¹ca macierz - wypelnia A i X liczbami z zakresu [-1,1)
	public void losujMac() {
		//double r;
		T r;
		T rand_max = (T) obl.typ(65536);
		//Random rand = new Random();
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n+1; j++)
			{	
				r = (T)( obl.nextRandom(131071,65536));
				//r = rand.nextInt(131071) - 65536 ; //(max-min)+max, bo nextInt(int n) zwraca [0;n)
				A[i][j] = (T) obl.podziel((Object)r, rand_max);
				//A[i][j] = (double)r/65536.0;
			}
			X[i] = A[i][n];
		}
	}
	
	//funkcja mno¿¹ca macierz i wektor 
	//w tym przypadku przemno¿enie macierzy A przez wektor X celem uzyskania wektora B
	//po czym z macierzy rozszerzonej [A|B] metod¹ Gaussa-Jordana wyliczone zostanie rozwi¹zanie X
	public void mnozenie() {
		//wyzerowanie ostatniej kolumny
		for(int i=0; i<n; i++) {
			this.A[i][n] = zero;
		}
		//mno¿enie macierzy A przez wektor X
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				this.B[i] = (T) obl.dodaj(this.B[i], (T)obl.mnoz(this.A[i][j], this.X[j]) );
				//this.B[i] += this.A[i][j]*this.X[j];
			}
		}
		//wpisanie B w ostatni¹ kolumnê macierzy A 
		for(int i=0; i<n; i++) {
			this.A[i][n] = this.B[i];
		}
	}
		
	//klonowanie macierzy
	public MojaMacierz klonujMac(char nazwa) 
	{
		int n = this.n;
		MojaMacierz klon = new MojaMacierz(n, nazwa, this.typGen, this.zero);
		//kopiowanie 'mac'
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n+1; j++)
			{
				klon.A[i][j] = this.A[i][j];
			}
			//kopiowanie B
			klon.B[i] = this.B[i];
			//kopiowanie 'rozw'
			klon.X[i] = this.X[i];
		}
		return klon;
	}
	//klasa do sklonowania macierzy typu Double do macierzy typu Float
	public MojaMacierz klonujMacTDtoTF(char nazwa)
	{
		int n = this.n;
		MojaMacierz klon = new MojaMacierz(n, nazwa, Float.class, 0.0f);
		//kopiowanie 'mac'
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n+1; j++)
			{
				klon.A[i][j] = ((Double)this.A[i][j]).floatValue();
			}
			//kopiowanie B
			klon.B[i] = ((Double) this.B[i]).floatValue();
			//kopiowanie 'rozw'
			klon.X[i] = ((Double) this.X[i]).floatValue();
		}
		return klon;
	}
		
	//funkcja wyliczaj¹ca b³¹d bezwzglêdny wyliczonego rozw. B na podstawie rozw. X
	public void wyliczBladBezwzgl() {
		double suma = 0.0;
		for(int i=0; i<n; i++) {
			this.blad[i] = (T) obl.Absolute((T)obl.odejmij(this.B[i], this.X[i]));
			//this.blad[i] = Absolute(this.B[i] - this.X[i]);
			this.suma_bledu = (T) obl.dodaj(this.suma_bledu, this.blad[i]);
			//suma += this.blad[i];
		}
	}
		
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	//funkcje potrzebne do ró¿nych wariantów://////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	//uzyskanie rozwi¹zania (do G i PG)
	public void uzyskajRozw() {
		for(int i=0; i<n; i++)
		{
			B[i] = (T) obl.podziel(A[i][n], A[i][i]);
			//B[i] = A[i][n]/A[i][i];
		}
	}
	//dalsza czêœæ redukcji Gaussa (gdy jesteœmy w 1 pêtli, tutaj najczêœciej i)
	public void dalszaCzRedGaussa(int i, T ratio) {
		for(int j=0; j<n; j++) 
		{
			if(i!=j) 
			{
				ratio = (T) obl.podziel(A[j][i], A[i][i]);
				//ratio = A[j][i]/A[i][i] ;
				for(int k=i; k<n+1; k++) 
				{
					A[j][k] = (T) obl.odejmij( A[j][k] , (T)obl.mnoz(ratio, A[i][k]) );
					//A[j][k] = A[j][k] - ratio*A[i][k];
				}
			}
		}
	}
	//zamiana wierszy
	public void zamienWiersze(int w1, int w2, int odKtoregoInd) {
		T temp = this.zero;
		for(int q=odKtoregoInd; q<n+1; q++) {
			temp = A[w1][q];
			A[w1][q] = A[w2][q];
			A[w2][q] = temp;
		}
	}
		
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////---GAUSS---//////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	public void gauss()
	{
		//wyliczenie B
		this.mnozenie();
		//redukcja Gaussa
		T ratio = zero;
		for(int i=0; i<n; i++) 
		{
			if(A[i][i]==zero && i<n-1) //je¿eli na g³ównej diagonalnej jest 0 w obecnym miejscu to zamien wiersze z wierszem ni¿ej
			{ 
				//System.out.println("Error!");
				//zamiana wierszy
				this.zamienWiersze(i, i+1, i);
			}
			//dalsza czêœæ redukcji Gaussa
			this.dalszaCzRedGaussa(i, ratio);
		}
		//uzyskanie rozwi¹zania
		this.uzyskajRozw();
		//wyliczenie bledu
		this.wyliczBladBezwzgl();
	}
		
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////---GAUSS CZÊŒCIOWY WYBÓR (PARTIAL SELECTION)--//////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	public void gaussCzesciowy()
	{
		//wyliczenie B
		this.mnozenie();
		//redukcja Gaussa
		T ratio = zero;
		int max = 0;
		for(int i=0; i<n; i++) 
		{
			//szukania maksa w kolumnie poni¿ej
			max = i;
			for(int p=i+1; p<n; p++) {
				//if(Absolute(A[max][i]) < Absolute(A[p][i]) )
				if(obl.czyRowne(obl.Absolute(A[max][i]) , obl.Absolute(A[p][i])) == -1) {
					max = p;
				}
			}
			//zamiana wiersza
			this.zamienWiersze(i, max, i);
			if(A[i][i]==zero && i<n-1) //je¿eli na g³ównej diagonalnej WCI¥¯ jest 0 w obecnym miejscu to zamien wiersze z wierszem ni¿ej
			{ 
				//System.out.println("Error!");
				this.zamienWiersze(i, i+1, i);
			}
			//dalsza czêœæ redukcji Gaussa
			this.dalszaCzRedGaussa(i, ratio);
		}
		//uzyskanie rozwi¹zania
		this.uzyskajRozw();
		//wyliczenie bledu
		this.wyliczBladBezwzgl();
	}
		
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////---GAUSS PE£NY WYBÓR (FULL SELECTION)--/////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	public void gaussPelny()
	{
		//wyliczenie B
		this.mnozenie();
		//redukcja Gaussa
		T ratio = zero;
		int imax=0, jmax=0, ptemp=0;
		for(int i=0; i<n; i++) 
		{
			imax = i;
			jmax = i;
			//szukania maksa w kolumnie poni¿ej
			for(int p=i; p<n; p++) {
				for(int q=i; q<n; q++) {
					if(q!=i || p!=i) {
						//System.out.println("A[imax][jmax]:"+Absolute(A[imax][jmax]) + "\tA[p][q]:"+Absolute(A[p][q]));
						//if(Absolute(A[imax][jmax])<Absolute(A[p][q]) && Absolute(A[p][q])!=0.0) {
						if(obl.czyRowne(obl.Absolute(A[imax][jmax]), obl.Absolute(A[p][q]))==-1 && obl.Absolute(A[p][q])!=zero) {
							imax = p;
							jmax = q;
							//System.out.println("nowy max:" + A[imax][jmax]);
						}
					}
				}
			}
			//System.out.println("max: " + A[imax][jmax]);
			//zamiana wiersza macierzy 'A' je¿eli jest taka potrzeba
			if(imax!=i) {
				this.zamienWiersze(i, imax, i);
			}	
			/*System.out.println("-po zamianie wiersza-");
			this.wypiszMac();*/
			//zamiana kolumny macierzy 'A' je¿eli jest taka potrzeba
			//jezeli zamieniamy kolumne to zmieniamy tez wartosci w wektorze 'przestaw'
			if(jmax!=i) {//bo i tak przek¹tna
				T temp = zero;
				for(int a=0; a<n; a++) {
					temp = A[a][i];
					A[a][i] = A[a][jmax];
					A[a][jmax] = temp;
				}
				//przestawienie w wektorze 'przestaw'
				ptemp = przestaw[i];
				przestaw[i] = przestaw[jmax];
				przestaw[jmax] = ptemp;
			}
			if(A[i][i]==zero && i<n-1) //je¿eli na g³ównej diagonalnej WCI¥¯ jest 0 w obecnym miejscu to zamien wiersze z wierszem ni¿ej
			{ 
				//System.out.println("Error!");
				this.zamienWiersze(i, i+1, i);
			}
			//dalsza czêœæ redukcji Gaussa
			this.dalszaCzRedGaussa(i, ratio);
		}
		//uzyskanie rozwi¹zania na podstawie zredukowanej macierzy 'A' i wektora przestawieñ 'przestaw'
		for(int i=0; i<n; i++) {
			//sprawdzenie dla którego indexu wektora 'przestaw' mamy wybrac rozwiazanie
			for(int j=0; j<n; j++) {
				if(przestaw[j]==i) {
					ptemp = j;
					//System.out.println("-jjj:" + ptemp);
					break;
				}
			}
			//wyliczenie rozwi¹zania
			B[i] = (T) obl.podziel(A[ptemp][n], A[ptemp][ptemp]);
			//B[i] = A[ptemp][n] / A[ptemp][ptemp];
		}
		//wyliczenie bledu
		this.wyliczBladBezwzgl();
	}
	
	//
}

