package io.github.ziginsider.restapilib.resttools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GsonImpl {

    private static GsonImpl instance;
    private Gson gson;

    private GsonImpl() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static synchronized GsonImpl getInstance() {
        if (instance == null) {
            instance = new GsonImpl();
        }
        return instance;
    }

    Gson getGson() {
        return gson;
    }
}
