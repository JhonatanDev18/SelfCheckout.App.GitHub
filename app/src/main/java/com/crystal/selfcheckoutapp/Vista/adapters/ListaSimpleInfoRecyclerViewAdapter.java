package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystal.selfcheckoutapp.Modelo.clases.ClaveValor;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.R;

import java.util.List;

public class ListaSimpleInfoRecyclerViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<ClaveValor> listLS;
    View viewLS;
    TextView tvClave, tvValor;
    private int accion;
    public ListaSimpleInfoRecyclerViewAdapter(Context context, List<ClaveValor> listLS, int accion) {
        this.context = context;
        this.listLS = listLS;
        this.accion = accion;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"ViewHolder", "InflateParams", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(accion == Constantes.ACCION_DEFAULT){
            viewLS = inflater.inflate(R.layout.vista_fragment_clave_valor_2, null);
        }else{
            viewLS = inflater.inflate(R.layout.vista_fragment_clave_valor, null);
        }

        tvClave = viewLS.findViewById(R.id.tvClave);
        tvValor = viewLS.findViewById(R.id.tvValor);

        tvClave.setText(listLS.get(position).getClave());
        tvValor.setText(listLS.get(position).getValor());

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
