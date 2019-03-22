package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.controller.ControllerAlbum;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements AdapterAlbum.AdapterInterface{

    private AdapterAlbum adapter;
    private List<Album> datos = new ArrayList<>();

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAlbum);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new AdapterAlbum(this, datos);
        recyclerView.setAdapter(adapter);

        new ControllerAlbum().obtenerTopAlbums(getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                datos = resultado;
                adapter.setDatos(datos);
            }
        });

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefreshAlbum);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ControllerAlbum().obtenerTopAlbums(getContext(), new ResultListener<List<Album>>() {
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

    @Override
    public void irPlaylist(Bundle datos) {
        AlbumFragmentListener escuchador = (AlbumFragmentListener) getContext();
        escuchador.irPlaylist(datos);
    }

    public interface AlbumFragmentListener{
        void irPlaylist(Bundle datos);
    }
}
