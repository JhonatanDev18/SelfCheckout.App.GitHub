package com.crystal.selfcheckoutapp.Vista.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.R;

import java.util.Objects;

public class VistaLogoMarcaFragment extends Fragment implements View.OnClickListener  {

    private ImageView ivLogoMarca;
    private String formato;

    public VistaLogoMarcaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        inicializar(view);
        escucharCambios();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.vista_fragment_logo_marca, container, false);
    }

    private void inicializar(View view) {
        ivLogoMarca = view.findViewById(R.id.ivLogoMarca);
        formato = SPM.getString(Constantes.PARAM_FORMATO_TIENDA);

        if(formato != null){
            switch (Objects.requireNonNull(formato)){
                case Constantes.FORMATO_GEF:
                    ivLogoMarca.setImageResource(R.drawable.logogef);
                    break;
                case Constantes.FORMATO_PB:
                    ivLogoMarca.setImageResource(R.drawable.logopuntoblanco);
                    break;
                case Constantes.FORMATO_BABY_FRESH:
                    ivLogoMarca.setImageResource(R.drawable.logobabyfresh);
                    break;
                case Constantes.FORMATO_GALAX:
                    ivLogoMarca.setImageResource(R.drawable.logogalax);
                    break;
                default:
                    ivLogoMarca.setImageResource(R.drawable.logo_multimarca);
                    break;
            }
        }else{
            ivLogoMarca.setImageResource(R.drawable.logo_multimarca);
        }
    }

    private void escucharCambios() {
    }

    @Override
    public void onClick(View v) {

    }
}