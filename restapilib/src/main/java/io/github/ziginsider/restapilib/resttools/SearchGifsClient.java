package io.github.ziginsider.restapilib.resttools;

import android.support.annotation.NonNull;
import io.github.ziginsider.restapilib.db.dao.GifsDao;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.model.gifs.SearchData;
import io.github.ziginsider.restapilib.restapi.SearchGifsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static io.github.ziginsider.restapilib.restapi.SearchGifsContract.API_KEY;
import static io.github.ziginsider.restapilib.restapi.SearchGifsContract.LIMIT_SEARCH_QUERY;

class SearchGifsClient {
    private static SearchGifsClient instance;

    private SearchGifsApi searchGifsApi;

    private SearchGifsClient(OkHttpClientImpl.StateCallListener stateCallListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance(stateCallListener).getOkHttpClient())
                .baseUrl("http://api.giphy.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getGson()))
                .build();

        searchGifsApi = retrofit.create(SearchGifsApi.class);
    }

    void populateGifsResult(String query, final GifsDao gifsDao, final FavoriteGifs rawFavoriteGifs) {
        Call<SearchData> gifsCall = getSearchGifsService().getSearchGifs(query, LIMIT_SEARCH_QUERY, API_KEY);
        gifsCall.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()) {
                    rawFavoriteGifs.gifs = response.body().getData();
                    saveGifsToDb(gifsDao, rawFavoriteGifs);
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Timber.i(t.getMessage());
            }
        });
    }

    private void saveGifsToDb(final GifsDao gifsDao, final FavoriteGifs favoriteGifs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                gifsDao.insertFavoriteGifs(favoriteGifs);
            }
        }).start();
    }

    private SearchGifsApi getSearchGifsService() {
        return searchGifsApi;
    }

    public static synchronized SearchGifsClient getInstance(OkHttpClientImpl.StateCallListener stateCallListener) {
        if (instance == null) {
            instance = new SearchGifsClient(stateCallListener);
        }
        return instance;
    }
}
