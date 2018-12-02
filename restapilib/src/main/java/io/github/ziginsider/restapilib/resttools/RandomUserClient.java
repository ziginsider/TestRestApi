package io.github.ziginsider.restapilib.resttools;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import io.github.ziginsider.restapilib.model.user.RandomUsers;
import io.github.ziginsider.restapilib.restapi.RandomUsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RandomUserClient {

    private static RandomUserClient instance;

    private RandomUsersApi randomUsersApi;

    private RandomUserClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance().getOkHttpClient())
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getGson()))
                .build();

        randomUsersApi = retrofit.create(RandomUsersApi.class);
    }

    public void populateUsers(final TextView textView) {
        Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(10);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if(response.isSuccessful()) {
                    String result = response.body().getResults().get(1).toString();
                    Log.d("TAGTAG", result);
                    textView.append(result);
                    textView.postInvalidate();
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

    public static synchronized RandomUserClient getInstance() {
        if (instance == null) {
            instance = new RandomUserClient();
        }
        return instance;
    }
}
