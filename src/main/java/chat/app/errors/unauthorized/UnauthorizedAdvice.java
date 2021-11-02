package chat.app.errors.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.app.errors.ErrorResponse;

@ControllerAdvice
public class UnauthorizedAdvice {
  @ResponseBody
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  ErrorResponse unauthorizedHandler(UnauthorizedException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return response;
  }
}
