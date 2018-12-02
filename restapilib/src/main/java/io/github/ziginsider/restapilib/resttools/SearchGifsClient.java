package io.github.ziginsider.restapilib.resttools;

import android.support.annotation.NonNull;
import android.util.Log;
import io.github.ziginsider.restapilib.model.gifs.Gif;
import io.github.ziginsider.restapilib.model.gifs.SearchData;
import io.github.ziginsider.restapilib.restapi.SearchGifsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import java.util.List;

import static io.github.ziginsider.restapilib.restapi.SearchGifsContract.API_KEY;
import static io.github.ziginsider.restapilib.restapi.SearchGifsContract.LIMIT_SEARCH_QUERY;

public class SearchGifsClient {
    private static SearchGifsClient instance;

    private SearchGifsApi searchGifsApi;

    private SearchGifsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance().getOkHttpClient())
                .baseUrl("http://api.giphy.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getGson()))
                .build();

        searchGifsApi = retrofit.create(SearchGifsApi.class);
    }

    public void getSearchResult(String query) {
        Call<SearchData> gifsCall = getSearchGifsService().getSearchGifs(query, LIMIT_SEARCH_QUERY, API_KEY);
        gifsCall.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, @NonNull Response<SearchData> response) {
                if(response.isSuccessful()) {
                    //List<Gif> result = response.body().getData();
                    //Log.d("TAGTAG", result.toString());
                    Timber.i("SUCCESS");
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });
    }

    private SearchGifsApi getSearchGifsService(){
        return searchGifsApi;
    }

    public static synchronized SearchGifsClient getInstance() {
        if (instance == null) {
            instance = new SearchGifsClient();
        }
        return instance;
    }
}
