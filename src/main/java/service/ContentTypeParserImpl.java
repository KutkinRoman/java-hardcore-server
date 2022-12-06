package service;

import response.ContentType;

import java.nio.file.Path;
import java.util.Locale;

class ContentTypeParserImpl implements ContentTypeParser {

    @Override
    public ContentType parse (Path path) {
        String filename = path.getFileName ().toString ();
        String extension = filename.split ("\\.")[1];
        String type = extension.toUpperCase(Locale.ROOT);
        return ContentType.valueOf (type);
    }
}
