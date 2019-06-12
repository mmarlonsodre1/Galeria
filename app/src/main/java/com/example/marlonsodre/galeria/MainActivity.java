package com.example.marlonsodre.galeria;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ListView mLvwFotos;
    private ArrayList<String>mFotos;
    private ImageView mImgFoto;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFotos = new ArrayList<>();
        mLvwFotos = findViewById(R.id.lvwFotos);
        mImgFoto = findViewById(R.id.imgFoto);

        mLvwFotos.setOnItemClickListener(mostrar);

        mHandler = new Handler();

        carregarArquivos();
    }

    private AdapterView.OnItemClickListener mostrar = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mImgFoto.setImageURI(Uri.parse((String) mFotos.get(position)));
            mImgFoto.setVisibility(View.VISIBLE);

            TimerTask timer = new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mImgFoto.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            };

            new Timer().schedule(timer, 5000);
        }
    };

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int [] grantResults){
        switch (requestCode){
            case 100:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    carregarArquivos();
                }
        }
    }

    private void carregarArquivos() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }

        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        for (File foto : diretorio.listFiles()) {
            mFotos.add(foto.getAbsolutePath());
        }

        mLvwFotos.setAdapter(new MyBaseAdapter(this, mFotos));

    }
}
