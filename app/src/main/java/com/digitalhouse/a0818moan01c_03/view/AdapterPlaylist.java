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
import com.digitalhouse.a0818moan01c_03.model.Cancion;

import java.util.ArrayList;
import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter implements ItemTouchHelperGestures.GestureListener{

    private List<Cancion> elementos;
    private List<Cancion> elementosCopia = new ArrayList<>();
    private OnAdapterPlaylistListener listener;


    public AdapterPlaylist(List<Cancion> elementos, OnAdapterPlaylistListener listener) {
        this.elementos = elementos;
        this.listener = listener;
        elementosCopia.addAll(elementos);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context contexto = parent.getContext();
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View view = inflador.inflate(R.layout.layout_item_playlist, parent, false);
        ViewHolderPlaylist holder = new ViewHolderPlaylist(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderPlaylist holderPlaylist = (ViewHolderPlaylist) holder;
        holderPlaylist.cargar(elementos.get(position));
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }


    private class ViewHolderPlaylist extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView artista;
        private TextView album;
        private ImageView imagen;
        private ImageView botonAgregar;

        public ViewHolderPlaylist(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textoTituloCancionPlaylist);
            artista = itemView.findViewById(R.id.textoArtistaPlaylist);
            album = itemView.findViewById(R.id.textoAlbumPlaylist);
            imagen = itemView.findViewById(R.id.imagenAlbumPlaylist);
            botonAgregar = itemView.findViewById(R.id.botonAgregarAPlaylist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String textoTitulo = elementos.get(getAdapterPosition()).getTitulo();
                    String textoArtista = elementos.get(getAdapterPosition()).getArtista().getNombre();
                    Integer idTrack = elementos.get(getAdapterPosition()).getId();
                    String preview = elementos.get(getAdapterPosition()).getPreview();
                    Bundle datos = new Bundle();

                    datos.putString(ReproductorActivity.TITULO, textoTitulo);
                    datos.putString(ReproductorActivity.ARTISTA, textoArtista);
                    datos.putInt(ReproductorActivity.IDTRACK, idTrack);
                    datos.putString(ReproductorActivity.PREVIEW, preview);
                    listener.pasarReproductor(datos);
                }
            });

            botonAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer idCancion = elementos.get(getAdapterPosition()).getId();
                    Bundle datos = new Bundle();
                    datos.putInt(AgregarPlaylistActivity.KEY_IDCANCION, idCancion);
                    listener.pasarAgregarPlaylist(datos);
                }
            });
        }

        public void cargar(Cancion unElemento){
            titulo.setText(unElemento.getTitulo());
            artista.setText(unElemento.getArtista().getNombre()+",");
            album.setText(unElemento.getAlbum().getNombre());
            Glide.with(itemView.getContext()).load(unElemento.getAlbum().getImagen()).into(imagen);
        }
    }


    public interface OnAdapterPlaylistListener{
        void pasarReproductor(Bundle datos);
        void pasarAgregarPlaylist(Bundle datos);
        void snackBar(Integer posicion, Integer idCancion);
    }

    public void filtro(String texto){
        elementos.clear();
        if (texto.isEmpty())
            elementos.addAll(elementosCopia);
        else {
            texto = texto.toLowerCase();
            for (Cancion unElemento : elementosCopia)
                if (unElemento.getTitulo().toLowerCase().contains(texto))
                    elementos.add(unElemento);
        }
        notifyDataSetChanged();
    }

    public void setElementos(List<Cancion> elementos) {
        this.elementos = elementos;
        elementosCopia.clear();
        elementosCopia.addAll(this.elementos);
        notifyDataSetChanged();
    }

    @Override
    public void itemBorrado(int posicion) {
        Integer idCancion = elementos.get(posicion).getId();
        elementos.remove(posicion);
        notifyItemRemoved(posicion);
        listener.snackBar(posicion, idCancion);
    }

    public void deshacerBorrado(Integer posicion){
        elementos.add(posicion, elementosCopia.get(posicion));
        notifyDataSetChanged();
    }
}
