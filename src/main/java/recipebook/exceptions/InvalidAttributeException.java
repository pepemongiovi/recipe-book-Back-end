package recipebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAttributeException extends RuntimeException {
    private String message;

    public InvalidAttributeException(String message) {
        super(message);
    }
}
