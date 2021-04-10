package com.tss.pakbourini.DBhelperclasses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CartsTableModel.class,version = 1,exportSchema = true)
public abstract class DataBaseCarts extends RoomDatabase{
    //data base instance
  public static DataBaseCarts instance;
    private static String databasename="db_technicalsTea";
    public synchronized static DataBaseCarts getInstance(Context mcontext){
        // check
        if(instance == null){
            instance = Room.databaseBuilder(mcontext,DataBaseCarts.class,databasename).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
     return instance;
    }
public abstract DaoCarts daoCarts();
}
