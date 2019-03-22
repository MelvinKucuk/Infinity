package com.digitalhouse.a0818moan01c_03.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerPlaylist;
import com.digitalhouse.a0818moan01c_03.model.Playlist;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AgregarPlaylistActivity extends AppCompatActivity implements AdapterPlaylistMenu.AdapterListener, LoginFragment.OnFragmentLoginListener {

    public static final String KEY_IDCANCION = "idCancion";
    private String userId;
    private DatabaseReference reference;
    private Integer idCancion;
    private List<Playlist> datos = new ArrayList<>();
    private AdapterPlaylistMenu adapter;
    private LoginFragment fragment;
    private boolean flagBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_playlist);

        fragment = new LoginFragment();

        Toolbar toolbar = findViewById(R.id.toolbarAgregar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.elegiPlaylist));

        // Comprueba si esta logeado
        if (FirebaseAuth.getInstance().getUid() != null) {
            onCreateHelper();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainerAgregar, fragment).commit();
        }
    }

    @Override
    public void accion(final String idPlaylist, final String nombrePlaylist) {

        new ControllerPlaylist().agregarCancionAPlaylist(AgregarPlaylistActivity.this, idCancion, idPlaylist, new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                if (resultado) {
                    Toast.makeText(AgregarPlaylistActivity.this, getString(R.string.agregoCancion) + " " + nombrePlaylist, Toast.LENGTH_SHORT).show();
                    terminar();
                }
            }
        });
    }

    private void onCreateHelper(){

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("playlist/" + userId);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Playlist unaPlaylist = dataSnapshot.getValue(Playlist.class);
                datos.add(unaPlaylist);
                adapter.setDatos(datos);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        idCancion = getIntent().getExtras().getInt(KEY_IDCANCION);

        Toolbar toolbar = findViewById(R.id.toolbarAgregar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.elegiPlaylist));

        RecyclerView recycler = findViewById(R.id.recyclerAgregar);
        recycler.setHasFixedSize(true);
        adapter = new AdapterPlaylistMenu(datos, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    private void terminar() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAgregarPlaylist) {
            FragmentManager manager = getSupportFragmentManager();
            CrearPlaylistFragment fragment = new CrearPlaylistFragment();
            fragment.show(manager, "tag");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (FirebaseAuth.getInstance().getUid() != null) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_agregar_playlist, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void borrarPlaylist(final String idPlaylist, final Integer posicion) {

        RecyclerView recycler = findViewById(R.id.recyclerAgregar);

        Snackbar.make(recycler, R.string.eliminarPlaylist, Snackbar.LENGTH_LONG)
                .setAction(R.string.deshacer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flagBorrar = false;
                        adapter.deshacerBorrado(posicion);
                    }
                }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (flagBorrar){
                    new ControllerPlaylist().borrarPlaylist(AgregarPlaylistActivity.this, idPlaylist, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado){
                                Toast.makeText(AgregarPlaylistActivity.this, R.string.eliminarPlaylist, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).show();
        flagBorrar = true;

    }

    @Override
    public void pasarLoginExitoso() {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        onCreateHelper();
    }
}
