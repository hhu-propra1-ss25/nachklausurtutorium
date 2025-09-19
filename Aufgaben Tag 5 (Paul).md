## Übungsaufgaben für Tag 5

### Nachklausur 2022 Aufgabe 1

Wir wollen eine Software für die Qualitätskontrolle in einer Fabrik schreiben, die Würfel für Casinos herstellt.
Die Würfel sollen für jede Augenzahl die gleiche Wahrscheinlichkeit haben.
In der Fabrik werden die Würfel testweise geworfen und die Daten zu jedem einzelnen Wurf in einer Liste festgehalten.
Ein einzelner Wurf wird in folgendem Record gespeichert, in welchem neben dem Ergebnis auch der Zeitpunkt des Wurfs und der Name der Person, die gewürfelt hat, festgehalten wird.

```java
public record Wurf (LocalDateTime zeitpunkt,
                    String name,
                    int ergebnis) {}
```

Für die Qualitätssicherung benötigen wir eine Methode, mit der wir die Häufigkeitsverteilung für einen Würfel bestimmen können.
Vervollständigen Sie die Methode `histogramm`, sodass sie ein Histogramm aus den Testwurf-Daten erzeugt.
Ein Histogramm ist eine Map, in der für jede Augenzahl die Anzahl der Würfe, bei der diese Augenzahl aufgetreten ist, gespeichert ist.

Beispiel: `{1=10, 2=9, 3=11, 4=12, 5=9, 6=10}`

Ihre Lösung **muss genau einen** Stream verwenden und darf keine anderen Kontrollstrukturen verwenden.

```java
public static Map<Integer, Long> histogramm(List<Wurf> wuerfe) {

}
```

**Lösung:**

```java
// vgl. Worthäufigkeit aus Woche 10
return wuerfe.stream()
    .collect(Collectors.groupingBy(
        Wurf::ergebnis,
        Collectors.counting()));
```

### Nachklausur 2022 Aufgabe 2

Wir wollen eine Multimap implementieren, die es im Gegensatz zu einer normalen Map erlaubt, zu einem Schlüssel mehr als einen Wert zu speichern.
Der Einfachheit halber erlauben wir nur Strings als Schlüssel und Werte. Werte können mehrfach, auch unter demselben Schlüssel, vorkommen.
Implementieren Sie die folgenden Methoden und alle notwendigen Attribute der Klasse.

* eine Methode `add`, welche einen Schlüssel und einen Wert übergeben bekommt und den Wert unter dem Schlüssel abspeichert
* eine Methode `get`, welche einen Schlüssel übergeben bekommt und die gespeicherten Werte zu diesem Schlüssel zurückgibt

Die Multimap könnte folgendermaßen verwendet werden:

```java
MultiMap multiMap = new MultiMap();
multiMap.add("3A", "Alex");
multiMap.add("3A", "Sascha");
multiMap.add("3A", "Alex");
multiMap.add("5D", "Alex");
// Resultat von multiMap.get("3A") enthält zweimal Alex und einmal Sascha
// Resultat von multiMap.get("5D") enthält nur Alex
```

Verwenden Sie ausschließlich Datenstrukturen, die in Java bereits vorhanden sind. Ihre Implementierung muss ähnlich effizient wie eine normale Map sein und darf keine Exceptions werfen.

```java
public class MultiMap {
    _____
}
```

**Lösung:**

```java
private final Map<String,List<String>> map = new HashMap<>();

public void add(String key, String value) {
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    // Alternativ:
    // List<String> values = map.getOrDefault(key, new ArrayList<>());
    // values.add(value);
    // map.put(key, values);
    // Wert holen und nullcheck ist auch ok
}

// Statt leerer Liste ist auch null oder Optional ok
// List.copyOf(...) um das IHP einzuhalten für extra Punkte
public List<String> get(String key) {
    return map.getOrDefault(key, new ArrayList<>());
}
```

### Hauptklausur 2025 Aufgabe 4

