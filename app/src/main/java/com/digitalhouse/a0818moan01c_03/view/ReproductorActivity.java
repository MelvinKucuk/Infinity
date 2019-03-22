package com.digitalhouse.a0818moan01c_03.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerAlbum;
import com.digitalhouse.a0818moan01c_03.controller.ControllerCancion;
import com.digitalhouse.a0818moan01c_03.controller.ControllerPlaylist;
import com.digitalhouse.a0818moan01c_03.dao.ServiceMusica;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.github.tbouron.shakedetector.library.ShakeDetector;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

public class ReproductorActivity extends AppCompatActivity {

    public static final String TITULO = "titulo";
    public static final String ARTISTA = "artista";
    public static final String IDTRACK = "idtrack";
    public static final String PREVIEW = "preview";
    public static final String ACTION_PLAY_NEW_AUDIO = "com.digitalhouse.a0818moan01c_03.PlayNewAudio";
    public static final String KEY_ID_GENERO = "idGenero";
    public static final String KEY_ID_ALBUM = "idAlbum";
    public static final String PREFERENCES = "preferences";
    public static final String BOUNDED = "bounded";
    public static final String KEY_BUSQUEDA = "busqueda";
    public static final String KEY_TEXTO_BUSQUEDA = "textoBusqueda";
    public static final String KEY_ID_PLAYLIST = "idPlaylist";

    private List<Cancion> canciones = new ArrayList<>();
    private AudioWaveView audioWave;
    private static ServiceMusica player;
    private boolean serviceBound = false;
    private List<String> albumes = new ArrayList<>();
    private AlbumPagerAdapter adapter;
    private ViewPager viewPagerAlbum;
    private TextView tituloView;
    private ImageView fondo;
    private boolean estaActivo;
    private SharedPreferences preferences;
    private Handler musicHandler = new Handler();
    private Vibrator vibrator;
    private int posicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Flag de control para dibujar el progreso del AudioWave
        estaActivo = true;

        // Elementos de la View
        tituloView = findViewById(R.id.tituloCancion);
        audioWave = findViewById(R.id.reproduccion);
        final ImageView play_pause = findViewById(R.id.botonPlayPausa);
        ImageView botonAvanzar10 = findViewById(R.id.botonAvanzar10);
        ImageView botonRetroceder10 = findViewById(R.id.botonRetroceder10);
        ImageView botonAgregarAPlaylist = findViewById(R.id.botonAgregarAPlaylistReproductor);
        ImageView  shareButtom = findViewById(R.id.sharebuttom);
        fondo = findViewById(R.id.fondo);

        // Datos del Intent

        Intent unIntent = getIntent();
        Bundle datos = unIntent.getExtras();

        // Flag en el shared preferences para marcar que esta en uso o no el servicio de musica
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        serviceBound = preferences.getBoolean(BOUNDED, false);

        // Seteo de titulo de la canion y artista en la View
        String titulo = datos.getString(TITULO);
        String artista = datos.getString(ARTISTA);

        tituloView.setText(titulo + " - " + artista);
        tituloView.setSelected(true);


        // Pido los datos del Bundle
        final int idGenero = datos.getInt(KEY_ID_GENERO);
        final int idAlbum = datos.getInt(KEY_ID_ALBUM);
        final boolean esBusqueda = datos.getBoolean(KEY_BUSQUEDA);
        final String textoBusqueda = datos.getString(KEY_TEXTO_BUSQUEDA);
        String idPlaylist = datos.getString(KEY_ID_PLAYLIST);
        final int idTrack = datos.getInt(IDTRACK);

        // Si viene de una lista de Genero, carga con datos de genero y reproduce
        // La cancion seleccionada

