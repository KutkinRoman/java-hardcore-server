package handler.method;

import service.ReflectionService;

public class MethodHandlerFactory {

    public static MethodHandler createMethodHandler (ReflectionService reflectionService) {
        return new MethodHandlerInitializer (reflectionService).run ();
    }
}