Um die Verteilung von Studis auf Klausur-Hörsäle vorzunehmen, haben wir eine Software geschrieben. Folgender Code, der berechnen soll, welche der verfügbaren Hörsäle benutzt werden sollten, ist bereits fertig:

```java
record Studi(int matNr) {}

record Hörsaal(String name, Set<String> plätze) {
    public Hörsaal(String name, Set<String> plätze) {
        this.name = name;
        this.plätze = Set.copyOf(plätze);
    }
}

class Aufteilung {
    static List<Hörsaal> benötigteHörsäle(List<Hörsaal> alleHörsäle, List<Studi> studis) {
        // Implementierung ist egal
    }
}
```

**(a)**

Schreiben Sie einen Test, der Folgendes überprüft: Wenn wir drei Hörsäle mit jeweils 3, 2 und 1 Plätzen haben und sich 4 Studierende angemeldet haben, werden die Hörsäle mit 3 und 1 Plätzen ausgewählt.

```java
@Test
void test_1() {
    _____
}
```

**(b)**

Sie haben sicher bemerkt, dass der Arrange-Schritt etwas unangenehm wird, besonders, wenn wir viele Tests haben. Schreiben Sie eine **statische Factory-Methode**, der wir den Namen eines Hörsaals und die Anzahl der Plätze übergeben und die uns dann ein Hörsaal-Objekt mit der passenden Anzahl von Plätzen liefert. Die Namen der Plätze können beliebig sein.

**Lösung:**

**(a)**

```java
@Test
void test_1() {
    // Arrange
    Hörsaal h1 = new Hörsaal("Hörsaal A", Set.of("A1", "A2", "A3"));
    Hörsaal h2 = new Hörsaal("Hörsaal B", Set.of("B1", "B2"));
    Hörsaal h3 = new Hörsaal("Hörsaal C", Set.of("C1"));
    List<Hörsaal> alleHörsäle = List.of(h1, h2, h3);

    List<Studi> studis = List.of(
        new Studi(101),
        new Studi(102),
        new Studi(103),
        new Studi(104)
    );

    // Act
    List<Hörsaal> actual = Aufteilung.benötigteHörsäle(alleHörsäle, studis);

    // Assert
    assertThat(actual).containsExactlyInAnyOrder(h1, h3);
}
```

**(b)**

Die statische Factory-Methode kann direkt in den `Hörsaal`-Record eingefügt werden. Sie generiert ein Set von Plätzen basierend auf der gewünschten Anzahl.

```java
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Hörsaal(String name, Set<String> plätze) {
    public Hörsaal(String name, Set<String> plätze) {
        this.name = name;
        this.plätze = Set.copyOf(plätze);
    }

    // Die eigentliche Statische Factory-Methode
    public static Hörsaal mitKapazität(String name, int kapazität) {
        Set<String> generiertePlätze = IntStream.rangeClosed(1, kapazität)
                                                .mapToObj(i -> "Platz-" + i)
                                                .collect(Collectors.toSet());
        return new Hörsaal(name, generiertePlätze);
    }
}
```

### Aufgabe zu Streams

In der Verwaltung eines Programmierkurses werden die Punkte für mehrere Übungsblätter in einer `List<Map<String, Integer>>` gespeichert.
Jeder Eintrag in der Liste repräsentiert ein Übungsblatt, und die darin enthaltene Map speichert die erreichten Punkte pro Studierendem.

Ihre Aufgabe ist es, eine Methode zu schreiben, die diese Liste verarbeitet und eine einzelne `Map<String, Integer>` zurückgibt, die die Gesamtpunktzahl für jede:n Studierende:n enthält.

**Beispieldaten:**
```java
List<Map<String, Integer>> alleBlätter = List.of(
    Map.of("Anna", 10, "Ben", 8, "Clara", 10),
    Map.of("Anna", 9, "Ben", 10),
    Map.of("Ben", 7, "Clara", 9)
);
```

**Erwartetes Ergebnis:**
`{Anna=19, Ben=25, Clara=19}`

