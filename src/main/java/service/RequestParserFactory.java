package service;

public class RequestParserFactory {

    public static RequestParser createRequestParser(){
        return new RequestParserImpl ();
    }
}
