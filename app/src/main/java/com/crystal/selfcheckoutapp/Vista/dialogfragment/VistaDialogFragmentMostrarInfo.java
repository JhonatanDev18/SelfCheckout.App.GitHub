package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.clases.ClaveValor;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.ImpresoraDevice;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.VistaImpresionTirilla;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleInfoRecyclerViewAdapter;

import java.util.List;

public class VistaDialogFragmentMostrarInfo extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private List<ClaveValor> listaClaveValor;
    private ListView lvMostrarInfo;
    private ImageView ivCloseMostrarInfo, ivIconMostrarInfo;
    private int imagen;
    private String titulo;
    private TextView tvTituloMostrarInfo;
    private Button btnImprimirInfo, btnAtrasMostrarInfo;
    private Factura factura;
    private int accion;
    private Context contexto;

    public VistaDialogFragmentMostrarInfo(List<ClaveValor> listaClaveValor) {
        this.listaClaveValor = listaClaveValor;
        imagen = 0;
        titulo = "";
    }

    public VistaDialogFragmentMostrarInfo(int imagen, String titulo, List<ClaveValor> listaClaveValor,
                                          int accion) {
        this.listaClaveValor = listaClaveValor;
        this.imagen = imagen;
        this.titulo = titulo;
        this.accion = accion;
    }

    @SuppressLint("InflateParams")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_mostrar_info, null);

        log("Abriendo POP-UP");

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);

        return builder.create();
    }

    private void inicializar() {
        factura = new Factura();
        contexto = requireContext();
        ivCloseMostrarInfo = view.findViewById(R.id.ivCloseMostrarInfo);
        ivIconMostrarInfo = view.findViewById(R.id.ivIconMostrarInfo);
        tvTituloMostrarInfo = view.findViewById(R.id.tvTituloMostrarInfo);
        lvMostrarInfo = view.findViewById(R.id.lvMostrarInfo);
        btnImprimirInfo = view.findViewById(R.id.btnImprimirInfo);
        btnAtrasMostrarInfo = view.findViewById(R.id.btnAtrasMostrarInfo);

        if(accion == Constantes.ACCION_DEFAULT){
            btnAtrasMostrarInfo.setVisibility(View.GONE);
            btnImprimirInfo.setText(getResources().getString(R.string.entiendo));
        }
    }

    private void cargarDatos() {
        if(imagen!=0){
            ivIconMostrarInfo.setImageResource(imagen);
        }

        if(!titulo.isEmpty()){
            tvTituloMostrarInfo.setText(titulo);
        }

        ListaSimpleInfoRecyclerViewAdapter adapter =
                new ListaSimpleInfoRecyclerViewAdapter(requireContext(),listaClaveValor, accion);
        lvMostrarInfo.setAdapter(adapter);
    }

    private void eventos() {
        ivCloseMostrarInfo.setOnClickListener(this::cerrar);
        btnImprimirInfo.setOnClickListener(this::imprimir);
        btnAtrasMostrarInfo.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    private void imprimir(View view){
        if(accion == Constantes.ACCION_DEFAULT){
            log("Entiendo");
            dismiss();
        }else{
            log("Imprimir");
            try {
                factura.setConfiguracion(false);
                ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE,
                        ImpresoraDevice.class);

                if(impresoraDevice.isConnected()){
                    factura.setImpresora(getResources().getString(R.string.bixolon));
                }else{
                    factura.setImpresora(getResources().getString(R.string.epson));
                }

                factura.setLabelImprimir(Constantes.LABEL_CLAVE_VALOR);
                factura.setListaClaveValor(listaClaveValor);
                factura.setActivity(Constantes.VISTA_CONSULTA_CLIENTE);
                factura.setTituloClaveValor(titulo);
                Intent intent = new Intent(contexto, VistaImpresionTirilla.class);
                intent.putExtra(getResources().getString(R.string.key_intent_factura), factura);
                requireActivity().startActivity(intent);
            }catch (Exception e){
                Utilidades.mjsToast(e.getMessage(), Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG,
                        requireContext());
            }
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaDialogFragmentMostrarInfo] - Información", "Selecciono: "+ opcion);
    }
}
