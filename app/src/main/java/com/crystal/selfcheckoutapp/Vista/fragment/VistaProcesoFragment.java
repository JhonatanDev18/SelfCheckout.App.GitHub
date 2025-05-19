package com.crystal.selfcheckoutapp.Vista.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.R;

public class VistaProcesoFragment extends Fragment{
    private ImageView ivOvalUno, ivOvalDos, ivOvalTres, ivOvalCuatro, ivPasoUno, ivPasoDos, ivPasoTres, ivPasoCuatro;
    private TextView tvMarcadorUno, tvMarcadorDos, tvMarcadorTres, tvMarcadorCuatro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        inicializar(view);
    }

    private void inicializar(View view) {
        int vista = SPM.getInt(Constantes.PROCESO_ACTIVITY);
        ivOvalUno = view.findViewById(R.id.ivOvalUno);
        ivOvalDos = view.findViewById(R.id.ivOvalDos);
        ivOvalTres = view.findViewById(R.id.ivOvalTres);
        ivOvalCuatro = view.findViewById(R.id.ivOvalCuatro);

        ivPasoUno = view.findViewById(R.id.ivPasoUno);
        ivPasoDos = view.findViewById(R.id.ivPasoDos);
        ivPasoTres = view.findViewById(R.id.ivPasoTres);
        ivPasoCuatro = view.findViewById(R.id.ivPasoCuatro);

        tvMarcadorUno = view.findViewById(R.id.tvMarcadorUno);
        tvMarcadorDos = view.findViewById(R.id.tvMarcadorDos);
        tvMarcadorTres = view.findViewById(R.id.tvMarcadorTres);
        tvMarcadorCuatro = view.findViewById(R.id.tvMarcadorCuatro);

        switch (vista){
            case Constantes.VISTA_CONSULTA_CLIENTE:
                activarPaso(false, false, false, false);
                tvMarcadorUno.setVisibility(View.VISIBLE);
                break;
            case Constantes.VISTA_COMPRA_CLIENTE:
                activarPaso(true, false, false, false);
                tvMarcadorDos.setVisibility(View.VISIBLE);
                break;
            case Constantes.VISTA_MEDIO_PAGO:
                activarPaso(true, true, false, false);
                tvMarcadorTres.setVisibility(View.VISIBLE);
                break;
            case Constantes.VISTA_IMPRESION:
                activarPaso(true, true, true, true);
                tvMarcadorCuatro.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void activarPaso(boolean pasoUno, boolean pasoDos, boolean pasoTres, boolean pasoCuatro){
        if(pasoUno){
            ivOvalUno.setImageResource(R.drawable.oval2);
            ivPasoUno.setImageResource(R.drawable.usuario_consulta_blanco);
        }

        if(pasoDos){
            ivOvalDos.setImageResource(R.drawable.oval2);
            ivPasoDos.setImageResource(R.drawable.usuario_escaneo_blanco);
        }

        if(pasoTres){
            ivOvalTres.setImageResource(R.drawable.oval2);
            ivPasoTres.setImageResource(R.drawable.usuario_medio_pago_blanco);
        }

        if(pasoCuatro){
            ivPasoCuatro.setImageResource(R.drawable.usuario_comprobar_verde);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.vista_fragment_proceso, container, false);
    }
}
