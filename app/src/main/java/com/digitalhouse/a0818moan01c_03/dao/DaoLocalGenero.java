package com.digitalhouse.a0818moan01c_03.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.digitalhouse.a0818moan01c_03.model.Genero;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoLocalGenero {

    @Query("SELECT * FROM Genero")
    List<Genero> obtenerGeneros();

    @Insert(onConflict = REPLACE)
    void grabarGenero(List<Genero> generos);
}
