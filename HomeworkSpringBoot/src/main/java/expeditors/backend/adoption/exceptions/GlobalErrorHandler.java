package expeditors.backend.adoption.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalErrorHandler {

    /**
     * Handle validation errors for automatic validation, i.e with the @Valid annotation.
     * For this to be invoked, you have to have a controller argument of object type
     * to which you have attached the @Valid annotation.
     * Look at the end of StudentRestController for an example, which may be commented out
     * by default.
     * @param ex the exception thrown
     * @param request the incoming request
     * @return a bad request + restresult that contains the errors
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          WebRequest request) {

        var errors = ex.getFieldErrors();

        List<String> errMsgs = errors.stream()
                .map(error -> "@Valid error:" + error.getField() + ": " + error.getDefaultMessage()
                        + ", supplied Value: " + error.getRejectedValue())
                .collect(toList());

        ResponseEntity<?> rr = ResponseEntity.badRequest().body(errMsgs);

        return rr;
    }
}
