package io.github.ziginsider.restapilib.model.user;

import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("title")
    private String title;

    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
