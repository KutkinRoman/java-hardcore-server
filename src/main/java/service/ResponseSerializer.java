package service;

import response.HttpResponse;

public interface ResponseSerializer {

    byte[] serialize(HttpResponse httpResponse);
}
