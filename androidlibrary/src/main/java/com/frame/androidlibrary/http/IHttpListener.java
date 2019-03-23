package com.frame.androidlibrary.http;

import java.io.IOException;
import java.io.InputStream;

public interface IHttpListener {

    void onSuccess(InputStream inputStream);

    void onFailure(IOException e);
}
