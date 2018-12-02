package io.github.ziginsider.restapilib.db.entity;

import androidx.annotation.NonNull;
import androidx.room.*;
import io.github.ziginsider.restapilib.model.gifs.Gif;

import java.util.List;

@Entity(tableName = "favorite_gifs", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
})
@TypeConverters(GifsConverter.class)
public class FavoriteGifs {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public String id;

    @ColumnInfo(name = "user_id")
    public String userId;

    public List<Gif> gifs;
}
