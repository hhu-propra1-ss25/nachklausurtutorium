### Übungsaufgaben für Tag 3

### Themenblock 1: Bausteine & Strukturen

**Aufgabe 1.1: Verkettete Methodenaufrufe**

Der folgende Codeausschnitt soll die Postleitzahl des Abteilungsleiters eines Mitarbeiters abrufen.

```java
public class AdressService {
    public String getLeiterPLZ(Mitarbeiter mitarbeiter) {
        // Hole Abteilung, dann Leiter, dann Adresse, dann PLZ
        return mitarbeiter.getAbteilung().getLeiter().getAdresse().getPostleitzahl();
    }
}
```

**(a)** Welches Entwurfsprinzip wird hier verletzt? Nennen Sie auch den zugehörigen Code Smell.
**(b)** Warum ist dieser Code schlecht wartbar? Nennen Sie ein konkretes Szenario, das eine Änderung an dieser Stelle erfordern würde, obwohl sich die direkte Abhängigkeit (`Mitarbeiter`) nicht wesentlich geändert hat.

**Lösung:**

**(a)**
*   **Prinzip:** Das **Law of Demeter (LoD)** ("Don't talk to strangers").
*   **Code Smell:** **Message Chains** (Nachrichtenketten). Die Methode ruft eine Kette von Gettern auf Objekten auf, die sie nicht direkt "kennt".

**(b)** Der Code ist schlecht wartbar, weil er stark an die interne Struktur der Klassen `Abteilung`, `Mitarbeiter` und `Adresse` gekoppelt ist. Wenn sich zum Beispiel die Klasse `Abteilung` ändert und den Leiter nicht mehr direkt, sondern über eine `Management`-Klasse verwaltet (`getManagement().getLeiter()`), müsste die `AdressService`-Klasse angepasst werden. Der Code ist brüchig gegenüber Änderungen in weit entfernten Teilen des Systems.

---

**Aufgabe 1.2: Analyse eines Bestellprozesses**

Analysieren Sie die folgende Klasse `BestellManager`. Sie dient zur Verarbeitung von Online-Bestellungen.

```java
public class BestellManager {
    private final DatenbankConnector db;

    public BestellManager(DatenbankConnector db) {
        this.db = db;
    }

    public void verarbeiteBestellung(String kundenName, String strasse, String plz, List<Artikel> artikel) {
        // 1. Validierung der Kundendaten
        if (kundenName == null || strasse == null || plz == null || plz.length() != 5) {
            System.err.println("Fehler: Ungültige Kundendaten.");
            return;
        }

        // 2. Berechnung des Gesamtpreises
        double gesamtpreis = 0;
        for (Artikel a : artikel) {
            gesamtpreis += a.getPreis();
        }
        if (gesamtpreis > 100) {
            gesamtpreis *= 0.9; // 10% Rabatt
        }

        // 3. Speichern der Bestellung
        Bestellung b = new Bestellung(kundenName, strasse, plz, artikel, gesamtpreis);
        db.speichern(b);

        // 4. E-Mail an den Kunden senden
        String mailText = "Vielen Dank für Ihre Bestellung! Gesamtpreis: " + gesamtpreis;
        MailSender.send(db.getKundenEmail(kundenName), "Bestellbestätigung", mailText);
    }
}
```

Identifizieren Sie **drei** verschiedene Verstöße gegen die in diesem Block besprochenen Prinzipien (z.B. SRP, IHP, Kopplung, Kohäsion, LoD). Begründen Sie jeweils Ihre Antwort.

**Lösung:**

