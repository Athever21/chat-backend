package chat.app.errors.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.app.errors.ErrorResponse;

@ControllerAdvice
public class NotFoundAdvice {
  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorResponse notFoundHandler(NotFoundException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return response;
  }
}
