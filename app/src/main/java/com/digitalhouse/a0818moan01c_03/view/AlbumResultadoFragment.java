package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerAlbum;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumResultadoFragment extends Fragment implements AdapterAlbumBusqueda.AdapterInterfaceBusqueda{

    public static final String KEY_TEXTO_BUSQUEDA = "textoBsuqueda";

    private List<Album> datos = new ArrayList<>();
    private AdapterAlbumBusqueda adapter;

    public AlbumResultadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_resultado, container, false);
        RecyclerView recycler = view.findViewById(R.id.recyclerAlbumResultado);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterAlbumBusqueda(datos, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
        String textoBusqueda = getArguments().getString(KEY_TEXTO_BUSQUEDA);

        // Si se busco algo
        if (textoBusqueda != null) {
            new ControllerAlbum().obtenerAlbumsPorBusquedaTotal(getContext(), textoBusqueda, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    datos = resultado;
                    adapter.setDatos(datos);
                }
            });
        }else {
            new ControllerAlbum().obtenerTopAlbums(getContext(), new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    datos = resultado;
                    adapter.setDatos(datos);
                }
            });
        }

        return view;
    }

    public interface InterFaceAlbumResultado{
        void irPlaylist(Bundle datos);
    }

    @Override
    public void irPlaylist(Bundle datos) {
        ((InterFaceAlbumResultado) getContext()).irPlaylist(datos);
    }
}
