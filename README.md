This is a demo for the [spring-icomponent](https://github.com/pavel-grigorev/spring-icomponent) library. It provides the sample implementations of the `MethodHandler` and `MethodHandlerResolver` interfaces.

The demo imitates a typical application in the part responsible for sending emails. There is an interface:

```java
public interface EmailService {
  void sendConfirmation(String username, String link, String email);
  void sendWelcome(User user);
}
```

A typical implementation of the `EmailService` interface may look like so:

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

This demo provides the sample implementations of the `MethodHandler` and `MethodHandlerResolver` interfaces that let us, with the help of [spring-icomponent](https://github.com/pavel-grigorev/spring-icomponent), turn the code above into this:

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
