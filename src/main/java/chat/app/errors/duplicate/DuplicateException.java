package chat.app.errors.duplicate;

public class DuplicateException extends RuntimeException {
  public DuplicateException(String message) {
    super(message);
  }
}
