package com.example.noam.eventor;

import org.json.JSONObject;

import static com.example.noam.eventor.NetworkManager.ReqMethod.POST;

/**
 * Created by itayrin on 1/22/2018.
 */

public class PostEventRequest extends BaseRequest {

    String json;

    public PostEventRequest(String newjson, ServerCallback mCallback)
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
        return "addRow";
    }

    @Override
    public String getJsonEntity() {
        return json;
    }
}
