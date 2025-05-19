package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Seguridad;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestLogin;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.login.Login;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.login.ResponseLogin;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class VistaAutorizacionSimpleFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    View view;
    EditText etuser,etpassword;

    //Declaración de la variables del DialogFragment
    boolean consultar = true, scanUser;
    String usuarioAdministrador;
    private IVistaConsultaCliente.Vista vista;
    private Button btnAutorizarCierre, btnAtrasAutorizacionCierre;
    private ImageView ivCloseAutorizacion;
    private boolean isConfiguracion;
    private TextView tvTextoAutorizacion;
    private IVistaConsultaCliente.Presentador presentador;

    public VistaAutorizacionSimpleFragment(IVistaConsultaCliente.Vista vista,
            IVistaConsultaCliente.Presentador presentador, boolean isConfiguracion) {
        this.vista = vista;
        this.presentador = presentador;
        this.isConfiguracion = isConfiguracion;
    }

    public interface OnInputListener{
        void sendInputItemAutorizacionSimpleFrament(boolean isConfi);
    }
    public OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_autorizacion_simple, null);

        log("Abriendo POP-UP");

        inicializar();
        eventos();

        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void inicializar() {
        usuarioAdministrador = SPM.getString(Constantes.PARAM_GRUPO_CONFIGURADOR);
        etuser = view.findViewById(R.id.etUsuarioASDF);
        etpassword = view.findViewById(R.id.etPassASDF);
        btnAutorizarCierre = view.findViewById(R.id.btnAutorizarCierre);
        ivCloseAutorizacion = view.findViewById(R.id.ivCloseAutorizacion);
        btnAtrasAutorizacionCierre = view.findViewById(R.id.btnAtrasAutorizacionCierre);
        tvTextoAutorizacion = view.findViewById(R.id.tvTextoAutorizacion);

        if(isConfiguracion){
            tvTextoAutorizacion.setText(requireContext().getString(R.string.auto_configurar));
        }
    }

    private void eventos() {
        //Evento enter sobre password
        etpassword.setImeActionLabel(getResources().getString(R.string._ir), KeyEvent.KEYCODE_ENTER);
        etpassword.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                final AlertDialog d = (AlertDialog)getDialog();
                if(d != null){
                    btnAutorizarCierre.callOnClick();
                }
                return true;
            }
            return false;
        });

        etpassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                final AlertDialog d = (AlertDialog)getDialog();
                if(d != null){
                    btnAutorizarCierre.callOnClick();
                }
            }
            return false;
        });

        btnAutorizarCierre.setOnClickListener(this::autorizarCierre);
        ivCloseAutorizacion.setOnClickListener(this::cerrar);
        btnAtrasAutorizacionCierre.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    private void autorizarCierre(View view){
        if(consultar){
            consultar = false;
            String usuario = etuser.getText().toString();
            String pass = etpassword.getText().toString();

            if(usuario.isEmpty()){
                Utilidades.mjsToast(getResources().getString(R.string.usurio_requerido),
                        Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
                consultar = true;
            }else if(pass.isEmpty()){
                Utilidades.mjsToast(getResources().getString(R.string.pass_requerido),
                        Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
                consultar = true;
            }else{
                log("Autorizar: "+ usuario);
                consultarApiAutorizacion(usuario, pass);
            }
        }
    }

    private void consultarApiAutorizacion(String usuario, String pass) {
        presentador.dialogProgressBar( getResources().getString(R.string.consultando_autorizacion),false);
        //Consultar la Api de login
        RequestLogin requestLogin = new RequestLogin(new Seguridad(usuario, pass));
        Call<ResponseLogin> loginCall = Utilidades.servicioRetrofit(Constantes.API_NN).doLogin(requestLogin);

        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NotNull Call<ResponseLogin> call, @NotNull Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        Login login = response.body().getLogin();
                        validarGrupoAutorizacion(login.getGrupo());
                    }else{
                        consultar=true;
                        presentador.ocultarDialogProgressBar();
                        Utilidades.mjsToast(response.body().getMensaje(),
                                Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
                    }
                }else{
                    consultar=true;
                    presentador.ocultarDialogProgressBar();
                    Utilidades.mjsToast(getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseLogin> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiLogin", t);
                consultar=true;
                presentador.ocultarDialogProgressBar();
                Utilidades.mjsToast(getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
            }
        });
    }

    private void validarGrupoAutorizacion(String grupo){
        if(usuarioAdministrador.contains(grupo)){
            mOnInputListener.sendInputItemAutorizacionSimpleFrament(isConfiguracion);
            dismiss();
        }else{
            consultar=true;
            etuser.setText("");
            etpassword.setText("");
            etuser.requestFocus();
            Utilidades.mjsToast(getResources().getString(R.string.tipo_usuario_invalido),
                    Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, getContext());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: AutorizacionSimpleFrament: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaAutorizacionSimpleFragment] - Autorización", "Acción: "+ opcion);
    }
}