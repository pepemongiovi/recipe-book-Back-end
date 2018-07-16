package recipebook.responses;

import org.springframework.http.HttpStatus;

public class ResponseBadRequest extends Response {

    public ResponseBadRequest(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message, null);
    }
}
