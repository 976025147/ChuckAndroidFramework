package com.frame.androidlibrary.http;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class JsonHttpService implements IHttpService {
    private static final String TAG = JsonHttpService.class.getName();
    private String url;
    private byte[] requestData;

    private IHttpListener httpListener;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequest(byte[] requestData) {
        this.requestData = requestData;
    }


    @Override
    public void setHttpCallback(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void execut() {
        httpUrlConnPost();
    }


    /**
     * 网络请求
     */
    private void httpUrlConnPost() {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, this.requestData);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (null != httpListener) {
                    httpListener.onFailure(e);
                }

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (null != httpListener) {
                    httpListener.onSuccess(response.body().byteStream());
                }
            }

        });
    }
}
