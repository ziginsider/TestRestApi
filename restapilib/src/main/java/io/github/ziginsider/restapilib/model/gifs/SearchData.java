package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchData {

    @SerializedName("data")
    private List<Gif> data;

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<Gif> getData() {
        return data;
    }

    public void setData(List<Gif> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
