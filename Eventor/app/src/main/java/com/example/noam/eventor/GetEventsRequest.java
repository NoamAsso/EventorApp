package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;

/**
 * Created by itayrin on 1/22/2018.
 */

public class GetEventsRequest extends BaseRequest {

    public GetEventsRequest(ServerCallback mCallback) {
        super(mCallback);
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return GET;
    }

    //get papi/notifications?skip=&limit=sort=[ts|level] --> get all
    @Override
    public String getServiceUrl() {
        return "events";
    }

}
