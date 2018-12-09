package io.github.ziginsider.restapilib.resttools;

import android.content.Context;
import io.github.ziginsider.restapilib.db.AppDatabase;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.db.entity.User;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * API restapilib.
 *
 * @author Aliaksei Kisel
 */
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

    /**
     * Gets Random User (https://randomuser.me/) and Gifs (http://api.giphy.com) according to query.
     * Saves the User and Gifs to SQLite Data Base.
     *
     * @param context the {@link Context}
     * @param query   search request for http://api.giphy.com
     */
    public static synchronized void createUser(Context context, String query) {
        final AppDatabase db = AppDatabase.getDatabase(context);
        final User rawUser = new User(0);
        final FavoriteGifs rawFavoriteGifs = new FavoriteGifs(0);
        RandomUserClient.getInstance(mStateCallListener).populateUsers(db.userModel(), rawUser);
        SearchGifsClient.getInstance(mStateCallListener).populateGifsResult(query, db.gifsModel(), rawFavoriteGifs);
    }

    /**
     * Returns list of {@link User} from SQLite Data Base.
     *
     * @param context the {@link Context}
     * @return list of {@link User}
     */
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
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Timber.e(e);
        }
        return null;
    }

    /**
     * Returns list of {@link FavoriteGifs} from SQLite Data Base.
     *
     * @param context the {@link Context}
     * @return list of {@link FavoriteGifs}
     */
    public static synchronized List<FavoriteGifs> getFavoriteGifs(final Context context) {
        Callable<List<FavoriteGifs>> callable = new Callable<List<FavoriteGifs>>() {
            @Override
            public List<FavoriteGifs> call() throws Exception {
                final AppDatabase db = AppDatabase.getDatabase(context);
                return db.gifsModel().loadAllFavoriteGifs();
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<List<FavoriteGifs>> futureTask = (FutureTask<List<FavoriteGifs>>) executor.submit(callable);
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Timber.e(e);
        }
        return null;
    }

    /**
     * Returns description (elapsed time and answer byte count) of last Http request.
     *
     * @return The description of last http request
     */
    public static synchronized String getStatusPreviousCall() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (int i = 0; i < mCallCounter; i++) {
                stringBuilder.append("Request #" + String.valueOf(i + 1) + "\n");
                if (i % 2 == 0) {
                    stringBuilder.append("from \"http://api.giphy.com/\":\n");
                } else {
                    stringBuilder.append("from \"https://randomuser.me/\":\n");
                }
                stringBuilder.append("Response Time = ");
                stringBuilder.append(mSumElapsedTimes.get(i));
                stringBuilder.append("s\nBody Byte Count = ");
                stringBuilder.append(mSumBodyCount.get(i));
                stringBuilder.append("b\n\n");
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return stringBuilder.toString();
    }
}
