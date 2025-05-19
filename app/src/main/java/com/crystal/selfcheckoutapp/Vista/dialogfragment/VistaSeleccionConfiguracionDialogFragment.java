package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.clases.Configuraciones;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaConfiguracionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class VistaSeleccionConfiguracionDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private List<Configuraciones> listaConfiguraciones;
    private ListView lvConfiguracionesSelect;
    private ImageView ivCloseConfiguracion;

    public VistaSeleccionConfiguracionDialogFragment(List<Configuraciones> listaConfiguraciones) {
        this.listaConfiguraciones = listaConfiguraciones;
    }

    public interface OnInputListener{
        void sendInputItemConfiSelectDialogFragment(Configuraciones configuracion);
    }

    public OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_configuracion_opcion, null);

        log("Abriendo POP-UP");

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);

        return builder.create();
    }

    private void inicializar() {
        lvConfiguracionesSelect = view.findViewById(R.id.lvConfiguracionesSelect);
        ivCloseConfiguracion = view.findViewById(R.id.ivCloseConfiguracion);
    }

    private void cargarDatos() {
        ListaConfiguracionRecyclerViewAdapter adapter =
                new ListaConfiguracionRecyclerViewAdapter(requireContext(),listaConfiguraciones);
        lvConfiguracionesSelect.setAdapter(adapter);
    }

    private void eventos() {
        lvConfiguracionesSelect.setOnItemClickListener((parent, view, position, id) -> {
            try{
                if(listaConfiguraciones.get(position).isEnable()){
                    LogFile.adjuntarLog("POP-UP [VistaSeleccionConfiguracionDialogFragment] - Configuración Seleccionada",
                            listaConfiguraciones.get(position));
                    mOnInputListener.sendInputItemConfiSelectDialogFragment(listaConfiguraciones.get(position));
                }else{
                    Utilidades.mjsToast(getResources().getString(R.string.opcion_deshabilitada),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
                }
            }catch (Exception e){
                Utilidades.mjsToast(e.getMessage(),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
            }
        });

        ivCloseConfiguracion.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (VistaSeleccionConfiguracionDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: TiendaSelectDialogFragment: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaSeleccionConfiguracionDialogFragment] - Seleccionar Configuración", "Acción: "+ opcion);
    }
}