package com.example.noam.eventor;

import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;


/**
 * Created by itayrin on 1/25/2018.
 */
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class UpdateJoinRequest extends BaseRequest {
    Integer userid;
    Integer eventid;

    public UpdateJoinRequest(int userid, int eventid, ServerCallback mCallback) {
        super(mCallback);
        this.userid = userid;
        this.eventid = eventid;
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return GET;
    }

    @Override
    public String getServiceUrl() {
        return "joinEvent/" + this.userid.toString() + "/" + this.eventid.toString();
    }

}
