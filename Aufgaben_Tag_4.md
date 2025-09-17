# Übung: Mocking mit Mockito

## 

Wir benutzen eine Klasse \texttt{NotificationService}, die zur konstuktion eine Instanz von \texttt{MailSender} benötigt. Wir wollen testen, ob der Service diese Abhängigkeit korrekt aufruft, ohne eine echte Mail zu senden.

### 1. Interface: `MailSender.java`

Dieses Interface definiert den Vertrag für das Senden von E-Mails.

```java
public interface MailSender {
    void sendMail(String recipient, String subject, String body);
}
```
### 2. Klasse: `NotificationService.java`
```java
public class NotificationService {

    private final MailSender mailSender;

    public NotificationService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notifyUser(String userId, String message) {
        if (userId == null || userId.isBlank() || message == null || message.isBlank()) {
            throw new IllegalArgumentException("User ID and message must not be empty.");
        }

        String userEmail = userId + "@example.com";
        String subject = "You have a new notification";

        mailSender.sendMail(userEmail, subject, message);
    }
}
```

### 3. TestKlasse `NotificationServiceTest.java`
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {

    @Test
    void whenUserIsNotNull_thenMailShouldBeSent() { 
    }
    
    @Test
    void whenUserIsNull_thenThrowIllegalArgumentException(){
    }
}
```
Vervollständigen sie die beiden Methoden und überlegen sie jeweils ob sie einen Dummy, Stub oder einen Mock benutzen.
### 3.0 Lösung zu a,b
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {
	@Test
	void whenUserIsNotNull_thenMailShouldBeSent() {
	    MailSender mockedMailSender = mock(MailSender.class);
	    NotificationService notificationService = new NotificationService(mockedMailSender);

	    String userId = "testuser123";
	    String message = "Your request has been approved.";
	    String expectedEmail = "testuser123@example.com";
	    String expectedSubject = "You have a new notification";

	    notificationService.notifyUser(userId, message);

	    verify(mockedMailSender).sendMail(expectedEmail, expectedSubject, message);
	}
	@Test
	void whenUserIsNull_thenThrowIllegalArgumentException() {

	    MailSender mockedMailSender = mock(MailSender.class);
	    NotificationService notificationService = new NotificationService(mockedMailSender);
	    String message = "Some message that should not be sent.";

	    assertThrows(IllegalArgumentException.class, () -> {
		notificationService.notifyUser(null, message);
	    });
	}
}
```

### 3.1 Aufgabe c (ich weiß ist semi gut strukturiert)
```java
public interface BlacklistService {
    boolean isUserBlacklisted(String userId);
}
```
```java
public class NotificationService {

    private final MailSender mailSender;
    private final BlacklistService blacklistService;

    // Der Konstruktor nimmt nun beide Abhängigkeiten entgegen
    public NotificationService(MailSender mailSender, BlacklistService blacklistService) {
        this.mailSender = mailSender;
        this.blacklistService = blacklistService;
    }

    public void notifyUser(String userId, String message) {
        if (userId == null || userId.isBlank() || message == null || message.isBlank()) {
            throw new IllegalArgumentException("User ID and message must not be empty.");
        }

        // Neue Logik: Nur senden, wenn der User NICHT auf der Blacklist ist
        if (blacklistService.isUserBlacklisted(userId)) {
            return; // Sende keine E-Mail
        }

        String userEmail = userId + "@example.com";
        String subject = "You have a new notification";

        mailSender.sendMail(userEmail, subject, message);
    }
}
```
```java
@Test
void whenUserIsNotBlacklisted_thenMailShouldBeSent() {
}
```
### 3.1 Lösung c
```java
@Test
void whenUserIsBlacklisted_thenMailShouldNotBeSent() {
    MailSender mailSenderMock = mock(MailSender.class);
    BlacklistService blacklistServiceStub = mock(BlacklistService.class);
    String blacklistedUserId = "blockedUser";

    when(blacklistServiceStub.isUserBlacklisted(blacklistedUserId)).thenReturn(true);
    
    NotificationService notificationService = new NotificationService(mailSenderMock, blacklistServiceStub);
    
    notificationService.notifyUser(blacklistedUserId, "This message should be blocked.");
    
    verify(mailSenderMock, never()).sendMail(anyString(), anyString(), anyString());
}
```

