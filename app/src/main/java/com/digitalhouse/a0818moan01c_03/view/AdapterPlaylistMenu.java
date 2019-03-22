package com.digitalhouse.a0818moan01c_03.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class AdapterPlaylistMenu extends RecyclerView.Adapter {

    private List<Playlist> datos;
    private List<Playlist> datosCopia = new ArrayList<>();
    private AdapterListener escuchador;

    public AdapterPlaylistMenu(List<Playlist> datos, AdapterListener escuchador) {
        this.datos = datos;
        this.escuchador = escuchador;
        datosCopia.addAll(datos);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context contexto = parent.getContext();
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View view = inflador.inflate(R.layout.layout_item_menu_playlist, parent, false);
        AdapterViewHolder holder = new AdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        AdapterViewHolder adapterViewHolder = (AdapterViewHolder) holder;
        adapterViewHolder.cargar(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public interface AdapterListener {
        void accion(String idPlaylist, String nombrePlaylist);

        void borrarPlaylist(String idPlaylist, Integer posicion);
    }

    private class AdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView texto;
        private TextView cantidad;
        private ImageView botonBorrar;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.textoNombrePlaylist);
            cantidad = itemView.findViewById(R.id.textoCantidad);
            botonBorrar = itemView.findViewById(R.id.botonBorrar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idPlaylist = datos.get(getAdapterPosition()).getIdPlaylist();
                    String nombrePlaylist = datos.get(getAdapterPosition()).getNombre();
                    escuchador.accion(idPlaylist, nombrePlaylist);
                }
            });

            botonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarItem(getAdapterPosition());
                }
            });
        }

        public void cargar(Playlist unaPlaylist) {
            texto.setText(unaPlaylist.getNombre());
            cantidad.setText(unaPlaylist.getCantidadCanciones().toString());
        }
    }

    public void filtro(String texto) {
        datos.clear();
        if (texto.isEmpty())
            datos.addAll(datosCopia);
        else {
            texto = texto.toLowerCase();
            for (Playlist playlist : datosCopia)
                if (playlist.getNombre().toLowerCase().contains(texto))
                    datos.add(playlist);
        }
        notifyDataSetChanged();
    }

    public void setDatos(List<Playlist> datos) {
        this.datos = datos;
        datosCopia.clear();
        datosCopia.addAll(datos);
        notifyDataSetChanged();
    }

    private void borrarItem(int posicion) {
        String idPlaylist = datos.get(posicion).getIdPlaylist();
        datos.remove(posicion);
        notifyItemRemoved(posicion);
        escuchador.borrarPlaylist(idPlaylist, posicion);
    }

    public void deshacerBorrado(Integer posicion) {
        datos.add(posicion, datosCopia.get(posicion));
        notifyDataSetChanged();
    }
}
