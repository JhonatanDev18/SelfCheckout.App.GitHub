package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;

import java.util.Objects;

public class VistaCodigoVerificacionDialogFragment extends DialogFragment {

    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private EditText etCodigoOTP;
    private Button btnVerificarCodigoOTP, btnAtrasOTP, btnReenviarOTP;
    private Context contexto;
    private Utilidades util;
    private Cliente cliente;
    private Descuento descuento;
    private ImageView ivCloseVerificarCodigo;
    private IVistaMediosPago.Presentador presentador;
    private TextView tvTextoVerificarCodigo;

    public VistaCodigoVerificacionDialogFragment(Cliente cliente, Descuento descuento, IVistaMediosPago.Presentador presentador) {
        this.cliente = cliente;
        this.descuento = descuento;
        this.presentador = presentador;
    }

    public interface OnInputListener{
        void sendInputVerificacionCodigoDialogFragment(Descuento descuento);
    }

    public VistaCodigoVerificacionDialogFragment.OnInputListener mOnInputListener;

    @SuppressLint({"InflateParams", "StringFormatMatches"})
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_verificar_codigo, null);

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
        contexto = requireContext();
        etCodigoOTP = view.findViewById(R.id.etCodigoOTP);
        tvTextoVerificarCodigo = view.findViewById(R.id.tvTextoVerificarCodigo);
        tvTextoVerificarCodigo.setText(String.format(getResources().getString(R.string.texto_dialog_verificacion),
                cliente.celularIncognito(), cliente.correoIncognito()));

        btnVerificarCodigoOTP = view.findViewById(R.id.btnVerificarCodigoOTP);
        btnAtrasOTP = view.findViewById(R.id.btnAtrasOTP);
        btnReenviarOTP = view.findViewById(R.id.btnReenviarOTP);
        ivCloseVerificarCodigo = view.findViewById(R.id.ivCloseVerificarCodigo);
        util = new Utilidades(requireContext());
    }

    private void eventos() {
        btnVerificarCodigoOTP.setOnClickListener(this::verificarCodigoOtp);
        btnAtrasOTP.setOnClickListener(this::atrasOtp);
        btnReenviarOTP.setOnClickListener(this::reenviarCodigoOtp);
        ivCloseVerificarCodigo.setOnClickListener(this::atrasOtp);
    }

    private void verificarCodigoOtp(View view){
        String codigo = etCodigoOTP.getText().toString().trim();
        log("Verificar código \nCódigo ingresado: "+ codigo);

        if(!codigo.isEmpty()){
            if(codigo.equals(cliente.getCodigoOTP())){
                cliente.setOtpVerificado(true);
                mOnInputListener.sendInputVerificacionCodigoDialogFragment(descuento);
                dismiss();
            }else{
                Utilidades.mjsToast(getResources().getString(R.string.codigo_verificacion_incorrecto),
                        Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
                log("El código ingresado es incorrecto");
            }
        }else{
            log("Ingrese el código de verificación");
            Utilidades.mjsToast(getResources().getString(R.string.introduce_codigo_verificacion),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }
    }

    private void atrasOtp(View view){
        log("Atras");
        final AlertDialog d = (AlertDialog)getDialog();
        assert d != null;
        d.dismiss();
    }

    private void reenviarCodigoOtp(View view){
        log("Reenviar Código");
        presentador.dialogProgressBar(requireContext().getResources().getString(R.string.progress_reenviando), false);
        presentador.reenviarCodigoOTP(cliente);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (VistaCodigoVerificacionDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: DescuentoTipoClienteDialogFragment: " + e.getMessage() );
        }
    }

    public void insertarNuevoCodigo(String nuevoCodigo){
        cliente.setCodigoOTP(nuevoCodigo);
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        assert d != null;
        util.vozAndroid(requireActivity().getResources().getString(R.string.voz_codigo_verificacion));
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaCodigoVerificacionDialogFragment] - Código Verificación", "Acción: "+ opcion);
    }
}