Ihre Lösung muss **mindestens einen Stream** benutzen und darf keine anderen Kontrollstrukturen verwenden.

```java
public static Map<String, Integer> berechneGesamtpunkte(List<Map<String, Integer>> alleBlätter) {
    _____
}
```

**Lösung:**

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public static Map<String, Integer> berechneGesamtpunkte(List<Map<String, Integer>> alleBlätter) {
    return alleBlätter.stream() // Stream<Map<String, Integer>>
        .flatMap(blatt -> blatt.entrySet().stream()) // Stream<Map.Entry<String, Integer>>
        .collect(Collectors.groupingBy(
            (entry) -> entry.getKey(), // Gruppiere nach Name (Alternativ geht auch: Map.Entry::getKey)
            Collectors.summingInt((entry) -> entry.getValue()) // Addiere die Punkte (Alternativ geht auch: Map.Entry::getValue)
        ));
}
```

### Aufgabe zu Higher-Order Functions

In der funktionalen Programmierung sind "Higher-Order Functions" (Funktionen, die andere Funktionen als Parameter erhalten) ein zentrales Konzept.
Schreiben Sie eine solche generische Hilfsmethode `transformMap` für `Map`-Datenstrukturen in Java.

Die `transformMap`-Methode soll für jeden Eintrag in der Map die gegebene Funktion anwenden und alle Ergebnisse in einer Liste sammeln.

**Beispielhafte Verwendung:**
```java
Map<String, Integer> studentenNoten = Map.of("Alex", 1, "Maria", 4, "Tom", 5);

// Eine BiFunction, die Name und Note in einen Status-String umwandelt
BiFunction<String, Integer, String> statusPruefer =
    (name, note) -> name + " hat " + (note <= 4 ? "bestanden" : "nicht bestanden");

List<String> ergebnisse = MapTransformer.transformMap(studentenNoten, statusPruefer);
// ergebnisse enthält: ["Alex hat bestanden", "Maria hat bestanden", "Tom hat nicht bestanden"] (Die Reihenfolge kann variieren)
```

Ihre Lösung muss **genau einen Streams verwenden** und keine weiteren Kontrollstrukturen.
Die Reihenfolge der Elemente in der Ergebnisliste ist nicht vorgegeben.

```java
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class MapTransformer {
    public static <K, V, R> List<R> transformMap(Map<K, V> map, BiFunction<K, V, R> transformer) {
        _____
    }
}
```

**Lösung:**

```java
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MapTransformer {
    public static <K, V, R> List<R> transformMap(Map<K, V> map, BiFunction<K, V, R> transformer) {
        return map.entrySet()
                  .stream() // Erzeugt einen Stream von Map.Entry<K, V>
                  .map(entry -> transformer.apply(entry.getKey(), entry.getValue())) // Wendet die Funktion auf jedes Key-Value-Paar an
                  .collect(Collectors.toList()); // Sammelt die Ergebnisse in einer Liste
    }
}
```

### Aufgabe zu Mocking

Betrachten Sie den folgenden Code-Ausschnitt aus einer Anwendung zur Rechtschreibkorrektur. Die Klasse `Checker` nutzt eine `Buchsammlung`, um an ein `Woerterbuch` zu gelangen und dann zu prüfen, ob ein Satz korrekt ist.

```java
public interface Woerterbuch {
    boolean enthaelt(String wort);
}

public interface Buchsammlung {
    Woerterbuch get(String name);
}

public class Checker {
    private final Buchsammlung sammlung;

    public Checker(Buchsammlung sammlung) {
        this.sammlung = sammlung;
    }

