package com.digitalhouse.a0818moan01c_03.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.model.Genero;

@Database(entities = {Album.class, Artista.class, Genero.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static MyDatabase INSTANCE;

    public abstract DaoLocalGenero daoGenero();
    public abstract DaoLocalAlbum daoAlbum();
    public abstract DaoLocalArtista daoArtista();

    public static MyDatabase getInMemoryDatabes(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "dbInfiniy").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
