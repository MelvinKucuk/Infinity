package com.digitalhouse.a0818moan01c_03.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.model.Genero;
import com.digitalhouse.a0818moan01c_03.util.Util;

import java.util.List;

public class AdapterGenero extends RecyclerView.Adapter {

    private AdapterInterface escuchador;

    //Atributos

    private List<Genero> categorias;

    //Constructor

    public AdapterGenero (AdapterInterface escuchador, List<Genero> categorias) {
        this.categorias = categorias;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_genero,parent,false);
        GeneroHolder generoHolder = new GeneroHolder(view);
        return generoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Genero genero = categorias.get(position);
        GeneroHolder generoHolder = (GeneroHolder) holder;
        generoHolder.cargar(genero);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public interface AdapterInterface {
        void irPlaylist(int idGenero, String nombreGenero);
    }


    public class GeneroHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private ImageView imagen;


        public GeneroHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.genero_nombre);
            imagen = itemView.findViewById(R.id.genero_imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escuchador.irPlaylist(categorias.get(getAdapterPosition()).getId(),
                            categorias.get(getAdapterPosition()).getNombre());
                }
            });

        }

        public void  cargar(Genero genero){
            nombre.setText(genero.getNombre());
            Glide.with(itemView.getContext())
                    .load(genero.getImagen())
                    .apply(Util.requestOptionsGenero())
                    .into(imagen);
        }


    }

    public void setCategorias(List<Genero> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }
}
