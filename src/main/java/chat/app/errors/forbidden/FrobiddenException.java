package chat.app.errors.forbidden;

public class FrobiddenException extends RuntimeException {
  public FrobiddenException(String message) {
    super(message);
  }
}
