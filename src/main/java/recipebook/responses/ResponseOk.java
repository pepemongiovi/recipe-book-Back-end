package recipebook.responses;

public class ResponseOk extends Response {

    public ResponseOk(String message, Object data) {
        super(200, message, data);
    }

    public ResponseOk(Object data) {
        this(null, data);
    }
}
