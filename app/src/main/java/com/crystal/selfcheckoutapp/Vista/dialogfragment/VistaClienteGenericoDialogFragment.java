package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaClienteGenericoDialogFragment extends DialogFragment {

    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private Cliente cliente;
    private IVistaConsultaCliente.Vista vista;
    private EditText etCedula;
    private ImageView ivCloseConsultaClienteG;
    private Button btnSiContinuar, btnNoContinuar;
    private Utilidades util;

    public VistaClienteGenericoDialogFragment(Cliente cliente, IVistaConsultaCliente.Vista vista) {
        this.cliente = cliente;
        this.vista = vista;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_cliente_generico, null);

        log("Abriendo POP-UP");

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
        etCedula = requireActivity().findViewById(R.id.etCedula);
        ivCloseConsultaClienteG = view.findViewById(R.id.ivCloseConsultaClienteG);
        btnNoContinuar = view.findViewById(R.id.btnNoContinuar);
        btnSiContinuar = view.findViewById(R.id.btnSiContinuar);
        util = new Utilidades(requireContext());
    }

    private void eventos() {
        ivCloseConsultaClienteG.setOnClickListener(this::regresar);
        btnNoContinuar.setOnClickListener(this::regresar);
        btnSiContinuar.setOnClickListener(this::continuar);
    }

    private void continuar(View view){
        log("Sí, continuar");
        vista.vistaClienteDetalle(cliente);
        dismiss();
    }

    private void regresar(View view){
        log("No");
        etCedula.setText("");
        etCedula.requestFocus();
        VistaMsjCustomUnaAccionDialogFragment msjCustom =
                new VistaMsjCustomUnaAccionDialogFragment(
                   R.drawable.informacion_cupo,
                        getResources().getString(R.string.informacion),
                        getResources().getString(R.string.info_usar_app),
                        "",
                        getResources().getString(R.string.entiendo),
                        Constantes.ACCION_DEFAULT
                );
        msjCustom.show(getParentFragmentManager(), "MsjCustomDialogFragment");
        dismiss();
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaClienteGenericoDialogFragment] - Continuar sin datos", "Acción: "+ opcion);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        util.vozAndroid(requireActivity().getResources().getString(R.string.voz_cliente_generico));
    }
}
