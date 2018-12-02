package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

public class Gif {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("images")
    private ImageSet images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageSet getImages() {
        return images;
    }

    public void setImages(ImageSet images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