### 4 Aufgabe FactoryMethode
```java
// Die zu testende Klasse (unverändert lassen)
class User {
    private final String id;
    private final String username;
    private final String email;
    private boolean isActive;

    public User(String id, String username, String email, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
    }
    
    public boolean isActive() { return isActive; }
    public String getUsername() { return username; }
}

class UserServiceTest {

    @Test
    void whenUserIsActive_shouldReturnUsername() {
        // Arrange - Dieser Block ist repetitiv
        User user = new User("user-123", "default_user", "test@example.com", true);

        assertNotNull(user.getUsername());
        assertTrue(user.isActive());
    }

    @Test
    void whenCheckingStatus_shouldConfirmUserIsActive() {
        // Arrange - Dieser Block ist identisch und damit auch repetitiv
        User user = new User("user-123", "default_user", "test@example.com", true);
        
        assertTrue(user.isActive());
    }
}
```
### 4 Lösung
```java
class TestUsers {
    public static User createDefaultUser() {
        // ... hier die Logik zum Erstellen des Users
    }
}
```
### 5 Builder Pattern
```java
public class Computer {
    private final String cpu;
    private final int ramInGB;
    private final int storageInGB;
    private final String graphicsCard;

    Computer(String cpu, int ramInGB, int storageInGB, String graphicsCard) {
        this.cpu = cpu;
        this.ramInGB = ramInGB;
        this.storageInGB = storageInGB;
        this.graphicsCard = graphicsCard;
    }

    @Override
    public String toString() {
        return "PC Config: [CPU=" + cpu + ", RAM=" + ramInGB + "GB]";
    }
}

public class ComputerBuilder {

}
```
Vervollständigen sie den ComputerBuilder.
### 5 Lösung
```java
public class ComputerBuilder {
    private final String cpu;
    private final int ramInGB;
    private int storageInGB;
    private String graphicsCard;

    public ComputerBuilder(String cpu, int ramInGB) {
        this.cpu = cpu;
        this.ramInGB = ramInGB;
    }

    public ComputerBuilder withStorage(int storageInGB) {
        this.storageInGB = storageInGB;
        return this;
    }

    public ComputerBuilder withGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
        return this;
    }

    public Computer build() {
        return new Computer(cpu,ramInGB,storageInGB,graphicsCard);
    }
}
```
### 6 Custom Assertion Beispiel
```java
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    public UserAssert(User actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public UserAssert isActive() {
        isNotNull();

        if (!actual.isActive()) {
            failWithMessage("Expected user to be active, but was not.");
        }
        
        return this;
    }

    public UserAssert hasUsername(String expectedUsername) {
        isNotNull();
        Assertions.assertThat(actual.getUsername())
                  .overridingErrorMessage("Expected username to be <%s> but was <%s>",
                                          expectedUsername, actual.getUsername())
                  .isEqualTo(expectedUsername);
        
        return this;
    }
}
```
### 7 Aggregate Aufgabe
Du entwirfst das Kernmodell für eine Bibliotheksverwaltungssoftware. Das System muss Bücher, deren einzelne Exemplare und die Ausleihvorgänge verwalten. Die Herausforderung besteht darin, die Objekte korrekt als Entitäten, Value Objects und Aggregate zu modellieren, um Geschäftsregeln durchzusetzen und die Konsistenz des Systems zu gewährleisten.

## Aufgabe 7.1: Das Buch als Katalogeintrag (ca. 10 Minuten)

Zuerst modellierst du das "Buch" als generischen Eintrag im Bibliothekskatalog.

Ein Buch im Katalog wird durch seine ISBN (International Standard Book Number) eindeutig identifiziert. Die ISBN ändert sich nie. Zu einem Buch werden Titel, Autor und das Erscheinungsjahr erfasst. Titel und Autor können nachträglich korrigiert werden, falls bei der Erfassung ein Fehler gemacht wurde.

In der Bibliothek gibt es von einem Buch mehrere physische Exemplare. Jedes Exemplar hat eine eigene, einzigartige Inventarnummer, die auf einen Aufkleber gedruckt wird. Der Zustand eines Exemplars (z.B. "Neu", "Gebraucht", "Beschädigt") kann sich im Laufe der Zeit ändern.

Fragen:

  1.  Ist das Buch (definiert durch die ISBN) eine Entität oder ein Value Object?

  2. Ist das physische Exemplar (definiert durch die Inventarnummer) eine Entität oder ein Value Object?

  3. Die ISBN wird oft als Objekt modelliert, anstatt nur als String. Was wäre die ISBN – eine Entität oder ein Value Object? Begründen sie kurz.

## Aufgabe 7.2: Das Aggregat entwerfen (ca. 15 Minuten)

Jetzt geht es darum, die Konsistenz der Daten sicherzustellen. Die zentrale Geschäftsregel lautet: Die Anzahl der verfügbaren Exemplare eines Buches muss immer korrekt sein. Wenn ein neues Exemplar hinzugefügt oder ein beschädigtes entfernt wird, muss sich der Gesamtbestand des Buches konsistent ändern.

Um diese Regel durchzusetzen, entscheidest du dich, ein Aggregat zu bilden.

Fragen:

  1. Welches Objekt aus Aufgabe 1 ist der natürliche Kandidat für das Aggregate Root? Warum ist dieses Objekt am besten geeignet, um die Geschäftsregeln für alle zugehörigen Teile durchzusetzen?

  2. Welche Objekte würden zu diesem Aggregat gehören?

  3. Beschreibe eine Methode, die am Aggregate Root existieren könnte, um ein neues Exemplar hinzuzufügen (z.B. addBookCopy(...)). Welche Informationen würde diese Methode benötigen und welche Logik würde sie ausführen, um die Konsistenz sicherzustellen?

## Aufgabe 7.3: Der Ausleihvorgang (ca. 5 Minuten)

Ein Benutzer kann ein bestimmtes Exemplar eines Buches ausleihen. Ein Ausleihvorgang wird durch die Inventarnummer des Exemplars und die Mitgliedsnummer des Benutzers definiert. Wichtig ist auch der Ausleihzeitraum, der ein Start- und ein Rückgabedatum hat.

Frage:
Stell dir vor, du modellierst den Ausleihzeitraum. Handelt es sich hierbei um eine Entität oder ein Value Object? Begründe deine Entscheidung im Kontext des Ausleihvorgangs.
