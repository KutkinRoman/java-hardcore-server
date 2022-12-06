package request;

import java.util.Map;

public class HttpRequest {

    private RequestMethod method;

    private String url;

    private Map<String, String> headers;

    private String body;

    private Map<String, String> params;

    private HttpRequest () {
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public static Builder createBuilder(){
        return new Builder ();
    }

    public String getParam (String key) {
        return params.get (key);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }

    public static class Builder{

        private final HttpRequest httpRequest;

        private Builder () {
            this.httpRequest = new HttpRequest ();
        }

        public Builder withMethod(RequestMethod method){
            this.httpRequest.method = method;
            return this;
        }

        public Builder withUrl(String url){
            this.httpRequest.url = url;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers){
            this.httpRequest.headers = headers;
            return this;
        }

        public Builder withBody(String body){
            this.httpRequest.body = body;
            return this;
        }

        public Builder withParams(Map<String, String> params){
            this.httpRequest.params = params;
            return this;
        }

        public HttpRequest build(){
            return this.httpRequest;
        }
    }
}
