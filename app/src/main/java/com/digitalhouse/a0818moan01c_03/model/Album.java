package com.digitalhouse.a0818moan01c_03.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Album {

    @SerializedName("title")
    private String nombre;
    @SerializedName("cover_big")
    private String imagen;
    @Ignore
    private Artista artist;
    @PrimaryKey
    private Integer id;
    @Ignore
    private CancionContainer tracks;

    @Ignore
    public Album(String nombre, String imagen, Artista artist, Integer id, CancionContainer tracks) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.artist = artist;
        this.id = id;
        this.tracks = tracks;
    }

    @Ignore
    public Album(String nombre, String imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Album(){}

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public Artista getArtist() {
        return artist;
    }

    public Integer getId() {
        return id;
    }

    public CancionContainer getTracks() {
        return tracks;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
