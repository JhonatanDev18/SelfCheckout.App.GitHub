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
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;

public class VistaClienteConsultaDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private Cliente cliente;
    private EditText etCedula, etConsultaCedula, etConsultaNombre, etConsultaCelular, etConsultaCorreo;
    private IVistaConsultaCliente.Vista vista;
    private Utilidades util;
    private Button btnSiSoyYo, btnDatosIncorrectos;
    private ImageView ivCloseConsultaCliente;

    public VistaClienteConsultaDialogFragment(Cliente cliente, IVistaConsultaCliente.Vista vista ) {
        this.cliente = cliente;
        this.vista = vista;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_framgent_consulta_cliente, null);

        log("Abriendo POP-UP");

        //Asignacion de Referencias
        inicializar();

        //Mostrar datos consultados
        mostrarDatos();

        //Eventos
        eventos();

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void inicializar() {
        etCedula = requireActivity().findViewById(R.id.etCedula);
        util = new Utilidades(requireContext());

        etConsultaCedula = view.findViewById(R.id.etConsultaCedula);
        etConsultaNombre = view.findViewById(R.id.etConsultaNombre);
        etConsultaCelular = view.findViewById(R.id.etConsultaCelular);
        etConsultaCorreo = view.findViewById(R.id.etConsultaCorreo);

        ivCloseConsultaCliente = view.findViewById(R.id.ivCloseConsultaCliente);
        btnSiSoyYo = view.findViewById(R.id.btnSiSoyYo);
        btnDatosIncorrectos = view.findViewById(R.id.btnDatosIncorrectos);
    }

    private void eventos() {
        ivCloseConsultaCliente.setOnClickListener(this::regresar);
        btnSiSoyYo.setOnClickListener(this::continuar);
        btnDatosIncorrectos.setOnClickListener(this::regresar);
    }

    private void continuar(View view){
        log("Sí, soy yo");
        vista.vistaClienteDetalle(cliente);
        dismiss();
    }

    private void regresar(View view){
        log("Cerrar");
        etCedula.setText("");
        etCedula.requestFocus();
        dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void mostrarDatos() {
        etConsultaCedula.setText(cliente.getCedula(true));
        etConsultaNombre.setText(cliente.getNombre() + " " + cliente.getApellido());
        etConsultaCelular.setText(cliente.celularIncognito());
        etConsultaCorreo.setText(cliente.correoIncognito());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        util.vozAndroid(requireActivity().getResources().getString(R.string.voz_confirmacion_de_datos_cliente));
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaClienteConsultaDialogFragment] - Verificación de datos", "Selecciono: "+ opcion);
    }
}