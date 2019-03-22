package com.digitalhouse.a0818moan01c_03.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitalhouse.a0818moan01c_03.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSharedPreferences(ReproductorActivity.PREFERENCES,MODE_PRIVATE).edit().putBoolean(ReproductorActivity.BOUNDED, false).apply();
        LogoFragment fragmentLogo = new LogoFragment();
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentWholeMain, fragmentLogo);
        transaction.commit();

        // Runnable para poder ejecutar ese codigo despues de un tiempo determinado
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Cambio el fragment que tiene solo el logo a la activity principal
                Intent unIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(unIntent);
                finish();
            }
        };

        // El Handler maneja en cuanto tiempo (en milisegundos) se va a ejecutar el Runnable
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2500);
    }
}
