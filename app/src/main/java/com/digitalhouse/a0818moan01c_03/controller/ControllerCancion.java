package com.digitalhouse.a0818moan01c_03.controller;

import android.content.Context;
import com.digitalhouse.a0818moan01c_03.dao.DaoInternetCancion;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ControllerCancion {

    public void obtenerCacionPorGenero(Context context, int idGenero, final ResultListener<List<Cancion>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerCancionesPorGenero(idGenero, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void obtenerCacionTopCaciones(Context context, final ResultListener<List<Cancion>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerTopCanciones(new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void obtenerCacionTopCacionesBusqueda(Context context, final ResultListener<List<Cancion>> listenerView){

        final List<Cancion> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerTopCanciones(new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
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

    public void obtenerCacionesPorBusquedaTotal(Context context, String texto, final ResultListener<List<Cancion>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerCancionesPorBusqueda(texto, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void obtenerCacionesPorBusqueda(Context context, String texto, final ResultListener<List<Cancion>> listenerView){

        final List<Cancion> datos = new ArrayList<>();

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerCancionesPorBusqueda(texto, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
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

    public void obtenerCancionesPorId(Context context, List<Integer> idCanciones, final ResultListener<List<Cancion>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetCancion().obtenerCancionesPorId(idCanciones, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }


}
