package io.github.ziginsider.restapilib.db.dao;

import android.arch.persistence.room.*;
import io.github.ziginsider.restapilib.db.entity.DateConverter;
import io.github.ziginsider.restapilib.db.entity.FavoriteGifs;
import io.github.ziginsider.restapilib.db.entity.GifsConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters({GifsConverter.class, DateConverter.class})
public interface GifsDao {

    @Query("SELECT * FROM favorite_gifs WHERE id = :id")
    FavoriteGifs loadFavoriteGifsById(String id);

    @Query("SELECT * FROM favorite_gifs WHERE id = :userId")
    FavoriteGifs loadFavoriteGifsByUserId(String userId);

    @Query("SELECT * FROM favorite_gifs")
    List<FavoriteGifs> loadAllFavoriteGifs();

    @Insert(onConflict = IGNORE)
    void insertFavoriteGifs(FavoriteGifs favoriteGifs);

    @Update(onConflict = REPLACE)
    void updateFavoriteGifs(FavoriteGifs favoriteGifs);

    @Query("DELETE FROM favorite_gifs")
    void deleteAllFavoriteGifs();
}
