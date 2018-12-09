package io.github.ziginsider.restapilib.db.dao;

import android.arch.persistence.room.*;
import io.github.ziginsider.restapilib.db.entity.DateConverter;
import io.github.ziginsider.restapilib.db.entity.User;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface UserDao {

    @Query("SELECT * FROM users WHERE id = :id")
    User loadUserById(String id);

    @Query("SELECT * FROM users")
    List<User> loadAllUsers();

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);

    @Query("DELETE FROM users")
    void deleteAll();
}