    public boolean satzKorrekt(String satz) {
        Woerterbuch woerterbuch = sammlung.get("WildesWortWerk");
        if (woerterbuch == null) return false; // Wörterbuch könnte nicht existieren
        return Arrays.stream(satz.split(" "))
            .allMatch(woerterbuch::enthaelt);
    }
}
```

**(a)**

Schreiben Sie einen Unit-Test für den Fall, dass der Satz "ProPra ist super" geprüft wird und alle Wörter im Wörterbuch enthalten sind. Ihr Test muss Mockito verwenden und darf den `Checker`-Code nicht verändern.

**(b)**

Der Test in (a) deckt ein Design-Problem auf, das als "Stubs, die Stubs zurückgeben" bekannt ist und oft auf eine Verletzung des **Law of Demeter** hinweist.
1. Erklären Sie kurz, woran man die Verletzung des Law of Demeter im `Checker`-Code erkennt.
2. Beschreiben Sie, wie Sie den Konstruktor und die Methode `satzKorrekt` der `Checker`-Klasse umgestalten würden, um das Problem zu beheben und die Testbarkeit zu verbessern. Sie müssen keinen vollständigen Code schreiben.

**Lösung:**

**(a)**

```java
@Test
@DisplayName("Ein korrekter Satz wird als korrekt erkannt")
void testSatzKorrekt() {
    // Arrange
    // Stub für das Wörterbuch erstellen
    Woerterbuch woerterbuchMock = mock(Woerterbuch.class);
    when(woerterbuchMock.enthaelt(anyString())).thenReturn(true);

    // Stub für die Buchsammlung, der den Wörterbuch-Stub zurückgibt
    Buchsammlung sammlungMock = mock(Buchsammlung.class);
    when(sammlungMock.get("WildesWortWerk")).thenReturn(woerterbuchMock);

    Checker checker = new Checker(sammlungMock);

    // Act
    boolean istKorrekt = checker.satzKorrekt("ProPra ist super");

    // Assert
    assertThat(istKorrekt).isTrue();
    verify(woerterbuchMock).enthaelt("ProPra");
    verify(woerterbuchMock).enthaelt("ist");
    verify(woerterbuchMock).enthaelt("super");
}
```

**(b)**

1. **Verletzung des Law of Demeter:** Die Verletzung ist in der Zeile `sammlung.get("WildesWortWerk")` zu erkennen, auf deren Ergebnis (`Woerterbuch`) dann die Methode `enthaelt` aufgerufen wird. Der `Checker` "spricht mit einem Fremden" (dem `Woerterbuch`), den er über einen Freund (`sammlung`) bekommen hat, anstatt direkt mit dem Freund zu sprechen.
2. **Umgestaltung:**
* **Konstruktor:** Der Konstruktor sollte direkt eine `Woerterbuch`-Instanz anstelle der `Buchsammlung` erhalten: `public Checker(Woerterbuch woerterbuch)`.
* **Methode `satzKorrekt`:** Die Methode würde das `Woerterbuch` direkt als Klassenattribut verwenden und nicht mehr über die `Buchsammlung` holen. Die Verantwortung, das richtige Wörterbuch zu finden, wird so aus dem `Checker` herausgezogen. Der Test würde dadurch viel einfacher, da nur noch ein Objekt (`Woerterbuch`) gemockt werden müsste.


### Aufgabe zu JUnit Tests 1

In einem Ticketsystem soll eine `TicketService`-Klasse den Kauf eines Tickets abwickeln. Dazu wird ein externer `PaymentProvider` aufgerufen, um die Zahlung durchzuführen. Der folgende Code ist bereits implementiert:

```java
public interface PaymentProvider {
    boolean processPayment(double amount); // true für Erfolg, false für Fehler
}

public record Ticket(String eventName, double basePrice) {}

public class TicketService {
    private final PaymentProvider paymentProvider;
    private static final double BOOKING_FEE = 2.50;

