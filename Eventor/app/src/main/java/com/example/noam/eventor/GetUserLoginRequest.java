package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;

/**
 * Created by itayrin on 1/25/2018.
 */

public class GetUserLoginRequest extends BaseRequest {

    public GetUserLoginRequest(ServerCallback mCallback) {
        super(mCallback);
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return GET;
    }

    @Override
    public String getServiceUrl() {
        return "login";
    }
}
