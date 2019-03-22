package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moan01c_03.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPagerHome);

        // Datos
        List<Fragment> datos = new ArrayList<>();

        // Fragments
        datos.add(new GeneroFragment());
        datos.add(new AlbumFragment());
        datos.add(new ArtistaFragment());

        // Titulos
        List<String> titulos = new ArrayList<>();
        titulos.add(getString(R.string.generos));
        titulos.add(getString(R.string.albums));
        titulos.add(getString(R.string.artistas));

        AdapterViewPagerHome adapter = new AdapterViewPagerHome(getChildFragmentManager(), titulos, datos);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //Inicializo
        viewPager.setCurrentItem(0);

        return  view;
    }

}
