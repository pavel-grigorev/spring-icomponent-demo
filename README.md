This is a demo project for the [icomponent](https://github.com/pavel-grigorev/icomponent) library. It provides sample implementations of the `MethodHandler` and `MethodHandlerResolver` interfaces.

Classes in the project imitate a typical application in the part responsible for sending emails. There is usually a transport service. Sending emails is all it does. It does not modify the message body or subject. It may look like so:

```java
public interface EmailTransport {
  void send(String subject, String body, String... to);
}
```

Then, there is usually sort of an `EmailSender` that builds the message body based on a template and passes it to the email transport service. It may look like so:

```java
public interface EmailSender {
  void send(String subjectMessageName, String bodyTemplateName, Map<String, Object> templateParameters, String... to);
}
```

At last, there is an `EmailService` that provides API for the business classes. It may look like so:

```java
public interface EmailService {
  void sendConfirmation(String username, String link, String email);
  void sendWelcome(User user);
}
```

A typical implementation of the `EmailService` may look like so:

```java
@Service
public class MyEmailService implements EmailService {
  @Autowired
  EmailSender emailSender;

  @Override
  public void sendConfirmation(String username, String link, String email) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("username", username);
    parameters.put("link", link);

    emailSender.send("email.subject.confirmation", "confirmation", parameters, email);
  }

  @Override
  public void sendWelcome(User user) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user", user);

    emailSender.send("email.subject.welcome", "welcome", parameters, user.getEmail());
  }
}
```

The demo project provides sample implementations of `MethodHandler` and `MethodHandlerResolver` that let us, with the help of [icomponent](https://github.com/pavel-grigorev/icomponent), turn that into this:

```java
@Service
public interface EmailService {
  @Subject("email.subject.confirmation")
  @Template("confirmation")
  void sendConfirmation(@Param("username") String username, @Param("link") String link, @To String email);

  @Subject("email.subject.welcome")
  @Template("welcome")
  void sendWelcome(@Param("user") @To User user);
}
```
