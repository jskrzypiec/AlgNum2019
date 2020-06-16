package z3;

//g³owna klasa projektu - prezentuje dzia³anie Parsera i metody ALS 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Z3App {
	public static void main(String[] args) throws IOException{
		//testy funkcji ALS
		/*
		Macierz R = new Macierz('R',3,4);
		R.A[0][0] = 0; R.A[0][1] = 4.5; R.A[0][2] = 2.0; R.A[0][3] = 0;
		R.A[1][0] = 4.0; R.A[1][1] = 0; R.A[1][2] = 3.5; R.A[1][3] = 0;
		R.A[2][0] = 0; R.A[2][1] = 5.0; R.A[2][2] = 0; R.A[2][3] = 2.0;
		//R.wypiszMac();
		//
		Macierz P = new Macierz('P',d, R.m);
		Macierz U = new Macierz('U',d, R.n);
		P.A[0][0] = 1.1; P.A[0][1] = 1.2; P.A[0][2] = 1.3; P.A[0][3] = 1.4;
		P.A[1][0] = 1.5; P.A[1][1] = 1.6; P.A[1][2] = 1.7; P.A[1][3] = 1.8;
		P.A[2][0] = 1.9; P.A[2][1] = 2.0; P.A[2][2] = 2.1; P.A[2][3] = 2.2;
		U.A[0][0] = 2.3; U.A[0][1] = 2.4; U.A[0][2] = 2.5;
		U.A[1][0] = 2.6; U.A[1][1] = 2.7; U.A[1][2] = 2.8; 
		U.A[2][0] = 2.9; U.A[2][1] = 3.0; U.A[2][2] = 3.1;
		P.wypiszMac();
		U.wypiszMac();
		//
		Macierz newR = R.ALS(3, 0.1);
		R.wypiszMac();
		newR.wypiszMac();
		*/

		//parser
		Parser p = new Parser();
		List<Produkt> podlista = p.dajPodliste(10);
		//utworzenie macierzy R
		Macierz R = p.dajMacierz(podlista);
		//R.wypiszMac();
		//testy 
		for(int d=1; d<21; d++) {
			System.out.println("d:"+d);
			Macierz newR = R.ALS(d, 0.1);
		}
		/*Macierz newR = R.ALS(2, 0.1);
		newR.wypiszMac();
		newR.round();
		newR.wypiszMac();*/
	}
}
