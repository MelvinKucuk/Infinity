package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.AlbumContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAlbum {

    @GET("chart/0/albums")
    Call<AlbumContainer> obtenerTopAlbums();

    @GET("search/album")
    Call<AlbumContainer> obtenerAlbumsPorBusqueda(@Query("q") String query);

    @GET("album/{idAlbum}/")
    Call<Album> obtenerAlbumPorId(@Path("idAlbum") int idAlbum);
}
