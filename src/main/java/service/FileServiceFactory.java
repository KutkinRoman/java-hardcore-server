package service;

import config.ConfigFactory;

public class FileServiceFactory {

    public static FileService createFileService(){
        return new FileServiceImpl (ConfigFactory.getConfig ());
    }
}
