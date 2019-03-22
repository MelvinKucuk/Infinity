package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerAlbum;
import com.digitalhouse.a0818moan01c_03.controller.ControllerArtista;
import com.digitalhouse.a0818moan01c_03.controller.ControllerCancion;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment  implements AdapterPlaylist.OnAdapterPlaylistListener,
        AdapterAlbumBusqueda.AdapterInterfaceBusqueda, AdapterArtistaBusqueda.ArtistaBusquedaInterface{

    private List<Album> datosAlbum = new ArrayList<>();
    private List<Artista> datosArtista = new ArrayList<>();
    private List<Cancion> datosCancion = new ArrayList<>();
    private AdapterAlbumBusqueda adapterAlbum;
    private AdapterArtistaBusqueda adapterArtista;
    private AdapterPlaylist adapterCancion;
    private String textoBusqueda = null;
    private MenuItem item;
    private CardView cardView;

    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        // Este metodo en true indica que va a aportar en el ActionBar
        setHasOptionsMenu(true);

        RecyclerView recyclerTitulo = view.findViewById(R.id.recyclerPorTitulo);
        RecyclerView recyclerAlbum = view.findViewById(R.id.recyclerPorAlbum);
        RecyclerView recyclerArtista = view.findViewById(R.id.recyclerPorArtista);
        cardView = view.findViewById(R.id.cardViewBuscar);

        RecyclerView.LayoutManager managerTitulo = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager managerAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager managerArtista = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapterCancion = new AdapterPlaylist(datosCancion, this);
        adapterAlbum = new AdapterAlbumBusqueda(datosAlbum, this);
        adapterArtista = new AdapterArtistaBusqueda(datosArtista, this);

        recyclerTitulo.setHasFixedSize(true);
        recyclerArtista.setHasFixedSize(true);
        recyclerAlbum.setHasFixedSize(true);

        recyclerAlbum.setLayoutManager(managerAlbum);
        recyclerTitulo.setLayoutManager(managerTitulo);
        recyclerArtista.setLayoutManager(managerArtista);

        recyclerAlbum.setAdapter(adapterAlbum);
        recyclerArtista.setAdapter(adapterArtista);
        recyclerTitulo.setAdapter(adapterCancion);

        //Listener de los textos "Mostrar todos" llaman a la activity para que reemplaze el fragment

        Button mostrarAlbums = view.findViewById(R.id.mostrarAlbums);
        Button mostrarCanciones = view.findViewById(R.id.mostrarCanciones);
        Button mostrarArtistas = view.findViewById(R.id.mostrarArtistas);

        mostrarAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarFragmentInterface listener = (BuscarFragmentInterface) getContext();
                listener.pasarAlbumResultado(textoBusqueda);
            }
        });

        mostrarCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarFragmentInterface listener = (BuscarFragmentInterface) getContext();
                listener.pasarCancionesResultado(textoBusqueda);
            }
        });

        mostrarArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarFragmentInterface listener = (BuscarFragmentInterface) getContext();
                listener.pasarArtistasResultado(textoBusqueda);
            }
        });


        new ControllerAlbum().obtenerTopAlbumsBusqueda(getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                datosAlbum = resultado;
                adapterAlbum.setDatos(datosAlbum);
            }
        });

        new ControllerArtista().obtenerTopArtistasBusqueda(getContext(), new ResultListener<List<Artista>>() {
            @Override
            public void finish(List<Artista> resultado) {
                datosArtista = resultado;
                adapterArtista.setDatos(datosArtista);
            }
        });

        new ControllerCancion().obtenerCacionTopCacionesBusqueda(getContext(), new ResultListener<List<Cancion>>() {
            @Override
            public void finish(List<Cancion> resultado) {
                datosCancion = resultado;
                adapterCancion.setElementos(datosCancion);
            }
        });

        // Esconde el CardView y expande el buscador
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.expandActionView();
                cardView.setVisibility(View.GONE);
            }
        });

        return view;
    }


    @Override
    public void pasarReproductor(Bundle datos) {

        datos.putBoolean(ReproductorActivity.KEY_BUSQUEDA, true);
        datos.putString(ReproductorActivity.KEY_TEXTO_BUSQUEDA, textoBusqueda);

        ((BuscarFragmentInterface) getContext()).pasarReproductorActivity(datos);
    }

    public interface BuscarFragmentInterface{
        void pasarAlbumResultado(String texto);
        void pasarCancionesResultado(String texto);
        void pasarArtistasResultado(String texto);
        void pasarReproductorActivity(Bundle datos);
        void irPlaylist(Bundle datos);
        void pasarElementoFragment(Bundle datos);
        void pasarAgregarPlaylist(Bundle datos);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_busqueda, menu);
        item = menu.findItem(R.id.itemBusquedaMenu);
        SearchView searchView = (SearchView) item.getActionView();

        // Listener para que se vuelva a mostrar el CardView
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                cardView.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                cardView.setVisibility(View.VISIBLE);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                new ControllerCancion().obtenerCacionesPorBusqueda(getContext(), query, new ResultListener<List<Cancion>>() {
                    @Override
                    public void finish(List<Cancion> resultado) {
                        datosCancion = resultado;
                        adapterCancion.setElementos(datosCancion);
                    }
                });

                new ControllerArtista().obtenerArtistasPorBusqueda(getContext(), query, new ResultListener<List<Artista>>() {
                    @Override
                    public void finish(List<Artista> resultado) {
                        datosArtista = resultado;
                        adapterArtista.setDatos(datosArtista);
                    }
                });

                new ControllerAlbum().obtenerAlbumsPorBusqueda(getContext(), query, new ResultListener<List<Album>>() {
                    @Override
                    public void finish(List<Album> resultado) {
                        datosAlbum = resultado;
                        adapterAlbum.setDatos(datosAlbum);
                    }
                });

                textoBusqueda = query;

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void irPlaylist(Bundle datos) {
        ((BuscarFragmentInterface) getContext()).irPlaylist(datos);
    }

    @Override
    public void pasarElementoArtista(Bundle datos) {
        ((BuscarFragmentInterface) getContext()).pasarElementoFragment(datos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            cardView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pasarAgregarPlaylist(Bundle datos) {
        ((BuscarFragmentInterface) getContext()).pasarAgregarPlaylist(datos);
    }

    @Override
    public void snackBar(Integer posicion, Integer idCancion) {

    }
}
