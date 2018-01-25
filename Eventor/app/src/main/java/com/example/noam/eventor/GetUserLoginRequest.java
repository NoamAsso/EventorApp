package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.POST;

/**
 * Created by itayrin on 1/25/2018.
 */

public class GetUserLoginRequest extends BaseRequest {
    String json;

    public GetUserLoginRequest(String json, ServerCallback mCallback) {
        super(mCallback);
        this.json = json;
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return POST;
    }

    @Override
    public String getServiceUrl() {
        return "login";
    }

    @Override
    public String getJsonEntity() {
        return json;
    }
}