    public TicketService(PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public boolean purchaseTicket(Ticket ticket) {
        double finalPrice = ticket.basePrice() + BOOKING_FEE;
        return paymentProvider.processPayment(finalPrice);
    }
}
```

Schreiben Sie einen vollständigen Unit-Test für die Methode `purchaseTicket`. Der Test soll den Erfolgsfall prüfen: Ein Ticket mit einem Grundpreis von 50.00 wird gekauft, und der `PaymentProvider` meldet eine erfolgreiche Zahlung. Stellen Sie sicher, dass der `PaymentProvider` mit dem korrekten Endpreis (Grundpreis + Buchungsgebühr) aufgerufen wird.

**Lösung:**

```java
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Test
    @DisplayName("Ein Ticketkauf sollte erfolgreich sein, wenn die Zahlung klappt")
    void purchaseTicketSuccessfully() {
        // Arrange
        PaymentProvider paymentProviderMock = mock(PaymentProvider.class);
        // Stub: Wenn processPayment mit 52.50 aufgerufen wird, gib true zurück
        when(paymentProviderMock.processPayment(52.50)).thenReturn(true);

        TicketService ticketService = new TicketService(paymentProviderMock);
        Ticket concertTicket = new Ticket("Rock am Ring", 50.00);

        // Act
        boolean result = ticketService.purchaseTicket(concertTicket);

        // Assert
        assertThat(result).isTrue();
        // Mock-Verifikation: Stelle sicher, dass die Methode mit dem korrekten Preis aufgerufen wurde
        verify(paymentProviderMock).processPayment(52.50);
    }
}
```

### Aufgabe zu JUnit Tests 2 (schwierig)

Wir betrachten das Aggregat `Patient`. Standard-Assertions wie `assertThat(patient.name()).isEqualTo("...")` können zu einer starken Kopplung zwischen Test und Implementierungsdetails führen.

```java
// Gegebene (vereinfachte) Klasse
public class Patient {
    private String name;
    private final List<String> termine = new ArrayList<>();

    public Patient(String name) { this.name = name; }
    public String name() { return name; }
    public List<String> termine() { return List.copyOf(termine); }
    public void addTermin(String termin) { this.termine.add(termin); }
}
```

**(a)**

Erklären Sie kurz, warum die wiederholte Verwendung von `assertThat(patient.termine().size()).isEqualTo(1)` und `assertThat(patient.termine().get(0)).isEqualTo("...")` in vielen Tests die Wartbarkeit erschweren kann.

**(b)**

Implementieren Sie eine **eigene AssertJ-Assertion** für die `Patient`-Klasse. Die Assertion soll eine Fluent API bereitstellen, um den Namen und die Anzahl der Termine zu prüfen.

**Beispielhafte Verwendung:**
`PatientAssertions.assertThat(patient).hasName("Max Mustermann").hasTerminCount(2);`

Sie müssen nur die Assertion-Klasse `PatientAssertions` und die notwendigen Methoden `assertThat`, `hasName` und `hasTerminCount` implementieren.

**Lösung:**

**(a)**

Die Wartbarkeit wird erschwert, weil die Tests an die genaue Implementierung der `termine()`-Methode (z.B. dass sie eine `List` zurückgibt) und die Art, wie Daten abgefragt werden (`.size()`, `.get(0)`), gekoppelt sind. Wenn sich die interne Datenstruktur von `Patient` ändert (z.B. zu einem `Set` oder einer eigenen `Termin`-Klasse), müssen alle diese Tests manuell angepasst werden, obwohl das fachliche Verhalten ("hat einen Termin") gleich geblieben ist.

**(b)**

```java
import org.assertj.core.api.AbstractAssert;
import java.util.List;

public class PatientAssertions extends AbstractAssert<PatientAssertions, Patient> {

    // Privater Konstruktor, wird von der Factory-Methode aufgerufen
    private PatientAssertions(Patient actual) {
        super(actual, PatientAssertions.class);
    }

    // Statische Factory-Methode - der Einstiegspunkt für die Assertion
    public static PatientAssertions assertThat(Patient actual) {
        return new PatientAssertions(actual);
    }

    // Eigene Assertion-Methode für den Namen
    public PatientAssertions hasName(String expectedName) {
        isNotNull(); // Prüft, ob das actual-Objekt nicht null ist

        if (!actual.name().equals(expectedName)) {
            failWithMessage("Expected patient's name to be <%s> but was <%s>",
                            expectedName, actual.name());
        }

        return this; // Ermöglicht das Verketten (Fluent API)
    }

