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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleMedioPagoRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VistaDescuentosClienteDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ListView lvDescuentosCliente;
    private boolean isEmpleado;
    private boolean isReferido;
    private TextInputLayout tliCuponReferido;
    private Button btnValidarCupon;
    private EditText etCuponReferido;
    private IVistaMediosPago.Presentador presentador;
    private IVistaMediosPago.Vista vista;
    private Cliente cliente;
    private ImageView ivCloseDescuentos;
    private TextView tvTituloDescuentosCliente, tvTextoDescuentosCliente;
    private boolean habilitarAtras;

    //Declaración de la variables del DialogFragment
    private List<Descuento> descuentoList;
    private final List<String> palabraClavesList = new ArrayList<String>();

    public VistaDescuentosClienteDialogFragment(List<Descuento> list, Cliente cliente, boolean isReferido,
                                                IVistaMediosPago.Presentador presentador, IVistaMediosPago.Vista vista,
                                                boolean habilitarAtras) {
        this.descuentoList = list;
        this.isReferido = isReferido;
        this.presentador = presentador;
        this.vista = vista;
        this.cliente = cliente;
        this.habilitarAtras = habilitarAtras;

        isEmpleado = cliente.isEmpleado();
    }

    public interface OnInputListener{
        void sendInputListDescuentoTipoDialogFragment(Descuento descuento, boolean referido);
    }

    public VistaDescuentosClienteDialogFragment.OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_descuentos_cliente, null);

        log("Abriendo POP-UP");

        inicializar();

        eventos();

        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void inicializar() {
        lvDescuentosCliente = view.findViewById(R.id.lvDescuentosCliente);
        tliCuponReferido = view.findViewById(R.id.tliCuponReferido);
        btnValidarCupon = view.findViewById(R.id.btnValidarCupon);
        etCuponReferido = view.findViewById(R.id.etCuponReferido);
        ivCloseDescuentos = view.findViewById(R.id.ivCloseDescuentos);
        tvTituloDescuentosCliente = view.findViewById(R.id.tvTituloDescuentosCliente);
        tvTextoDescuentosCliente = view.findViewById(R.id.tvTextoDescuentosCliente);

        llenarVista();

        for(Descuento descuento : descuentoList){
            palabraClavesList.add(descuento.getMensaje());
        }

        LogFile.adjuntarLog("PASO 3: POP-UP [VistaDescuentosClienteDialogFragment] - Descuentos/Medio de pago [Referido: "+isReferido+"]",
                palabraClavesList);

        //Adaptador con la lista de descuento
        if(!isEmpleado && !isReferido){
            tliCuponReferido.setVisibility(View.VISIBLE);
            btnValidarCupon.setVisibility(View.VISIBLE);

            ListaSimpleMedioPagoRecyclerViewAdapter adapter =
                    new ListaSimpleMedioPagoRecyclerViewAdapter(requireContext(), palabraClavesList);
            lvDescuentosCliente.setAdapter(adapter);
        }else{
            ListaSimpleRecyclerViewAdapter adapterPalabras =
                    new ListaSimpleRecyclerViewAdapter(requireContext(),palabraClavesList, true);
            lvDescuentosCliente.setAdapter(adapterPalabras);
        }
    }

    private void llenarVista() {
        if(isEmpleado){
            tvTituloDescuentosCliente.setText(R.string.descuentos_medio_de_pago);
            tvTextoDescuentosCliente.setText(R.string.select_descuento_medio_de_pago_compra);
        }else if(isReferido){
            tvTituloDescuentosCliente.setText(R.string.medio_de_pago_referido);
            tvTextoDescuentosCliente.setText(R.string.select_medio_de_pago_compra_referido);
        }else{
            tvTituloDescuentosCliente.setText(R.string.medio_de_pago);
            tvTextoDescuentosCliente.setText(R.string.select_medio_de_pago_compra);
        }
    }

    private void eventos() {
        //Seleccion del descuento
        lvDescuentosCliente.setOnItemClickListener((parent, view, position, id) -> {
            LogFile.adjuntarLog("PASO 3: POP-UP [VistaDescuentosClienteDialogFragment] - Descuentos/Medio de pago seleccionado [Referido: "+isReferido+"]",
                    descuentoList.get(position));
            if(Objects.requireNonNull(SPM.getString(Constantes.PARAM_MEDIOS_PAGOS_CREDITO))
                    .contains(descuentoList.get(position).getCodigoMP())){
                if(cliente.isOtpVerificado()){
                    mOnInputListener.sendInputListDescuentoTipoDialogFragment(descuentoList.get(position), false);
                    Objects.requireNonNull(getDialog()).dismiss();
                }else{
                    if(presentador.estadoProgress()){
                        presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                                false);
                        presentador.generarCodigoOTP(cliente, descuentoList.get(position));
                    }
                }
            }else{
                if(isReferido){
                    mOnInputListener.sendInputListDescuentoTipoDialogFragment(descuentoList.get(position), true);
                    Objects.requireNonNull(getDialog()).dismiss();
                }else{
                    mOnInputListener.sendInputListDescuentoTipoDialogFragment(descuentoList.get(position), false);
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });

        btnValidarCupon.setOnClickListener(this::validarCupon);
        ivCloseDescuentos.setOnClickListener(this::cerrar);
    }

    private void validarCupon(View view){
        String codigo = etCuponReferido.getText().toString().trim();
        etCuponReferido.setText("");
        if(!codigo.isEmpty()){
            presentador.dialogProgressBar(getResources().getString(R.string.dialog_progress_validar_cupon), false);
            presentador.validacionCuponReferido(codigo);
        }else{
            Utilidades.mjsToast(getResources().getString(R.string.inserte_codigo_cupon), Constantes.TOAST_TYPE_INFO,
                    Toast.LENGTH_LONG, getContext());
            etCuponReferido.setText("");
            etCuponReferido.requestFocus();
        }
    }

    private void cerrar(View view){
        log("Cerrar");
        if(habilitarAtras){
            mOnInputListener.sendInputListDescuentoTipoDialogFragment(null, false);
            dismiss();
        }else{
            dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (VistaDescuentosClienteDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: DescuentoTipoClienteDialogFragment: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaDescuentosClienteDialogFragment] - Descuentos/Medio de pago", "Selecciono: "+ opcion);
    }
}
