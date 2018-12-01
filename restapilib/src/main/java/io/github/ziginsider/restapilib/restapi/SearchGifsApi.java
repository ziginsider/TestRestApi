package io.github.ziginsider.restapilib.restapi;

import io.github.ziginsider.restapilib.model.gifs.SearchData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchGifsApi {

    @GET("v1/gifs/search")
    Call<SearchData> getSearchGifs(@Query("q") String search,
                                   @Query("limit") int limit,
                                   @Query("api_key") String key);
}
