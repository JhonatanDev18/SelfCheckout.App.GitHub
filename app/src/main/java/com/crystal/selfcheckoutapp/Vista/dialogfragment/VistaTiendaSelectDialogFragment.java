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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class VistaTiendaSelectDialogFragment extends DialogFragment {
    private View view;
    private List<String> listaTiendas;
    private ListView lvTiendas;
    private ProgressBar pb;
    private List<String> tiendaList = new ArrayList<>();
    private List<String> listAuxBuscador = new ArrayList<>();
    private List<String> listAuxClickBuscador = new ArrayList<>();
    private Utilidades util;
    private TextView tvTituloConsultaCliente, tvTextoCaja;
    private ImageView ivCloseCaja;
    private TextInputLayout tliBuscadorTiendas;
    private EditText etBuscadorTiendas;

    public VistaTiendaSelectDialogFragment(List<String> listaTiendas, Context contexto){
        this.listaTiendas = listaTiendas;
        util = new Utilidades(contexto);
    }

    public interface OnInputListener{
        void sendInputItemTiendaSelectDialogFragment(String tienda);
    }

    public VistaTiendaSelectDialogFragment.OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_caja_select, null);

        log("Abriendo POP-UP");

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);

        return builder.create();
    }

    private void inicializar() {
        tliBuscadorTiendas = view.findViewById(R.id.tliBuscadorTiendas);
        ivCloseCaja = view.findViewById(R.id.ivCloseCaja);
        tvTituloConsultaCliente = view.findViewById(R.id.tvTituloConsultaCliente);
        lvTiendas = view.findViewById(R.id.lvTiendasSelect);
        pb = view.findViewById(R.id.pbTiendasSelect);
        pb.setVisibility(View.GONE);
        etBuscadorTiendas = view.findViewById(R.id.etBuscadorTiendas);
        tvTextoCaja = view.findViewById(R.id.tvTextoCaja);
    }

    @SuppressLint("SetTextI18n")
    private void cargarDatos() {
        tvTituloConsultaCliente.setText("Tiendas");
        tvTextoCaja.setText("Seleccione una tienda para los perifericos");
        tliBuscadorTiendas.setHint("Buscar tienda");

        tiendaList.addAll(listaTiendas);

        Set<String> set = new HashSet<>(tiendaList);
        tiendaList.clear();
        tiendaList.addAll(set);
        Collections.sort(tiendaList);

        ListaSimpleRecyclerViewAdapter arrayAdapterTiendas =
                new ListaSimpleRecyclerViewAdapter(requireContext(),tiendaList, false);
        lvTiendas.setAdapter(arrayAdapterTiendas);
    }

    private void eventos() {
        lvTiendas.setOnItemClickListener((parent, view, position, id) -> {
            if(!listAuxBuscador.isEmpty()){
                listAuxClickBuscador = listAuxBuscador;
            }else{
                listAuxClickBuscador = tiendaList;
            }

            LogFile.adjuntarLog("POP-UP [VistaTiendaSelectDialogFragment] - Tienda Seleccionada", listAuxClickBuscador.get(position));

            mOnInputListener.sendInputItemTiendaSelectDialogFragment(listAuxClickBuscador.get(position));
            Objects.requireNonNull(getDialog()).dismiss();
        });

        etBuscadorTiendas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(tiendaList != null){
                    String search = etBuscadorTiendas.getText().toString();
                    listAuxBuscador.clear();

                    if(!search.isEmpty()){
                        for(int i=0; i< tiendaList.size(); i++) {
                            String buscar = tiendaList.get(i);
                            if (buscar.contains(search)) {
                                listAuxBuscador.add(buscar);
                            }
                        }

                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),listAuxBuscador, false);
                        lvTiendas.setAdapter(arrayAdapterCajaTienda);
                    }else{
                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),tiendaList, false);
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
            mOnInputListener = (VistaTiendaSelectDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: TiendaSelectDialogFragment: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaTiendaSelectDialogFragment] - Seleccionar Tienda", "Acción: "+ opcion);
    }
}