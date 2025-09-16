### Übungsaufgaben für Tag 2

### Themenblock 1: Testing Basics

**Aufgabe 1.1: Das AAA-Schema**

Gegeben ist die folgende Klasse `TextUtil`:

```java
public class TextUtil {
    /**
     * Kürzt einen Text auf eine maximale Länge und fügt "..." an.
     * Wenn der Text bereits kurz genug ist, wird er unverändert zurückgegeben.
     */
    public static String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}
```

Schreiben Sie eine vollständige Testmethode, die prüft, dass der Aufruf `TextUtil.truncate("Hallo Welt", 5)` den String `"Hallo..."` zurückgibt. Machen Sie in Ihrem Code durch Kommentare deutlich, welche Statements jeweils zu **Arrange**, **Act** bzw. **Assert** gehören.

**Lösung:**

```java
@Test
@DisplayName("Sollte einen langen String korrekt kürzen und '...' anhängen")
void testTruncateLongString() {
    // Arrange
    String inputText = "Hallo Welt";
    int maxLength = 5;
    String expectedText = "Hallo...";

    // Act
    String result = TextUtil.truncate(inputText, maxLength);

    // Assert
    assertThat(result).isEqualTo(expectedText);
}
```

---

**Aufgabe 1.2: Eigenschaften von Funktionen**

Betrachten Sie die folgende Klasse `ListProcessor`.

```java
public class ListProcessor {
    // Methode 1
    public int sumOfSquares(List<Integer> numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n * n;
        }
        return sum;
    }

    // Methode 2
    public void addAndLog(List<Integer> numbers, int newNumber) {
        numbers.add(newNumber);
        System.out.println("Added new number. List size is now: " + numbers.size());
    }
}
```

**(a)** Welche der beiden Methoden ist eine "pure function"?
**(b)** Begründen Sie für **beide** Methoden, warum sie die Kriterien einer "pure function" erfüllen oder nicht. Gehen Sie dabei auf das Konzept der Seiteneffekte ein.

**Lösung:**

**(a)** Die Methode `sumOfSquares` ist eine "pure function".

**(b)**
*   **`sumOfSquares` ist pure**, weil ihr Rückgabewert ausschließlich von den Eingabeparametern abhängt und sie keinerlei beobachtbare Seiteneffekte hat. Sie verändert weder die übergebene Liste noch interagiert sie mit externen Systemen (wie der Konsole).
*   **`addAndLog` ist nicht pure**, weil sie zwei Seiteneffekte hat:
    1.  Sie modifiziert den Zustand des übergebenen `numbers`-Objekts (veränderlicher Zustand wird geändert).
    2.  Sie schreibt auf die Konsole (`System.out.println`), was eine Interaktion mit der Außenwelt darstellt.

---

**Aufgabe 1.3: Test-Prinzipien**

Betrachten Sie die folgende Testmethode:

```java
@Test
void testWeeklyReportGeneration() {
    // Erzeugt einen Report für den aktuellen Wochentag
    Report report = ReportGenerator.createWeeklyReport();

    // Annahme: Heute ist Montag, der Test soll nur montags laufen
    if (LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY) {
        assertThat(report.getTitle()).isEqualTo("Wochenstart-Report");
    } else {
        assertThat(report.getTitle()).isEqualTo("Regulärer Wochen-Report");
    }
}
```

Welches der **FIRST**-Prinzipien für gute Tests wird durch diesen Test verletzt? Begründen Sie Ihre Antwort in 1-2 Sätzen.

**Lösung:**

Das **R**-Prinzip (**Repeatable** / Wiederholbar) wird verletzt. Der Test liefert unterschiedliche Ergebnisse, je nachdem, an welchem Wochentag er ausgeführt wird. Ein Test sollte immer das gleiche Ergebnis liefern, unabhängig von externen Faktoren wie dem aktuellen Datum.

---

**Aufgabe 1.4: Testen auf Exceptions**

Gegeben ist eine Methode `StringUtils.repeat(String str, int times)`, die einen String mehrfach wiederholt. Schreiben Sie einen Testfall, der sicherstellt, dass diese Methode eine `IllegalArgumentException` wirft, wenn sie mit einer negativen Anzahl an Wiederholungen aufgerufen wird (z.B. -1).

**Lösung:**

