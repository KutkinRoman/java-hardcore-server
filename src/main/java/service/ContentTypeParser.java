package service;

import response.ContentType;

import java.nio.file.Path;

public interface ContentTypeParser {

    ContentType parse(Path path);
}
