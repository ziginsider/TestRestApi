package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

class Image {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("size")
    private int size;

    @SerializedName("mp4")
    private String mp4;

    @SerializedName("mp4_size")
    private int mp4_size;

    @SerializedName("webp")
    private String webp;

    @SerializedName("webp_size")
    private int webp_size;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public int getMp4_size() {
        return mp4_size;
    }

    public void setMp4_size(int mp4_size) {
        this.mp4_size = mp4_size;
    }

    public String getWebp() {
        return webp;
    }

    public void setWebp(String webp) {
        this.webp = webp;
    }

    public int getWebp_size() {
        return webp_size;
    }

    public void setWebp_size(int webp_size) {
        this.webp_size = webp_size;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
