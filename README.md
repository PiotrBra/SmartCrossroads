# Symulacja Inteligentnych Świateł Drogowych

Projekt **Symulacja Inteligentnych Świateł Drogowych** to system, który dynamicznie zarządza sygnalizacją świetlną na skrzyżowaniu, reagując na natężenie ruchu i liczbę pojazdów oczekujących na przejazd. Rozwiązanie zostało zaprojektowane i zaimplementowane w języku Java, przy wykorzystaniu nowoczesnych technologii oraz podejścia zorientowanego na testy i rozszerzalność systemu.

## Spis Treści

- [Opis projektu](#opis-projektu)
- [Algorytm zarządzania światłami](#algorytm-zarządzania-światłami)
- [Wymagania](#wymagania)
- [Instalacja i Uruchomienie](#instalacja-i-uruchomienie)
- [Testy](#testy)
- [Przykłady Wejścia/Wyjścia](#przykłady-wejściawyjścia)
- [Podsumowanie](#podsumowanie)



## Opis projektu

Celem projektu jest stworzenie symulacji inteligentnych świateł drogowych na skrzyżowaniu,
które posiada cztery drogi dojazdowe (północ, południe, wschód, zachód). 
System zarządza cyklem świateł oraz umożliwia wykonanie symulacji na podstawie listy komend zapisanych w formacie JSON.
Każda komenda definiuje akcję wykonywaną na skrzyżowaniu.

## Algorytm zarządzania światłami

Algorytm opiera się na dynamicznym przydzielaniu zielonego światła do par dróg, które są najbardziej obciążone. Poniżej przedstawiono główne założenia algorytmu:

1. **Zielone światło zmienia się w zależności od liczby pojazdów:**  
   Zielone światło przydzielane jest do drogi, na której pojazdy oczekują najdłużej lub jest ich najwięcej.

2. **Maksymalny czas zielonego światła:**  
   Światło w danym kierunku nie może pozostać zielone dłużej niż określony czas. Nawet jeśli pojazdy nadal czekają, po upływie tego czasu następuje zmiana.

3. **Zmiana świateł na czerwone:**  
   Gdy minie czas zielonego światła lub nie ma już oczekujących pojazdów, wszystkie światła zmieniają się na czerwone. Następnie algorytm wybiera nową parę dróg (np. północ-południe lub wschód-zachód) na podstawie największej liczby oczekujących pojazdów.

4. **Wybór najbardziej obleganego kierunku:**  
   Algorytm sumuje liczbę pojazdów oczekujących na drodze i jej przeciwnym kierunku. Para o największej sumie otrzymuje zielone światło, co zapewnia optymalny przepływ ruchu.

5. **Brak pojazdów – światła czerwone:**  
   Jeśli na żadnej drodze nie ma oczekujących pojazdów, wszystkie światła pozostają czerwone.

6. **Przydzielanie zielonego światła i oczekiwanie:**  
   Po przydzieleniu zielonego światła, algorytm sprawdza czy w danym kierunku pojazdy nadal oczekują. Jeśli tak, licznik kroków jest zwiększany, a w przeciwnym przypadku następuje reset i wybór nowej pary dróg.

### Metody pomocnicze

- **`findBusiestRoadPair(Map<Direction, Road> roads)`** – Znajduje parę dróg (np. północ-południe lub wschód-zachód) z największą liczbą oczekujących pojazdów.
- **`countWaitingVehicles(Map<Direction, Road> roads, Direction direction)`** – Sumuje pojazdy oczekujące na drodze i jej przeciwnej.
- **`areAllRoadsEmpty(Map<Direction, Road> roads, Direction direction)`** – Sprawdza, czy w danej parze dróg nie ma żadnych oczekujących pojazdów.
- **`getOppositeDirection(Direction direction)`** – Zwraca przeciwny kierunek (np. dla NORTH – SOUTH).


## Wymagania

- **Java:** Wersja 11 lub nowsza.
- **Gradle:** Narzędzie do budowania projektu.
- **Biblioteki:**
    - JUnit 5 – testy jednostkowe oraz integracyjne.
    - Mockito – do mockowania zależności.
    - JSONAssert – porównywanie wyników JSON.
- **Format danych JSON:**  
  Wejściowy plik JSON zawiera listę komend:
    - **Komenda `addVehicle`:** Dodaje pojazd na określonej drodze.
    - **Komenda `step`:** Wykonuje krok symulacji, przepuszczając pojazdy przez skrzyżowanie.

  Oczekiwanym wyjściem jest JSON zawierający listę statusów kroków symulacji, gdzie dla każdego kroku podana jest lista pojazdów, które opuściły skrzyżowanie.

## Instalacja i Uruchomienie

1. **Klonowanie repozytorium:**

   ```bash
   git clone https://github.com/PiotrBra/SmartCrossroads.git
   cd SmartCrossroads
    ```
2. **Budowanie projektu**
   ```bash
      .\gradlew clean build
    ```
3. **Uruchomienie projektu**
    ```bash
       java -jar build/libs/SmartCrossroads-1.0.jar input.json output.json      
    ``` 
   ***Uwaga! Powyższy przykład uruchomienia może nie działać na systemach operacyjnych innych niż Windows***
## Testy
Aplikacja jest pokryta zarówno testami jednostkowymi jak i integracyjnymi.

## Przykłady wejścia/wyjścia
***input.json***
```json
{

  "commands": [

    {

      "type": "addVehicle",

      "vehicleId": "vehicle1",

      "startRoad": "south",

      "endRoad": "north"

    },

    {

      "type": "addVehicle",

      "vehicleId": "vehicle2",

      "startRoad": "north",

      "endRoad": "south"

    },

    {

      "type": "step"

    },

    {

      "type": "step"

    },

    {

      "type": "addVehicle",

      "vehicleId": "vehicle3",

      "startRoad": "west",

      "endRoad": "south"

    },

    {

      "type": "addVehicle",

      "vehicleId": "vehicle4",

      "startRoad": "west",

      "endRoad": "south"

    },

    {

      "type": "step"

    },

    {

      "type": "step"

    }

  ]

}
```
***output.json***
```json
{
  "stepStatuses" : [ {
    "vehiclesLeft" : [ "vehicle2", "vehicle1" ]
  }, {
    "vehiclesLeft" : [ ]
  }, {
    "vehiclesLeft" : [ "vehicle3" ]
  }, {
    "vehiclesLeft" : [ "vehicle4" ]
  } ]
}
```
## Podsumowanie
Symulacja systemu zarządzania sygnalizacją świetlną na skrzyżowaniach, opracowana w języku Java
to systemm który dynamicznie analizuje natężenie ruchu oraz liczbę pojazdów oczekujących,
optymalizując przepływ samochodów w oparciu o inteligentny algorytm wyboru priorytetowych kierunków.

***Kluczowe funkcjonalności:***
- Dynamiczne przydzielanie zielonego światła dla najbardziej obciążonych dróg.
- Maksymalny czas świecenia zielonego światła dla zapewnienia płynności ruchu.
- Automatyczna zmiana świateł na czerwone w przypadku braku pojazdów.
-  Obsługa komend w formacie JSON do symulacji ruchu.
- Testy jednostkowe i integracyjne zapewniające niezawodność systemu.



