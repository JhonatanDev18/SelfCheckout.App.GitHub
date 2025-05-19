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

import java.util.List;

public class ListaSimpleMedioPagoRecyclerViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<String> listLS;
    View viewLS;
    TextView tvnombreLS;
    ImageView ivFotoFondo;
    LinearLayout linearLayoutLS;

    public ListaSimpleMedioPagoRecyclerViewAdapter(Context context, List<String> listLS) {
        this.context = context;
        this.listLS = listLS;
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

        String texto = tvnombreLS.getText().toString();

        if(texto.contains("Tar")){
            ivFotoFondo.setVisibility(View.VISIBLE);
            ivFotoFondo.setImageResource(R.drawable.pago_tarjeta_negro);
        }else if(texto.contains("QR")){
            ivFotoFondo.setVisibility(View.VISIBLE);
            ivFotoFondo.setImageResource(R.drawable.codigo_qr_negro);
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
