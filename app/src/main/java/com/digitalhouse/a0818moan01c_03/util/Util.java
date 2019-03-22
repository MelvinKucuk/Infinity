package com.digitalhouse.a0818moan01c_03.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.digitalhouse.a0818moan01c_03.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {


    public static boolean hayInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return cm.getActiveNetworkInfo() != null && netInfo.isConnected();
    }

    public static RequestOptions requestOptionsGenero(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.genero_placeholder);
        requestOptions.error(R.drawable.genero_placeholder);
        return requestOptions;
    }

    public static RequestOptions requestOptionsAlbum(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.album_placeholder);
        requestOptions.error(R.drawable.album_placeholder);
        return requestOptions;
    }

    public static RequestOptions requestOptionsArtista(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.artista_placeholder);
        requestOptions.error(R.drawable.artista_placeholder);
        return requestOptions;
    }
    public static void printHash(Context context) {
        try {

            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
