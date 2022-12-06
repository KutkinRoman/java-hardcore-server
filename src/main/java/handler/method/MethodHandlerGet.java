package handler.method;

import entity.User;
import request.HttpRequest;
import request.RequestMethod;
import response.ContentType;
import response.HttpResponse;
import response.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Handle(method = RequestMethod.GET, order = 1)
public class MethodHandlerGet extends MethodHandler {

    public MethodHandlerGet (MethodHandler methodHandler) {
        super (methodHandler);
    }

    @Override
    HttpResponse handle (HttpRequest httpRequest) {
        if (fileService.exists (httpRequest.getUrl ())) {
            return fileHandle (httpRequest);
        }
        return methodsHandle (this, httpRequest);
    }


    @UrlRequest("/")
    private HttpResponse index () {
        return createHtmlPage ("index");
    }

    @UrlRequest("/registration")
    private HttpResponse registration () {
        return createHtmlPage ("registration-form");
    }

    @UrlRequest("/users")
    private HttpResponse getUsersPage () {
        return createHtmlPage ("users-page");
    }

    @UrlRequest("/api/users")
    private HttpResponse getUsers (HttpRequest httpRequest) {
        List<User> users = userRepository.findAll ();
        System.out.println (users);
        byte[] body = jsonSerializer.serialize (users).getBytes(StandardCharsets.UTF_8);
        return HttpResponse.createBuilder ()
                .withStatus (HttpStatus.OK)
                .withContentType (ContentType.JSON)
                .withBody (body)
                .build ();
    }

    private HttpResponse fileHandle (HttpRequest httpRequest) {
        String url = httpRequest.getUrl ();
        ContentType contentType = contentTypeParser.parse (fileService.getPath (url));
        byte[] body = fileService.getBytes (url);
        return HttpResponse.createBuilder ()
                .withStatus (HttpStatus.OK)
                .withContentType (contentType)
                .withBody (body)
                .build ();
    }
}

