package com.frame.androidlibrary.http;

public class ChuckHttpFrame {


    public static <T, M> void doPost(T requestInfo, String url,
                                     Class<M> responceClass,
                                     IDataListener<M> dataListener) {

        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener(responceClass, dataListener);
        HttpTask httpTask = new HttpTask(requestInfo, url, httpService, httpListener);
        ThreadPoolManage.getInstance().execute(httpTask);
    }
}
