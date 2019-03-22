package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.model.CancionContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceCancion {

    @GET("chart/{idGenero}/tracks")
    Call<CancionContainer> obtenerCacionesPorGenero(@Path("idGenero") int idGenero);

    @GET("chart/0/tracks")
    Call<CancionContainer> obtenerTopCanciones();

    @GET("search/track")
    Call<CancionContainer> obtenerCancionPorBusqueda(@Query("q") String query);

    @GET("track/{idCancion}")
    Call<Cancion> obtenerCancionPorId(@Path("idCancion") int idCancion);
}
