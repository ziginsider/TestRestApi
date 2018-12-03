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

import java.util.List;

class RandomUserClient {

    private static RandomUserClient instance;
    private static final int ONE_USER_COUNT = 1;

    private RandomUsersApi randomUsersApi;

    private RandomUserClient(OkHttpClientImpl.StateCallListener stateCallListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance(stateCallListener).getOkHttpClient())
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        randomUsersApi = retrofit.create(RandomUsersApi.class);
    }

    void populateUsers(final UserDao userDao, final User rawUser) {
        Call<RandomUsers> randomUsersCall = getRandomUserService().getRandomUsers(ONE_USER_COUNT);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(@NonNull Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                if (response.isSuccessful()) {
                Timber.i("SUCCESS<<<<<<<<<<<<<<");
                List<Result> result = response.body().getResults();
                    saveUserToDb(userDao, createUser(result.get(0), rawUser));
                    //userDao.insertUser(createUser(result.get(0), rawUser));
                    //Timber.i("SUCCESS>>>>>>>>>>>>>>>>>>");
               }
            }

            @Override
            public void onFailure(@NonNull Call<RandomUsers> call, Throwable t) {
                Timber.i("FAIL>>>>>>>>>>>>>>>>>>");
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
        rawUser.email = result.getEmail();
        rawUser.phone = result.getPhone();
        rawUser.photoUrl = result.getPicture().getMedium();
        return rawUser;
    }

    private RandomUsersApi getRandomUserService() {
        return randomUsersApi;
    }

    static synchronized RandomUserClient getInstance(OkHttpClientImpl.StateCallListener stateCallListener) {
        if (instance == null) {
            instance = new RandomUserClient(stateCallListener);
        }
        return instance;
    }
}
