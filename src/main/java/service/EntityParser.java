package service;

public interface EntityParser {

    Object parse(String body, Class<?> clazz);
}
