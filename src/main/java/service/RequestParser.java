package service;

import request.HttpRequest;

import java.util.Deque;

public interface RequestParser {

    HttpRequest parseRequest(Deque<String> rawRequest);
}
