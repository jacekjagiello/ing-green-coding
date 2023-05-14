# Architektura aplikacji

W inżynierii oprogramowania istnieje wiele wypracowanych wzorców i technik, często uważanych za dobre praktyki.

Jednak pragmatyczne podejście promowane między innymi przez manifest Software Cratmanship, nakazuje nam nieustannie myśleć krytycznie o stosowanych technikach,
oraz zawsze rozpatrywać je pod kątem celu projektu, jego funkcjonalnych i niefunkcjonalnych wymagań.
Nigdy nie należy stosować technik "na ślepo" tylko dlatego, że powszechnie uważane są za dobre.

“Everything in software architecture is a trade-off. If an architect thinks they have discovered something that isn’t a trade-off, more likely they just haven’t identified the trade-off yet.”
[Fundamentals of Software Architecture, Mark Richards & Neil Ford](https://www.oreilly.com/library/view/fundamentals-of-software/9781492043447/)

## Prioretyzowane atrybuty jakości systemu
Nie istnieje żaden styl architektury, która pozwalałby by osiągnąć wszystkie możliwe [atrybuty jakości systemu](https://en.wikipedia.org/wiki/List_of_system_quality_attributes) bez kompromisów między nimi.
Na podstawie wymagań niefunkcjonlanych, stwierdziłem, aby architektura aplikacja prioretyzowała następujące atrybuty jakości:

**Efficiency** - Optymalne używanie dostępnych zasobów sprzętowych (głównie procesora i pamięci RAM).
Aby uzyskać efficiency, program poddany będzie profilowaniu pod względem zużycia CPU i pamięci RAM przez algorytmy i struktury danych.
Stosowane również będzie framework HTTP [Javalin](https://javalin.io/), który oferuje miniejszy code footprint od rozbudowanych frameworków jak na przykład Spring.

**Performance** - Osiągnięcie jak najkrótszego czasu odpowiedzi dla wysyłanych żądań. Aby osiągnąć performance, podobnie jak w przypadku efficiency,
stosowany jest "lekki" framework HTTP, oraz zoptymalizowane struktury danych.

**Security** - Zapewnienie możliwość analizy kodu źródłowego pod względem bezpieczeństwa.
Aby zapewnić security, system CI w repozytorium skonfigurowany jest aby używać CodeQL do analizy kodu źródłowego pod względem bezpieczeństwa.

**Maintainability** - Zastosowanie technik, które umożliwiają łatwość modyfikowania i dodawania nowych funkcjonalności przez deweloperów.
Aby zapewnić maintainability, logika działania programu (warstwa `usecase`) jest oddzielona od warstwy infrastrukturalnej `adapters`.

## Architektura aplikacji
Aplikacja posiada architekturę monolityczną, warstwową, wykorzystującą porty i adaptery.
Architektura monolityczna jest domniemana z wymagania organizatora, iż aplikacja powinna uruchamiać się na porcie 8080. Tym samym nie da się zastosować architektury rozproszonej, w których to osobne aplikacje musiały by używać innych portów.

Dzięki wykorzystaniu warstwy usecase-ów, możemy oddzielić logikę biznesową od środowiska w której jest ona uruchamiana, oraz zwiększyć testowalność funkcjonalności.

Wykorzystanie wzorca portów i adapterów umożliwia nam łatwą wymianę kluczowych zależnosci, jak np. frameworka HTTP, co jest przydatne przy porównaniu różnych frameworków HTTP pod względem wydajności i energooszczędności.

### Warstwy wykorzystane w aplikacji

Warstwa `ports` zawiera interfejsy dla kluczowych zewnętrznych zależności aplikacji. W przypadku tej aplikacji jest nim tylko interfejs `HttpServer`, który definiuje zachowanie dla frameworka obsługującego komunikację sieciową.
Wszystkie interfejsy powinny być implementowane w warstwie `adpaters`.

Warstwa `adapters` zawiera konkretne implementacje interfejsów z warstwy `ports`. Obecnie jedyną implementacją w tej warstwie jest

Warstwa `usecase` zawiera logikę biznesową aplikacji, w tym przypadku algorytmy dla poszczególnych zadań. Jest ona niezależna od warstwy adapterów.

Warstwa `entities` zawiera struktury danych wykorzystywane w każdym z trzech zadań. Warstwa tam może być użyta zarówno przez warstwę biznesową jak i infrastrukturalną.

## Podjęte kompromisy i decyzje architektoniczne
W moim rozwiązaniu postanowiłem podjąć pewne kompromisy, biorąc pod uwagę wcześniej zdefiniowane prioretyzowane atrybuty jakości systemu.

### Celowy coupling pomiędzy obiektami domenowymi a obiektami warstwy HTTP
Wiele wzorców architektury takie jak architektura hexagonalna, lub tzw "clean architecture", podkreślają zalety oddzielenia warstwy domenowej od warstwy infrastruktury.
Daje to możliwość enkapsulacji zasad biznesowych niezależnie od tego, skąd dostarczane są dane do wykonania funkcjonalności. Dane pomiędzy warstwami są wymieniane za pomocą obiektów DTO (Data Transfer Object) które służą jako interfejs komunikacji między warstwami.

Jednak zastosowanie tego podejścia i związania z nim konieczność tworzenia obiektów DTO, wymuszają kopiowanie danych w pamięci, co jak ustaliliśmy, powinno być unikane w nadmiarze w aplikacjach energooszczędnych.
Z tego powodu podjąłem decyzje o celowym couplingu między warstwa biznesową `usecase` i infrastrukturalną `adapters`. Obie te warstwy mogą korzystać ze wspólnych struktur danych z warstwy `entitites`, aby uniknąć kopiowania danych między nimi.

Można by stwierdzić, że struktury danych w aplikacji są na tyle małe, że dodanie mapowania nie będzie miało istotnego wpływu na jego energooszczędność czy czas wykonania.
W dużej mierze to stwierdzenie jest prawdziwe, jednak biorąc pod uwagę fakt, że głównym celem konkursu jest optymalizacja aplikacji pod kątem wykorzystania zasobów, pozostawiam swoją decyzję w mocy.

W przypadku bardziej zaawansowanego funkcjonalnie, produkcyjnego oprogramowania, decyzja ta mogłaby ulec zmianie.

### Użycie frameworka Javalin 
Pdostawową zależnością aplikcji jest framework HTTP [Javalin](https://javalin.io/). Poniważ aplikacja powinna oszczędnie gospodarować zasobami, zdecydowałem się nie używać "dużych" frameworków jak np. Spring.
Javalin harakteryzuje się niższym footprintem w prównaniu do Springa. Jest także oparty na bilbiotece [Jetty](https://www.eclipse.org/jetty/), która powstała do tworzenia szybkich serwerów HTTP. 
