package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Vista.VistaCompraCliente;
import com.crystal.selfcheckoutapp.Vista.VistaConsultaCliente;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaMsjCustomUnaAccionDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ImageView ivMsjCustom;
    private TextView tvTituloMsjCustom, tvTextoMsjCustom;
    private Button btnEntendidoMsjCustom;
    private int imagen;
    private String titulo, mensaje, mensajeDev;
    private String textoBoton;
    private int accion;

    private VistaCompraCliente vistaCompra;
    private VistaConsultaCliente vistaConsultaCliente;

    public VistaMsjCustomUnaAccionDialogFragment(int imagen, String titulo, String mensaje, String mensajeDev, String textoBoton,
                                                 int accion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.mensajeDev = mensajeDev;
        this.textoBoton = textoBoton;
        this.accion = accion;
    }

    public VistaMsjCustomUnaAccionDialogFragment(String titulo, String mensaje, String mensajeDev, String textoBoton,
                                                 int accion) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.mensajeDev = mensajeDev;
        this.textoBoton = textoBoton;
        this.accion = accion;
        imagen = 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_msj_custom_one, null);

        log("POP-UP Una Acción", "Abriendo POP-UP");

        //Asignacion de Referencias
        inicializar();

        //Eventos
        eventos();

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void inicializar() {
        ivMsjCustom = view.findViewById(R.id.ivMsjCustom);
        tvTituloMsjCustom = view.findViewById(R.id.tvTituloMsjCustom);
        tvTextoMsjCustom = view.findViewById(R.id.tvTextoMsjCustom);
        btnEntendidoMsjCustom = view.findViewById(R.id.btnEntendidoMsjCustom);
        btnEntendidoMsjCustom.setText(textoBoton);

        if(imagen == 0){
            ivMsjCustom.setVisibility(View.GONE);
        }else{
            ivMsjCustom.setImageResource(imagen);
        }

        tvTituloMsjCustom.setText(titulo);
        tvTextoMsjCustom.setText(mensaje);
    }

    private void eventos() {
        btnEntendidoMsjCustom.setOnClickListener(this::entiendo);
        ivMsjCustom.setOnClickListener(this::msjDev);
    }

    private void entiendo(View view){
        switch (accion){
            case Constantes.ACCION_DEFAULT:
                log("Acción Default", "Cerrar");
                dismiss();
                break;
            case Constantes.ACCION_ACTUALIZAR_TRANSACCION_TEF:
                log("Actualizar", "Reintentar Actualización Tef");
                vistaConsultaCliente.reintentarActualizacionTransaccion();
                break;
        }
    }

    private void msjDev(View view){
        if(!mensajeDev.isEmpty()){
            Utilidades.sweetAlert(getResources().getString(R.string.error), mensajeDev,
                    SweetAlertDialog.ERROR_TYPE, requireContext());
        }
    }

    public void setVistaCompra(VistaCompraCliente vistaCompra) {
        this.vistaCompra = vistaCompra;
    }

    public void setVistaConsultaCliente(VistaConsultaCliente vistaConsultaCliente) {
        this.vistaConsultaCliente = vistaConsultaCliente;
    }

    private void log(String titulo, String opcion){
        LogFile.adjuntarLog("POP-UP [VistaMsjCustomUnaAccionDialogFragment] - "+titulo, "Acción: "+ opcion);
    }
}