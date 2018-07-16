package recipebook.responses;

public class ResponseAuthorization {

    private String token;

    public ResponseAuthorization(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
