package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;

/**
 * Created by itayrin on 1/27/2018.
 */

public class GetEventsOfUserRequest extends BaseRequest {

    private int userID;

    public GetEventsOfUserRequest(int userID, ServerCallback mCallback)
    {
        super(mCallback);
        this.userID = userID;
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return GET;
    }

    @Override
    public String getServiceUrl() {
        return "eventsById/" + this.userID;
    }
}