1.  **Verletzung des SRP / Geringe Kohäsion:** Die Methode `verarbeiteBestellung` hat zu viele Verantwortlichkeiten. Sie validiert Kundendaten, berechnet Preise (inkl. Rabattlogik), speichert die Bestellung und versendet eine Bestätigungs-E-Mail. Diese Aufgaben haben unterschiedliche Änderungsgründe und gehören logisch nicht zusammen. Die Klasse hat eine geringe funktionale Kohäsion.
2.  **Verletzung des Law of Demeter (LoD):** Der Aufruf `db.getKundenEmail(kundenName)` gefolgt von `MailSender.send(...)` ist eine LoD-Verletzung. Der `BestellManager` sollte nicht wissen müssen, *wie* die E-Mail-Adresse beschafft wird, um sie dann an einen anderen Dienst weiterzugeben. Besser wäre es, einem `NotificationService` die Kunden-ID oder den Namen zu geben, und dieser kümmert sich um den Rest.
3.  **Hohe Kopplung:** Die Klasse ist direkt an konkrete Implementierungen wie `DatenbankConnector`, `MailSender` und `System.err.println` gekoppelt. Eine Änderung im Logging, in der Mail-Versand-Logik oder in der Datenbank würde Änderungen in dieser Klasse erfordern. Besser wäre die Verwendung von Interfaces (Dependency Inversion).

---

**Aufgabe 1.3: Verantwortlichkeiten einer Service-Klasse**

Betrachten Sie die folgende Klasse `MitarbeiterService`, die Mitarbeiterdaten verarbeitet.

```java
public class MitarbeiterService {
    public void speichereMitarbeiter(Mitarbeiter m) {
        // Logik zum Speichern in der Datenbank...
        System.out.println("Mitarbeiter " + m.getName() + " gespeichert.");
    }

    public String generiereMitarbeiterReport(Mitarbeiter m) {
        // Erstellt einen formatierten String mit Mitarbeiterdetails
        String report = "Report für: " + m.getName() + "\n";
        report += "Position: " + m.getPosition() + "\n";
        report += "Abteilung: " + m.getAbteilung() + "\n";
        return report;
    }

    public double berechneJahresgehalt(Mitarbeiter m) {
        // Berechnet das Jahresgehalt basierend auf dem Monatsgehalt
        return m.getMonatsgehalt() * 12;
    }
}
```

Welches zentrale Entwurfsprinzip wird von der Klasse `MitarbeiterService` verletzt? Begründen Sie Ihre Antwort und schlagen Sie vor, wie man den Code refaktorisieren könnte, um das Problem zu beheben.

**Lösung:**

*   **Verletztes Prinzip:** Das **Single Responsibility Prinzip (SRP)**.
*   **Begründung:** Die Klasse hat mehrere Verantwortlichkeiten (Änderungsgründe). Sie ist zuständig für:
    1.  Die Persistenz von Mitarbeiterdaten (`speichereMitarbeiter`).
    2.  Die Darstellung von Mitarbeiterdaten (`generiereMitarbeiterReport`).
    3.  Die Geschäftslogik zur Gehaltsberechnung (`berechneJahresgehalt`).
    Eine Änderung in der Datenbanklogik, im Reportformat oder in der Gehaltsberechnung würde jeweils eine Änderung dieser einen Klasse erfordern.
*   **Behebung:** Die Klasse sollte in drei separate Klassen aufgeteilt werden, die jeweils nur eine Verantwortung haben:
    *   `MitarbeiterRepository` (für die Speicherung).
    *   `MitarbeiterReportGenerator` (für die Reporterstellung).
    *   `Gehaltsrechner` (für die Geschäftslogik).

---

### Themenblock 2: Vererbung & Polymorphismus

**Aufgabe 2.1: Polymorphie in der Praxis**

Sie entwerfen ein System zur Verarbeitung von Dokumenten. Sie haben eine Basisklasse `Dokument` und zwei abgeleitete Klassen.

```java
public class Dokument {
    protected String inhalt;

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public void drucken() {
        // Simuliert das Drucken des Inhalts
        System.out.println("Drucke: " + inhalt);
    }
}

public class TextDokument extends Dokument {
    // Keine zusätzlichen Methoden
}

public class SchreibgeschuetztesDokument extends Dokument {
    public SchreibgeschuetztesDokument(String inhalt) {
        this.inhalt = inhalt;
    }

    @Override
    public void setInhalt(String inhalt) {
        throw new UnsupportedOperationException("Dokument ist schreibgeschützt!");
    }
}
```
Ein Client-Code verwendet diese Klassen polymorph:
```java
public void bearbeiteUndDrucke(Dokument doc, String neuerInhalt) {
    doc.setInhalt(neuerInhalt); // Hier kann es krachen!
    doc.drucken();
}
```

