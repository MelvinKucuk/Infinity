package com.digitalhouse.a0818moan01c_03.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.digitalhouse.a0818moan01c_03.dao.DaoFirebasePlaylist;
import com.digitalhouse.a0818moan01c_03.model.Cancion;
import com.digitalhouse.a0818moan01c_03.model.Playlist;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.digitalhouse.a0818moan01c_03.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class ControllerPlaylist {

    public void obtenerPlaylistPorId(final Context context, String idPlaylist, final ResultListener<List<Cancion>> listenerView) {

        if (Util.hayInternet(context)) {

            String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("playlist/"+idUsuario+"/"+idPlaylist);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Playlist playlist = dataSnapshot.getValue(Playlist.class);
                    if (playlist.getCanciones() != null) {
                        new ControllerCancion().obtenerCancionesPorId(context, playlist.getCanciones(), new ResultListener<List<Cancion>>() {
                            @Override
                            public void finish(List<Cancion> resultado) {
                                listenerView.finish(resultado);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void crearPlaylist(Context context, String nombrePlaylist, final ResultListener<Boolean> listenerView){

        if (Util.hayInternet(context)){
            new DaoFirebasePlaylist().crearPlaylist(nombrePlaylist, new ResultListener<Boolean>() {
                @Override
                public void finish(Boolean resultado) {
                    listenerView.finish(true);
                }
            });
        }
    }

    public void agregarCancionAPlaylist(Context context, Integer idCancion, String idPlaylist, final ResultListener<Boolean> listenerView){

        if (Util.hayInternet(context)){
            new DaoFirebasePlaylist().agregarCancionAPlaylist(idCancion, idPlaylist, new ResultListener<Boolean>() {
                @Override
                public void finish(Boolean resultado) {
                    listenerView.finish(true);
                }
            });
        }
    }

    public void borrarCancionDePlaylist(Context context, Integer idCanicon, String idPlaylist, final ResultListener<Boolean> listenerView){

        if (Util.hayInternet(context)){
            new DaoFirebasePlaylist().eliminarCancionDePlaylist(idCanicon, idPlaylist, new ResultListener<Boolean>() {
                @Override
                public void finish(Boolean resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    public void borrarPlaylist(Context context, String idPlaylist, final ResultListener<Boolean> listenerView){

        if (Util.hayInternet(context)){
            new DaoFirebasePlaylist().eliminarPlaylist(idPlaylist, new ResultListener<Boolean>() {
                @Override
                public void finish(Boolean resultado) {
                    listenerView.finish(true);
                }
            });
        }
    }
}
