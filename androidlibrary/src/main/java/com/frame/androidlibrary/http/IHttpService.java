package com.frame.androidlibrary.http;

public interface IHttpService {

    void setUrl(String url);

    void setRequest(byte[] requestData);

    void execut();

    void setHttpCallback(IHttpListener httpListener);
}
