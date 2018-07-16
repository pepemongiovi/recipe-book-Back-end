package recipebook.responses;

import org.springframework.http.HttpStatus;

public class ResponseConflict extends Response {
    public ResponseConflict(String message) {
        super(HttpStatus.CONFLICT.value(), message, null);
    }
}
