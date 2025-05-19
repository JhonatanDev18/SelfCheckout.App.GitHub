package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import androidx.fragment.app.DialogFragment;

public class VistaCambiarUrlDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private EditText etUrlIn, etUrlNn;
    private ImageView ivCloseCambiarUrl;
    private Button btnConfirmarUrls, btnAtrasUrls;

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        log("Abriendo POP-UP");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_cambiar_url, null);

        //Asignacion de Referencias
        etUrlIn = view.findViewById(R.id.etCURLDF);
        etUrlIn.setText(SPM.getString(Constantes.API_POSSERVICE_URL));

        etUrlNn = view.findViewById(R.id.etCURLDFNN);
        etUrlNn.setText(SPM.getString(Constantes.API_POSSERVICE_URL_NN));

        ivCloseCambiarUrl = view.findViewById(R.id.ivCloseCambiarUrl);
        ivCloseCambiarUrl.setOnClickListener(this::cerrar);

        btnConfirmarUrls = view.findViewById(R.id.btnConfirmarUrls);
        btnConfirmarUrls.setOnClickListener(this::confirmar);

        btnAtrasUrls = view.findViewById(R.id.btnAtrasUrls);
        btnAtrasUrls.setOnClickListener(this::cerrar);

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    private void confirmar(View view){
        String nuevaUrl = etUrlIn.getText().toString();
        String nuevaUrlNn = etUrlNn.getText().toString();
        //Validar que sea una URL
        if(URLUtil.isValidUrl(nuevaUrl)){
            //Guardar variable de sesion de la nueva URL Base de los servicios
            SPM.setString(Constantes.API_POSSERVICE_URL, nuevaUrl);

            if(URLUtil.isValidUrl(nuevaUrlNn)){
                SPM.setString(Constantes.API_POSSERVICE_URL_NN, nuevaUrlNn);
                log("Confirmación \n"+
                        "URL Intermedia: " + nuevaUrl + "\n"+
                        "URL No Nativa: " + nuevaUrlNn);

                dismiss();
                Intent intent = requireActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                requireActivity().finish();
                startActivity(intent);
            }else{
                msjToast(getResources().getString(R.string.url_invalida_nn));
            }
        }
        else{
            msjToast(getResources().getString(R.string.url_invalida));
        }
    }

    private void msjToast(String msj) {
        Utilidades.mjsToast(msj, Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, requireContext());
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaCambiarUrlDialogFragment] - Cambiar URLs bases", "Acción: "+ opcion);
    }
}
