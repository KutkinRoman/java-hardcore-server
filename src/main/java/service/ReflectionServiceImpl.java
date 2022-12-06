package service;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class ReflectionServiceImpl implements ReflectionService {

    @Override
    public List<Class<?>> getAllClasses (Class<?> superClass) {
        return getAllClassesThisPackage (superClass.getPackageName ());
    }

    private List<Class<?>> getAllClassesThisPackage (String packetName) {
        return new Reflections (packetName, new SubTypesScanner (false))
                .getAllTypes ()
                .stream ()
                .map (name -> {
                    try {
                        return Class.forName (name);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .filter (Objects::nonNull)
                .collect (Collectors.toList ());
    }
}