**(a)** Welches SOLID-Prinzip wird durch die Klasse `SchreibgeschuetztesDokument` verletzt?
**(b)** Begründen Sie, warum das Prinzip verletzt ist, und beziehen Sie sich dabei auf den Client-Code.
**(c)** Welcher Code Smell liegt in `SchreibgeschuetztesDokument` vor?

**Lösung:**

**(a)** Das **Liskov Substitution Principle (LSP)** wird verletzt.

**(b)** Das LSP besagt, dass Objekte einer abgeleiteten Klasse anstelle von Objekten der Basisklasse verwendet werden können müssen, ohne das Verhalten des Programms zu ändern. Der Client-Code `bearbeiteUndDrucke` erwartet, dass er auf jedem `Dokument`-Objekt die Methode `setInhalt` aufrufen kann. Wenn jedoch ein `SchreibgeschuetztesDokument` übergeben wird, wirft die Methode eine `UnsupportedOperationException`, was das Programm zum Absturz bringt. Das Verhalten ändert sich also unerwartet, weshalb die Substitution nicht sicher ist.

**(c)** Der Code Smell ist **Refused Bequest** (Verweigertes Erbe). Die Subklasse `SchreibgeschuetztesDokument` erbt die Methode `setInhalt`, kann oder will deren Funktionalität aber nicht sinnvoll bereitstellen und "verweigert" die Implementierung, indem sie eine Exception wirft.

---

**Aufgabe 2.2: Methoden in einer Klassenhierarchie**

Gegeben sind die folgenden Klassen:

```java
class Tier {
    public void geraeuschMachen() {
        System.out.println("Ein Tier macht ein Geräusch.");
    }

    public void fuettern(String futter) {
        System.out.println("Das Tier frisst " + futter);
    }
}

class Hund extends Tier {
    @Override
    public void geraeuschMachen() {
        System.out.println("Wuff!");
    }

    public void fuettern(String futter, int anzahlLeckerli) {
        System.out.println("Der Hund frisst " + futter + " und " + anzahlLeckerli + " Leckerli.");
    }
}
```

Welche Methode in der Klasse `Hund` ist ein Beispiel für **Überschreiben (Overriding)** und welche für **Überladen (Overloading)**? Begründen Sie kurz Ihre Zuordnung.

**Lösung:**

*   **Überschreiben (Overriding):** Die Methode `geraeuschMachen()` in `Hund` ist ein Beispiel für Overriding. Sie hat exakt dieselbe Signatur (Name und Parameter) wie die Methode in der Superklasse `Tier` und ersetzt deren Implementierung.
*   **Überladen (Overloading):** Die Methode `fuettern(String, int)` in `Hund` ist ein Beispiel für Overloading. Sie hat denselben Namen wie die Methode in der Superklasse, aber eine andere Parameterliste. Sie ersetzt die geerbte Methode nicht, sondern existiert zusätzlich zu ihr.

---

### Themenblock 3: Code Smells im Großen

**Aufgabe 3.1: Refactoring einer gewachsenen Klasse**

Die folgende Klasse `Artikel` ist Teil eines Shopsystems und ist über die Zeit gewachsen.

```java
public class Artikel {
    private String name;
    private double preis;
    private String waehrung; // z.B. "EUR"
    private int lagerbestand;

    // Konstruktor und Getter/Setter...

    // Methode 1: Berechnet den Rabattpreis für einen bestimmten Kundentyp
    public double getRabattPreis(Kunde kunde) {
        if (kunde.getTyp().equals("PREMIUM")) {
            return this.preis * 0.8; // 20% Rabatt
        } else if (kunde.getTyp().equals("GOLD")) {
            return this.preis * 0.9; // 10% Rabatt
        }
        return this.preis;
    }

    // Methode 2: Formatiert die Artikeldetails für die Anzeige im Webshop
    public String alsHtmlAnzeigen() {
        return "<div><h1>" + this.name + "</h1><p>Preis: " + this.preis + " " + this.waehrung + "</p></div>";
    }

    // Methode 3: Prüft, ob der Artikel an eine bestimmte Adresse lieferbar ist
    public boolean istLieferbarNach(String plz, String land) {
        // Komplexe Logik, die prüft, ob der Artikel in diese Region geliefert werden darf
        // ... verwendet nur plz und land
        return true;
    }
}
```

