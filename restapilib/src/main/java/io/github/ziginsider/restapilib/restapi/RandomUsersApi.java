package io.github.ziginsider.restapilib.restapi;

import io.github.ziginsider.restapilib.model.user.RandomUsers;
import retrofit2.Call;
import retrofit2.http.Query;

public interface RandomUsersApi {

    Call<RandomUsers> getRandomUsers(@Query("results") int count);
}
