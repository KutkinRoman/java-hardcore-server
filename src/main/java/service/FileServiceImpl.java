package service;

import config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileServiceImpl implements FileService {

    private final Config config;

    public FileServiceImpl (Config config) {
        this.config = config;
    }

    public boolean exists (String url) {
        Path path = getPath (url);
        return Files.exists (path) && !Files.isDirectory (path);
    }

    public byte[] getBytes (String url) {
        try {
            return Files.readAllBytes (getPath (url));
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    public Path getPath (String url) {
        return Path.of (config.getWwwHome (), url);
    }

}
