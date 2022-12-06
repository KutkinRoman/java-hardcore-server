package service;

import config.Config;

public class ResponseSerializerFactory {

    public static ResponseSerializer createResponseSerializer(Config config){
        return new ResponseSerializerImpl (config);
    }
}
