# Algorytmy Numeryczne - 5 semestr informatyki I stopnia UG
## Jakub Skrzypiec (@jakub.skrzypiec)

Programy z repozytorium są moimi rozwiązaniami zadań na laboratoria przedmiotu 'Algorytmy Numeryczne', które odbywały się na 5 semestrze studiów stacjonarnych I stopnia na Uniwersytecie Gdańskim.  

W katalogu każdego z zadań znajduje się pełna treść zadania, rozwiązanie oraz sprawozdanie.  

---

### **Zadanie 1: Sumowanie szeregów potęgowych (10.2019r.)**

##### Treść zadania:  
Napisz program obliczający wartości funkcji ln(x) na 4 sposoby:
1. sumując elementy szerego potęgowego obliczane bezpośrednio ze wzoru Taylora w kolejności od początku,  
2. sumując elementy szeregu potęgowego obliczane bezpośrednio ze wzoru Taylora w kolejności od końca,  
3. sumując elementy szeregu potęgowego od początku, ale obliczając kolejny wyraz szeregu na podstawie poprzedniego,  
4. sumując elementy szeregu potęgowego od końca, ale obliczając kolejny wyraz szeregu na podstawie poprzedniego.  
  
Zweryfikuj hipotezy:  
H1: sumowanie od końca daje dokładniejsze wyniki niż sumowanie od początku.  
H2: używając rozwinięcia wokół 0 (szereg MacLaurina), przy tej samej liczbie składników szeregu dokładniejsze wyniki uzyskujemy przy małych argumentach.  
H3: sumowanie elementów obliczanych na podstawie poprzedniego daje dokładniejsze wyniki niż obliczanych bezpośrednio ze wzoru.  
  
Odpowiedz na pytanie:  
Q1: Jak zależy dokładność obliczeń (błąd) od liczby sumowanych składników?  
  
---

### **Zadanie 2: Rozwiązywanie układów równań liniowych metodą eliminacji Gaussa (11.2019r.)**

##### Treść zadania:  
Napisz klasę MojaMacierz reprezentującą macierz nad ciałem liczb rzeczywistych.  
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

### **Zadanie 3: Metoda ALS w systemach rekomendacji (11-12.2019r.)**

##### Treść zadania:
W wybranym przez siebie języku programowania zaimplementuj metodę ALS korzystając z gotowej metody Gaussa z drugiego zadania.  

Dane testowe:  
Do testów proszę wykorzystać prawdziwe, archiwalne, zanonimizowane dane o produktach i rekomendacjach ze sklepu Amazon udostępnione w ramach projektu [SNAP](https://snap.stanford.edu/data/amazon-meta.html). Plik zawiera rekordy dotyczące ponad 500 000 produktów i łącznie ponad 7 mld ocen.  

Testy proszę przeprowadzić na 3 wybranych samodzielnie podzbiorach danych:  
S: małym, zawierającym od 10 do 100 produktów,  
M: średnim, około 10 razy większym niż mały, zawierającym od 100 do 1000 produktów,  
*B: dużym, około 10 razy większym niż średni, zawierającym więcej niż 1000 produktów.  

Dla każdego zbioru produktów należy dobrać użytkowników (być może nie wszystkich), którzy oceniali produkty z danej grupy.  

---

