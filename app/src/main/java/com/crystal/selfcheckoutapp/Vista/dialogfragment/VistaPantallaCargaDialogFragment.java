package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaDatafono;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaMediosPago;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

public class VistaPantallaCargaDialogFragment extends DialogFragment {
    private String mensaje;
    private LottieAnimationView lottieAnimationCarga;
    private boolean cancel, animacionDatafono;
    private TextView tvTextoCarga;
    private ImageView ivCerrarMsjCarga;
    private View view;
    private PresentadorVistaDatafono presentador;
    private PresentadorVistaMediosPago presentadorVistaMediosPago;

    public VistaPantallaCargaDialogFragment(String mensaje, boolean cancel) {
        this.mensaje = mensaje;
        this.cancel = cancel;
    }

    @SuppressLint("InflateParams")
    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_pantalla_carga, null);

        //Asignacion de Referencias
        inicializar();

        //Eventos
        eventos();

        setCancelable(false);
        builder.setView(view);
        SPM.setBoolean(Constantes.BOOL_ANIMACION_DATAFONO, false);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void inicializar() {
        animacionDatafono = SPM.getBoolean(Constantes.BOOL_ANIMACION_DATAFONO);

        tvTextoCarga = view.findViewById(R.id.tvTextoCarga);
        tvTextoCarga.setText(mensaje);
        ivCerrarMsjCarga = view.findViewById(R.id.ivCerrarMsjCarga);

        if(cancel){
            ivCerrarMsjCarga.setVisibility(View.VISIBLE);
        }

        lottieAnimationCarga = view.findViewById(R.id.lottieAnimationCarga);
        if(animacionDatafono){
            lottieAnimationCarga.setAnimation(getResources().getString(R.string.animacion_datafono));
        }else{
            lottieAnimationCarga.setAnimation(getResources().getString(R.string.animacion_splash_inicio2));
        }

        lottieAnimationCarga.playAnimation();
    }

    private void eventos(){
        ivCerrarMsjCarga.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        if(presentador != null){
            presentador.ocultarDialogProgressBar();
        }else if(presentadorVistaMediosPago != null){
            presentadorVistaMediosPago.ocultarDialogProgressBar();
        }else{
            dismiss();
        }
    }

    public void setPresentador(PresentadorVistaDatafono presentador) {
        this.presentador = presentador;
    }

    public void setPresentadorVistaMediosPago(PresentadorVistaMediosPago presentadorVistaMediosPago) {
        this.presentadorVistaMediosPago = presentadorVistaMediosPago;
    }
}