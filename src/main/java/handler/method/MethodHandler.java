package handler.method;

import entity.User;
import orm.Repository;
import orm.RepositoryFactory;
import request.HttpRequest;
import request.RequestMethod;
import response.ContentType;
import response.HtmlPage;
import response.HttpResponse;
import response.HttpStatus;
import service.*;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public abstract class MethodHandler {

    private final MethodHandler next;

    protected FileService fileService;
    protected ContentTypeParser contentTypeParser;
    protected Repository<User> userRepository;
    protected EntityParser entityParser;
    protected JsonSerializer jsonSerializer;

    public MethodHandler (MethodHandler methodHandler) {
        this.next = methodHandler;
        this.fileService = FileServiceFactory.createFileService ();
        this.contentTypeParser = ContentTypeParserFactory.createContentTypeParser ();
        this.userRepository = RepositoryFactory.createUserRepository ();
        this.entityParser = EntityParserFactory.createEntityParser ();
        this.jsonSerializer = JsonSerializerFactory.createJsonSerializer ();
    }

    private RequestMethod getRequestMethod () {
        return getClass ().getAnnotation (Handle.class).method ();
    }

    public HttpResponse getHttpResponse (HttpRequest httpRequest) {
        if (getRequestMethod ().equals (httpRequest.getMethod ())) {
            return handle (httpRequest);
        } else if (next != null) {
            return next.getHttpResponse (httpRequest);
        } else {
            return createHtmlMessage (HttpStatus.METHOD_NOT_ALLOWED, "Метод не поддерживается");
        }
    }

    abstract HttpResponse handle (HttpRequest httpRequest);

    protected HttpResponse methodsHandle (MethodHandler methodHandler, HttpRequest httpRequest) {
        for (Method method : methodHandler.getClass ().getDeclaredMethods ()) {
            try {
                HttpResponse httpResponse;
                UrlRequest urlRequest = method.getAnnotation (UrlRequest.class);
                if (urlRequest != null && urlRequest.value ().equals (httpRequest.getUrl ())) {
                    method.setAccessible (true);
                    if (method.getParameterTypes ().length <= 0){
                        httpResponse = ( HttpResponse ) method.invoke (this);
                    } else {
                        httpResponse = ( HttpResponse ) method.invoke (this, httpRequest);
                    }
                    method.setAccessible (false);
                    return httpResponse;
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        return createHtmlMessage (HttpStatus.NOT_FOUND, "Ссылка не найдена");
    }

    protected HttpResponse createHtmlMessage (HttpStatus httpStatus, String msg) {

        byte[] body = HtmlPage.createBilder ()
                .withPath (fileService.getPath ("message-page.html"))
                .withAttribute ("message", msg)
                .build ()
                .getBody ();

        return HttpResponse.createBuilder ()
                .withStatus (httpStatus)
                .withContentType (ContentType.HTML)
                .withBody (body)
                .build ();
    }

    protected HttpResponse createHtmlPage (String pageName) {
        return HttpResponse.createBuilder ()
                .withStatus (HttpStatus.OK)
                .withContentType (ContentType.HTML)
                .withBody (fileService.getBytes (pageName + ".html"))
                .build ();
    }

    protected HttpResponse createJsonMessage (HttpStatus status, String message) {
        HashMap<String, String> json = new HashMap<> () {{
            put ("message", message);
        }};
        byte[] body = jsonSerializer.serialize (json).getBytes (StandardCharsets.UTF_8);
        return HttpResponse.createBuilder ()
                .withStatus (HttpStatus.NOT_FOUND)
                .withContentType (ContentType.JSON)
                .withBody (body)
                .build ();
    }


}
