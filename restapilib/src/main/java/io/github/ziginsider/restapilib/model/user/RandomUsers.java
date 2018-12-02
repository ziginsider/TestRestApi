package io.github.ziginsider.restapilib.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomUsers {

    @SerializedName("results")
    private List<Result> results = null;

    @SerializedName("info")
    private Info info;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
