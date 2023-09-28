package com.amadeu.avaliacao_1_amadeu.ui.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.amadeu.avaliacao_1_amadeu.R;
import com.amadeu.avaliacao_1_amadeu.model.Texto;

import java.util.List;


public class AdapterTexto extends ArrayAdapter<Texto> {
    private final Context context;
    private final List<Texto> listaDeTextos;

    public AdapterTexto(Context context, List<Texto> listaDeTextos) {
        super(context, R.layout.item_layout, listaDeTextos);
        this.context = context;
        this.listaDeTextos = listaDeTextos;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_layout, parent, false);

        TextView textView = rowView.findViewById(R.id.tv_TextoColorido);

        if (listaDeTextos.get(position) != null) {
            textView.setText(listaDeTextos.get(position).getTexto_exibir());
            textView.setTextColor(ContextCompat.getColor(context, listaDeTextos.get(position).getCor_selecionada()));
        }

        Log.d("Cor recebida Adapter ->", String.valueOf(listaDeTextos.get(position).getCor_selecionada()));

        Log.d("Cor TextView ->", String.valueOf(textView.getCurrentTextColor()));

        return rowView;
    }
}
