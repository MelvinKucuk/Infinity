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
import com.digitalhouse.a0818moan01c_03.model.Artista;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.List;

public class AdapterArtistaBusqueda extends RecyclerView.Adapter {

    private List<Artista> datos;
    private ArtistaBusquedaInterface listener;

    public AdapterArtistaBusqueda(List<Artista> datos, ArtistaBusquedaInterface listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_artista_busqueda, parent, false);
        ArtistaHolder holder = new ArtistaHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ArtistaHolder) holder).cargar(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    private class ArtistaHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private ImageView imagen;

        public ArtistaHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textoArtistaBusqueda);
            imagen = itemView.findViewById(R.id.imagenArtistaBusqueda);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String nombreArtista = datos.get(getAdapterPosition()).getNombre();
                    bundle.putString(ArtistaElementoFragment.KEY_ARTISTA, nombreArtista);
                    listener.pasarElementoArtista(bundle);
                }
            });
        }

        public void cargar(Artista unArtista){
            nombre.setText(unArtista.getNombre());
            Glide.with(itemView.getContext())
                    .load(unArtista.getImagen())
                    .apply(Util.requestOptionsArtista())
                    .into(imagen);
        }
    }

    public void setDatos(List<Artista> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }

    public interface ArtistaBusquedaInterface{
        void pasarElementoArtista(Bundle datos);
    }
}
