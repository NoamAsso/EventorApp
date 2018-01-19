package com.example.noam.eventor;

import android.widget.BaseAdapter;
import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;
/**
 * Created by itayrin on 1/19/2018.
 */

public class GetNotificationByIdRequest extends BaseRequest {

    String id="";

    public GetNotificationByIdRequest(String newId, ServerCallback sCallback){
        super(sCallback);
        if (newId != null){
            id = newId;
        }
    }

    @Override
    public NetworkManager.ReqMethod getMethod() {
        return GET;
    }

    @Override
    public String getServiceUrl() {
        return "sessions/" + id;
    }
}
