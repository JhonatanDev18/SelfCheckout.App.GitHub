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
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.VistaConfiguracion;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VistaConfigurarPerifericosDialogFragment extends DialogFragment{
    private View view;
    private ImageView ivClosePerifericos;
    private Button btnExportarPerifericos;
    private EditText etTiendaPerifericos, etCajaPerifericos;
    private IVistaConfiguracion.Presentador presentador;
    private ImageView ivEditarTiendaPerifericos, ivEditarCajaPerifericos;
    private List<String> tiendasSelect;
    private List<Caja> cajasSelect;
    private VistaConfiguracion vista;
    
    public VistaConfigurarPerifericosDialogFragment(IVistaConfiguracion.Presentador presentador,
                                                    VistaConfiguracion vista) {
       this.presentador = presentador;
       this.vista = vista;
    }

    @SuppressLint("InflateParams")
    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_tienda_caja_perifericos, null);

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
        ivClosePerifericos = view.findViewById(R.id.ivClosePerifericos);
        btnExportarPerifericos = view.findViewById(R.id.btnExportarPerifericos);

        etTiendaPerifericos = view.findViewById(R.id.etTiendaPerifericos);
        etCajaPerifericos = view.findViewById(R.id.etCajaPerifericos);

        ivEditarTiendaPerifericos = view.findViewById(R.id.ivEditarTiendaPerifericos);
        ivEditarCajaPerifericos = view.findViewById(R.id.ivEditarCajaPerifericos);

        if(etTiendaPerifericos.getText().toString().isEmpty()){
            ivEditarCajaPerifericos.setVisibility(View.GONE);
        }
    }

    private void eventos() {
        btnExportarPerifericos.setOnClickListener(this::exportarPerifericos);
        ivClosePerifericos.setOnClickListener(this::cerrar);

        ivEditarTiendaPerifericos.setOnClickListener(this::seleccionarTienda);
        ivEditarCajaPerifericos.setOnClickListener(this::seleccionarCaja);
    }

    private void seleccionarTienda(View view){
        log("Seleccionar tienda");
        if(tiendasSelect != null){
            vista.abrirTiendasSelect(tiendasSelect);
        }else{
            presentador.consultarTiendas();
        }
    }

    private void seleccionarCaja(View view){
        log("Seleccionar caja");
        if(cajasSelect != null){
            List<Caja> cajas = new ArrayList<>();
            String tiendaDefecto = etTiendaPerifericos.getText().toString();

            if(!cajasSelect.isEmpty()){
                for(Caja caja: cajasSelect){
                    if(tiendaDefecto.equals(caja.getCodigoTienda())){
                        cajas.add(caja);
                    }
                }

                if(!cajas.isEmpty()){
                    vista.abrirCajasSelect(cajas);
                }else{
                    presentador.errorServicio(requireContext().getResources().getString(R.string.error),
                            String.format(requireContext().getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                            Constantes.SERVICIO_CAJAS);
                }
            }else {
                presentador.errorServicio(requireContext().getResources().getString(R.string.error),
                        String.format(requireContext().getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                        Constantes.SERVICIO_CAJAS);
            }
        }else{
            presentador.consultarCajas(etTiendaPerifericos.getText().toString());
            presentador.consultarParametrosQRBancolombia(etTiendaPerifericos.getText().toString());
        }
    }

    private void exportarPerifericos(View view){
        String tienda = etTiendaPerifericos.getText().toString();
        String caja = etCajaPerifericos.getText().toString();

        if(!tienda.isEmpty()){
            if(!caja.isEmpty()){
                log("Exportando Perifericos \nTienda: "+ tienda +
                        "\nCaja: "+ caja);
                presentador.configurarPerifericos(tienda, caja);
            }else{
                Utilidades.mjsToast("Seleccione la caja", Constantes.TOAST_TYPE_INFO,
                        Toast.LENGTH_LONG, getContext());
            }
        }else{
            Utilidades.mjsToast("Seleccione la tienda", Constantes.TOAST_TYPE_INFO,
                    Toast.LENGTH_LONG, getContext());
        }
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    public void insertarCaja(String caja){
        etCajaPerifericos.setText(caja);
    }

    public void insertarTienda(String tienda){
        etTiendaPerifericos.setText(tienda);
        ivEditarCajaPerifericos.setVisibility(View.VISIBLE);
        etCajaPerifericos.setText("");
    }

    public void setTiendasSelect(List<String> tiendasSelect) {
        this.tiendasSelect = tiendasSelect;
    }

    public void setCajasSelect(List<Caja> cajasSelect) {
        this.cajasSelect = cajasSelect;
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaConfigurarPerifericosDialogFragment] - Exportar Perifericos", "Acción: "+ opcion);
    }
}