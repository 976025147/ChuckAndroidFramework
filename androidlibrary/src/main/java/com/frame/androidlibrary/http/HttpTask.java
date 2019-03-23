package com.frame.androidlibrary.http;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;


public class HttpTask<T> implements Runnable {

    IHttpListener httpListener;
    IHttpService httpService;

    public HttpTask(T responceInfo, String url, IHttpService httpService, IHttpListener httpListener) {
        this.httpService = httpService;
        this.httpListener = httpListener;
        if (this.httpService != null) {
            this.httpService.setUrl(url);
            this.httpService.setHttpCallback(httpListener);
            if (null != responceInfo) {
                String requestContent = new Gson().toJson(responceInfo);
                if (null != requestContent) {
                    try {
                        httpService.setRequest(requestContent.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    @Override
    public void run() {
        if (null != this.httpService) {
            this.httpService.execut();
        }
    }
}
