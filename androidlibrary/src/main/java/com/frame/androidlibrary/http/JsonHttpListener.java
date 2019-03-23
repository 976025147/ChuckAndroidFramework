package com.frame.androidlibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonHttpListener<M> implements IHttpListener {

    Class<M> mClass;
    IDataListener<M> dataListener;

    android.os.Handler handler = new Handler(Looper.myLooper());

    public JsonHttpListener(Class<M> mClass, IDataListener<M> dataListener) {
        this.mClass = mClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        try {
            String msg = getContext(inputStream);
            if (null != mClass) {
                final M data = new Gson().fromJson(msg, mClass);
                if (null != this.dataListener) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataListener.onSuccess(data);
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(final IOException e) {
        if (null != this.dataListener) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onFailure(e);
                }
            });
        }
    }


    private String getContext(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String tmp = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (!TextUtils.isEmpty((tmp = bufferedReader.readLine()))) {
            stringBuffer.append(tmp);
            stringBuffer.append("\n");
        }
        reader.close();
        bufferedReader.close();
        return stringBuffer.toString();

    }


}
