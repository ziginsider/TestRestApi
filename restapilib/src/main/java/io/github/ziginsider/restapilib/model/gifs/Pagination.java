package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

class Pagination {

    @SerializedName("total_count")
    private int total_count;

    @SerializedName("count")
    private int count;

    @SerializedName("offset")
    private int offset;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
