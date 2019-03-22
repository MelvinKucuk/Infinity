package com.digitalhouse.a0818moan01c_03.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String nombre;
    private Integer cantidadCanciones;
    private String idPlaylist;
    private List<Integer> canciones;

    public Playlist(){}

    public Playlist(String nombre, Integer cantidadCanciones, String idPlaylist, List<Integer> canciones) {
        this.nombre = nombre;
        this.cantidadCanciones = cantidadCanciones;
        this.idPlaylist = idPlaylist;
        this.canciones = canciones;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidadCanciones() {
        return cantidadCanciones;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public List<Integer> getCanciones() {
        return canciones;
    }

    public void agregarCancion(Integer id){
        if (canciones == null){
            canciones = new ArrayList<>();
        }
        if (!canciones.contains(id)) {
            canciones.add(id);
            cantidadCanciones++;
        }
    }

    public void removerCancion(Integer id){
        if (canciones.remove(id)){
            cantidadCanciones--;
        }

    }

}
