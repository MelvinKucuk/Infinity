package com.digitalhouse.a0818moan01c_03.view;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalhouse.a0818moan01c_03.R;
import com.digitalhouse.a0818moan01c_03.controller.ControllerPlaylist;
import com.digitalhouse.a0818moan01c_03.util.ResultListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearPlaylistFragment extends DialogFragment {


    public CrearPlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_playlist, container, false);

        final EditText editText = view.findViewById(R.id.editTextNombrePlaylist);
        Button botonCrear = view.findViewById(R.id.botonCrearPlaylist);
        Button botonCancelar = view.findViewById(R.id.botonCancelar);
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    final String nombrePlaylist = editText.getText().toString();
                    new ControllerPlaylist().crearPlaylist(getContext(), nombrePlaylist, new ResultListener<Boolean>() {
                        @Override
                        public void finish(Boolean resultado) {
                            if (resultado) {
                                editText.setText("");
                                Toast.makeText(getContext(), getString(R.string.creacionPlaylist) + " " + nombrePlaylist, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

}
