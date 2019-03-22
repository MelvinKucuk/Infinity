package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerArtista;
import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistaResultadoFragment extends Fragment implements AdapterArtistaBusqueda.ArtistaBusquedaInterface{

    public static final String KEY_TEXTO_BUSQUEDA = "textoBusqueda";

    private List<Artista> datos = new ArrayList<>();
    private AdapterArtistaBusqueda adapter;

    public ArtistaResultadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artista_resultado, container, false);
        RecyclerView recycler = view.findViewById(R.id.recyclerArtistaResultado);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterArtistaBusqueda(datos, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
        String textoBusqueda = getArguments().getString(KEY_TEXTO_BUSQUEDA);

        // Si se busco algo
        if (textoBusqueda != null) {
            new ControllerArtista().obtenerArtistasPorBusquedaTotal(getContext(), textoBusqueda, new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    datos = resultado;
                    adapter.setDatos(datos);
                }
            });
        }else {
            new ControllerArtista().obtenerTopArtistas(getContext(), new ResultListener<List<Artista>>() {
                @Override
                public void finish(List<Artista> resultado) {
                    datos = resultado;
                    adapter.setDatos(datos);
                }
            });
        }

        return view;
    }

    public interface ArtistaResultadoInteface{
        void pasarElementoFragment(Bundle datos);
    }

    @Override
    public void pasarElementoArtista(Bundle datos) {
        ((ArtistaResultadoInteface) getContext()).pasarElementoFragment(datos);
    }
}
