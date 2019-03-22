package com.digitalhouse.a0818moan01c_03.view;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.digitalhouse.a0818moan01c_03.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements
        GeneroFragment.FragmentListener, AlbumFragment.AlbumFragmentListener,
        BuscarFragment.BuscarFragmentInterface,
        FragmentManager.OnBackStackChangedListener, CancionesResultadoFragment.FragmentCancionesResultadoListener,
        AlbumResultadoFragment.InterFaceAlbumResultado, ArtistaFragment.ArtistaFragmentInterface,
        ArtistaElementoFragment.ArtistaElementoInterface, ArtistaResultadoFragment.ArtistaResultadoInteface,
        LoginFragment.OnFragmentLoginListener, MenuPlaylistFragment.MenuPlaylistInterface {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        final HomeFragment fragmentHome = new HomeFragment();
        final MenuPlaylistFragment fragmentMenuPlaylist = new MenuPlaylistFragment();
        final BuscarFragment fragmentBuscar = new BuscarFragment();
        final LoginFragment fragmentLogin = new LoginFragment();

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationHome);
        cargarFragment(fragmentHome);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.itemHome:
                        cargarFragment(fragmentHome);
                        return true;

                    case R.id.itemBuscar:
                        cargarFragment(fragmentBuscar);
                        return true;

                    case R.id.itemPlaylist:
                        if (mAuth.getUid() != null) {
                            cargarFragment(fragmentMenuPlaylist);
                        } else {
                            cargarFragment(fragmentLogin);
                        }
                        return true;
                }
                return false;
            }
        });

        //  Para poder mostrar el boton "Back" en la navegacion de Fragments
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }


    private void cargarFragment(Fragment unFragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, unFragment);
        transaction.commit();
    }

    @Override
    public void irPlaylist(int idGenero, String nombreGenero) {
        Intent unIntent = new Intent(HomeActivity.this, PlaylistActivity.class);

        Bundle datos = new Bundle();

        datos.putInt(PlaylistActivity.KEY_GENERO, idGenero);
        datos.putString(PlaylistActivity.KEY_NOMBRE_GENERO, nombreGenero);
        unIntent.putExtras(datos);
        startActivity(unIntent);
    }

    @Override
    public void irPlaylist(Bundle datos) {
        Intent unIntent = new Intent(HomeActivity.this, PlaylistActivity.class);
        unIntent.putExtras(datos);
        startActivity(unIntent);
    }

    @Override
    public void pasarReproductorActivity(Bundle datos) {
        Intent unIntent = new Intent(HomeActivity.this, ReproductorActivity.class);
        unIntent.putExtras(datos);
        startActivity(unIntent);
    }

    @Override
    public void pasarAlbumResultado(String texto) {
        FragmentManager manager = getSupportFragmentManager();
        AlbumResultadoFragment fragment = new AlbumResultadoFragment();
        Bundle datos = new Bundle();
        datos.putString(AlbumResultadoFragment.KEY_TEXTO_BUSQUEDA, texto);
        fragment.setArguments(datos);
        manager.beginTransaction().add(R.id.fragmentContainer, fragment).addToBackStack(null).commit();

        // Se agrega el "addToBackStack" para cuando volves atras reemplaze por el Fragment que estaba antes
    }

    @Override
    public void pasarCancionesResultado(String texto) {
        FragmentManager manager = getSupportFragmentManager();
        CancionesResultadoFragment fragment = new CancionesResultadoFragment();
        Bundle datos = new Bundle();
        datos.putString(CancionesResultadoFragment.KEY_TEXTO_RESULTADO, texto);
        fragment.setArguments(datos);
        manager.beginTransaction().add(R.id.fragmentContainer, fragment).addToBackStack(null).commit();

        // Se agrega el "addToBackStack" para cuando volves atras reemplaze por el Fragment que estaba antes
    }

    @Override
    public void pasarArtistasResultado(String texto) {
        FragmentManager manager = getSupportFragmentManager();
        ArtistaResultadoFragment fragment = new ArtistaResultadoFragment();
        Bundle datos = new Bundle();
        datos.putString(ArtistaResultadoFragment.KEY_TEXTO_BUSQUEDA, texto);
        fragment.setArguments(datos);
        manager.beginTransaction().add(R.id.fragmentContainer, fragment).addToBackStack(null).commit();

        // Se agrega el "addToBackStack" para cuando volves atras reemplaze por el Fragment que estaba antes
    }

    @Override
    public void onBackStackChanged() {
        // Cuando se actualiza el Stack comprueba si se muestra el boton o no
        muestraBotonAtras();
    }

    private void  muestraBotonAtras(){
        // Habilita el boton solo si hay entradas en el Stack
        boolean puedeIrAtras = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(puedeIrAtras);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Este metodo se llama cuando se toca el boton para sacar el Fragment del Stack
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void pasarElementoFragment(Bundle datos) {
        FragmentManager manager = getSupportFragmentManager();
        ArtistaElementoFragment fragment = new ArtistaElementoFragment();
        fragment.setArguments(datos);
        manager.beginTransaction().add(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void pasarLoginExitoso() {
        MenuPlaylistFragment fragmentMenuPlaylist = new MenuPlaylistFragment();
        cargarFragment(fragmentMenuPlaylist);
    }

    @Override
    public void pasarALogin() {
        LoginFragment fragment = new LoginFragment();
        cargarFragment(fragment);
    }

    @Override
    public void pasarACrearPlaylist() {
        FragmentManager manager = getSupportFragmentManager();
        CrearPlaylistFragment fragment = new CrearPlaylistFragment();
        fragment.show(manager, "tag");
    }

    @Override
    public void pasarPlaylist(String idPlaylist, String nombrePlaylist) {
        Intent unIntent = new Intent(HomeActivity.this, PlaylistActivity.class);
        Bundle datos = new Bundle();
        datos.putString(PlaylistActivity.KEY_ID_PLAYLIST, idPlaylist);
        datos.putString(PlaylistActivity.KEY_NOMBRE_PLAYLIST, nombrePlaylist);
        unIntent.putExtras(datos);
        startActivity(unIntent);
    }

    @Override
    public void pasarAgregarPlaylist(Bundle datos) {
        Intent intent = new Intent(HomeActivity.this, AgregarPlaylistActivity.class);
        intent.putExtras(datos);
        startActivity(intent);
    }
}
