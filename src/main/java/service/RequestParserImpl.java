package service;

import request.HttpRequest;
import request.RequestMethod;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class RequestParserImpl implements RequestParser {

    public HttpRequest parseRequest (Deque<String> rawRequest) {
        String[] firstLine = rawRequest.pollFirst ().split (" ");
        RequestMethod method = RequestMethod.valueOf (firstLine[0]);
        String url = firstLine[1];

        Map<String, String> params = new HashMap<> ();
        if (url.contains ("?")) {
            String[] splitUrl = url.split ("\\?|&");
            url = splitUrl[0];
            for (int i = 1; i < splitUrl.length; i++) {
                 String[] splitParam = splitUrl[i].split ("=");
                 params.put (splitParam[0], splitParam[1]);
            }
        }

        Map<String, String> headers = new HashMap<> ();
        while (!rawRequest.isEmpty ()) {
            String line = rawRequest.pollFirst ();
            if (line.isBlank ()) {
                break;
            }
            String[] header = line.split (": ");
            headers.put (header[0], header[1]);
        }
        StringBuilder body = new StringBuilder ();
        while (!rawRequest.isEmpty ()) {
            body.append (rawRequest.pollFirst ());
        }

        return HttpRequest.createBuilder ()
                .withMethod (method)
                .withParams (params)
                .withUrl (url)
                .withHeaders (headers)
                .withBody (body.toString ())
                .build ();
    }


}
