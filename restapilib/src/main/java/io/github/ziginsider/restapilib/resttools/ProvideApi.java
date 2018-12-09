package io.github.ziginsider.restapilib.resttools;

import android.content.Context;
import io.github.ziginsider.restapilib.db.AppDatabase;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.db.entity.User;
import io.github.ziginsider.restapilib.model.gifs.Gif;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public final class ProvideApi {

    private static List<String> mSumElapsedTimes = new ArrayList<>();
    private static List<String> mSumBodyCount = new ArrayList<>();

    private static int mCallCounter = 0;

    private static OkHttpClientImpl.StateCallListener mStateCallListener = new OkHttpClientImpl.StateCallListener() {
        @Override
        public void setElapsedTime(String elapsedTime) {
            mSumElapsedTimes.add(elapsedTime);
            mCallCounter++;
        }

        @Override
        public void setBodyByteCount(String bodyByteCount) {
            mSumBodyCount.add(bodyByteCount);
        }
    };

    public static synchronized void createUser(Context context, String query) {

        final AppDatabase db = AppDatabase.getDatabase(context);

        final User rawUser = new User(0);
        final FavoriteGifs rawFavoriteGifs = new FavoriteGifs(0);

        RandomUserClient.getInstance(mStateCallListener).populateUsers(db.userModel(), rawUser);

        SearchGifsClient.getInstance(mStateCallListener).populateGifsResult(query, db.gifsModel(), rawFavoriteGifs);
    }

    public static synchronized List<User> getUsers(final Context context) {

        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                final AppDatabase db = AppDatabase.getDatabase(context);
                return db.userModel().loadAllUsers();
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<List<User>> futureTask = (FutureTask<List<User>>) executor.submit(callable);
        try {
            List<User> users = futureTask.get();
            for (User user : users) {
                Timber.i(">>>>>>>>>>>> USER ID = " + String.valueOf(user.id));
                Timber.i(">>>>>>>>>>>> USER NAME = " + user.name);
            }

            return users;
        } catch (InterruptedException | ExecutionException e) {
            Timber.e(e);
        }
        return null;
    }

    public static synchronized List<FavoriteGifs> getFavoriteGifs(Context context) {
        final AppDatabase db = AppDatabase.getDatabase(context);
        List<FavoriteGifs> favoriteGifs = db.gifsModel().loadAllFavoriteGifs();
        for (FavoriteGifs favorite : favoriteGifs) {
            for (Gif gif : favorite.gifs) {
                Timber.i(">>>>>>>>>>>> GIF URL = " + gif.getImages().getFixed_width().getUrl());
            }
        }
        return favoriteGifs;
    }

    public static synchronized String getStatusPreviousCall() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mCallCounter; i++) {
            stringBuilder.append("Request #" + String.valueOf(i + 1) + "\n");
            if (i % 2 == 0) {
                stringBuilder.append("from \"http://api.giphy.com/\":\n");
            } else {
                stringBuilder.append("from \"https://randomuser.me/\":\n");
            }
            stringBuilder.append("Response Time = ");
            stringBuilder.append(mSumElapsedTimes.get(i));
            stringBuilder.append("\nBody Byte Count = ");
            stringBuilder.append(mSumBodyCount.get(i));
            stringBuilder.append("\n\n");
        }

        return stringBuilder.toString();
    }

}
