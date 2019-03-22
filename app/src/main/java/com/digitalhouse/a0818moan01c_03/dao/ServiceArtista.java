package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.ArtistaContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceArtista {

    @GET("chart/0/artists")
    Call<ArtistaContainer> obtenerTopArtistas();

    @GET("search/artist")
    Call<ArtistaContainer> obtenerArtistasPorBusqueda(@Query("q") String query);
}
