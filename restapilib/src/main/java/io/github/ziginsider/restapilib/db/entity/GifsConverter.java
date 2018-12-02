package io.github.ziginsider.restapilib.db.entity;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.ziginsider.restapilib.model.gifs.Gif;
import io.github.ziginsider.restapilib.resttools.GsonImpl;

import java.lang.reflect.Type;
import java.util.List;

public class GifsConverter {

    @TypeConverter
    public static String fromListOfGifs(List<Gif> gifs) {
        if (gifs == null) {
            return null;
        }
        Gson gson = GsonImpl.getInstance().getGson();
        Type type = new TypeToken<List<Gif>>() {}.getType();
        String json = gson.toJson(gifs, type);
        return json;
    }

    @TypeConverter
    public static List<Gif> toGifsList(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = GsonImpl.getInstance().getGson();
        Type type = new TypeToken<List<Gif>>() {}.getType();
        List<Gif> gifs = gson.fromJson(json, type);
        return gifs;
    }
}
