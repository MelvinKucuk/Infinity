package com.digitalhouse.a0818moan01c_03.controller;

import android.content.Context;

import com.digitalhouse.a0818moan01c_03.dao.DaoInternetArtista;
import com.digitalhouse.a0818moan01c_03.dao.MyDatabase;
import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ControllerArtista {

    public void obtenerTopArtistas(final Context context, final ResultListener<List<Artista>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetArtista().obtenerTopArtistas(new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    listenerView.finish(resultado);
                    MyDatabase.getInMemoryDatabes(context).daoArtista().grabarArtistas(resultado);
                }
            });
        } else {
            List<Artista> datos = MyDatabase.getInMemoryDatabes(context).daoArtista().obtenerArtistas();
            listenerView.finish(datos);
        }
    }

    public void obtenerTopArtistasBusqueda(Context context, final ResultListener<List<Artista>> listenerView){

        final List<Artista> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetArtista().obtenerTopArtistas(new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    if (resultado.size() > 2) {
                        datos.add(0, resultado.get(0));
                        datos.add(1, resultado.get(1));
                        datos.add(2, resultado.get(2));
                    }
                    listenerView.finish(datos);
                }
            });
        }
    }

    public void  obtenerArtistasPorBusquedaTotal(Context context, String texto, final ResultListener<List<Artista>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetArtista().obtenerArtistaPorBusqueda(texto, new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void  obtenerArtistasPorBusqueda(Context context, String texto, final ResultListener<List<Artista>> listenerView){

        final List<Artista> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetArtista().obtenerArtistaPorBusqueda(texto, new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    if (resultado.size() > 2) {
                        datos.add(0, resultado.get(0));
                        datos.add(1, resultado.get(1));
                        datos.add(2, resultado.get(2));
                    }
                    listenerView.finish(datos);
                }
            });
        }
    }
}
