package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class ArtistaFragment extends Fragment implements AdapterArtista.ArtistaInterface{

    private List<Artista> artistaList = new ArrayList<>();
    private AdapterArtista adaptadorRecycler;


    public ArtistaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artista, container, false);

        // BUSCO EL RECYCLER VIEW
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewArtista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adaptadorRecycler = new AdapterArtista(artistaList, this);
        recyclerView.setAdapter(adaptadorRecycler);

        new ControllerArtista().obtenerTopArtistas(getContext(), new ResultListener<List<Artista>>() {
            @Override
            public void finish(List<Artista> resultado) {
                artistaList = resultado;
                adaptadorRecycler.setArtistaLista(artistaList);
            }
        });

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefreshArtista);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ControllerArtista().obtenerTopArtistas(getContext(), new ResultListener<List<Artista>>() {
                    @Override
                    public void finish(List<Artista> resultado) {
                        artistaList = resultado;
                        adaptadorRecycler.setArtistaLista(artistaList);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        return view;
    }

    public interface ArtistaFragmentInterface{
        void pasarElementoFragment(Bundle datos);
    }

    @Override
    public void pasarElementoFragment(Bundle datos) {
        ((ArtistaFragmentInterface) getContext()).pasarElementoFragment(datos);
    }
}
