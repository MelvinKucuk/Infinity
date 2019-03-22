package com.digitalhouse.a0818moan01c_03.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumPagerAdapter extends FragmentPagerAdapter {

    private List<String> datos;
    private List<AlbumElementoFragment> fragments = new ArrayList<>();

    public AlbumPagerAdapter(FragmentManager fm, List<String> datos) {
        super(fm);
        this.datos = datos;

        for (String idImagen : datos){
            AlbumElementoFragment unFragment = AlbumElementoFragment.fabrica(idImagen);
            fragments.add(unFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    public void setDatos(List<String> datos) {
        this.datos = datos;

        for (String idImagen : datos){
            AlbumElementoFragment unFragment = AlbumElementoFragment.fabrica(idImagen);
            fragments.add(unFragment);
        }
        notifyDataSetChanged();
    }
}
