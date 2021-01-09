package com.example.test5;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.test5.interfaces.cartDao;
import com.example.test5.models.cartItem;

@Database(entities = cartItem.class, exportSchema = false, version = 2)
public abstract class cartDatabase extends RoomDatabase {
    private static final String DB_NAME = "cartItem";
    public static cartDatabase instance;

    public static synchronized cartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), cartDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract cartDao cartDao();
}
