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

class SearchGifsClient {
    private static final int LIMIT_SEARCH_QUERY = 9;
    private static final String API_KEY = "rKV5x4vFsIfFQCfaQb4cUNEUnLrD9MoV";
    private static final String BASE_URL = "http://api.giphy.com/";
    private static SearchGifsClient mInstance;
    private SearchGifsApi mSearchGifsApi;

    private SearchGifsClient(OkHttpClientImpl.StateCallListener stateCallListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpClientImpl.getInstance(stateCallListener).getOkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonImpl.getInstance().getmGson()))
                .build();

        mSearchGifsApi = retrofit.create(SearchGifsApi.class);
    }

    void populateGifsResult(String query, final GifsDao gifsDao, final FavoriteGifs rawFavoriteGifs) {
        Call<SearchData> gifsCall = getSearchGifsService().getSearchGifs(query, LIMIT_SEARCH_QUERY, API_KEY);
        gifsCall.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NonNull Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()) {
                    rawFavoriteGifs.gifs = response.body().getData();
                    saveGifsToDb(gifsDao, rawFavoriteGifs);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchData> call, Throwable t) {
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
        return mSearchGifsApi;
    }

    public static synchronized SearchGifsClient getInstance(OkHttpClientImpl.StateCallListener stateCallListener) {
        if (mInstance == null) {
            mInstance = new SearchGifsClient(stateCallListener);
        }
        return mInstance;
    }
}
