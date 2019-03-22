package com.digitalhouse.a0818moan01c_03.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.digitalhouse.a0818moan01c_03.model.Album;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoLocalAlbum {

    @Query("SELECT * FROM Album LIMIT 10")
    List<Album> obtenerAlbums();

    @Insert(onConflict = REPLACE)
    void grabarAlbums (List<Album> album);
}
