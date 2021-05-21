This is a demo for the [spring-icomponent](https://github.com/pavel-grigorev/spring-icomponent) library. It provides a sample implementation of the `MethodHandler` interface.

The demo imitates a typical application in the part responsible for sending emails. There is an interface:

```java
public interface RegistrationEmailService {
  void sendConfirmation(String name, String link, String email);
  void sendWelcome(User user);
}
```

A typical implementation of the above interface may look like so:

```java
@Service
public class MyEmailService implements EmailService {
  @Autowired
  EmailSender emailSender;

  @Override
  public void sendConfirmation(String name, String link, String email) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", name);
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

This demo provides a sample implementation of the `MethodHandler` interface that let us, with the help of [spring-icomponent](https://github.com/pavel-grigorev/spring-icomponent), turn the code above into this:

```java
@Service
public interface EmailService {
  void sendConfirmation(@Param("name") String name, @Param("link") String link, @To String email);
  void sendWelcome(@Param("user") @To User user);
}
```
