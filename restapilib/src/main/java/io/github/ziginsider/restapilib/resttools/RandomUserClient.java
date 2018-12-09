package io.github.ziginsider.restapilib.resttools;

import android.support.annotation.NonNull;
import io.github.ziginsider.restapilib.db.dao.UserDao;
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

import java.util.Date;
import java.util.List;

class RandomUserClient {

    private static final int ONE_USER_COUNT = 1;
    private static final String BASE_URL = "https://randomuser.me/";
    private static RandomUserClient mInstance;
    private RandomUsersApi mRandomUsersApi;

    private RandomUserClient(OkHttpClientImpl.StateCallListener stateCallListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance(stateCallListener).getOkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getmGson()))
                .build();

        mRandomUsersApi = retrofit.create(RandomUsersApi.class);
    }

    void populateUsers(final UserDao userDao, final User rawUser) {
        Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(ONE_USER_COUNT);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(@NonNull Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if (response.isSuccessful()) {
                    List<Result> result = response.body().getResults();
                    saveUserToDb(userDao, createUser(result.get(0), rawUser));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RandomUsers> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });
    }

    private void saveUserToDb(final UserDao userDao, final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        }).start();
    }

    private User createUser(Result result, User rawUser) {
        rawUser.gender = result.getGender();
        rawUser.name = result.getName().getFirst();
        rawUser.lastname = result.getName().getLast();
        rawUser.age = result.getDob().getAge();
        rawUser.birth = result.getDob().getDate();
        rawUser.email = result.getEmail();
        rawUser.phone = result.getPhone();
        rawUser.photoUrl = result.getPicture().getMedium();
        rawUser.date = new Date();
        return rawUser;
    }

    private RandomUsersApi getRandomUserService() {
        return mRandomUsersApi;
    }

    static synchronized RandomUserClient getInstance(OkHttpClientImpl.StateCallListener stateCallListener) {
        if (mInstance == null) {
            mInstance = new RandomUserClient(stateCallListener);
        }
        return mInstance;
    }
}
