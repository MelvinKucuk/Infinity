package com.digitalhouse.a0818moan01c_03.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Album;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.List;

public class AdapterAlbum extends RecyclerView.Adapter {

    private AdapterInterface escuchador;
    private List<Album> datos;

    public AdapterAlbum(AdapterInterface escuchador, List<Album> datos) {
        this.escuchador = escuchador;
        this.datos = datos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context contexto = parent.getContext();
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View view = inflador.inflate(R.layout.layout_item_album, parent, false);
        AlbumHolder holder = new AlbumHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((AlbumHolder) holder).cargar(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public interface AdapterInterface{
        void irPlaylist(Bundle datos);
    }

    private class AlbumHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private ImageView imagen;


        public AlbumHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.album_nombre);
            imagen = itemView.findViewById(R.id.album_imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    Integer idAlbum = datos.get(getAdapterPosition()).getId();

                    bundle.putInt(PlaylistActivity.KEY_ID_ALBUM, idAlbum);
                    escuchador.irPlaylist(bundle);
                }
            });
        }

        public void cargar(Album unAlbum){
            nombre.setText(unAlbum.getNombre());
            Glide.with(itemView.getContext())
                    .load(unAlbum.getImagen())
                    .apply(Util.requestOptionsAlbum())
                    .into(imagen);
        }
    }

    public void setDatos(List<Album> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }
}
