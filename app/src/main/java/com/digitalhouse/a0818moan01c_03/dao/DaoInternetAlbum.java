package com.digitalhouse.a0818moan01c_03.dao;


import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.AlbumContainer;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetAlbum extends DaoHelper{

    private static final String BASE_URL = "https://api.deezer.com/";
    private ServiceAlbum serviceAlbum;

    public DaoInternetAlbum() {
        super(BASE_URL);
        serviceAlbum = retrofit.create(ServiceAlbum.class);
    }

    public void obtenerTopAlbums(final ResultListener<List<Album>> listenerController){

        Call<AlbumContainer> call = serviceAlbum.obtenerTopAlbums();
        call.enqueue(new Callback<AlbumContainer>() {
            @Override
            public void onResponse(Call<AlbumContainer> call, Response<AlbumContainer> response) {
                AlbumContainer container = response.body();
                List<Album> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<AlbumContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerAlbumsPorBusqueda(String texto, final ResultListener<List<Album>> listenerController){

        Call<AlbumContainer> call = serviceAlbum.obtenerAlbumsPorBusqueda(texto);
        call.enqueue(new Callback<AlbumContainer>() {
            @Override
            public void onResponse(Call<AlbumContainer> call, Response<AlbumContainer> response) {
                AlbumContainer container = response.body();
                List<Album> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<AlbumContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerAlbumPorId(Integer idAlbum, final ResultListener<Album> listenerController){

        Call<Album> call = serviceAlbum.obtenerAlbumPorId(idAlbum);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                Album datos = response.body();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });
    }

}
