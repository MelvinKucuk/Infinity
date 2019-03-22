package com.digitalhouse.a0818moan01c_03.dao;

import android.support.annotation.NonNull;

import com.digitalhouse.a0818moan01c_03.model.Playlist;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DaoFirebasePlaylist {

    public void crearPlaylist(String nombrePlaylist, final ResultListener<Boolean> listenerController){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        DatabaseReference referencePlaylist = FirebaseDatabase.getInstance().getReference("playlist/" + id);
        List<Integer> canciones = new ArrayList<>();
        String key = referencePlaylist.push().getKey();

        Playlist unaPlaylist = new Playlist(nombrePlaylist, 0, key, canciones);

        referencePlaylist.child(unaPlaylist.getIdPlaylist()).setValue(unaPlaylist).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listenerController.finish(true);
            }
        });
    }


    public void agregarCancionAPlaylist(final Integer idCancion, final String idPlaylist,final ResultListener<Boolean> listenerController){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("playlist/"+userId);
        reference.child(idPlaylist).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Playlist unaPlaylist = dataSnapshot.getValue(Playlist.class);
                unaPlaylist.agregarCancion(idCancion);
                reference.child(idPlaylist).setValue(unaPlaylist).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listenerController.finish(true);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void eliminarCancionDePlaylist(final Integer idCancion, final String idPlaylist, final ResultListener<Boolean> listenerController){

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("playlist/"+userId+"/"+idPlaylist);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Playlist playlist = dataSnapshot.getValue(Playlist.class);
                playlist.removerCancion(idCancion);
                reference.setValue(playlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listenerController.finish(true);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void eliminarPlaylist(String idPlaylist, final ResultListener<Boolean> listenerController){

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("playlist/"+userId+"/"+idPlaylist);
        reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listenerController.finish(true);
            }
        });
    }
}
