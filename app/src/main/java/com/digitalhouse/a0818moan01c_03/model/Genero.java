package com.digitalhouse.a0818moan01c_03.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Genero {

    @SerializedName("name")
    private String nombre;
    @SerializedName("picture_big")
    private String imagen;
    @PrimaryKey
    private int id;


    public Genero(String nombre, String imagen, int id) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public int getId() {
        return id;
    }
}
