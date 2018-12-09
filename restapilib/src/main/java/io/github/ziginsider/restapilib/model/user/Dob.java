package io.github.ziginsider.restapilib.model.user;

import com.google.gson.annotations.SerializedName;

public class Dob {

    @SerializedName("date")
    private String date;

    @SerializedName("age")
    private int age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dob{" +
                "date='" + date + '\'' +
                ", age=" + age +
                '}';
    }
}
