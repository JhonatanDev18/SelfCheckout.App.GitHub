package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.KeyboardUtils;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;

public class VistaCantidadBolsasDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private EditText etCantidadBolsas;
    private ImageView ivRestarBolsa, ivSumarBolsa, ivCloseBolsas;
    private IVistaCompraCliente.Vista vista;
    private Button btnAtrasBolsa, btnContinuarBolsa;
    private int cantidad;
    private Utilidades util;
    private int limiteBolsas;
    private boolean primeraVez;

    public VistaCantidadBolsasDialogFragment(IVistaCompraCliente.Vista vista, int cantidad, boolean primeraVez) {
        this.vista = vista;
        this.cantidad = cantidad;
        this.primeraVez = primeraVez;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_cantidad_bolsas, null);

        log("Abriendo POP-UP");

        //Asignacion de Referencias
        inicializar();

        //Eventos
        eventos();

        Utilidades.ocultarBarraNavegacion(requireActivity().getWindow());

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void inicializar() {
        limiteBolsas = Integer.parseInt(SPM.getString(Constantes.PARAM_CANTIDAD_BOLSAS_FACTURABLES));
        etCantidadBolsas = view.findViewById(R.id.etCantidadBolsas);
        etCantidadBolsas.setText(Integer.toString(cantidad));
        ivRestarBolsa = view.findViewById(R.id.ivRestarBolsa);
        ivSumarBolsa = view.findViewById(R.id.ivSumarBolsa);

        btnAtrasBolsa = view.findViewById(R.id.btnAtrasBolsa);
        btnContinuarBolsa = view.findViewById(R.id.btnContinuarBolsa);
        ivCloseBolsas = view.findViewById(R.id.ivCloseBolsas);

        util = new Utilidades(requireContext());
        KeyboardUtils.hideKeyboard(requireActivity());
    }

    private void eventos(){
        ivRestarBolsa.setOnClickListener(this::restar);
        ivSumarBolsa.setOnClickListener(this::sumar);

        btnAtrasBolsa.setOnClickListener(this::atras);
        btnContinuarBolsa.setOnClickListener(this::agregar);
        ivCloseBolsas.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        applyClickAnimation(ivCloseBolsas);
        dismiss();
    }

    private void agregar(View view){
        final AlertDialog d = (AlertDialog)getDialog();
        assert d != null;
        vista.addBolsaProducto(Integer.parseInt(etCantidadBolsas.getText().toString()));
        log("Agregar bolsas, Cantidad: " + etCantidadBolsas.getText().toString());
        d.dismiss();
    }

    private void atras(View view){
        log("Atras");
        final AlertDialog d = (AlertDialog)getDialog();
        assert d != null;
        d.dismiss();
    }

    private void restar(View view){
        applyClickAnimation(ivRestarBolsa);
        String cantidad = etCantidadBolsas.getText().toString();
        if(ivSumarBolsa.getVisibility() == View.GONE){
            ivSumarBolsa.setVisibility(View.VISIBLE);
        }
        etCantidadBolsas.setText(Utilidades.bajarCantidad(cantidad));
        log("Restar bolsa, Cantidad: "+ etCantidadBolsas.getText().toString());
        cambiarBotonAgregar();
    }

    @SuppressLint("StringFormatInvalid")
    private void cambiarBotonAgregar() {
        String cantidad = etCantidadBolsas.getText().toString();
        if(cantidad.equals("0")){
            btnContinuarBolsa.setText(getResources().getString(R.string.btn_continuar_bolsas));
        }else{
            btnContinuarBolsa.setText(cantidad.equals("1")  ? String.format(getResources().getString(R.string.btn_continuar_bolsas_cantidad), cantidad)
                    : String.format(getResources().getString(R.string.btn_continuar_bolsas_cantidad_mas), cantidad));
        }
    }

    @SuppressLint("StringFormatMatches")
    private void sumar(View view){
        applyClickAnimation(ivSumarBolsa);
        String cantidad = etCantidadBolsas.getText().toString();
        String sumado = Utilidades.subirCantidad(cantidad);
        if(Integer.parseInt(sumado) > limiteBolsas){
            ivSumarBolsa.setVisibility(View.GONE);
            Utilidades.mjsToast(String.format(getResources().getString(R.string.limite_bolsas), limiteBolsas),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
        }else{
            etCantidadBolsas.setText(Utilidades.subirCantidad(cantidad));
        }
        log("Sumar bolsa, Cantidad: "+ etCantidadBolsas.getText().toString());
        cambiarBotonAgregar();
    }

    private void applyClickAnimation(View view) {
        AlphaAnimation clickAnimation = new AlphaAnimation(1.0f, 0.5f);
        clickAnimation.setDuration(100); // Duración de la animación en milisegundos
        view.startAnimation(clickAnimation);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(primeraVez){
            util.vozAndroid(requireActivity().getResources().getString(R.string.voz_bolsas));
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaCantidadBolsasDialogFragment] - Cantidad de bolsas", "Acción: "+ opcion);
    }
}