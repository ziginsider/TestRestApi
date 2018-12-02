package io.github.ziginsider.restapilib.resttools;

import android.os.Environment;
import android.support.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import java.io.File;

class OkHttpClientImpl {

    private static OkHttpClientImpl instance;
    private OkHttpClient okHttpClient;

    private OkHttpClientImpl() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File cacheFile = new File(path,"HttpCache");
        cacheFile.mkdirs();

        // 10 Mb
        Cache cache = new Cache(cacheFile, 10 * 1000 * 1000);

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static synchronized OkHttpClientImpl getInstance() {
        if (instance == null) {
            instance = new OkHttpClientImpl();
        }
        return instance;
    }

    OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
