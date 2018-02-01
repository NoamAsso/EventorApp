package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.POST;

/**
 * Created by itayrin on 1/24/2018.
 */
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class PostUserRequest extends BaseRequest {

    String json;

    public PostUserRequest(String newjson, ServerCallback mCallback)
    {
        super(mCallback);
        this.json = newjson;
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return POST;
    }

    @Override
    public String getServiceUrl() {
        return "register";
    }

    @Override
    public String getJsonEntity() {
        return json;
    }
}
