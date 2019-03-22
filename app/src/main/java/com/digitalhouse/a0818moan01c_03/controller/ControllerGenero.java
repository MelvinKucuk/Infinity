package com.digitalhouse.a0818moan01c_03.controller;

import android.content.Context;

import com.digitalhouse.a0818moan01c_03.dao.DaoInternetGenero;
import com.digitalhouse.a0818moan01c_03.dao.MyDatabase;
import com.digitalhouse.a0818moan01c_03.model.Genero;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.List;

public class ControllerGenero {


    public void obtenerGeneros(final Context context, final ResultListener<List<Genero>> listenerView){

        if (Util.hayInternet(context)){
            new DaoInternetGenero().obtenerGeneros(new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> resultado) {
                    if(resultado.size()>2) {
                        // Saco el primero porque no es un genero que trae Deezer
                        resultado.remove(0);
                    }
                    listenerView.finish(resultado);
                    MyDatabase.getInMemoryDatabes(context).daoGenero().grabarGenero(resultado);
                }
            });
        } else {
            List<Genero> datos = MyDatabase.getInMemoryDatabes(context).daoGenero().obtenerGeneros();
            if(datos.size()>2) {
                // Saco el primero porque no es un genero que trae Deezer
                datos.remove(0);
            }
            listenerView.finish(datos);
        }
    }
}
