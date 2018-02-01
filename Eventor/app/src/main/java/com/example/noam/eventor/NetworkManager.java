package com.example.noam.eventor;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.protocol.HTTP;

import static com.example.noam.eventor.NetworkManager.ReqMethod.GET;

/**
 * Created by itayrin on 1/19/2018.
 */
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class NetworkManager {//handles network connections

    public static final String TAG = "NetworkManager";

    private static NetworkManager sInstance;
    private LooperThread mNetThread;

    public enum ReqMethod {GET, POST, DELETE, PUT}

    private NetworkManager() {
        mNetThread = new LooperThread("NetworkThread");
        mNetThread.start();
        mNetThread.waitUntilReady();
    }

    public static String getServerUrl() {
        return "http://192.168.43.143:5000/";    //Development
    }

    public static NetworkManager getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkManager();
        }
        return sInstance;
    }

    public void addRequest(BaseRequest request) {
        Message message = new Message();
        message.obj = request;
        mNetThread.addMessage(message);
    }

    private void putData(BaseRequest request) {
        ServerCallback callback = request.getCallback();

        try {
            String uri = getServerUrl() + request.getServiceUrl();

            Log.e(TAG, "Put Send: " + uri);

            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut(uri);

            httpPut.setHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8);

            HttpResponse response = httpclient.execute(httpPut);
            Log.e(TAG, "Put Received: " + response.toString());

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                Log.e(TAG, "Put Received: " + responseString);


                out.close();
                if (callback != null) {
                    callback.onSuccess(responseString, statusCode);
                }
            } else {
                //Closes the connection.
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity.getContent().close();
                }
                Log.e(TAG, "Put Received: " + "Http Response: " + response.toString());
                Log.e(TAG, "Put Received: " + "Http Response: " + statusCode);

                if (callback != null) {
                    callback.onFailure("Http Response: " + statusCode, statusCode);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "Put Received Error: " + e.getMessage());
            if (callback != null) {
                callback.onFailure(null, -1);
            }
        }
    }

    private void getData(BaseRequest request) {
        ServerCallback callback = request.getCallback();

        try {
            String uri = getServerUrl() + request.getServiceUrl();

            Log.e(TAG, "Get Send: " + uri);

            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(uri);

            httpGet.setHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8);

            HttpResponse response = httpclient.execute(httpGet);
            Log.e(TAG, "Get Received: " + response.toString());

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                Log.e(TAG, "Get Received: " + responseString);


                out.close();
                if (callback != null) {
                    callback.onSuccess(responseString, statusCode);
                }
            } else {
                //Closes the connection.
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity.getContent().close();
                }
                Log.e(TAG, "Get Received: " + "Http Response: " + response.toString());
                Log.e(TAG, "Get Received: " + "Http Response: " + statusCode);

                if (callback != null) {
                    callback.onFailure("Http Response: " + statusCode, statusCode);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "Get Received Error: " + e.getMessage());
            if (callback != null) {
                callback.onFailure(null, -1);
            }
        }
    }

    /*public void sendDataToServer(String dataToSend){
        final String json = dataToSend;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e(TAG, result);
            }
        }.execute();
    }*/

    private void postData(BaseRequest request){
        ServerCallback callback = request.getCallback();

        try {
            String uri = getServerUrl() + request.getServiceUrl();

            Log.e(TAG, "Post Send: " + uri);

            HttpPost httpPost = new HttpPost(uri);
            StringEntity entity = new StringEntity(request.getJsonEntity());
            httpPost.setEntity(entity);
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpClient httpclient = HttpClientBuilder.create().build();

//            BasicResponseHandler handler = new BasicResponseHandler();
//            String response = httpclient.execute(httpPost,handler);

            HttpResponse response = httpclient.execute(httpPost);
            Log.e(TAG, "Post Received: " + response.toString());

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                Log.e(TAG, "Post Received: " + responseString);


                out.close();
                if (callback != null) {
                    callback.onSuccess(responseString, statusCode);
                }
            } else {
                //Closes the connection.
                HttpEntity httpentity = response.getEntity();
                if (httpentity != null) {
                    httpentity.getContent().close();
                }
                Log.e(TAG, "Post Received: " + "Http Response: " + response.toString());
                Log.e(TAG, "Post Received: " + "Http Response: " + statusCode);

                if (callback != null) {
                    callback.onFailure("Http Response: " + statusCode, statusCode);
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
        catch (Throwable e) {
            Log.e(TAG, "Post Received Error: " + e.getMessage());
            if (callback != null) {
                callback.onFailure(null, -1);
            }
        }
    }

    class LooperThread extends HandlerThread {
        public NetHandler mHandler;

        public LooperThread(String name) {
            super(name);
        }

        public synchronized void waitUntilReady() {
            mHandler = new NetHandler(this.getLooper());
        }

        public void addMessage(Message message) {
            mHandler.sendMessage(message);
        }
    }

    private class NetHandler extends Handler {
        public NetHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            final BaseRequest req = (BaseRequest) msg.obj;

            new Thread() {
                @Override
                public void run() {
                    sendRequest(req);
                }
            }.start();
        }

        private void sendRequest(BaseRequest req) {
            switch (req.getMethod()) {
                case GET: {
                    getData(req);
                    break;
                }
                case POST: {
                    postData(req);
                    break;
                }
                case PUT: {
                    putData(req);
                    break;
                }
            }
        }
    }
}
