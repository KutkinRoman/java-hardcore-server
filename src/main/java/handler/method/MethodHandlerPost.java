package handler.method;

import entity.User;
import request.HttpRequest;
import request.RequestMethod;
import response.ContentType;
import response.HtmlPage;
import response.HttpResponse;
import response.HttpStatus;

@Handle(method = RequestMethod.POST, order = 2)
public class MethodHandlerPost extends MethodHandler {


    public MethodHandlerPost (MethodHandler methodHandler) {
        super (methodHandler);
    }

    @Override
    HttpResponse handle (HttpRequest httpRequest) {
        return methodsHandle (this, httpRequest);
    }

    @UrlRequest("/registration")
    private HttpResponse registration (HttpRequest httpRequest) {
        User user = ( User ) entityParser.parse (httpRequest.getBody (), User.class);
        userRepository.beginTransaction ();
        userRepository.insert (user);
        userRepository.commitTransaction ();

        byte[] body = HtmlPage.createBilder ()
                .withPath (fileService.getPath ("user-info.html"))
                .withAttribute ("title", "Информация о пользователе")
                .withAttribute ("lastName", user.getLastName ())
                .withAttribute ("firstName", user.getFirstName ())
                .withAttribute ("email", user.getEmail ())
                .withAttribute ("ega", String.valueOf (user.getAge ()))
                .build ().getBody ();

        return HttpResponse.createBuilder ()
                .withStatus (HttpStatus.OK)
                .withContentType (ContentType.HTML)
                .withBody (body)
                .build ();
    }

}
