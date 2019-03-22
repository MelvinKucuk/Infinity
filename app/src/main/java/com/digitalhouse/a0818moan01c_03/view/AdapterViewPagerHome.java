package com.digitalhouse.a0818moan01c_03.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class AdapterViewPagerHome extends FragmentPagerAdapter {

    private List<String> titulos;
    private List<Fragment> datos;

    public AdapterViewPagerHome(FragmentManager fm, List<String> titulos, List<Fragment> datos) {
        super(fm);
        this.titulos = titulos;
        this.datos = datos;
    }

    @Override
    public Fragment getItem(int position) {
        return datos.get(position);
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}
