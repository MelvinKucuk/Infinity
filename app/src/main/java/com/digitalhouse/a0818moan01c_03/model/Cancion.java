package com.digitalhouse.a0818moan01c_03.model;


import com.google.gson.annotations.SerializedName;

public class Cancion {

    @SerializedName("title")
    private String titulo;
    @SerializedName("artist")
    private Artista artista;
    private Album album;
    private Integer id;
    private String link;
    private String preview;

    public Cancion(String titulo, Artista artista, Album album, Integer id, String link, String preview) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.id = id;
        this.link = link;
        this.preview = preview;
    }

    public Cancion(String titulo, Artista artista, Album album, Integer id) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.id = id;
    }

    public Cancion(){}

    public String getTitulo() {
        return titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public Album getAlbum() {
        return album;
    }

    public Integer getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getPreview() {
        return preview;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
