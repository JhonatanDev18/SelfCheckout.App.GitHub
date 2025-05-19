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
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Vista.VistaCompraCliente;
import com.crystal.selfcheckoutapp.Vista.VistaConsultaCliente;
import com.crystal.selfcheckoutapp.Vista.VistaMediosPago;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaMsjCustomDosAccionesDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ImageView ivMsjCustomTwo, ivCloseCustomTwo;
    private TextView tvTituloMsjCustomTwo, tvTextoMsjCustomTwo;
    private Button btnPositivoTwo, btnNegativoTwo;
    private int imagen;
    private String titulo;
    private String mensaje;
    private String mensajeDev;
    private String textoBotonPositivo, textoBotonNegativo;
    private int accion;
    private boolean permitirCierreSuperior;
    private VistaConsultaCliente vistaConsultaCliente;
    private VistaCompraCliente vistaCompraCliente;
    private VistaMediosPago vistaMediosPago;
    private CuerpoRespuestaDatafono cuerpoRespuestaDatafono;
    private RespuestaCompletaTef respuestaCompletaTef;

    public VistaMsjCustomDosAccionesDialogFragment(int imagen, String titulo, String mensaje, String mensajeDev,
                                                   String textoBotonPositivo, String textoBotonNegativo, int accion,
                                                   boolean permitirCierreSuperior) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.mensajeDev = mensajeDev;
        this.textoBotonPositivo = textoBotonPositivo;
        this.textoBotonNegativo = textoBotonNegativo;
        this.accion = accion;
        this.permitirCierreSuperior = permitirCierreSuperior;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_msj_custom_two, null);

        log("POP-UP Dos Acciones", "Abriendo POP-UP");

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
        ivMsjCustomTwo = view.findViewById(R.id.ivMsjCustomTwo);
        tvTituloMsjCustomTwo = view.findViewById(R.id.tvTituloMsjCustomTwo);
        tvTextoMsjCustomTwo = view.findViewById(R.id.tvTextoMsjCustomTwo);
        btnPositivoTwo = view.findViewById(R.id.btnPositivoTwo);
        btnNegativoTwo = view.findViewById(R.id.btnNegativoTwo);
        ivCloseCustomTwo = view.findViewById(R.id.ivCloseCustomTwo);

        if(imagen == 0){
            ivMsjCustomTwo.setVisibility(View.GONE);
        }else{
            ivMsjCustomTwo.setImageResource(imagen);
        }

        if(permitirCierreSuperior){
            ivCloseCustomTwo.setVisibility(View.VISIBLE);
        }

        tvTituloMsjCustomTwo.setText(titulo);
        tvTextoMsjCustomTwo.setText(mensaje);
        btnPositivoTwo.setText(textoBotonPositivo);
        btnNegativoTwo.setText(textoBotonNegativo);
    }

    private void eventos() {
        ivMsjCustomTwo.setOnClickListener(this::msjDev);
        btnPositivoTwo.setOnClickListener(this::accionPositiva);
        btnNegativoTwo.setOnClickListener(this::accionNegativa);
        ivCloseCustomTwo.setOnClickListener(this::cerrar);
    }

    public void cerrar(View view){
        dismiss();
    }

    public void accionPositiva(View view){
        switch (accion){
            case Constantes.ACCION_ANULAR_TEF:
                //Anular tef
                if(respuestaCompletaTef.getHeader().getAnulada()){
                    Utilidades.mjsToast(getResources().getString(R.string.la_transaccion_ya_anulada),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
                }else{
                    log("Anular Tef", "Anular");
                    vistaConsultaCliente.anularTransaccionTef(respuestaCompletaTef);
                }
                break;
            case Constantes.ACCION_REINICIAR_TODO:
                log("Reiniciar Todo", "Sí");
                dismiss();
                vistaCompraCliente.reiniciarTodo();
                break;
            case Constantes.ACCION_CUPO_EMPLEADO:
                log("Cupo Empleado", "Mostrar");
                vistaMediosPago.mostrarDescuentosMp();
                dismiss();
                break;
            case Constantes.ACCION_CLIENTE_CARACTER_ESPECIAL:
                log("Cliente Caracter Especial", "Continuar, cliente genérico");
                vistaConsultaCliente.vistaClienteDetalle(Utilidades.clienteGenerico(requireContext()));
                dismiss();
                break;
        }
    }

    public void accionNegativa(View view){
        switch (accion){
            case Constantes.ACCION_ANULAR_TEF:
                //Mostrar info tef
                log("Anular Tef", "Mostrar Info Tef");
                vistaConsultaCliente.mostrarInfoTef(cuerpoRespuestaDatafono);
                break;
            case Constantes.ACCION_REINICIAR_TODO:
                log("Reiniciar Todo", "No");
                dismiss();
                break;
            case Constantes.ACCION_CUPO_EMPLEADO:
                log("Cupo Empleado", "Regresar");
                vistaMediosPago.regresarConsultaCliente();
                dismiss();
            case Constantes.ACCION_CLIENTE_CARACTER_ESPECIAL:
                log("Cliente Caracter Especial", "Entiendo");
                dismiss();
                break;
        }
    }

    private void msjDev(View view){
        if(!mensajeDev.isEmpty()){
            Utilidades.sweetAlert(getResources().getString(R.string.error), mensajeDev,
                    SweetAlertDialog.ERROR_TYPE, requireContext());
        }
    }

    public void setVistaConsultaCliente(VistaConsultaCliente vistaConsultaCliente) {
        this.vistaConsultaCliente = vistaConsultaCliente;
    }

    public void setCuerpoRespuestaDatafono(CuerpoRespuestaDatafono cuerpoRespuestaDatafono) {
        this.cuerpoRespuestaDatafono = cuerpoRespuestaDatafono;
    }

    public void setRespuestaCompletaTef(RespuestaCompletaTef respuestaCompletaTef) {
        this.respuestaCompletaTef = respuestaCompletaTef;
    }

    public void setVistaCompraCliente(VistaCompraCliente vistaCompraCliente) {
        this.vistaCompraCliente = vistaCompraCliente;
    }

    public void setVistaMediosPago(VistaMediosPago vistaMediosPago) {
        this.vistaMediosPago = vistaMediosPago;
    }

    private void log(String titulo, String opcion){
        LogFile.adjuntarLog("POP-UP [VistaMsjCustomDosAccionesDialogFragment] - "+titulo, "Acción: "+ opcion);
    }
}