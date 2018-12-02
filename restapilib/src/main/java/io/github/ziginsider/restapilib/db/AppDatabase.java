package io.github.ziginsider.restapilib.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.ziginsider.restapilib.db.dao.GifsDao;
import io.github.ziginsider.restapilib.db.dao.UserDao;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.db.entity.User;

@Database(entities = {User.class, FavoriteGifs.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final String NAME = "libraryDatabase";

    public abstract UserDao userModel();

    public abstract GifsDao gifsModel();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
