package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
public class ArtistaElementoFragment extends Fragment implements AdapterAlbum.AdapterInterface{

    public static final String KEY_ARTISTA = "artista";
    private AdapterAlbum adapter;
    private List<Album> datos = new ArrayList<>();
    private String artista;
    private ActionBar actionBar;


    public ArtistaElementoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artista_elemento, container, false);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        artista = getArguments().getString(KEY_ARTISTA);
        actionBar.setTitle(artista);

        new ControllerAlbum().obtenerAlbumsPorBusquedaTotal(getContext(), artista, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                datos = resultado;
                adapter.setDatos(datos);
            }
        });

        RecyclerView recycler = view.findViewById(R.id.recyclerArtistaElemento);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(manager);
        adapter = new AdapterAlbum(this, datos);
        recycler.setAdapter(adapter);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefreshElementoArtista);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ControllerAlbum().obtenerAlbumsPorBusquedaTotal(getContext(), artista, new ResultListener<List<Album>>() {
                    @Override
                    public void finish(List<Album> resultado) {
                        datos = resultado;
                        adapter.setDatos(datos);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        return view;
    }

    public interface ArtistaElementoInterface{
        void irPlaylist(Bundle datos);
    }

    @Override
    public void irPlaylist(Bundle datos) {
        ((ArtistaElementoInterface) getContext()).irPlaylist(datos);
    }

    @Override
    public void onDestroy() {
        actionBar.setTitle(R.string.app_name);
        super.onDestroy();
    }
}
