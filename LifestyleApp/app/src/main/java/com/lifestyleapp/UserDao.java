package com.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//    What is the DAO?
//    A DAO (data access object) validates your SQL at compile-time and associates it with a method.
//    In your Room DAO, you use handy annotations, like @Insert, to represent the most common database operations!
//    Room uses the DAO to create a clean API for your code.
//    The DAO must be an interface or abstract class.
//    By default, all queries must be executed on a separate thread.

@Dao
public interface UserDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE) // Completely replaces any User with the same key
        void insert(User user);

        @Query("SELECT * FROM user_table")  // returns only one row from the table, if there is a match
        LiveData<List<User>> getUsers();

}
