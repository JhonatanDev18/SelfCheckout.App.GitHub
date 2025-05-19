package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crystal.selfcheckoutapp.R;

import java.util.Arrays;
import java.util.List;

public class ListaSimpleRecyclerViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<String> listLS;
    View viewLS;
    boolean isGeneric;
    TextView tvnombreLS;
    ImageView ivFotoFondo;
    LinearLayout linearLayoutLS;
    List<String> listaPaises;

    public ListaSimpleRecyclerViewAdapter(Context context, List<String> listLS, boolean isGeneric) {
        this.context = context;
        this.listLS = listLS;
        this.isGeneric = isGeneric;
        listaPaises = Arrays.asList(context.getResources().getStringArray(R.array.language_options));
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewLS = inflater.inflate(R.layout.vista_fragment_lista_simple, null);
        ivFotoFondo = viewLS.findViewById(R.id.ivFotoFondo);
        tvnombreLS = viewLS.findViewById(R.id.tvLayoutListaSimpleAdapter);
        linearLayoutLS = viewLS.findViewById(R.id.linearLayoutListaSimple);

        tvnombreLS.setText(listLS.get(position));

        if(!isGeneric){
            ivFotoFondo.setVisibility(View.VISIBLE);
        }else{
            String texto = tvnombreLS.getText().toString();

            if(texto.contains("tar")){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.pago_tarjeta_negro);
            }else if(texto.contains("QR")){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.codigo_qr_negro);
            }else if(texto.contains("Crédito")){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.pedir_prestado_negro);
            }else if(texto.contains(listaPaises.get(0))){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.idioma_colombia);
            }else if(texto.contains(listaPaises.get(1))){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.idioma_estados_unidos);
            }else if(texto.contains(listaPaises.get(2))){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.idioma_frances);
            }else if(texto.contains(listaPaises.get(3))){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.idioma_rusia);
            }else if(texto.contains(listaPaises.get(4))){
                ivFotoFondo.setVisibility(View.VISIBLE);
                ivFotoFondo.setImageResource(R.drawable.idioma_china);
            }
        }
        return viewLS;
    }

    @Override
    public int getCount() {
        return listLS.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
