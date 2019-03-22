package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.model.ArtistaContainer;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetArtista extends DaoHelper{

    public static final String BASE_URL = "https://api.deezer.com/";
    private ServiceArtista serviceArtista;

    public DaoInternetArtista() {
        super(BASE_URL);
        serviceArtista = retrofit.create(ServiceArtista.class);
    }

    public void obtenerTopArtistas(final ResultListener<List<Artista>> listenerController){

        Call<ArtistaContainer> call = serviceArtista.obtenerTopArtistas();
        call.enqueue(new Callback<ArtistaContainer>() {
            @Override
            public void onResponse(Call<ArtistaContainer> call, Response<ArtistaContainer> response) {
                ArtistaContainer container = response.body();
                List<Artista> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<ArtistaContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerArtistaPorBusqueda(String texto, final ResultListener<List<Artista>> listenerController){

        Call<ArtistaContainer> call = serviceArtista.obtenerArtistasPorBusqueda(texto);
        call.enqueue(new Callback<ArtistaContainer>() {
            @Override
            public void onResponse(Call<ArtistaContainer> call, Response<ArtistaContainer> response) {
                ArtistaContainer container = response.body();
                List<Artista> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<ArtistaContainer> call, Throwable t) {

            }
        });
    }
}
