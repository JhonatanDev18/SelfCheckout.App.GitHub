package com.crystal.selfcheckoutapp.Vista.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDialogFragmentMostrarInfo;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;

import java.util.Objects;

public class VistaFooterFragment extends Fragment implements View.OnClickListener {
    private TextView tvTextoDesarrollado, tvTerminosCondiciones;

    public VistaFooterFragment() {
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
        return inflater.inflate(R.layout.vista_fragment_footer, container, false);
    }

    private void inicializar(View view) {
        tvTextoDesarrollado = view.findViewById(R.id.tvTextoDesarrollado);
        tvTerminosCondiciones = view.findViewById(R.id.tvTerminosCondiciones);

        // Obtener la vista raíz de tu actividad o fragmento
        View decorView = requireActivity().getWindow().getDecorView();

        // Establecer las banderas para activar el modo inmersivo
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void escucharCambios() {
        tvTerminosCondiciones.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvTerminosCondiciones){
            LogFile.adjuntarLog("POP-UP [vistaDialogFragmentMostrarInfo] - Terminos y condiciones",
                    "Oprimio terminos y condiciones");
            VistaDialogFragmentMostrarInfo vistaDialogFragmentMostrarInfo =
                    new VistaDialogFragmentMostrarInfo(R.drawable.acuerdo,
                            getResources().getString(R.string.titulo_terminos_condiciones),
                            Utilidades.politicasLegales(requireContext()), Constantes.ACCION_DEFAULT);
            vistaDialogFragmentMostrarInfo.show(getParentFragmentManager(), "MostrarInfoClaveValor");
        }
    }
}