```java
@Test
@DisplayName("Sollte eine IllegalArgumentException bei negativer Wiederholung werfen")
void testRepeatWithNegativeTimes() {
    // Arrange
    String input = "a";
    int repetitions = -1;

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> StringUtils.repeat(input, repetitions)
    );

    // Optional: Überprüfung der Exception-Nachricht
    assertThat(exception.getMessage()).isEqualTo("Times must be non-negative");
}
```

---

**Aufgabe 1.5: Test-Prinzipien**

Betrachten Sie die folgende Testklasse, die einen statischen Zähler verwendet:

```java
public class StaticCounterTest {
    private static int counter = 0;

    @Test
    void testIncrementOnce() {
        counter++;
        assertThat(counter).isEqualTo(1);
    }

    @Test
    void testIncrementTwice() {
        counter++;
        counter++;
        assertThat(counter).isEqualTo(2);
    }
}
```

Welches der **FIRST**-Prinzipien wird hier am deutlichsten verletzt? Begründen Sie Ihre Antwort in 1-2 Sätzen.

**Lösung:**

Das **I**-Prinzip (**Independent** / Unabhängig) wird verletzt. Die Tests teilen sich einen Zustand (`static int counter`). Das Ergebnis eines Tests hängt davon ab, ob und in welcher Reihenfolge andere Tests ausgeführt wurden. Wenn `testIncrementOnce` vor `testIncrementTwice` läuft, wird `testIncrementTwice` fehlschlagen, da der Zähler bereits bei 1 startet und am Ende 3 wäre.

---

**Aufgabe 1.6: Umfassendes Testen einer Klasse**

Sie sollen die folgende Klasse `Account` testen, die ein einfaches Bankkonto darstellt.

```java
public class Account {
    private double balance;
    private final String owner;

    public Account(String owner, double initialBalance) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.owner = owner;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > this.balance) {
            throw new IllegalStateException("Insufficient funds.");
        }
        this.balance -= amount;
    }
}
```

Schreiben Sie eine Testklasse `AccountTest` mit den folgenden vier Testfällen:
1.  Testen Sie, dass eine Einzahlung (`deposit`) den Kontostand korrekt erhöht.
2.  Testen Sie, dass eine Abhebung (`withdraw`) den Kontostand korrekt verringert.
3.  Testen Sie, dass der Versuch, mehr Geld abzuheben als vorhanden ist, eine `IllegalStateException` auslöst.
4.  Testen Sie, dass der Konstruktor eine `IllegalArgumentException` wirft, wenn ein negativer Startsaldo übergeben wird.

**Lösung:**

```java
class AccountTest {

    @Test
    @DisplayName("Einzahlung erhöht den Kontostand korrekt")
    void testDeposit() {
        // Arrange
        Account account = new Account("Max Mustermann", 100.0);

        // Act
        account.deposit(50.0);

        // Assert
        assertThat(account.getBalance()).isEqualTo(150.0);
    }

    @Test
    @DisplayName("Abhebung verringert den Kontostand korrekt")
    void testWithdraw() {
        // Arrange
        Account account = new Account("Max Mustermann", 100.0);

        // Act
        account.withdraw(30.0);

        // Assert
        assertThat(account.getBalance()).isEqualTo(70.0);
    }

    @Test
    @DisplayName("Sollte IllegalStateException bei unzureichender Deckung werfen")
    void testWithdrawInsufficientFunds() {
        // Arrange
        Account account = new Account("Max Mustermann", 100.0);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> account.withdraw(150.0));
    }

    @Test
    @DisplayName("Sollte IllegalArgumentException bei negativem Startsaldo werfen")
    void testConstructorWithNegativeBalance() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Account("Max Mustermann", -50.0));
    }
}
```

---

### Themenblock 2: TDD-Zyklus

**Aufgabe 2.1: TDD-Zyklus simulieren**

Sie sollen eine Methode `FizzBuzz.convert(int number)` testgetrieben entwickeln. Die erste Anforderung lautet: "Die Methode soll die Zahl als String zurückgeben."

**(a) (RED)** Schreiben Sie den ersten, fehlschlagenden Test für die Anforderung. Testen Sie, dass `convert(1)` den String `"1"` zurückgibt. Der Code für die Klasse `FizzBuzz` und die Methode `convert` existiert noch nicht.

