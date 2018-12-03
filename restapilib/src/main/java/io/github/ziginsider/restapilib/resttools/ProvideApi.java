package io.github.ziginsider.restapilib.resttools;

import android.content.Context;
import io.github.ziginsider.restapilib.db.AppDatabase;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.db.entity.User;
import io.github.ziginsider.restapilib.model.gifs.Gif;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;

public class ProvideApi {

    private static List<String> sumElapsedTimes = new ArrayList<>();
    private static List<String> sumBodyCount = new ArrayList<>();

    private static int callCounter = 0;

    private static OkHttpClientImpl.StateCallListener stateCallListener = new OkHttpClientImpl.StateCallListener() {
        @Override
        public void setElapsedTime(String elapsedTime) {
            sumElapsedTimes.add(elapsedTime);
            callCounter++;
        }

        @Override
        public void setBodyByteCount(String bodyByteCount) {
            sumBodyCount.add(bodyByteCount);
        }
    };

    public static synchronized void createUser(Context context, String query) {

        final AppDatabase db = AppDatabase.getDatabase(context);

        final User rawUser = new User(0);
        final FavoriteGifs rawFavoriteGifs = new FavoriteGifs(0);

        RandomUserClient.getInstance(stateCallListener).populateUsers(db.userModel(), rawUser);

        SearchGifsClient.getInstance(stateCallListener).populateGifsResult(query, db.gifsModel(), rawFavoriteGifs);
    }

    public static synchronized List<User> getUsers(Context context) {
        final AppDatabase db = AppDatabase.getDatabase(context);
        List<User> users = db.userModel().loadAllUsers();
        for (User user : users) {
            Timber.i(">>>>>>>>>>>> USER ID = " + String.valueOf(user.id));
            Timber.i(">>>>>>>>>>>> USER NAME = " + user.name);
        }

        return users;
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
        for (int i = 0; i < callCounter; i++) {
            stringBuilder.append("Request #" + String.valueOf(i + 1) + "\n");
            if (i % 2 == 0) {
                stringBuilder.append("from \"http://api.giphy.com/\":\n");
            } else {
                stringBuilder.append("from \"https://randomuser.me/\":\n");
            }
            stringBuilder.append("Response Time = ");
            stringBuilder.append(sumElapsedTimes.get(i));
            stringBuilder.append("\nBody Byte Count = ");
            stringBuilder.append(sumBodyCount.get(i));
            stringBuilder.append("\n\n");
        }

        return stringBuilder.toString();
    }

}
