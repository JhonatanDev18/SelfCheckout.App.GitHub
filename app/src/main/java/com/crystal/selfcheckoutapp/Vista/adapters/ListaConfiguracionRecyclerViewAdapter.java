package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.crystal.selfcheckoutapp.Modelo.clases.Configuraciones;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ListaConfiguracionRecyclerViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<Configuraciones> listLS;
    View viewLS;
    TextView tvnombreLS;
    ImageView ivFotoFondo, ivFotoBloqueo;
    LinearLayout linearLayoutLS;
    ConstraintLayout clFondoPrincipal;
    CardView cvLista;

    public ListaConfiguracionRecyclerViewAdapter(Context context, List<Configuraciones> listLS) {
        this.context = context;
        this.listLS = listLS;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"ViewHolder", "InflateParams", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewLS = inflater.inflate(R.layout.vista_fragment_lista_simple, null);
        ivFotoFondo = viewLS.findViewById(R.id.ivFotoFondo);
        tvnombreLS = viewLS.findViewById(R.id.tvLayoutListaSimpleAdapter);
        linearLayoutLS = viewLS.findViewById(R.id.linearLayoutListaSimple);
        clFondoPrincipal = viewLS.findViewById(R.id.clFondoPrincipal);
        ivFotoBloqueo = viewLS.findViewById(R.id.ivFotoBloqueo);
        cvLista = viewLS.findViewById(R.id.cvLista);

        if(!listLS.get(position).isEnable()){
            cvLista.setBackgroundColor(Color.parseColor("#1AB1A0A0"));
            clFondoPrincipal.setBackgroundColor(Color.parseColor("#1AB1A0A0"));
            ivFotoBloqueo.setVisibility(View.VISIBLE);
        }

        tvnombreLS.setText(listLS.get(position).getNombre());

        ivFotoFondo.setVisibility(View.VISIBLE);

        switch (listLS.get(position).getCodigo()){
            case Constantes.CONFIGURACION_CONSULTA_ULTIMA_TEF:
                ivFotoFondo.setImageResource(R.drawable.ultima_tef_dos);
                break;
            case Constantes.CONFIGURACION_ANULAR_TRANSACION_TEF:
                ivFotoFondo.setImageResource(R.drawable.anular_tef_dos);
                break;
            default:
                ivFotoFondo.setImageResource(R.drawable.configuraciones);
                break;
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