**(b) (GREEN)** Schreiben Sie den minimal nötigen Code in der Klasse `FizzBuzz`, damit Ihr Test aus (a) erfolgreich durchläuft.

**(c)** Die nächste Anforderung lautet: "Wenn die Zahl durch 3 teilbar ist, gib 'Fizz' zurück." Beschreiben Sie, wie der TDD-Zyklus für diese neue Anforderung aussehen würde. Sie müssen keinen vollständigen Code schreiben, sondern die Schritte (RED, GREEN, REFACTOR) beschreiben.

**Lösung:**

**(a) (RED) - Der fehlschlagende Test:**
```java
@Test
@DisplayName("Sollte 1 als '1' zurückgeben")
void testConvertOne() {
    // Arrange & Act
    String result = FizzBuzz.convert(1);

    // Assert
    assertThat(result).isEqualTo("1");
}
// Dieser Test schlägt fehl, weil FizzBuzz.convert nicht existiert.
```

**(b) (GREEN) - Minimaler Code:**
```java
public class FizzBuzz {
    public static String convert(int number) {
        return "1"; // Hardcodiert, um den Test zu bestehen
    }
}
```

**(c) TDD-Zyklus für die "Fizz"-Anforderung:**
1.  **RED:** Einen neuen Test schreiben, der fehlschlagen muss. Zum Beispiel: `testConvertThree()` der prüft, ob `FizzBuzz.convert(3)` den String `"Fizz"` zurückgibt. Dieser Test wird fehlschlagen, weil die aktuelle Implementierung `"1"` zurückgibt.
2.  **GREEN:** Den Code minimal anpassen, damit *beide* Tests (für 1 und für 3) grün werden.
    ```java
    public static String convert(int number) {
        if (number == 3) {
            return "Fizz";
        }
        return String.valueOf(number); // Verallgemeinerung von "1"
    }
    ```
3.  **REFACTOR:** Der Code ist noch einfach, aber man könnte prüfen, ob die `if`-Bedingung verbessert werden kann. Hier wäre der nächste Schritt, die Teilbarkeit durch 3 (`number % 3 == 0`) einzuführen, um den Code für weitere Vielfache von 3 (z.B. 6) vorzubereiten.

---

### Themenblock 3: Qualität von Software & Code Smells

**Aufgabe 3.1: Code Smell - Unklare Benennung**

Analysieren Sie die folgende Methode. Identifizieren Sie mindestens zwei Stellen, an denen die Benennung unklar oder zu kurz ist, und schlagen Sie bessere Namen vor.

```java
public class DataHandler {
    // Verarbeitet die Daten und gibt eine Liste zurück
    public List<String> proc(List<String> data) {
        List<String> res = new ArrayList<>();
        for (String s : data) {
            if (s.length() > 5) {
                res.add(s.toUpperCase());
            }
        }
        return res;
    }
}
```

**Lösung:**

1.  **Methodenname `proc`:** Der Name ist nicht aussagekräftig. Ein besserer Name wäre `filterAndUppercaseLongStrings` oder `formatLongWords`.
2.  **Variablenname `res`:** Steht vermutlich für "result", ist aber zu kurz und unklar. Ein besserer Name wäre `formattedStrings` oder `resultList`.
3.  **Variablenname `s`:** Im Kontext einer kurzen Schleife akzeptabel, aber `item` oder `word` wäre expliziter.

---

**Aufgabe 3.2: Code Smell - Duplizierter Code**

Betrachten Sie die folgenden zwei Methoden in einer E-Commerce-Anwendung. Liegt hier eine Verletzung des DRY-Prinzips vor? Begründen Sie Ihre Entscheidung und schlagen Sie gegebenenfalls eine Verbesserung vor.


```java
public class OrderService {
    // Berechnet den Endpreis für einen normalen Kunden
    public double calculatePriceForRegularCustomer(Order order) {
        double total = order.getSubtotal();
        // Standard-Versandkosten
        total += 5.99;
        // 5% Rabatt für Bestellungen über 100€
        if (total > 100.0) {
            total *= 0.95;
        }
        return total;
    }

    // Berechnet den Endpreis für einen Premium-Kunden
    public double calculatePriceForPremiumCustomer(Order order) {
        double total = order.getSubtotal();
        // Premium-Kunden haben kostenlosen Versand
        // 5% Rabatt für Bestellungen über 100€
        if (total > 100.0) {
            total *= 0.95;
        }
        return total;
    }
}
```

