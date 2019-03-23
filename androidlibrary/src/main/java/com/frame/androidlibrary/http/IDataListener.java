package com.frame.androidlibrary.http;

import java.io.IOException;

public interface IDataListener<M> {

    void onSuccess(M data);

    void onFailure(IOException e);

}
