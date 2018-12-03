package io.github.ziginsider.restapilib.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    public User(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String gender;

    public String name;

    public String lastname;

    public String email;

    public String phone;

    @ColumnInfo(name = "photo_url")
    public String photoUrl;
}
