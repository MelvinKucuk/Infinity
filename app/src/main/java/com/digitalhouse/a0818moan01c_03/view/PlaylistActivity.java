package com.digitalhouse.a0818moan01c_03.view;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.digitalhouse.a0818moan01c_03.controller.ControllerAlbum;
import com.digitalhouse.a0818moan01c_03.controller.ControllerCancion;
import com.digitalhouse.a0818moan01c_03.controller.ControllerPlaylist;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements
        AdapterPlaylist.OnAdapterPlaylistListener, SearchView.OnQueryTextListener {

    public static final String KEY_GENERO = "genero";
    public static final String KEY_NOMBRE_GENERO = "nombreGenero";
    public static final String KEY_ID_ALBUM = "idAlbum";
    public static final String KEY_ID_PLAYLIST = "idPlaylist";
    public static final String KEY_NOMBRE_PLAYLIST = "nombrePlaylist";

    private AdapterPlaylist adapter;
    private List<Cancion> data = new ArrayList<>();
    private Integer idGenero;
    private Integer idAlbum;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String idPlaylist;
    private boolean flagBorrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Toolbar toolbar = findViewById(R.id.toolbarPlaylist);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        final RecyclerView recycler = findViewById(R.id.recycler_playlist);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterPlaylist(data, this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        // Hace que aparezca la felcha "Back" para navegacion

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle datos = getIntent().getExtras();

        idGenero = datos.getInt(KEY_GENERO);
        idAlbum = datos.getInt(KEY_ID_ALBUM);
        idPlaylist = datos.getString(KEY_ID_PLAYLIST);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshPlaylist);

        // Si viene desde un Genero
        if (idGenero != 0) {

            new ControllerCancion().obtenerCacionPorGenero(this, idGenero, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    data = resultado;
                    adapter.setElementos(data);
                }
            });

            String titulo = datos.getString(KEY_NOMBRE_GENERO);

            actionBar.setTitle(titulo);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new ControllerCancion().obtenerCacionPorGenero(PlaylistActivity.this, idGenero, new ResultListener<List<Cancion>>() {
                        @Override
                        public void finish(List<Cancion> resultado) {
                            data = resultado;
                            adapter.setElementos(data);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                }
            });
        }

        // Si viene de un album
        if (idAlbum != 0) {

            new ControllerAlbum().obtenerAlbumPorId(this, idAlbum, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
                    data = resultado.getTracks().getData();
                    for (Cancion unaCancion : data) {
                        unaCancion.setAlbum(new Album(resultado.getNombre(), resultado.getImagen()));
                    }
                    adapter.setElementos(data);
                    actionBar.setTitle(resultado.getNombre());
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new ControllerAlbum().obtenerAlbumPorId(PlaylistActivity.this, idAlbum, new ResultListener<Album>() {
                        @Override
                        public void finish(Album resultado) {
                            data = resultado.getTracks().getData();

                            for (Cancion unaCancion : data) {
                                unaCancion.setAlbum(new Album(resultado.getNombre(), resultado.getImagen()));
                            }
                            adapter.setElementos(data);
                            actionBar.setTitle(resultado.getNombre());
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });
        }

        // Si viene de una Playlist
        if (idPlaylist != null) {

            ItemTouchHelper.Callback callback = new ItemTouchHelperGestures(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recycler);

            String nombrePlaylist = datos.getString(KEY_NOMBRE_PLAYLIST);

            actionBar.setTitle(nombrePlaylist);

            new ControllerPlaylist().obtenerPlaylistPorId(this, idPlaylist, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    data = resultado;
                    adapter.setElementos(data);
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new ControllerPlaylist().obtenerPlaylistPorId(PlaylistActivity.this, idPlaylist, new ResultListener<List<Cancion>>() {
                        @Override
                        public void finish(List<Cancion> resultado) {
                            data = resultado;
                            adapter.setElementos(data);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });

        }


    }

    @Override
    public void pasarReproductor(Bundle datos) {
        Intent unIntent = new Intent(PlaylistActivity.this, ReproductorActivity.class);

        datos.putInt(ReproductorActivity.KEY_ID_GENERO, idGenero);
        datos.putInt(ReproductorActivity.KEY_ID_ALBUM, idAlbum);
        datos.putString(ReproductorActivity.KEY_ID_PLAYLIST, idPlaylist);

        unIntent.putExtras(datos);

        startActivity(unIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Para que aparezca la lupa y esta activity es el listener con sus 2 metodos implementados

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);
        MenuItem item = menu.findItem(R.id.itemFiltroMenu);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filtro(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtro(newText);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Funcionamiento de la flecha "Back"

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void pasarAgregarPlaylist(Bundle datos) {
        Intent intent = new Intent(PlaylistActivity.this, AgregarPlaylistActivity.class);
        intent.putExtras(datos);
        startActivity(intent);
    }

    @Override
    public void snackBar(final Integer posicion, final Integer idCancion) {
        Snackbar.make(swipeRefreshLayout, R.string.eliminarCancion, Snackbar.LENGTH_LONG)
                .setAction(R.string.deshacer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.deshacerBorrado(posicion);
                        flagBorrado = false;
                    }
                }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (flagBorrado) {
                    new ControllerPlaylist().borrarCancionDePlaylist(PlaylistActivity.this, idCancion, idPlaylist, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                Toast.makeText(PlaylistActivity.this, R.string.eliminarCancion, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).show();
        flagBorrado = true;
    }
}
