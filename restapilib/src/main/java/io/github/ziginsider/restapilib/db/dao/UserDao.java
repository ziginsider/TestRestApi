package io.github.ziginsider.restapilib.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.github.ziginsider.restapilib.db.entity.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
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
