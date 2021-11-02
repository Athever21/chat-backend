package chat.app.errors.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chat.app.errors.ErrorResponse;

@ControllerAdvice
public class BadRequestAdvice {
  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponse wrongPasswordHandler(BadRequestException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return response;
  }  
}
