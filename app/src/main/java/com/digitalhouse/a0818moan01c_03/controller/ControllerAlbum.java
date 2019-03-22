package com.digitalhouse.a0818moan01c_03.controller;

import android.content.Context;

import com.digitalhouse.a0818moan01c_03.dao.DaoInternetAlbum;
import com.digitalhouse.a0818moan01c_03.dao.MyDatabase;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ControllerAlbum {

    public void obtenerTopAlbums(final Context context, final ResultListener<List<Album>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetAlbum().obtenerTopAlbums(new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    listenerView.finish(resultado);
                    MyDatabase.getInMemoryDatabes(context).daoAlbum().grabarAlbums(resultado);
                }
            });
        } else {
            List<Album> datos = MyDatabase.getInMemoryDatabes(context).daoAlbum().obtenerAlbums();
            listenerView.finish(datos);
        }
    }

    public void obtenerTopAlbumsBusqueda(Context context, final ResultListener<List<Album>> listenerView){

        final List<Album> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetAlbum().obtenerTopAlbums(new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
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

    public void obtenerAlbumsPorBusquedaTotal(Context context, String texto, final ResultListener<List<Album>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetAlbum().obtenerAlbumsPorBusqueda(texto, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void obtenerAlbumsPorBusqueda(Context context, String texto, final ResultListener<List<Album>> listenerView){

        final List<Album> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetAlbum().obtenerAlbumsPorBusqueda(texto, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
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

    public void obtenerAlbumPorId(Context context, Integer idAlbum, final ResultListener<Album> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetAlbum().obtenerAlbumPorId(idAlbum, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }
}
