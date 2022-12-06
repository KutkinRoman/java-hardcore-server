package handler.request;

import handler.method.MethodHandler;
import service.RequestParser;
import service.ResponseSerializer;
import service.SocketService;

public class RequestHandlerFactory {

    public static RequestHandler createRequestHandler (SocketService socketService,
                                                       RequestParser requestParser,
                                                       ResponseSerializer responseSerializer,
                                                       MethodHandler methodHandler) {

        return new RequestHandlerImpl (socketService, requestParser, responseSerializer, methodHandler);
    }
}
