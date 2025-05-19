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

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListaAnulacionRecyclerViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    List<CuerpoRespuestaDatafono> cuerpoRespuestaDatafono;
    List<RespuestaCompletaTef> listLS;
    View viewLS;
    TextView tvnombreLS;
    ImageView ivFotoFondo;
    LinearLayout linearLayoutLS;
    ConstraintLayout clFondoPrincipal;
    CardView cvLista;

    public ListaAnulacionRecyclerViewAdapter(Context context, List<RespuestaCompletaTef> listLS) {
        this.context = context;
        this.listLS = listLS;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"ViewHolder", "InflateParams", "ResourceAsColor", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewLS = inflater.inflate(R.layout.vista_fragment_lista_simple_anulacion, null);
        ivFotoFondo = viewLS.findViewById(R.id.ivFotoFondo);
        tvnombreLS = viewLS.findViewById(R.id.tvLayoutListaSimpleAdapter);
        linearLayoutLS = viewLS.findViewById(R.id.linearLayoutListaSimple);
        clFondoPrincipal = viewLS.findViewById(R.id.clFondoPrincipal);
        cvLista = viewLS.findViewById(R.id.cvLista);

        // Obtén la fecha actual
        Calendar calendario = Calendar.getInstance();
        String horaMinutos = listLS.get(position).getRespuestaTef().getHoraTransaccion();

        // Establece la hora y los minutos deseados
        int horas = Integer.parseInt(horaMinutos.substring(0, 2));
        int minutos =  Integer.parseInt(horaMinutos.substring(2, 4));

        calendario.set(Calendar.HOUR_OF_DAY, horas);
        calendario.set(Calendar.MINUTE, minutos);

        // Convierte el Calendar a un objeto Date
        Date fechaConHora = calendario.getTime();

        // Imprime la fecha con la hora
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatoFechaHora =
                new SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' hh:mm a", new Locale("es", "ES"));
        String fechaHoraFormateada = formatoFechaHora.format(fechaConHora);

        tvnombreLS.setText(fechaHoraFormateada+" - Nro Recibo: "+listLS.get(position).getRespuestaTef().getRecibo());

        if(listLS.get(position).getHeader().getAnulada()){
            ivFotoFondo.setVisibility(View.VISIBLE);
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
