package io.github.ziginsider.restapilib.resttools;

import android.support.annotation.NonNull;
import io.github.ziginsider.restapilib.db.AppDatabase;
import io.github.ziginsider.restapilib.db.entity.User;
import io.github.ziginsider.restapilib.model.user.RandomUsers;
import io.github.ziginsider.restapilib.model.user.Result;
import io.github.ziginsider.restapilib.restapi.RandomUsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import java.util.List;

class RandomUserClient {

    private static RandomUserClient instance;
    private static final int ONE_USER_COUNT = 1;

    private RandomUsersApi randomUsersApi;

    private RandomUserClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance().getOkHttpClient())
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getGson()))
                .build();

        randomUsersApi = retrofit.create(RandomUsersApi.class);
    }

    public void populateUsers(final AppDatabase db, final User rawUser) {
        Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(ONE_USER_COUNT);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if (response.isSuccessful()) {
                    List<Result> result = response.body().getResults();
                    db.userModel().insertUser(createUser(result.get(0), rawUser));
                }
            }

            @Override
            public void onFailure(Call<RandomUsers> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });
    }

    private User createUser(Result result, User rawUser) {
        rawUser.gender = result.getGender();
        rawUser.name = result.getName().getFirst();
        rawUser.lastname = result.getName().getLast();
        rawUser.city = result.getLocation().getCity();
        rawUser.street = result.getLocation().getStreet();
        rawUser.postcode = result.getLocation().getPostcode();
        rawUser.email = result.getEmail();
        rawUser.phone = result.getPhone();
        rawUser.photoUrl = result.getPicture().getMedium();
        return rawUser;
    }

    private RandomUsersApi getRandomUserService() {
        return randomUsersApi;
    }

    public static synchronized RandomUserClient getInstance() {
        if (instance == null) {
            instance = new RandomUserClient();
        }
        return instance;
    }
}
