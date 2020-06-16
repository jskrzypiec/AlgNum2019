package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
////////////////////////////////
//  szereg Taylora dla ln(x)  //
//      x z zakresu[0,2]      //
////////////////////////////////

//program 2 - u¿yty do testów na liczbach oraz zapisu danych do pliku

public class Test {

	//funkcja do obliczania wartosci bezwzglednej dla double
	public static double Absolute(double x) {
		if(x==-0 || x==+0)
			return +0;
		else if(x==Double.POSITIVE_INFINITY || x==Double.NEGATIVE_INFINITY)
			return Double.POSITIVE_INFINITY;
		else if(x<0)
			return -x;
		return x;
	}
	
	//funkcja do sumowania od pocz¹tku
	public static double f1_1(double[] w,int n) {
		double suma=0.0;
		for(int i=0; i<n; i++) {
			suma += w[i];
		}
		return suma;
	}
		
	//funkcja do sumowania od koñca
	public static double f1_2(double[] w,int n) {
		double suma=0.0;
		for(int i=n-1; i>=0; i--) {
			suma += w[i];
		}
		return suma;
	}
	
	//funkcja do sumowania tablic
	public static double[] sum(double[] w, int i, double[] sums) {
		//sumowanie od POCZATKU
		sums[0] = f1_1(w, i);
		//System.out.println(sums[0] + " (ze wzoru, od pocz¹tku)");
				
		//sumowanie do KOÑCA
		sums[1] = f1_2(w, i);
		//System.out.println(sums[1] + " (ze wzoru, od koñca)");
		return sums;
	}
	
	/////////////////////////////////////////////////////
	//funkcja liczaca kolejne wyrazy na podstawie WZORU//
	/////////////////////////////////////////////////////
	public static double[] f1(double x, int n) {
		double[] w = new double[n]; //tablica wyrazów
		//double sum1=0.0, sum2=0.0;
		double sums[] = new double[2];
		double a1=0.0, a2=0.0, a2temp=0.0; //a2temp - podstawa do potegowania a2
		int i=0;
		for(i=0; i<n; i++) {
			if(i%2 == 0)
				a1=1;
			else 
				a1=-1;
			a2 = x-1;
			a2temp = a2;
			for(int j=0; j<i; j++)
				a2 = a2*a2temp;
			w[i] = (a1*a2)/(i+1);
			System.out.println("i:" + i + ", a1:" + a1 + ", a2:" + a2 + ", w[]:" + w[i]);
			//
			if(Absolute(w[i])<0.000001) {
				break;
			}
		}
		System.out.println("i:"+i);
		//sumowanie + wypisanie
		sum(w,i, sums);
		
		return sums;
	}
	
	///////////////////////////////////////////////////////////////////
	//funkcja liczaca kolejne wyrazy na podstawie POPRZEDNIEGO WYRAZU//
	///////////////////////////////////////////////////////////////////
	public static double[] f2(double x, int n) {
		double[] w = new double[n]; //tablica wyrazów
		//double sum1=0.0, sum2=0.0;
		double sums[] = new double[2];
		double nextWyr = 0.0; //wyraz wykorzystywyany do obliczenia nastepnego elementu
		//obliczenie pierwszego elementu
		//double a1 = 1;
		//double a2 = x-1; //n=1
		w[0] = (x-1); //pierwszy wyraz
		//System.out.println("i:" + 0 + ", w[]:" + w[0]);
		
		int i;
		for(i=1; i<n; i++) {
			nextWyr = ((-i) * (x-1)) /(i+1);
			w[i] = w[i-1] * nextWyr;
			//System.out.println("i:" + i + ", nextWyr:" + nextWyr + ", w[]:" + w[i]);	
			//
			if(Absolute(w[i])<0.00000001) {
				break;
			}
		}
		//sumowanie + wypisanie
		sum(w,i, sums);
		
		return sums;
	}
	
	public static double[] diffArr(double[] Arr1, double[] Arr2, int size){ //wraca ró¿nicê tablic
		double[] newArr = new double[size];
		for(int i=0; i<size; i++)
			newArr[i] = Arr1[i]-Arr2[i];
		return newArr;
	}
	
	/////////////////////////////////
	////////////// MAIN /////////////
	/////////////////////////////////
	public static void main(String[] args) throws IOException {
		//ROZMIAR TABLIC
		//int size = 1000000;
		
		
		//////////////////////////////////////////////////////////////////////////////////////
		//fragment do q1 - badanie b³êdu bezwzglêdnego w zale¿noœci od sumowanych argumentów//
		//////////////////////////////////////////////////////////////////////////////////////
		
		/*
		int arrays = 15;
		double tab[][] = new double[arrays][size];
		
		double x=0.0000001;
		double diff=2.0/size;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
		String line;
		
		for(int i=0; i<size; i++, x+=diff) { //0...999999
			//x=0.0;
			line = "";
			for(int j=0; j<arrays; j++) { //0...14
				//tab[j][i] = f2(x, j+1)[0];
				tab[j][i] = Absolute( f2(x, j+1)[0] - Math.log(x) );
				//System.out.print(tab[j][i] + "\t");
				line = line + tab[j][i] + "\t";
			}
			//System.out.print("\n");
			line = line + "\n";
			writer.write(line);
		}
		writer.close();*/
		
		
		///////////////////////////////////////////////////////////
		// fragment to testów liczbowych oraz rozpatrzenia h1,h3 //
		///////////////////////////////////////////////////////////
	
		//PÊTLE
		/*int loops = 1000;
		double Arr1[] = new double[size];
		double Arr2[] = new double[size];
		double Arr3[] = new double[size];
		double Arr4[] = new double[size];
		double Arr5[] = new double[size];
		double diffArr1[] = new double[size];
		double diffArr2[] = new double[size];
		double diffArr3[] = new double[size];
		double diffArr4[] = new double[size];
		int wieksza=0, mniejsza=0, rowna=0;
		//
		double i=0.0;
		double diff = 2.0/size;
		
		for(int j=0; j<size; i+=diff,j++)
		{
			 Arr1[j] = f1(i, loops)[0];
			 //Arr2[j] = f1(i, loops)[1];
			 Arr3[j] = f2(i, loops)[0];
			 //Arr4[j] = f2(i, loops)[1];
			 Arr5[j] = Math.log(i); //f.biblioteczna do porównañ
			 diffArr1[j] = Absolute(Arr5[j] - Arr1[j]);
			 diffArr2[j] = Absolute(Arr5[j] - Arr3[j]);
			 //diffArr3[j] = Absolute(diffArr1[j] - diffArr2[j]);
			 //diffArr4[j] = Absolute(Arr5[j] - Arr4[j]);
			 if(diffArr1[j] < diffArr2[j])
				 mniejsza++;
			 else if (diffArr2[j] < diffArr1[j])
				 wieksza++;
			 else
				 rowna++;
			 //System.out.println("x:"+i + " " + Arr1[j]+"-"+Arr5[j]+"="+diffArr1[j]);
			 //System.out.println("x:"+i + " " + Arr1[j]+"/"+Arr2[j]+"/"+Arr5[j]+"=="+diffArr1[j]+"//"+diffArr2[j]+"//"+diffArr3[j]);
			 //System.out.println("x:"+i + " " + diffArr1[j]+"-"+diffArr2[j]+"="+diffArr3[j]);
		}
		System.out.println("mniejsza:"+mniejsza + ", wieksza:"+wieksza + ", równa:"+rowna);
		*/
		
		//f1(double x, int n)
		f1(1.999998, 1000000);
	}
}


