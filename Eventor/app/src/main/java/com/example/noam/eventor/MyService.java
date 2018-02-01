package com.example.noam.eventor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
