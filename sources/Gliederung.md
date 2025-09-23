# Basis Gliederung für Programmier Praktikum 1 (basierend auf Gliederung 2024)

## Tag 1 - Grundlagen & Werkzeuge

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

## Tag 2 - Testing & Codequalität

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

## Tag 3 - Architektur & Prinzipien

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

## Tag 4 - Fortgeschrittene Themen

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

## Tag 5 - Klausurvorbereitung

* **Altklausuren lösen**
