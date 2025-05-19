package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.VistaConsultaCliente;

public class VistaCajonDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private Button btnEncenderRFID;
    private IVistaCompraCliente.Vista vista;
    private LottieAnimationView lottieAnimationInicio2, lottieAnimationInicio;
    private String mensaje;
    private TextView tvInfoCajon, tvLeerOtraCedula, tvInfoCajonGanchos;
    private ImageView ivCerrarCajon, ivCajon, ivCajon2;
    private Guideline glSeparadorImagenes;
    private String textoBoton;
    private int imagen;
    private boolean retirarArticulos;

    public VistaCajonDialogFragment(IVistaCompraCliente.Vista vista, String mensaje) {
        this.vista = vista;
        this.mensaje = mensaje;
        textoBoton = "";
        imagen = 0;
    }

    public VistaCajonDialogFragment(IVistaCompraCliente.Vista vista, String mensaje, String textoBoton,
                                    int imagen, boolean retirarArticulos) {
        this.vista = vista;
        this.mensaje = mensaje;
        this.textoBoton = textoBoton;
        this.imagen = imagen;
        this.retirarArticulos = retirarArticulos;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_cajon, null);

        log("Abriendo POP-UP");

        inicializar();
        eventos();

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void inicializar() {
        btnEncenderRFID = view.findViewById(R.id.btnEncenderRFID);
        tvInfoCajon = view.findViewById(R.id.tvInfoCajon);
        ivCerrarCajon = view.findViewById(R.id.ivCerrarCajon);
        ivCajon = view.findViewById(R.id.ivCajon);
        ivCajon2 = view.findViewById(R.id.ivCajon2);
        tvLeerOtraCedula = view.findViewById(R.id.tvLeerOtraCedula);
        glSeparadorImagenes = view.findViewById(R.id.glSeparadorImagenes);
        lottieAnimationInicio2 = view.findViewById(R.id.lottieAnimationInicio2);
        lottieAnimationInicio = view.findViewById(R.id.lottieAnimationInicio);
        tvInfoCajonGanchos = view.findViewById(R.id.tvInfoCajonGanchos);

        if(mensaje != null){
            tvInfoCajon.setText(mensaje);
            ivCerrarCajon.setVisibility(View.VISIBLE);
        }else{
            tvLeerOtraCedula.setVisibility(View.VISIBLE);
        }

        if(!textoBoton.isEmpty()){
            btnEncenderRFID.setText(textoBoton);
        }

        if(imagen!=0){
            ivCajon2.setImageResource(imagen);
        }

        if(retirarArticulos){
            ivCajon.setVisibility(View.GONE);
            tvInfoCajonGanchos.setVisibility(View.GONE);
            lottieAnimationInicio.setVisibility(View.GONE);
            lottieAnimationInicio2.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) glSeparadorImagenes.getLayoutParams();
            layoutParams.guidePercent = 0.0f;
            glSeparadorImagenes.setLayoutParams(layoutParams);
        }
    }

    private void eventos() {
        btnEncenderRFID.setOnClickListener(this::encenderRfid);
        ivCerrarCajon.setOnClickListener(this::cerrarCajon);
        tvLeerOtraCedula.setOnClickListener(this::regresarConsulta);
    }

    private void regresarConsulta(View view){
        log("Ingresar otra cédula");
        Intent intent = new Intent(requireContext(), VistaConsultaCliente.class);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }

    private void cerrarCajon(View view) {
        log("Cerrar");
        dismiss();
        vista.cerrarCajon();
    }

    private void encenderRfid(View view) {
        log("Listo, artículos en el cajón");
        dismiss();
        vista.encerderRFID();
    }

    private void log(String opcion){
        if(mensaje != null){
            LogFile.adjuntarLog("POP-UP [VistaCajonDialogFragment] - Introducir nuevos artículos",
                    "Selecciono: "+ opcion);
        }else{
            LogFile.adjuntarLog("POP-UP [VistaCajonDialogFragment] - Introducir artículos",
                    "Selecciono: "+ opcion);
        }
    }
}