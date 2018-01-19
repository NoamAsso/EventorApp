package com.example.noam.eventor;

/**
 * Created by itayrin on 1/19/2018.
 */

public interface ServerCallback<T> {

    void onSuccess(T res, int statusCode);

    void onFailure(T err, int statusCode);
}
