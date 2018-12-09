package io.github.ziginsider.restapilib.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "users")
@TypeConverters(DateConverter.class)
public class User {

    public User(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String gender;

    public String name;

    public String lastname;

    public int age;

    public String birth;

    public String email;

    public String phone;

    @ColumnInfo(name = "photo_url")
    public String photoUrl;

    public Date date;
}
