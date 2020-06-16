

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

////////////////////////////////
////szereg Taylora dla ln(x)////
////////x z zakresu[0,2]////////
////////////////////////////////

//program 1. - u¿yty do narysowania wykresów 1) - 5)
//wykres 6) zosta³ wygenerowany przy u¿yciu excela

public class App_wykresy extends Application{
	
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
			//System.out.println("i:" + i + ", a1:" + a1 + ", a2:" + a2 + ", w[]:" + w[i]);
			//
			if(Absolute(w[i])<0.00000001) {
				break;
			}
		}
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
	
	//funkcja, która zwraca ró¿nicê tablic
	public static double[] diffArr(double[] Arr1, double[] Arr2, int size){ 
		double[] newArr = new double[size];
		for(int i=0; i<size; i++)
			newArr[i] = Arr1[i]-Arr2[i];
		return newArr;
	}
	
	@Override
	public void start(Stage pStage) throws Exception {
		
		//**********pocz¹tek cudzego kodu**********//
		//*****************************************//
		//*****************************************//
		// skopiowa³em fragment dotycz¹cy definicji osi i wykresów z:
		// https://www.youtube.com/watch?v=h1W-jEqKCew (Professor Saad)
		//define axis
		NumberAxis xAxis = new NumberAxis(0,2,0.1); //(pocz¹tek,koniec,odstêp) 
		//zakresy (0,2,1) // (0,2,0.1)
		xAxis.setLabel("x");
		NumberAxis yAxis = new NumberAxis(-0.000000000000001, 0.000000000000001, 0); //(pocz¹tek,koniec,odstêp)
		//zakresy 
		//(-0.00001, 0.00001, 0) 
		//(-0.00000001, 0.00000003, 0)
		//(-0.00000000000001, 0.00000000000001, 0) 
		//(-0.000000000000001, 0.000000000000001, 0) 
		//(-0.000000000000005, 0.000000000000005, 0.0000000000000005) // (-8,2,1)
		yAxis.setLabel("y");
		LineChart lineChart = new LineChart(xAxis, yAxis);
		Group root = new Group(lineChart);
		//prepare XYCharts.Series objects by setting data
		//1wykres//
		XYChart.Series series1 = new XYChart.Series();
		/*
		series1.setName("1. wyraz liczony ze wzoru, sumowanie od pocz¹tku");
		//2
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("2. wyraz liczony ze wzoru, sumowanie od koñca");
		//3
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("3. wyraz liczony z poprzedniego wyrazu, sumowanie od pocz¹tku");
		//4
		XYChart.Series series4 = new XYChart.Series();
		series4.setName("4. wyraz liczony z poprzedniego wyrazu, sumowanie od koñca");
		//5
		XYChart.Series series5 = new XYChart.Series();
		series5.setName("5. wartoœci z funkcji bibliotecznej");
		*/
		//**************************************//
		//**************************************//
		//*********koniec cudzego kodu**********//
		
		////////////////////
		//////<LOGIKA>//////
		////////////////////
		//ROZMIAR TABLIC
		int size = 1000000;
		//PÊTLE
		int loops = 1000;
		//tablice wyników 
		double Arr1[] = new double[size]; //wzór, pocz
		double Arr2[] = new double[size]; //wzór, kon
		double Arr3[] = new double[size]; //wyraz, pocz
		double Arr4[] = new double[size]; //wyraz, kon
		double Arr5[] = new double[size]; //biblioteczna
		double diffArr[] = new double[size];
		double diffArr2[] = new double[size];
		double diffArr3[] = new double[size];
		//
		double i=0.0;
		double diff = 2.0/size;
		for(int j=0; j<size; i+=diff,j++)
		{
			//Arr1[j] = f1(i, loops)[0]; //wyraz obliczany ZE WZORU, sumowanie od POCZ¥TKU
			Arr2[j] = f1(i, loops)[1]; //wyraz obliczany ZE WZORU, sumowanie od KOÑCA
			//Arr3[j] = f2(i, loops)[0]; //wyraz obliczany Z POPRZEDNIEGO wyrazu, sumowanie od POCZ¥TKU
			Arr4[j] = f2(i, loops)[1]; //wyraz obliczany Z POPRZEDNIEGO wyrazu, sumowanie od KOÑCA
			Arr5[j] = Math.log(i); //f.biblioteczna do porównañ
			diffArr[j] = Absolute(Arr2[j] - Arr5[j]);
			diffArr2[j] = Absolute(Arr4[j] - Arr5[j]);
			diffArr3[j] = diffArr[j] - diffArr2[j];
			//diffArr[j] = diffArr2[j] - diffArr[j];
			
			if(j%100==0) //rysowanie co 100 punktu
				series1.getData().add(new XYChart.Data(i, diffArr3[j]));
			//series2.getData().add(new XYChart.Data(i, diffArr2[j]));
			//series3.getData().add(new XYChart.Data(i, Arr3[j]));
			//series4.getData().add(new XYChart.Data(i, Arr4[j]));
			//series5.getData().add(new XYChart.Data(i, Arr5[j]));			
			//series2.getData().add(new XYChart.Data(i, diffArr2[j]));
		}
		///////////////
		///</LOGIKA>///
		///////////////
		
		lineChart.getData().addAll(series1);
		lineChart.setTitle("");
		lineChart.setMinHeight(900);
		lineChart.setMinWidth(1800);
		
		Scene scene = new Scene(root,1900, 1000);
		scene.getStylesheets().add("style.css");
		pStage.setTitle("wykres");
		pStage.setScene(scene);
		pStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
