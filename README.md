# Nachklausurtutorium Programmier-Praktikum 1 SS25

Jeden Tag von Montag den 15.09.2025 bis Freitag den 19.09.2025 von 08:30 bis 12:30 im Hörsaal 5M.

## Links
- [GitHub Organisation](https://github.com/hhu-propra1-ss25/Organisation)
- [Fragen zur Vorlesung](https://github.com/hhu-propra1-ss25/Organisation/discussions)
- [Fragen zum Nachklausurtutorium](https://github.com/hhu-propra1-ss25/nachklausurtutorium/discussions)

- [Java Online Editor](https://www.jdoodle.com/online-java-compiler)
- [Mentimeter](https://www.mentimeter.com/)

## Aktuelles

**Ihr könnt euch jetzt Aufgaben für den fünften Tag des NKT wünschen: [Github Discussion](https://github.com/hhu-propra1-ss25/nachklausurtutorium/discussions/2).**

19.09.2025 (Tag 5):

18.09.2025 (Tag 4):
- Folien zu Tag 3 aktualisiert ([Folien Tag 1-3 (Paul).pdf](./Folien%20Tag%201-3%20(Paul).pdf))
  - Neue Cheat-Sheet Übersichts Folien zu `Prinzipien` und `Code Smells` um Folie 150 herum
  - Neue Visualisierung zu `Zerlegungsstrategien: Fachlich vs. Technisch`
  - Themenblock um `LCHC` umstrukturiert
  - Code Beipsiele zu `Code Smells im Großen` nachgebessert
- Themenblock `Git im Detail` übersprungen

16.09.2025 (Tag 3):

16.09.2025 (Tag 2):
- Gestern übersprungenen Themenblock `Werkzeuge für Softwareentwicklung` jetzt nachlesbar in [Folien Tag 1-3 (Paul).pdf](./Folien%20Tag%201-3%20(Paul).pdf)

15.09.2025 (Tag 1):
- Thema `UML-Diagramme` übersprungen
- Themenblock `Werkzeuge für Softwareentwicklung` nur teilweise besprochen

## Tagesplanung

### Tag 1 - Grundlagen & Werkzeuge

* **Java-Basics & Klassenbibliothek**
  * **Collections Framework (List, Set, Map, Queue, Utility-Klassen)**
  * **Optionals**
  * UML-Diagramme verstehen
  * **Sichtbarkeiten (public, private, package-private, protected)**
* **Generics**
  * Statische Typisierung
  * Generische Datentypen
  * Type Constraints
  * Generische Methoden
* **Funktionale Programmierung**
  * Funktionale Interfaces: Consumer, Supplier, Function, Predicate, BiFunction
  * **Lambda-Ausdrücke & Methodenreferenzen**
  * Funktionen höherer Ordnung
* **Streams**
  * Erzeugen von Streams
  * Streams mit primitiven Datentypen
  * **Intermediate- und Terminal-Operationen**
  * **map, filter, reduce**
  * **Collectors & eigene Collector-Implementierungen**
  * Parallele Streams
* **Werkzeuge für Softwareentwicklung**
  * **Gradle (assemble, build, jar, run, distZip)**
  * Klassen in Packages & JAR-Dateien
  * Classpath erklärung
  * **Git Basics** (clone, add, commit, push, .gitignore)
  * **Sinnvolle Commits (log, blame, diff, Commit-Messages)**

### Tag 2 - Testing & Codequalität

* **Testing Basics**
  * AAA-Schema (Arrange, Act, Assert)
  * **FIRST-Prinzipien**
  * Rückgaben testen
  * Testen mit Zustand & Interaktion
  * Testen auf Exceptions
* **TDD-Zyklus**
  * Fehlschlagender Test
  * Minimaler Code
  * Refaktorisierung
  * Beispiel: Roman Numbers
* **Qualität von Software**
  * ISO 25010: funktionale Angemessenheit, Performance, Usability, Sicherheit, **Wartbarkeit**
  * **Eigenschaften wartbarer Software**
  * Wartbarkeit als Investition
* **Code Smells im Kleinen**
  * **Mehrere Verantwortlichkeiten / Aufgaben**
  * **Mysterious Name / Namenskonventionen**
  * Kommentare & Verdächtige Kommentare
  * **Long Method & Long Parameter List**
  * **Duplicated Code / DRY**
  * **SLAP (Single Level of Abstraction Principle)**

### Tag 3 - Architektur & Prinzipien

* **Bausteine & Strukturen**
  * **Single Responsibility Prinzip (SRP)**
  * **Information Hiding Prinzip (IHP)**
  * Fachliche Zerlegung vs. technische Zerlegung
  * Kopplung (Aufruf, Konstruktion, Vererbung, unsichtbare Kopplung)
  * **Kohäsion**
  * **Law of Demeter**
* **Vererbung & Polymorphismus**
  * **Overloading vs. Overriding**
  * Interface-Vererbung vs. Klassen-Vererbung
  * **Polymorphismus zur Entkopplung**
  * **Kopplung durch Vererbung**
  * **Refused Bequest (Code Smell)**
* **SOLID-Prinzipien**
  * **SRP - Single Responsibility**
  * **OCP - Open/Closed Principle**
  * **LSP - Liskov Substitution**
  * ISP & DIP in Bezug auf Interfaces
* **Code Smells im Großen**
  * **Large Class**
  * **Primitive Obsession**
  * **Data Clumps**
  * **Divergent Change**
  * **Shotgun Surgery**
  * **Feature Envy**
  * **Message Chains**
  * Smell beheben oder nicht?

### Tag 4 - Fortgeschrittene Themen

* **Testtechniken & Entkopplung**
  * **Test-Doubles: Stub, Dummy, Mock**
  * **Mocking mit Mockito (z. B. MailSender, Datenbank-Stub)**
  * **Probleme beim Testing & Seiteneffekte**
  * **Kopplung im Arrange-Schritt** (Factory-Methoden, Builder Pattern)
  * **Object Mother / Testobjekte**
  * **Custom Assertions** & Assertions-Design
  * **Parametrisierte Tests, Setup/Teardown (@BeforeAll, @AfterEach)**
* **Frameworks**
  * **Spring Dependency Injection (DI) mit @Component, @Configuration, @Bean)**
  * **Injection-Arten: Konstruktor, Setter, Field**
  * **Scopes (Singleton, Prototype)**
  * **Konfiguration mit Properties, YAML, Umgebungsvariablen**
  * Logging-Frameworks
* **Nebenläufigkeit & Domain Driven Design**
  * **Threads & Runnable**
  * **Synchronisation & Konsistenz**
  * Race Conditions & Deadlocks
  * **Entities, Value Objects, Aggregates**
* **Git im Detail**
  * **Commits & SHA-Hashes**
  * Branches, Checkout, Detached Head
  * **Merge vs. Rebase**
  * Konflikte auflösen
  * **Remotes & Branching-Strategien**
  * Git intern: Blob, Tree, Commit
  * **Dezentralität von Git**

### Tag 5 - Klausurvorbereitung

* **Altklausuren lösen**
* **Eure Vorgeschlagenen Aufgaben erklären**
* **Eure Themenwünsche nochmal aufarbeiten**

## Kursmaterialien

### [Organisatorisches](https://propra.d.stups.hhu.de/ss25/550954fb9278c82/index.html)
### Woche 1: [Datenstrukturen, Gradle, Git](https://propra.d.stups.hhu.de/ss25/ff4ebaf259a7626/index.html)
### Woche 2: [Generics und Funktionen](https://propra.d.stups.hhu.de/ss25/0a5913a1afed640/index.html)
### Woche 3: [Streams](https://propra.d.stups.hhu.de/ss25/bf4830ab24787e1/index.html)
### Woche 4: [Testing, TDD](https://propra.d.stups.hhu.de/ss25/02b091f5b4042dc/index.html)
### Woche 5: [Guten Code schreiben](https://propra.d.stups.hhu.de/ss25/6e3a194027486ed/index.html)
### Woche 6: [Systematische Fehlersuche](https://propra.d.stups.hhu.de/ss25/43e00ad95d34c4e/index.html)
### Woche 7: [Strukturen bilden](https://propra.d.stups.hhu.de/ss25/afa248c5defe66f/index.html)
### Woche 8: [Vererbung](https://propra.d.stups.hhu.de/ss25/9c1147617027fd5/index.html)
### Woche 9: [Mehr Gestank](https://propra.d.stups.hhu.de/ss25/d24c5f880364a22/index.html)
### Woche 10: [Mocking](https://propra.d.stups.hhu.de/ss25/a668105ccaa4192/index.html)
### Woche 11: [Test-Kopplung](https://propra.d.stups.hhu.de/ss25/cf43c8247689c31/index.html)
### Woche 12: [Spring](https://propra.d.stups.hhu.de/ss25/67242e16e3bef6c/index.html)
### Woche 13: [Multithreading und Aggregate](https://propra.d.stups.hhu.de/ss25/c253cf0323a03f9/index.html)
### Woche 14: [Git](https://propra.d.stups.hhu.de/ss25/10488b895d35b1e/index.html)
### Woche 15: [Halbzeit](https://propra.d.stups.hhu.de/ss25/8839d9599b963c0/index.html)
