package chat.app.errors.forbidden;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.app.errors.ErrorResponse;

@ControllerAdvice
public class ForbiddenAdvice {
  @ResponseBody
  @ExceptionHandler(FrobiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  ErrorResponse handleForbidden(FrobiddenException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return response;
  }
}
