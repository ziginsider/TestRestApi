package io.github.ziginsider.restapilib.model.gifs;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("response_id")
    private String response_id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
