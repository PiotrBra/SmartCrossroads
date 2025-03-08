# Algorytm zarządzania światłami drogowymi na skrzyżowaniu

Algorytm opisuje sposób zarządzania sygnalizacją świetlną na skrzyżowaniu, aby zoptymalizować przepływ ruchu na podstawie liczby oczekujących pojazdów. Jego celem jest dynamiczne dostosowywanie czasów trwania zielonych świateł, aby jak najlepiej reagować na zmieniające się warunki na skrzyżowaniu.

## Główna idea algorytmu

1. **Zielone światło zmienia się w zależności od liczby pojazdów:** Algorytm przydziela zielone światło w tym kierunku, w którym pojazdy oczekują najdłużej (lub tam, gdzie jest ich najwięcej).
2. **Maksymalny czas zielonego światła:** Światło w danym kierunku nie może pozostawać zielone dłużej niż określony czas (5 kroków), nawet jeśli nadal są pojazdy.
3. **Zmiana świateł na czerwone:** Gdy minie czas zielonego światła lub nie ma już oczekujących pojazdów, światła zmieniają się na czerwone, a algorytm wybiera nowy kierunek dla zielonego światła.

## Szczegółowy opis działania

### 1. **Sprawdzanie obecnego stanu świateł**

Algorytm rozpoczyna cykl od sprawdzenia, czy światło w danym kierunku (`currentGreenDirection`) jest aktywne, a liczba upływających kroków (`stepsElapsed`) nie przekroczyła maksymalnego limitu (5 kroków). Jeśli warunki są spełnione, to:

- **Zielone światło zostaje na dłużej:** Jeśli w kierunku zielonego światła lub w jego przeciwnym kierunku znajdują się pojazdy oczekujące, algorytm zwiększa licznik kroków (`stepsElapsed`) o 1. Światło pozostaje zielone, aby dać pojazdom szansę na przejazd.

### 2. **Zakończenie trwania zielonego światła**

Jeśli liczba kroków przekroczyła maksymalny czas trwania zielonego światła (5 kroków) lub jeśli nie ma już pojazdów oczekujących, światło zmienia się na czerwone. Zostaje wtedy zresetowany licznik kroków.

### 3. **Zmiana świateł na czerwone**

Przed przejściem do zmiany kierunku zielonego światła, algorytm ustawia wszystkie światła na czerwone. Zapobiega to sytuacji, w której więcej niż jedno światło mogłoby świecić na zielono w tym samym czasie.

### 4. **Wybór najzajętszego kierunku**

Algorytm analizuje, które kierunki są najbardziej obciążone, czyli w których drogach znajduje się najwięcej oczekujących pojazdów. Do tego celu wykorzystywana jest funkcja, która sumuje liczbę oczekujących pojazdów w danym kierunku oraz w przeciwnym kierunku (pary kierunków: północ-południe, wschód-zachód).

Jeśli liczba oczekujących pojazdów w danej parze drogowej jest największa, to kierunki te będą miały zielone światło. Tylko te pary kierunków są brane pod uwagę, ponieważ skrzyżowanie obsługuje ruch na drogach w parach (np. północ-południe).

### 5. **Brak pojazdów – światła czerwone**

Jeśli żadna z dróg nie ma pojazdów oczekujących (np. wszystkie pojazdy opuściły skrzyżowanie), algorytm utrzymuje wszystkie światła na czerwonym. Algorytm nie przydziela zielonego światła, jeśli nie ma potrzeby przejazdu.

### 6. **Przydzielanie zielonego światła najzajętszym kierunkom**

Jeśli pojazdy nadal oczekują na skrzyżowaniu, algorytm wybiera kierunki z największą liczbą oczekujących pojazdów i ustawia w tych kierunkach światło na zielone. Zmienna `currentGreenDirection` zostaje zaktualizowana, aby wskazać aktualny kierunek z zielonym światłem.

### 7. **Oczekiwanie na zmianę**

Po przydzieleniu zielonego światła algorytm wraca do punktu wyjścia, gdzie ponownie sprawdza, czy w danym kierunku są pojazdy. Jeśli tak, licznik kroków zostaje zwiększony. Jeśli nie, algorytm ponownie zmienia światła na czerwone i wybiera nowe kierunki.

## Metody pomocnicze

### `findBusiestRoadPair(Map<Direction, Road> roads)`
Funkcja ta służy do znalezienia pary kierunków, które mają największą liczbę pojazdów oczekujących. Sumuje liczbę pojazdów na drodze w danym kierunku oraz na drodze w przeciwnym kierunku.

### `countWaitingVehicles(Map<Direction, Road> roads, Direction direction)`
Zlicza liczbę pojazdów oczekujących na drodze w określonym kierunku oraz w jego przeciwnym kierunku.

### `areAllRoadsEmpty(Map<Direction, Road> roads, Direction direction)`
Sprawdza, czy w danym kierunku oraz w przeciwnym nie ma żadnych pojazdów oczekujących. Jeśli żadna droga nie ma pojazdów, wszystkie światła zostają czerwone.

### `getOppositeDirection(Direction direction)`
Zwraca przeciwny kierunek do podanego, np. dla `NORTH` zwraca `SOUTH`.

## Podsumowanie

Algorytm zmienia sygnalizację świetlną na skrzyżowaniu na podstawie liczby oczekujących pojazdów. Jego głównym celem jest utrzymanie płynności ruchu poprzez dynamiczną zmianę świateł, zapewniając, że kierunki z większym natężeniem ruchu będą miały dłuższe okresy zielonego światła. Zmiana świateł na czerwone następuje, gdy nie ma już pojazdów, a algorytm zawsze dąży do tego, aby unikać sytuacji, w których niepotrzebnie świeci się zielone światło, gdy nie ma oczekujących pojazdów.
