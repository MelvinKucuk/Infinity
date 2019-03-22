package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.util.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumElementoFragment extends Fragment {

    private static final String KEY_IMAGEN = "imagen";

    public static AlbumElementoFragment fabrica(String idImagen){
        AlbumElementoFragment fragment = new AlbumElementoFragment();
        Bundle datos = new Bundle();
        datos.putString(KEY_IMAGEN, idImagen);
        fragment.setArguments(datos);
        return fragment;
    }


    public AlbumElementoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_elemento_album, container, false);
        ImageView imagen = view.findViewById(R.id.albumFragment);
        String url = getArguments().getString(KEY_IMAGEN);
        Glide.with(view.getContext()).load(url).apply(Util.requestOptionsAlbum()).into(imagen);
        return view;
    }

}
