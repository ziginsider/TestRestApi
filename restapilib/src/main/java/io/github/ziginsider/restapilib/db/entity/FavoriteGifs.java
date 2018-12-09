package io.github.ziginsider.restapilib.db.entity;

import android.arch.persistence.room.*;
import io.github.ziginsider.restapilib.model.gifs.Gif;

import java.util.Date;
import java.util.List;

@Entity(tableName = "favorite_gifs")
@TypeConverters({GifsConverter.class, DateConverter.class})
public class FavoriteGifs {

    public FavoriteGifs(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public List<Gif> gifs;

    public Date date;
}
