package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerCancion;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancionesResultadoFragment extends Fragment implements AdapterPlaylist.OnAdapterPlaylistListener{

    public static final String KEY_TEXTO_RESULTADO = "textoResultado";

    private List<Cancion> datos = new ArrayList<>();
    private AdapterPlaylist adapter;
    private String textoBusqueda;

    public CancionesResultadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canciones_resultado, container, false);
        RecyclerView recycler = view.findViewById(R.id.recyclerCancionesResultado);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterPlaylist(datos, this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        textoBusqueda = getArguments().getString(KEY_TEXTO_RESULTADO);

        // Si se busco algo
        if (textoBusqueda != null) {
            new ControllerCancion().obtenerCacionesPorBusquedaTotal(getContext(), textoBusqueda, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    datos = resultado;
                    adapter.setElementos(datos);
                }
            });
        }else {
            new ControllerCancion().obtenerCacionTopCaciones(getContext(), new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    datos = resultado;
                    adapter.setElementos(datos);
                }
            });
        }

        return view;
    }

    public interface FragmentCancionesResultadoListener{
        void pasarReproductorActivity(Bundle datos);
        void pasarAgregarPlaylist(Bundle datos);
    }

    @Override
    public void pasarReproductor(Bundle datos) {
        datos.putBoolean(ReproductorActivity.KEY_BUSQUEDA, true);
        datos.putString(ReproductorActivity.KEY_TEXTO_BUSQUEDA, textoBusqueda);
        ((FragmentCancionesResultadoListener) getContext()).pasarReproductorActivity(datos);
    }

    @Override
    public void pasarAgregarPlaylist(Bundle datos) {
        ((FragmentCancionesResultadoListener) getContext()).pasarAgregarPlaylist(datos);
    }

    @Override
    public void snackBar(Integer posicion, Integer idCancion) {
        // Tiene que sobreescribirlo por implementar la Interface del Adapter
        // pero en este Fragment no tiene implementacion
    }
}
