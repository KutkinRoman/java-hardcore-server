package response;

public class HttpResponse {


    private HttpStatus status;
    private ContentType contentType;
    private byte[] body;

    private HttpResponse(){}

    public HttpStatus getStatus () {
        return status;
    }

    public ContentType getContentType () {
        return contentType;
    }

    public byte[] getBody () {
        return body;
    }

    public static Builder createBuilder(){
        return new Builder ();
    }

    public static class Builder{

        private final HttpResponse httpResponse;

        private Builder(){
            httpResponse = new HttpResponse ();
        }

        public Builder withStatus(HttpStatus status){
            httpResponse.status = status;
            return this;
        }

        public Builder withContentType(ContentType contentType){
            httpResponse.contentType = contentType;
            return this;
        }

        public Builder withBody(byte[] body){
            httpResponse.body = body;
            return this;
        }

        public HttpResponse build(){
            return httpResponse;
        }
    }

}
