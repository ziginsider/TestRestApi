package io.github.ziginsider.restapilib.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public String id;

    public String gender;

    public String name;

    public String lastname;

    public String city;

    public String street;

    public String state;

    public String postcode;

    public String email;

    public String phone;

    @ColumnInfo(name = "photo_url")
    public String photoUrl;
}
