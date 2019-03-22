package com.digitalhouse.a0818moan01c_03.dao;

import com.digitalhouse.a0818moan01c_03.model.CancionContainer;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.model.Cancion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaoInternetCancion extends DaoHelper {

    private static final String BASE_URL = "https://api.deezer.com/";

    private ServiceCancion serviceCancion;

    public DaoInternetCancion() {
        super(BASE_URL);
        serviceCancion = retrofit.create(ServiceCancion.class);
    }

    public void obtenerCancionesPorGenero(int idGenero, final ResultListener<List<Cancion>> listenerController){

        Call<CancionContainer> call = serviceCancion.obtenerCacionesPorGenero(idGenero);
        call.enqueue(new Callback<CancionContainer>() {
            @Override
            public void onResponse(Call<CancionContainer> call, Response<CancionContainer> response) {
                CancionContainer container = response.body();
                List<Cancion> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<CancionContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerTopCanciones(final ResultListener<List<Cancion>> listenerController){

        Call<CancionContainer> call = serviceCancion.obtenerTopCanciones();
        call.enqueue(new Callback<CancionContainer>() {
            @Override
            public void onResponse(Call<CancionContainer> call, Response<CancionContainer> response) {
                CancionContainer container = response.body();
                List<Cancion> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<CancionContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerCancionesPorBusqueda(String texto, final ResultListener<List<Cancion>> listenerController){

        Call<CancionContainer> call = serviceCancion.obtenerCancionPorBusqueda(texto);
        call.enqueue(new Callback<CancionContainer>() {
            @Override
            public void onResponse(Call<CancionContainer> call, Response<CancionContainer> response) {
                CancionContainer container = response.body();
                List<Cancion> datos = container.getData();
                listenerController.finish(datos);
            }

            @Override
            public void onFailure(Call<CancionContainer> call, Throwable t) {

            }
        });
    }

    public void obtenerCancionPorId(Integer idCancion, final ResultListener<Cancion> listenerController){

        Call<Cancion> call = serviceCancion.obtenerCancionPorId(idCancion);
        call.enqueue(new Callback<Cancion>() {
            @Override
            public void onResponse(Call<Cancion> call, Response<Cancion> response) {
                Cancion dato = response.body();
                listenerController.finish(dato);
            }

            @Override
            public void onFailure(Call<Cancion> call, Throwable t) {

            }
        });
    }

    public void obtenerCancionesPorId(final List<Integer> idCanciones, final ResultListener<List<Cancion>> listenerController){

        final List<Cancion> datos = new ArrayList<>();
        final Integer cantidad = idCanciones.size();

        for (Integer id : idCanciones) {
            obtenerCancionPorId(id, new ResultListener<Cancion>() {
                @Override
                public void finish(Cancion resultado) {
                    datos.add(resultado);
                    if (cantidad == datos.size()){
                        listenerController.finish(datos);
                    }
                }
            });
        }

    }
}
