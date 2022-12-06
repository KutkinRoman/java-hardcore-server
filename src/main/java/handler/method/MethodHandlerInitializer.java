package handler.method;

import service.ReflectionService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class MethodHandlerInitializer {

    private ReflectionService reflectionService;

    public MethodHandlerInitializer (ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    public MethodHandler run () {

        MethodHandler next = null;
        MethodHandler methodHandler = null;

        List<Class<?>> classes = getAllClassesHandle ();

        for (int i = classes.size () - 1; i > -1; i--) {
            methodHandler = newInstanceMethodHandler (classes.get (i), next);
            next = methodHandler;
        }

        return methodHandler;
    }

    private MethodHandler newInstanceMethodHandler (Class<?> clazz, MethodHandler next) {
        try {
            return ( MethodHandler ) clazz.getConstructor (MethodHandler.class).newInstance (next);
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    private List<Class<?>> getAllClassesHandle () {
        return reflectionService.getAllClasses (MethodHandler.class)
                .stream ()
                .filter (aClass -> aClass.getAnnotation (Handle.class) != null)
                .sorted (Comparator.comparingInt (c -> c.getAnnotation (Handle.class).order ()))
                .collect (Collectors.toList ());
    }
}
