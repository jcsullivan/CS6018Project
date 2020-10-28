package com.lifestyleapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)

public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile UserRoomDatabase INSTANCE;
    private static String db_path;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);




    static UserRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                UserRoomDatabase.class, "user_database")
                                .createFromFile(new File(db_path))
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    static void closeDB(){
        if (INSTANCE!= null) {
            INSTANCE.close();
        }
    }

    static void set_db_path(String path_to_db){
        db_path=path_to_db;

    }
}


