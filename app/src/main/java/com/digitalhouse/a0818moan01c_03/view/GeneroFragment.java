package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.controller.ControllerGenero;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Genero;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneroFragment extends Fragment implements AdapterGenero.AdapterInterface{

    private AdapterGenero adapterGenero;
    private List<Genero> listaGeneros = new ArrayList<>();

    public GeneroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genero, container, false);

        //Lista

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGenero);
        recyclerView.setHasFixedSize(true);

        //COMO SE MUESTRA

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        //ADAPTER
        adapterGenero = new AdapterGenero(this, listaGeneros);
        recyclerView.setAdapter(adapterGenero);

        new ControllerGenero().obtenerGeneros(getContext(), new ResultListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> resultado) {
                listaGeneros = resultado;
                adapterGenero.setCategorias(listaGeneros);
            }
        });

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefreshGenero);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ControllerGenero().obtenerGeneros(getContext(), new ResultListener<List<Genero>>() {
                    @Override
                    public void finish(List<Genero> resultado) {
                        listaGeneros = resultado;
                        adapterGenero.setCategorias(listaGeneros);
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        return view;
    }

    public interface FragmentListener{
        void irPlaylist(int idGenero, String nombreGenero);
    }

    @Override
    public void irPlaylist(int idGenero, String nombreGenero) {
        FragmentListener escuchador = (FragmentListener) getContext();
        escuchador.irPlaylist(idGenero, nombreGenero);
    }
}
