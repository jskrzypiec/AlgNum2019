# Algorytmy Numeryczne - 5 semestr informatyki I stopnia UG
## Jakub Skrzypiec (@jakub.skrzypiec)

### **Zadanie 2: Rozwiązywanie układów równań liniowych metodą eliminacji Gaussa (11.2019r.)**
##### Treść zadania:  
Zdefiniuj klasę MojaMacierz reprezentującą macierz nad ciałem liczb rzeczywistych.  
Zaimplementuj algorytm eliminacji Gaussa w następujących wariantach:  
G: bez wyboru elementu podstawowego  
PG: z częściowym wyborem elementu podstawowego  
*FG: z pełnym wyborem elementu podstawowego  

Wszystkie testy należy przeprowadzić używając następujących  typów reprezentujących liczbę rzeczywistą:  
TF: typu pojedynczej precyzji: float  
TD: typu podwójnej precyzji: double  
*TC: własnego typu, który przechowuje liczbę w postaci ułamka liczb całkowitych - w tej wersji wszystkie operacje powinny być wykonywane bez utraty precyzji.  

Dla losowej macierzy kwadratowej A i wektora B, rozwiąż układ równań liniowych  
A · X = B  
korzystając z zaimplementowanych  wariantów metody Gaussa w klasie MojaMacierz. Jako współczynniki macierzy A i wektora X proszę przyjąć pseudolosowe liczby zmiennoprzecinkowe z przedziału [-1,1) otrzymane jako iloraz r/2^16, gdzie r jest pseudolosową liczbą całkowitą z przedziału: {-2^16, 2^16-1}. Wektor B obliczamy jako B := A·X. Macierz A i wektor B podajemy jako parametry do rozwiązania układu równań, wektor X zachowujemy jako rozwiązanie wzorcowe.  

Zweryfikuj hipotezy:  
H1: Dla dowolnego ustalonego rozmiaru macierzy czas działania metody Gaussa w kolejnych wersjach (G, PG, FP) rośnie.  
H2: Dla dowolnego ustalonego rozmiaru macierzy błąd uzyskanego wyniku metody Gaussa w kolejnych wersjach (G, PG, FG) maleje.  
H3: Użycie własnej arytmetyki na ułamkach zapewnia bezbłędne wyniki niezależnie od wariantu metody Gaussa.  

Odpowiedz na pytania:  
Q1: Jak zależy dokładność obliczeń (błąd) od rozmiaru macierzy dla dwóch wybranych przez Ciebie wariantów metody Gaussa, gdy obliczenia prowadzone są na typie podwójnej precyzji (TD)?  
Q2: Jak przy wybranym przez Ciebie wariancie metody Gaussa zależy czas działania algorytmu od rozmiaru macierzy i różnych typów?  

Wydajność implementacji:  
E1: Podaj czasy rozwiązania układu równań uzyskane dla macierzy o rozmiarze 500 dla 9 testowanych wariantów.  

---

## Build With
- Java

## Autor
- Jakub Skrzypiec (@jakub.skrzypiec - jakub.skrzypiec1@gmail.com)