    // Eigene Assertion-Methode für die Anzahl der Termine
    public PatientAssertions hasTerminCount(int expectedCount) {
        isNotNull();

        int actualCount = actual.termine().size();
        if (actualCount != expectedCount) {
            failWithMessage("Expected patient to have <%s> termine but had <%s>",
                            expectedCount, actualCount);
        }

        return this;
    }
}
```

### Aufgabe zu Spring 1 (schwierig)

In einer Spring Boot-Anwendung soll je nach Konfiguration entweder ein `FileLogger` oder ein `ConsoleLogger` verwendet werden. Beide implementieren das `LoggerService`-Interface. Die Auswahl soll über eine Property `logging.destination` in der `application.properties` gesteuert werden.

```java
public interface LoggerService {
    void log(String message);
}

// Implementierung für FileLogger (Code nicht relevant)
public class FileLogger implements LoggerService { /* ... */ }

// Implementierung für ConsoleLogger (Code nicht relevant)
public class ConsoleLogger implements LoggerService { /* ... */ }
```

Vervollständigen Sie die Annotationen für die beiden Logger-Implementierungen, sodass:
1. Der `ConsoleLogger` verwendet wird, wenn `logging.destination=console` gesetzt ist oder die Property gar nicht existiert (`matchIfMissing = true`).
2. Der `FileLogger` verwendet wird, wenn `logging.destination=file` gesetzt ist.
3. Beide Klassen von Spring als Komponenten erkannt werden.

```java
// Ergänzen Sie die Annotationen
@_____
@_____
public class ConsoleLogger implements LoggerService { /* ... */ }

@_____
@_____
public class FileLogger implements LoggerService { /* ... */ }
```

**Lösung:**

```java
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Component
@ConditionalOnProperty(name = "logging.destination", havingValue = "console", matchIfMissing = true)
public class ConsoleLogger implements LoggerService { /* ... */ }

@Component
@ConditionalOnProperty(name = "logging.destination", havingValue = "file")
public class FileLogger implements LoggerService { /* ... */ }
```

### Aufgabe zu Spring 2 (schwierig)

Sie konfigurieren eine Spring-Anwendung mit verschiedenen Umgebungen (Profilen). Es gibt eine `DataSource`-Schnittstelle.
* Für das `dev`-Profil soll eine `InMemoryDataSource` verwendet werden.
* Für das `prod`-Profil soll eine `PostgresDataSource` verwendet werden, deren Verbindungs-URL aus der `application.properties` (`db.connection.url`) gelesen wird.

Sie können die Implementierungsklassen nicht mit `@Component` annotieren, da sie aus einer externen Bibliothek stammen.

Schreiben Sie eine vollständige `@Configuration`-Klasse `AppConfig`, die die korrekte `DataSource`-Bean je nach aktivem Spring-Profil bereitstellt.

```java
public interface DataSource {
    String getConnectionInfo();
}

// Externe Klasse 1
public class InMemoryDataSource implements DataSource {
    @Override public String getConnectionInfo() { return "In-Memory DB"; }
}

// Externe Klasse 2
public class PostgresDataSource implements DataSource {
    private final String url;
    public PostgresDataSource(String url) { this.url = url; }
    @Override public String getConnectionInfo() { return "PostgreSQL at " + url; }
}
```

**Ihre Aufgabe:**
```java
// Vervollständigen Sie diese Konfigurationsklasse
public class AppConfig {
    _____
}
```

**Lösung:**

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Bean
    @Profile("dev")
    public DataSource inMemoryDataSource() {
        return new InMemoryDataSource();
    }

    @Bean
    @Profile("prod")
    public DataSource postgresDataSource(@Value("${db.connection.url}") String dbUrl) {
        return new PostgresDataSource(dbUrl);
    }
}
```
