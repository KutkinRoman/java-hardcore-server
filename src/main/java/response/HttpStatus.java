package response;

public enum HttpStatus {

    OK (200, "OK"),
    NOT_FOUND(404, "NOT FOUND"),
    METHOD_NOT_ALLOWED(405, "METHOD NOT ALLOWED");

    private int code;
    private String value;

    HttpStatus (int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString () {
        return code + "\r" + value;
    }
}
