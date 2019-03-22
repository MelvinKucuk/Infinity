package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.Genero;
import com.digitalhouse.a0818moan01c_03.model.GeneroContainer;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetGenero extends DaoHelper{

    private static final String BASE_URL = "https://api.deezer.com/";

    private ServiceGenero serviceGenero;

    public DaoInternetGenero() {
        super(BASE_URL);
        serviceGenero = retrofit.create(ServiceGenero.class);
    }

    public void obtenerGeneros(final ResultListener<List<Genero>> listenerController){

        Call<GeneroContainer> call = serviceGenero.obtenerGeneros();
        call.enqueue(new Callback<GeneroContainer>() {
            @Override
            public void onResponse(Call<GeneroContainer> call, Response<GeneroContainer> response) {
                GeneroContainer container = response.body();
                List<Genero> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<GeneroContainer> call, Throwable t) {

            }
        });


    }
}
