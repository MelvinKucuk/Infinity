package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerPlaylist;
import com.digitalhouse.a0818moan01c_03.model.Playlist;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPlaylistFragment extends Fragment implements AdapterPlaylistMenu.AdapterListener, SearchView.OnQueryTextListener {


    private AdapterPlaylistMenu adapter;
    private List<Playlist> datos = new ArrayList<>();
    private RecyclerView recycler;
    private boolean flagBorrar;

    public MenuPlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_playlist, container, false);
        setHasOptionsMenu(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        DatabaseReference referencePlaylist = FirebaseDatabase.getInstance().getReference("playlist/"+id);
        datos.clear();
        referencePlaylist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Playlist unaPlaylist = dataSnapshot.getValue(Playlist.class);
                datos.add(unaPlaylist);
                adapter.setDatos(datos);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Playlist unaPlaylist = dataSnapshot.getValue(Playlist.class);
                actualizarPlaylist(unaPlaylist);
                adapter.setDatos(datos);
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

        recycler = view.findViewById(R.id.recycler_menuPlaylist);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterPlaylistMenu(datos, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        return view;
    }

    @Override
    public void accion(String idPlaylist, String nombrePlaylist) {
        ((MenuPlaylistInterface) getContext()).pasarPlaylist(idPlaylist, nombrePlaylist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemLogout){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            // Logout de Google
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.idClient))
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
            mGoogleSignInClient.signOut();

            ((MenuPlaylistInterface) getContext()).pasarALogin();
        }
        if (item.getItemId() == R.id.itemNuevaPlaylist){
            ((MenuPlaylistInterface) getContext()).pasarACrearPlaylist();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_playlist_login, menu);

        MenuItem item = menu.findItem(R.id.itemFiltroMenuPlaylist);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
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

    public interface MenuPlaylistInterface{
        void pasarALogin();
        void pasarACrearPlaylist();
        void pasarPlaylist(String idPlaylist, String nombrePlaylist);
    }

    public void actualizarPlaylist(Playlist unaPlaylist){

        String nombre = unaPlaylist.getNombre();
        Playlist playlistVieja = null;
        for (Playlist elemento : datos){
            if (elemento.getNombre().equals(nombre)){
                playlistVieja = elemento;
            }
        }
        Integer index = datos.indexOf(playlistVieja);
        datos.remove(playlistVieja);
        datos.add(index, unaPlaylist);
    }

    @Override
    public void borrarPlaylist(final String idPlaylist, final Integer posicion) {

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
                    new ControllerPlaylist().borrarPlaylist(getContext(), idPlaylist, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado){
                                Toast.makeText(getContext(), R.string.eliminarPlaylist, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).show();
        flagBorrar = true;
    }
}