**Lösung:**

Ja, hier liegt eine klare Verletzung des DRY-Prinzips vor. Die Geschäftsregel "5% Rabatt für Bestellungen über 100€" ist identisch und wird an zwei Stellen wiederholt. Dies ist eine Duplizierung von Wissen. Wenn sich der Rabattsatz oder der Schwellenwert ändert, muss der Code an zwei Stellen angepasst werden, was fehleranfällig ist.

---

**Aufgabe 3.3: Code Smells identifizieren und beheben**

Der folgende Code dient dazu, einen Report über Benutzeraktivitäten zu erstellen und zu versenden. Der Code funktioniert, ist aber schlecht wartbar.

```java
public class ReportUtil {
    // Erstellt und sendet einen Report
    public void createAndSend(List<User> userList, boolean html) {
        // Header
        String report = "User Activity Report\n====================\n";

        // Body
        for (User u : userList) {
            if (u.isActive()) {
                // Füge aktive User zum Report hinzu
                report += "User: " + u.getName() + ", Last Login: " + u.getLastLogin() + "\n";
            }
        }

        // Footer
        report += "====================\nReport generated on: " + new Date();

        // Senden
        if (html) {
            // Konvertiere zu HTML und sende als HTML-Mail
            String htmlReport = "<html><body><pre>" + report + "</pre></body></html>";
            EmailService.send("admin@example.com", "HTML Report", htmlReport);
        } else {
            // Sende als Text-Mail
            EmailService.send("admin@example.com", "Text Report", report);
        }
    }
}
```

Identifizieren Sie **vier** verschiedene Code Smells in dieser Methode. Nennen Sie für jeden Smell:
1.  Den Namen des Smells.
2.  Eine kurze Begründung, warum es sich um diesen Smell handelt.
3.  Einen Vorschlag, wie der Code refaktorisiert werden könnte, um den Smell zu beheben.

**Lösung:**

1.  **Smell:** **Mehrere Verantwortlichkeiten** (Verletzung des Single Responsibility Principle)
    *   **Begründung:** Die Methode ist für die Erstellung des Report-Headers, des Bodys, des Footers UND für das Senden der E-Mail in zwei verschiedenen Formaten zuständig. Das sind mindestens drei verschiedene Aufgaben (Report-Erstellung, Formatierung, Versand).
    *   **Behebung:** Die Methode in mehrere kleinere Methoden oder sogar Klassen aufteilen: eine Klasse `ReportGenerator`, die den Report-String erstellt, und eine Klasse `ReportSender`, die den Versand übernimmt.

2.  **Smell:** **Long Method** / **Verletzung des SLAP** (Single Level of Abstraction Principle)
    *   **Begründung:** Die Methode mischt verschiedene Abstraktionsebenen: High-Level-Konzepte wie "Header" und "Footer" werden durch Low-Level-String-Konkatenation in einer Schleife unterbrochen.
    *   **Behebung:** Die einzelnen logischen Blöcke (Header, Body, Footer erstellen) in eigene private Methoden extrahieren, z.B. `createReportHeader()`, `createReportBody(userList)`, `createReportFooter()`.

3.  **Smell:** **Boolean-Parameter** (`boolean html`)
    *   **Begründung:** Der Parameter `html` steuert den Kontrollfluss der Methode und deutet darauf hin, dass die Methode zwei Dinge tut. Der Aufrufer muss wissen, was `true` oder `false` bedeutet (`createAndSend(users, true)` ist nicht selbsterklärend).
    *   **Behebung:** Zwei separate, klar benannte Methoden erstellen: `createAndSendHtmlReport(List<User> users)` und `createAndSendTextReport(List<User> users)`.

4.  **Smell:** **Kommentare als Code Smell**
    *   **Begründung:** Der Kommentar `// Füge aktive User zum Report hinzu` beschreibt nur, was der nachfolgende Code ohnehin schon tut. Guter Code sollte selbsterklärend sein.
    *   **Behebung:** Den Kommentar entfernen. Wenn die Logik komplexer wäre, könnte man sie in eine Methode mit einem sprechenden Namen wie `appendActiveUserToReport(report, user)` extrahieren.
