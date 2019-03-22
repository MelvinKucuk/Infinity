package com.digitalhouse.a0818moan01c_03.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.digitalhouse.a0818moan01c_03.model.Artista;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface DaoLocalArtista {

    @Query("SELECT * FROM Artista LIMIT 10")
    List<Artista> obtenerArtistas();

    @Insert(onConflict = REPLACE)
    void grabarArtistas(List<Artista> artistas);
}
