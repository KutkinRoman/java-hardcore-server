package service;

import java.nio.file.Path;

public interface FileService {

    boolean exists (String url);

    byte[] getBytes(String url);

    Path getPath(String url);

}
