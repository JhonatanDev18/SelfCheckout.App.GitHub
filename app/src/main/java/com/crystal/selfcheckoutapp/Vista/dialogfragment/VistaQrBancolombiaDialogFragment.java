package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class VistaQrBancolombiaDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ImageView ivQrBancolombia, ivCloseQrBancolombia;
    private Button btnFinalizarQr;
    private String qr;
    private IVistaMediosPago.Vista vista;
    private IVistaMediosPago.Presentador presentador;
    private Utilidades util;

    public VistaQrBancolombiaDialogFragment(String qr, IVistaMediosPago.Vista vista,
                                            IVistaMediosPago.Presentador presentador) {
        this.qr = qr;
        this.vista = vista;
        this.presentador = presentador;
    }

    @SuppressLint("InflateParams")
    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_qr_bancolombia, null);

        log("Abriendo POP-UP");

        //Asignacion de Referencias
        inicializar();

        //Eventos
        eventos();

        //Mostrar Qr
        generarQr();

        setCancelable(false);
        builder.setView(view);
        //Cree el objeto AlertDialog y devuélvalo
        return builder.create();
    }

    private void inicializar() {
        ivQrBancolombia = view.findViewById(R.id.ivQrBancolombia);
        btnFinalizarQr = view.findViewById(R.id.btnFinalizarQr);
        ivCloseQrBancolombia = view.findViewById(R.id.ivCloseQrBancolombia);
        util = new Utilidades(requireContext());
    }

    private void eventos() {
        btnFinalizarQr.setOnClickListener(this::finalizar);
        ivCloseQrBancolombia.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    private void generarQr() {
        try {
            String imagenSvg = decodificarBase64(qr);
            SVG svg = SVG.getFromString(imagenSvg);
            svg.setDocumentHeight("100%");
            svg.setDocumentWidth("100%");

            //create a drawable from svg
            PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
            ivQrBancolombia.setImageDrawable(drawable);
            log("Imagen Pintada");
        }catch (SVGParseException e){
            LogFile.adjuntarLog("POP-UP [VistaQrBancolombiaDialogFragment] - QR Bancolombia ERROR SVG", e.fillInStackTrace());
            vista.errorServicio(requireContext().getResources().getString(R.string.error),
                    requireContext().getResources().getString(R.string.error_crear_qr),
                    Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA);
        }
    }

    public String decodificarBase64(String dato){
        byte[] datasd = Base64.decode(dato, Base64.DEFAULT);
        dato = new String(datasd, StandardCharsets.UTF_8);
        return dato;
    }

    private void finalizar(View view){
        log("Botón Finalizar");
        presentador.dialogProgressBar(requireContext().getResources().getString(R.string.progress_cargando),
                false);
        presentador.consultarPagoQrBancolombia();
    }

    @Override
    public void onResume() {
        super.onResume();
        util.vozAndroid(requireActivity().getResources().getString(R.string.voz_qr_bancolombia));
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaQrBancolombiaDialogFragment] - QR Bancolombia", "Acción: "+ opcion);
    }
}