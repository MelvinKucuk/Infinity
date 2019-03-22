package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.GeneroContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceGenero {

    @GET("genre")
    Call<GeneroContainer> obtenerGeneros();
}
