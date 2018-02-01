package com.example.noam.eventor;


/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
/**
 * Created by itayrin on 1/19/2018.
 */

public abstract class BaseRequest {

    public static final String EXTRA_ACCESS_TOKEN = "at";
    public static final String EXTRA_REFRESH_TOKEN = "rt";
    private ServerCallback mCallback;

    public BaseRequest(ServerCallback mCallback) {
        this.mCallback = mCallback;
    }

    public ServerCallback getCallback() {
        return mCallback;
    }

    public abstract NetworkManager.ReqMethod getMethod();

    public abstract String getServiceUrl();

    public String getJsonEntity() {
        return null;
    }

    public String getMockObject() {
        return null;
    }
}
