package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Configuraciones;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaAnulacionRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaConfiguracionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VistaAnularTefDialogFragment extends DialogFragment {
    private View view;
    private ImageView ivCloseAnulacion;
    private List<RespuestaCompletaTef> respuestaDatafonoList;
    private Button btnAtrasAnularTef;
    private ListView lvAnulacionSelect;
    private Context contexto;

    public VistaAnularTefDialogFragment(List<RespuestaCompletaTef> respuestaDatafonoList) {
        this.respuestaDatafonoList = respuestaDatafonoList;
    }

    public interface OnInputListener{
        void sendInputItemAnularSelectDialogFragment(RespuestaCompletaTef respuestaDatafono);
    }

    public OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_anular_tef, null);

        log("Abriendo POP-UP");

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void cargarDatos() {
        // Definir un comparador personalizado para ordenar en orden descendente
        Comparator<RespuestaCompletaTef> comparador = (o1, o2) -> {
            int numero1 = Integer.parseInt(o1.getRespuestaTef().getRecibo());
            int numero2 = Integer.parseInt(o2.getRespuestaTef().getRecibo());
            return Integer.compare(numero2, numero1);
        };

        respuestaDatafonoList.sort((comparador));

        ListaAnulacionRecyclerViewAdapter adapter =
                new ListaAnulacionRecyclerViewAdapter(requireContext(),respuestaDatafonoList);
        lvAnulacionSelect.setAdapter(adapter);
    }

    private void inicializar() {
        contexto = requireContext();
        ivCloseAnulacion = view.findViewById(R.id.ivCloseAnulacion);
        btnAtrasAnularTef = view.findViewById(R.id.btnAtrasAnularTef);
        lvAnulacionSelect = view.findViewById(R.id.lvAnulacionSelect);
    }

    private void eventos() {
        lvAnulacionSelect.setOnItemClickListener((parent, view, position, id) -> {
            try{
                LogFile.adjuntarLog("POP-UP [VistaAnularTefDialogFragment] - Anulación [Selecciono]",
                        respuestaDatafonoList.get(position).getHeader());
                mOnInputListener.sendInputItemAnularSelectDialogFragment(respuestaDatafonoList.get(position));
            }catch (Exception e){
                Utilidades.mjsToast(e.getMessage(),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
            }
        });

        ivCloseAnulacion.setOnClickListener(this::cerrar);
        btnAtrasAnularTef.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }
    @Override
    public void dismiss() {
        log("Atras");
        super.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (VistaAnularTefDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: TiendaSelectDialogFragment: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaAnularTefDialogFragment] - Anulación", "Acción: "+ opcion);
    }
}