package com.carvajal.myapplication;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

public class ServiceManager {

    public static final String SIMPLEGET_URL = "https://www.icesi.edu.co/";
    public static String CODIGOSHASH_URL = "http://172.19.15.62:5000/cedula?cc=";

    public static class SimpleGET{
        OnResponseListener listener;

        public SimpleGET(OnResponseListener listener){
            this.listener = listener;
            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
            try {
                String response = util.GETrequest(SIMPLEGET_URL);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public interface OnResponseListener{
            void onResponse(String response);
        }
    }

    public static class hashGET{
        OnResponseListener listener;

        public hashGET(String hash, OnResponseListener listener){
            this.listener = listener;
            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
            try {
                String response = util.GETrequest(CODIGOSHASH_URL+hash);
                listener.onResponse(response);
            } catch (IOException e) {
                Log.e("ERROR", "mal landita"+ hash);
                e.printStackTrace();
            }
        }





        public interface OnResponseListener{
            void onResponse(String response);
        }
    }


}
