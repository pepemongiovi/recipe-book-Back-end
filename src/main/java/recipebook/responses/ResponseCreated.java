package recipebook.responses;

import org.springframework.http.HttpStatus;

public class ResponseCreated extends Response{

    public ResponseCreated(String message, Object data) {
        super(HttpStatus.CREATED.value(), message, data);
    }
}
