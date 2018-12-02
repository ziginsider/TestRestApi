package io.github.ziginsider.restapilib.resttools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonImpl {

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

    public Gson getGson() {
        return gson;
    }
}
