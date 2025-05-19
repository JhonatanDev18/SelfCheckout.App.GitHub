package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.clases.Buscador;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class VistaCajaSelectDialogFragment extends DialogFragment {

    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ProgressBar pb;
    private ListView lvTiendas;
    private EditText etBuscadorTiendas;
    private TextView tvTituloConsultaCliente;
    private ImageView ivCloseCaja;
    private Utilidades util;
    private String vozSeleccione;

    //Declaración de las variables del DialogFragment
    List<Caja> listaCajas;
    List<String> cajaList = new ArrayList<>();
    List<Caja> filtroList = new ArrayList<>();
    List<String> listAuxBuscador = new ArrayList<>();
    List<String> listAuxClickBuscador = new ArrayList<>();
    List<Buscador> listAuxFiltroBuscador = new ArrayList<>();
    List<Caja> listAuxFiltro = new ArrayList<>();
    List<Caja> listAuxClickFiltroBuscador = new ArrayList<>();

    public VistaCajaSelectDialogFragment(List<Caja> listaCajas, Context contexto){
        this.listaCajas = listaCajas;
        util = new Utilidades(contexto);
        vozSeleccione = String.format(contexto.getResources().getString(R.string.voz_seleccione_caja),
                listaCajas.get(0).getNombreTienda().split("-")[1]);
    }

    public interface OnInputListener{
        void sendInputItemCajaSelectDialogFragment(Caja caja);
    }

    public OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_caja_select, null);

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);

        return builder.create();
    }

    private void inicializar() {
        tvTituloConsultaCliente = view.findViewById(R.id.tvTituloConsultaCliente);
        lvTiendas = view.findViewById(R.id.lvTiendasSelect);
        pb = view.findViewById(R.id.pbTiendasSelect);
        pb.setVisibility(View.GONE);
        etBuscadorTiendas = view.findViewById(R.id.etBuscadorTiendas);
        ivCloseCaja = view.findViewById(R.id.ivCloseCaja);
    }

    private void cargarDatos() {
        tvTituloConsultaCliente.setText(String.format(requireContext().getResources().getString(R.string.cajas),
                listaCajas.get(0).getNombreTienda()));

        for(Caja str : listaCajas){
            cajaList.add(str.getCodigoCaja());
            filtroList.add(str);
        }

        Set<String> set = new HashSet<>(cajaList);
        cajaList.clear();
        cajaList.addAll(set);
        Collections.sort(cajaList);

        ListaSimpleRecyclerViewAdapter arrayAdapterTiendas =
                new ListaSimpleRecyclerViewAdapter(requireContext(),cajaList, false);
        lvTiendas.setAdapter(arrayAdapterTiendas);
    }

    private void eventos() {
        lvTiendas.setOnItemClickListener((parent, view, position, id) -> {
            if(!listAuxBuscador.isEmpty()){
                listAuxClickBuscador = listAuxBuscador;
                listAuxClickFiltroBuscador = listAuxFiltro;
            }else{
                listAuxClickBuscador = cajaList;
                listAuxClickFiltroBuscador = filtroList;
            }

            //Guardar en memoria imformación de la tienda seleccionada
            guardarCaja(listAuxClickFiltroBuscador.get(position));

            LogFile.adjuntarLog("POP-UP [VistaCajaSelectDialogFragment] - Caja seleccionada", listAuxClickFiltroBuscador.get(position));

            mOnInputListener.sendInputItemCajaSelectDialogFragment(listAuxClickFiltroBuscador.get(position));
            Objects.requireNonNull(getDialog()).dismiss();
        });

        etBuscadorTiendas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cajaList != null){
                    String search = etBuscadorTiendas.getText().toString();
                    search = search.toUpperCase();
                    listAuxFiltroBuscador.clear();
                    listAuxBuscador.clear();
                    listAuxFiltro.clear();

                    if(!search.isEmpty()){
                        for(int i=0; i< cajaList.size(); i++) {
                            String buscar = cajaList.get(i);
                            Buscador buscador;
                            if (buscar.contains(search)) {
                                listAuxBuscador.add(buscar);
                                buscador = new Buscador(buscar, i);
                                listAuxFiltroBuscador.add(buscador);
                            }
                        }

                        for (Buscador dato: listAuxFiltroBuscador) {
                            for(Caja caja : filtroList){
                                if(caja.getCodigoCaja().equals(dato.getDato())){
                                    listAuxFiltro.add(caja);
                                }
                            }
                        }

                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),listAuxBuscador, false);
                        lvTiendas.setAdapter(arrayAdapterCajaTienda);
                    }else{
                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),cajaList, false);
                        lvTiendas.setAdapter(arrayAdapterCajaTienda);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivCloseCaja.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: TiendaSelectDialogFragment: " + e.getMessage() );
        }
    }

    private void guardarCaja(Caja caja){
        SPM.setString(Constantes.CAJA_MEDIO_PAGO_AUTORIZADO, caja.getMediosPagoAutorizados());
        SPM.setString(Constantes.CAJA_CODIGO, caja.getCodigoCaja());
        SPM.setString(Constantes.CAJA_CODIGO_TIENDA, caja.getCodigoTienda());
        SPM.setString(Constantes.CAJA_MOVIL, caja.getCajaMovil());
        SPM.setString(Constantes.CAJA_PREFIJO, caja.getPrefijoCaja());
        SPM.setString(Constantes.CAJA_DIVISA, caja.getDivisa());
        SPM.setString(Constantes.CAJA_PAIS, caja.getPais());
        SPM.setString(Constantes.CAJA_IDENTIFICADOR, caja.getIdentificadorCaja());
        SPM.setString(Constantes.CAJA_NOMBRE_TIENDA, caja.getNombreTienda());
    }

    @Override
    public void onResume() {
        super.onResume();
        util.vozAndroid(vozSeleccione);
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaCajaSelectDialogFragment] - Seleccionar Caja", "Acción: "+ opcion);
    }
}