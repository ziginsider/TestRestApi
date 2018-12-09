package io.github.ziginsider.restapilib.resttools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonImpl {

    private static GsonImpl mInstance;
    private Gson mGson;

    private GsonImpl() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        mGson = gsonBuilder.create();
    }

    public static synchronized GsonImpl getInstance() {
        if (mInstance == null) {
            mInstance = new GsonImpl();
        }
        return mInstance;
    }

    public Gson getmGson() {
        return mGson;
    }
}
