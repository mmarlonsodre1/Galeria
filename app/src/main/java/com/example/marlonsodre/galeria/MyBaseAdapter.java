package com.example.marlonsodre.galeria;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyBaseAdapter extends BaseAdapter {

    private Context context;
    private List<String> fotos;

    public MyBaseAdapter(Context context, List<String> fotos){
        this.context = context;
        this.fotos = fotos;
    }

    @Override
    public int getCount(){
        return this.fotos.size();
    }

    @Override
    public Object getItem(int position){
        return this.fotos.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String foto = (String)getItem(position);
        ImageView imgFoto = new ImageView(context);
        imgFoto.setPadding(10,10,10,10);
        imgFoto.setImageURI(Uri.parse(foto));
        return imgFoto;
    }

}