Identifizieren Sie **drei** verschiedene Code Smells in dieser Klasse. Begründen Sie Ihre Wahl und schlagen Sie jeweils eine konkrete Verbesserung vor.

**Lösung:**

1.  **Smell: Feature Envy** (Merkmal-Neid)
    *   **Beschreibung:** Die Methode `getRabattPreis` ist "neidisch" auf die Klasse `Kunde`. Sie verwendet mehr Daten von der `Kunde`-Klasse (`kunde.getTyp()`) als von ihrer eigenen Klasse (`this.preis`). Die Logik für kundenabhängige Rabatte gehört eher in die `Kunde`-Klasse oder in eine separate `RabattService`-Klasse.
    *   **Verbesserung:** Verschieben Sie die Methode in eine `Preisrechner`-Klasse, die sowohl den Artikel als auch den Kunden als Parameter erhält: `berechnePreis(Artikel a, Kunde k)`.

2.  **Smell: Large Class / Verletzung des SRP**
    *   **Beschreibung:** Die Klasse `Artikel` hat mehrere Verantwortlichkeiten. Sie hält Kerndaten (`name`, `preis`), enthält Preisberechnungslogik (`getRabattPreis`), Darstellungslogik (`alsHtmlAnzeigen`) und Logistik-Logik (`istLieferbarNach`). Dies macht die Klasse groß und schwer zu warten.
    *   **Verbesserung:** Extrahieren Sie die Verantwortlichkeiten in eigene Klassen:
        *   `ArtikelHtmlFormatter` für die HTML-Darstellung.
        *   `LieferService` für die Logik der Lieferbarkeit.
        *   Die Preislogik wie oben beschrieben auslagern.

3.  **Smell: Primitive Obsession**
    *   **Beschreibung:** Fachliche Konzepte wie Preis, Währung und Adresse werden durch primitive Datentypen (`double`, `String`) dargestellt.
    *   **Verbesserung:** Führen Sie Value Objects ein:
        *   Eine Klasse `Geldbetrag` (oder `Preis`), die Betrag und Währung kapselt.
        *   Eine Klasse `Adresse` oder `Region`, die PLZ und Land kapselt.

---

**Aufgabe 3.2: Untersuchung einer Methodensignatur**

Betrachten Sie die Signatur der folgenden Methode zur Erstellung eines neuen Benutzers:

```java
public class UserService {
    public void createUser(String vorname, String nachname,
                           String strasse, String hausnummer, String plz, String stadt,
                           double startguthaben, String waehrung) {
        // ... Implementierung
    }
}
```

Identifizieren Sie zwei verschiedene "Code Smells im Großen" in dieser Methodensignatur. Beschreiben Sie, wie Sie den Code refaktorisieren würden, um diese Smells zu beheben.

**Lösung:**

1.  **Smell: Data Clump** (Datengruppen)
    *   **Beschreibung:** Die Parameter `vorname`, `nachname`, `strasse`, `hausnummer`, `plz` und `stadt` treten immer gemeinsam auf, da sie eine Adresse und einen Namen beschreiben. Sie bilden eine logische Einheit.
    *   **Behebung:** Führen Sie neue Klassen ein, um diese Daten zu kapseln. Zum Beispiel eine Klasse `Adresse` (mit Straße, Hausnummer, PLZ, Stadt) und eine Klasse `Personenname` (mit Vor- und Nachname). Die Signatur würde dann zu `createUser(Personenname name, Adresse adresse, ...)` vereinfacht.

2.  **Smell: Primitive Obsession** (Besessenheit von Primitiven)
    *   **Beschreibung:** Fachliche Konzepte wie "Geld" oder "Währung" werden durch primitive Datentypen (`double`, `String`) dargestellt. Dies führt zu Problemen, z.B. Rundungsfehlern bei `double` für Geldbeträge oder ungültigen Währungscodes als `String`.
    *   **Behebung:** Führen Sie eine fachliche Klasse (Value Object) `Geldbetrag` ein, die sowohl den Betrag (z.B. als `BigDecimal`) als auch die Währung kapselt. Die Signatur würde zu `createUser(..., Geldbetrag startguthaben)` geändert.
