package io.github.ziginsider.restapilib.resttools;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ziginsider.restapilib.model.user.RandomUsers;
import io.github.ziginsider.restapilib.restapi.RandomUsersApi;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import java.io.File;

public class RandomUserClient {

    private Retrofit retrofit;
    private RandomUsersApi randomUsersApi;

    public RandomUserClient() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File cacheFile = new File(path,"HttpCache");
        cacheFile.mkdirs();

        // 10 Mb
        Cache cache = new Cache(cacheFile, 10 * 1000 * 1000);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        randomUsersApi = retrofit.create(RandomUsersApi.class);
    }

    public void populateUsers(final TextView textView) {
        Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(10);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if(response.isSuccessful()) {
                    String result = response.body().getResults().get(0).toString();
                    Log.d("TAGTAG", result);
                    textView.append(result);
                }
            }

            @Override
            public void onFailure(Call<RandomUsers> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });
    }

    private RandomUsersApi getRandomUserService(){
        return randomUsersApi;
    }

}