        if (idGenero != 0) {

            new ControllerCancion().obtenerCacionPorGenero(this, idGenero, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {

                    canciones = resultado;
                    playMusic(canciones.get(posicion).getPreview());
                    for (Cancion cancion : canciones) {
                        albumes.add(cancion.getAlbum().getImagen());
                        if (cancion.getId() == idTrack){
                            posicion = canciones.indexOf(cancion);
                        }
                    }
                    cargarFondo(canciones.get(posicion).getAlbum().getImagen());
                    adapter.setDatos(albumes);
                    viewPagerAlbum.setCurrentItem(posicion);
                }
            });


        }

        // Si viene de una lista de un album, carga los datos del album y reproduce
        // La cancion seleccionada

        if (idAlbum != 0) {

            new ControllerAlbum().obtenerAlbumPorId(this, idAlbum, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {

                    canciones = resultado.getTracks().getData();
                    playMusic(canciones.get(posicion).getPreview());
                    for (Cancion unaCancion : canciones) {
                        unaCancion.setAlbum(new Album(resultado.getNombre(), resultado.getImagen()));
                        albumes.add(unaCancion.getAlbum().getImagen());
                        if (unaCancion.getId() == idTrack){
                            posicion = canciones.indexOf(unaCancion);
                        }
                    }
                    cargarFondo(canciones.get(posicion).getAlbum().getImagen());
                    adapter.setDatos(albumes);
                    viewPagerAlbum.setCurrentItem(posicion);

                }
            });
        }

        // Si viene de una Busqueda de texto que traiga los datos de esa busqueda y reproduce
        // La cancion seleccionada

        if (esBusqueda) {

            if (textoBusqueda != null) {

                new ControllerCancion().obtenerCacionesPorBusquedaTotal(this, textoBusqueda, new ResultListener<List<Cancion>>() {
                    @Override
                    public void finish(List<Cancion> resultado) {
                        canciones = resultado;
                        playMusic(canciones.get(posicion).getPreview());
                        for (Cancion cancion : canciones) {
                            albumes.add(cancion.getAlbum().getImagen());
                            if (cancion.getId() == idTrack){
                                posicion = canciones.indexOf(cancion);
                            }
                        }
                        cargarFondo(canciones.get(posicion).getAlbum().getImagen());
                        adapter.setDatos(albumes);
                        viewPagerAlbum.setCurrentItem(posicion);
                    }
                });

            }else {

                new ControllerCancion().obtenerCacionTopCaciones(this, new ResultListener<List<Cancion>>() {
                    @Override
                    public void finish(List<Cancion> resultado) {

                        canciones = resultado;
                        playMusic(canciones.get(posicion).getPreview());
                        for (Cancion cancion : canciones) {
                            albumes.add(cancion.getAlbum().getImagen());
                            if (cancion.getId() == idTrack){
                                posicion = canciones.indexOf(cancion);
                            }
                        }
                        cargarFondo(canciones.get(posicion).getAlbum().getImagen());
                        adapter.setDatos(albumes);
                        viewPagerAlbum.setCurrentItem(posicion);
                    }
                });
            }

        }

        // Si viene de una Playlist de texto que traiga los datos de esa busqueda y reproduce
        // la cancion seleccionada

        if (idPlaylist != null){

            new ControllerPlaylist().obtenerPlaylistPorId(this, idPlaylist, new ResultListener<List<Cancion>>() {
                @Override
                public void finish(List<Cancion> resultado) {
                    canciones = resultado;
                    playMusic(canciones.get(posicion).getPreview());
                    for (Cancion cancion : canciones){
                        albumes.add(cancion.getAlbum().getImagen());
                        if (cancion.getId() == idTrack){
                            posicion = canciones.indexOf(cancion);
                        }
                    }
                    cargarFondo(canciones.get(posicion).getAlbum().getImagen());
                    adapter.setDatos(albumes);
                    viewPagerAlbum.setCurrentItem(posicion);
                }
            });
        }




        // Inicializar el AudioWave

        generarPicos(R.raw.cancion_fallo, audioWave);

        // Cambiar el icono al del Pausa porque empezo la reproduccion

        play_pause.setImageResource(R.drawable.ic_pausa);

        // Listeners de los Botones

        play_pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (player.getMediaPlayerState()) {
                    player.pauseCancion();
                    play_pause.setImageResource(R.drawable.ic_play);
                } else {
                    player.playCancion();
                    play_pause.setImageResource(R.drawable.ic_pausa);
                }
            }
        });


        botonAvanzar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = player.getCurrentPosition();

                player.seekToCacion(pos + 10000);

            }
        });

        botonRetroceder10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = player.getCurrentPosition();

                player.seekToCacion(pos - 10000);
            }
        });

        botonAgregarAPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idCancion = canciones.get(posicion).getId();
                Bundle datos = new Bundle();
                datos.putInt(AgregarPlaylistActivity.KEY_IDCANCION, idCancion);
                Intent unIntent = new Intent(ReproductorActivity.this, AgregarPlaylistActivity.class);
                unIntent.putExtras(datos);
                startActivity(unIntent);
            }
        });

        // Boton Share

        shareButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, R.string.compartir);
                String linkCancion = canciones.get(posicion).getLink();
                String enunciado = getString(R.string.escuchaCancion)+" "+linkCancion;
                share.putExtra(Intent.EXTRA_TEXT, enunciado);
                String compartir = getString(R.string.compartirCancion);
                startActivity(Intent.createChooser(share, compartir));
            }
        });


        //Encargado de evento, cada medio segundo actualiza el seekBar con la posicion actual de la cancion

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ReproductorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (estaActivo && player != null) {
                            float p = (float) (player.getCurrentPosition() * 100) / player.getDuration();
                            if (p >= 0 && p <= 100) {
                                audioWave.setProgress(p);
                            }
                            musicHandler.postDelayed(this, 500);
                        }
                    }
                });
            }
        };
        
        Handler handler = new Handler();

        // Se neceista retrasar 1 segundo porque hay que esperar que terminen de cargar los datos

        handler.postDelayed(runnable, 1000);

        // Control de cambio de posicion del AudioWave

        audioWave.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onStartTracking(float v) {

            }

            //Cuando el usuario suelta el seekBar en la posicion deseada cambia la posicion de la cancion
            @Override
            public void onStopTracking(float v) {
                if (player.getMediaPlayer() != null) {
                    int progress = (int) (player.getDuration() * v) / 100;
                    player.seekToCacion(progress);
                }
            }

            @Override
            public void onProgressChanged(float v, boolean b) {

            }
        });


        // ViewPager de imagenes de los Albums
        viewPagerAlbum = findViewById(R.id.viewPagerAlbum);
        adapter = new AlbumPagerAdapter(getSupportFragmentManager(), albumes);
        viewPagerAlbum.setAdapter(adapter);
        viewPagerAlbum.setPageMargin(100);
        viewPagerAlbum.setClipToPadding(false);

        viewPagerAlbum.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Cuando avance el album en el ViewPager, que cambie de cancion a la correspondiente
                posicion = position;
                playMusic(canciones.get(position).getPreview());
                play_pause.setImageResource(R.drawable.ic_pausa);
                cargarFondo(canciones.get(position).getAlbum().getImagen());
                viewPagerAlbum.setCurrentItem(position);
                tituloView.setText(canciones.get(position).getTitulo() + " - "
                        + canciones.get(position).getArtista().getNombre());
                tituloView.setSelected(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Encargado de que cuando agitas pase de cancion

        ShakeDetector.create(this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {

                if (viewPagerAlbum.getCurrentItem() + 1 < adapter.getCount()) {

                    posicion++;
                    playMusic(canciones.get(viewPagerAlbum.getCurrentItem() + 1).getPreview());
                    play_pause.setImageResource(R.drawable.ic_pausa);
                    cargarFondo(canciones.get(viewPagerAlbum.getCurrentItem() + 1).getAlbum().getImagen());
                    tituloView.setText(canciones.get(viewPagerAlbum.getCurrentItem() + 1).getTitulo() + " - "
                            + canciones.get(viewPagerAlbum.getCurrentItem() + 1).getArtista().getNombre());
                    viewPagerAlbum.setCurrentItem(viewPagerAlbum.getCurrentItem() + 1);
                    tituloView.setSelected(true);
                    vibrator.vibrate(300);

                }

            }
        });

        ShakeDetector.updateConfiguration(4, 2);
    }


    private void generarPicos(int recurso, AudioWaveView audio) {

        byte[] soundBytes = null;
        try {
            InputStream inputStream = getResources().openRawResource(recurso);
            soundBytes = new byte[inputStream.available()];
            inputStream.read(soundBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        audio.setRawData(soundBytes);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Funcionamiento del boton "Back"

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceMusica.LocalBinder binder = (ServiceMusica.LocalBinder) service;

            player = binder.getService();
            serviceBound = true;
            preferences.edit().putBoolean(BOUNDED, serviceBound).apply();
            Toast.makeText(ReproductorActivity.this, "Servicios Bindeado", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            preferences.edit().putBoolean(BOUNDED, serviceBound).apply();
        }
    };

    private void playMusic(String url) {

        // Fijarse si el servicio esta activo
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, ServiceMusica.class);
            playerIntent.putExtra(ServiceMusica.KEY_URL, url);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //El servicio esta activo
            Intent broadcastIntent = new Intent(ACTION_PLAY_NEW_AUDIO);
            broadcastIntent.putExtra(ServiceMusica.KEY_URL, url);
            sendBroadcast(broadcastIntent);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShakeDetector.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShakeDetector.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShakeDetector.destroy();
    }

    @Override
    public void onBackPressed() {
        estaActivo = false;
        super.onBackPressed();

    }

    private void cargarFondo(String url){

        Glide.with(ReproductorActivity.this).load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                .into(fondo);
    }
}
