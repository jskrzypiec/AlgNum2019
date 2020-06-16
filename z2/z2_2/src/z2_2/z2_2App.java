package z2_2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//klasa z2_2App - g³ówna klasa aplikacji s³u¿¹ca do wywo³ywania metod klasy generycznej MojaMacierz
//oraz do generowania danych do plików
//na podstawie plików zosta³y utworzone wykresy w sprawozdaniu
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

public class z2_2App {
	public static void main(String[] args) {
		
		//H1
		/*
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
			//System.out.print("\tG\tGP\tFP\n");
			writer.write("\tFG\tPG\tG\n");
			String line = "";
			
			long czas1 = 0;
			long czas2 = 0;
			long czas3 = 0;
			long start = 0;
			long koniec = 0;
			for(int i=2; i<500; i++) {
				MojaMacierz<Float> D = new MojaMacierz<Float>(i, 'D', Float.class, 0.0f);
				D.losujMac();
				MojaMacierz<Float> A;
				MojaMacierz<Float> B;
				MojaMacierz<Float> C;
			
				//for(int j=0; j<3; j++) {
					A = D.klonujMac('A');
					B = D.klonujMac('B');
					C = D.klonujMac('C');
					//start czas - G
					start = System.currentTimeMillis();
					A.gaussPelny();
					koniec = System.currentTimeMillis();
					czas1 = koniec-start;
					//System.out.print("czas1:" + czas1 + "ms");
					//start czas - PG
					start = System.currentTimeMillis();
					B.gaussCzesciowy();
					koniec = System.currentTimeMillis();
					czas2 = koniec-start;
					//System.out.print(",czas2:" + czas2 + "ms");
					//start czas - FG
					start = System.currentTimeMillis();
					C.gauss();
					koniec = System.currentTimeMillis();
					czas3 = koniec-start;
					//System.out.println(",czas3:" + czas3 + "ms");
				//}
				//System.out.println("i:" + i + ",czas1:" + czas1 + ",czas2:" + czas2 + ",czas3:" + czas3);
				System.out.println(i);
				line = i + "\t" + czas1 + "\t" + czas2 + "\t" + czas3 + "\n";
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//H1
		
		//H2
		/*
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
			//System.out.print("\tG\tGP\tFP\n");
			writer.write("\tG\tPG\tFG\n");
			String line = "";
			for(int i=3; i<1004; i+=10) {
				MojaMacierz<Double> A = new MojaMacierz<Double>(i, 'A', Double.class, 0.0d);
				A.losujMac();
				MojaMacierz<Double> B = A.klonujMac('B');
				MojaMacierz<Double> C = A.klonujMac('C');
			
				A.gauss();
				B.gaussCzesciowy();
				C.gaussPelny();

				//System.out.print(i + "\t" + A.suma_bledu + "\t" + B.suma_bledu + "\t" + C.suma_bledu + "\n");
				System.out.println(i);
				line = i + "\t" + A.suma_bledu + "\t" + B.suma_bledu + "\t" + C.suma_bledu + "\n";
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//H2
		
		//Q1
		/*
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
			//System.out.print("\tGP\tFP\n");
			writer.write("\tG\tPG\n");
			String line = "";
			for(int i=3; i<1004; i+=10) {
				MojaMacierz<Double> A = new MojaMacierz<Double>(i, 'A', Double.class, 0.0d);
				A.losujMac();
				MojaMacierz<Double> B = A.klonujMac('B');
						
				A.gauss();
				B.gaussCzesciowy();
						
				
				//System.out.print(i + "\t" + A.suma_bledu + "\t" + B.suma_bledu + "\n");
				System.out.println(i);
				line = i + "\t" + A.suma_bledu + "\t" + B.suma_bledu + "\n";
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//Q1
		
		//Q2
		/*
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
			//System.out.print("\tTF\tTD\n");
			writer.write("\tTF\tTD\n");
			String line = "";
			
			long czas1 = 0; 
			long czas2 = 0;
			long start = 0;
			long koniec = 0;
			for(int i=2; i<501; i++) {
				MojaMacierz<Double> C = new MojaMacierz<Double>(i, 'C', Double.class, 0.0d);
				C.losujMac();
				MojaMacierz<Float> D = C.klonujMacTDtoTF('D');
				MojaMacierz<Double> A;
				MojaMacierz<Float> B;
				
				for(int j=0; j<3; j++) {
					A = C.klonujMac('A');
					B = D.klonujMac('B');
					//start czas - float
					start = System.currentTimeMillis();
					B.gauss();
					koniec = System.currentTimeMillis();
					czas1 = koniec-start;
					//System.out.print("czas1:" + czas1 + "ms");
					//start czas - double
					start = System.currentTimeMillis();
					A.gauss();
					koniec = System.currentTimeMillis();
					czas2 = koniec-start;
					//System.out.print(",czas2:" + czas2 + "ms");
				}
				//System.out.println("i:" + i + ",czas1:" + czas1 + ",czas2:" + czas2);
				System.out.println(i);
				line = i + "\t" + czas1 + "\t" + czas2 + "\n";
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//Q2
		
		//E1
		/*
		//wyliczenie œrednich czasu do 6 wariantów na podstawie 20 pomiarów dla ka¿dego wariantu
		MojaMacierz<Double> Z = new MojaMacierz<Double>(500, 'Z', Double.class, 0.0d); 
		Z.losujMac();
		MojaMacierz<Double> A; //TF - G
		MojaMacierz<Double> B; //TF - PG
		MojaMacierz<Double> C; //TF - FP
		MojaMacierz<Float> D; //TD - G
		MojaMacierz<Float> E; //TD - PG
		MojaMacierz<Float> F; //TD - FG
		//
		long czas[] = new long[6];
		long sr_czasu[] = new long[6];
		for(int i=0; i<6; i++) {
			czas[i] = 0;
			sr_czasu[i] = 0;
		}
		long start = 0;
		long koniec = 0;
		for(int j=0; j<10; j++) {
			A = Z.klonujMac('A');
			B = Z.klonujMac('B');
			C = Z.klonujMac('C');
			D = Z.klonujMacTDtoTF('D');
			E = Z.klonujMacTDtoTF('D');
			F = Z.klonujMacTDtoTF('D');
			//start czas - 1
			start = System.currentTimeMillis();
			A.gaussPelny();
			koniec = System.currentTimeMillis();
			czas[0] = koniec-start;
			sr_czasu[0] += czas[0];
			System.out.print("czas[0]:" + czas[0] + "ms");
			//start czas - 2
			start = System.currentTimeMillis();
			B.gaussCzesciowy();
			koniec = System.currentTimeMillis();
			czas[1] = koniec-start;
			sr_czasu[1] += czas[1];
			System.out.print(",czas[1]:" + czas[1] + "ms");
			//start czas - 3
			start = System.currentTimeMillis();
			C.gauss();
			koniec = System.currentTimeMillis();
			czas[2] = koniec-start;
			sr_czasu[2] += czas[2];
			System.out.print(",czas[2]:" + czas[2] + "ms");
			//start czas - 4
			start = System.currentTimeMillis();
			D.gaussPelny();
			koniec = System.currentTimeMillis();
			czas[3] = koniec-start;
			sr_czasu[3] += czas[3];
			System.out.print(",czas[3]:" + czas[3] + "ms");
			//start czas - 5
			start = System.currentTimeMillis();
			E.gaussCzesciowy();
			koniec = System.currentTimeMillis();
			czas[4] = koniec-start;
			sr_czasu[4] += czas[4];
			System.out.print(",czas[4]:" + czas[4] + "ms");
			//start czas - 6
			start = System.currentTimeMillis();
			F.gauss();
			koniec = System.currentTimeMillis();
			czas[5] = koniec-start;
			sr_czasu[5] += czas[5];
			System.out.println(",czas[5]:" + czas[5] + "ms");
		}
		for(int i=0; i<6; i++) {
			sr_czasu[i] /= 10;
			System.out.println(sr_czasu[i]);
		}*/
		//E1
		
		//sprawdzenie, ¿e u¿ycie Ulamka eliminuje b³êdy ca³kowicie
		/*
		MojaMacierz<Ulamek> A = new MojaMacierz<Ulamek>(20,'Z', Ulamek.class, new Ulamek());
	
		A.wypiszMac();
		A.losujMac();
		A.wypiszMac();
		
		//A.gaussPelny();
		//A.gaussCzesciowy();
		A.gauss();
		
		A.wypiszMac();
		
		A.wypiszRozwMac();
		A.wypiszB();
		A.wypiszWektBlad();
		*/
		//sprawdzenie, ¿e u¿ycie Ulamka eliminuje b³êdy ca³kowicie
		
		//sprawdzenie czasu dla typu Ulamek
		/*
		for(int i=2; i<50; i++) {
			MojaMacierz<Ulamek> A = new MojaMacierz<Ulamek>(i,'Z', Ulamek.class, new Ulamek());
			A.losujMac();
			long czas = 0;
			//czas start
			long start = System.currentTimeMillis();
			A.gauss();
			long koniec = System.currentTimeMillis();
			//
			czas = koniec - start;
			System.out.println("Czas("+i+"):" + czas);
			A.wypiszRozwMac();
			A.wypiszB();
			A.wypiszWektBlad();
		}*/
		//sprawdzenie czasu dla typu Ulamek
		
		/*
		MojaMacierz<Double> A = new MojaMacierz<Double>(10,'Z', Double.class, 0.0d);
		A.losujMac();
		MojaMacierz<Double> B = A.klonujMac('B');
		MojaMacierz<Double> C = A.klonujMac('C');
		
		A.gauss();
		B.gaussCzesciowy();
		C.gaussPelny();
		
		A.wypiszRozwMac();
		A.wypiszB();
		B.wypiszB();
		C.wypiszB();
		A.wypiszWektBlad();
		B.wypiszWektBlad();
		C.wypiszWektBlad();
		*/
	}
}
