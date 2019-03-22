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

public class AdapterArtista extends RecyclerView.Adapter {

    // Atributo
    private List<Artista> artistaLista;
    private ArtistaInterface listener;

    // Constructor
    public AdapterArtista(List<Artista> artistaLista, ArtistaInterface listener) {
        this.artistaLista = artistaLista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewDeLaCelda = inflater.inflate(R.layout.layout_item_artista, parent, false);
        ArtistaViewHolder artistaViewHolder = new ArtistaViewHolder(viewDeLaCelda);
        return artistaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Artista artista = artistaLista.get(position);
        ArtistaViewHolder artistaViewHolder = (ArtistaViewHolder) holder;
        artistaViewHolder.cargarArtista(artista);
    }

    public int getItemCount() {
        return artistaLista.size();
    }

    private class ArtistaViewHolder extends RecyclerView.ViewHolder {
        // Atributos
        private TextView textViewNombre;
        private ImageView imagen;

        // Constructor
        public ArtistaViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewCelda);
            imagen = itemView.findViewById(R.id.imageViewCelda);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle datos = new Bundle();
                    String nombreArtista = artistaLista.get(getAdapterPosition()).getNombre();
                    datos.putString(ArtistaElementoFragment.KEY_ARTISTA, nombreArtista);
                    listener.pasarElementoFragment(datos);
                }
            });
        }

        // Metodos
        public void cargarArtista(Artista unArtista) {
            textViewNombre.setText(unArtista.getNombre());
            Glide.with(itemView.getContext())
                    .load(unArtista.getImagen())
                    .apply(Util.requestOptionsArtista())
                    .into(imagen);
        }
    }

    public void setArtistaLista(List<Artista> artistaLista) {
        this.artistaLista = artistaLista;
        notifyDataSetChanged();
    }

    public interface ArtistaInterface{
        void pasarElementoFragment(Bundle datos);
    }
}
