package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.crystal.selfcheckoutapp.Modelo.clases.Buscador;
import com.crystal.selfcheckoutapp.Modelo.clases.Departamento;
import com.crystal.selfcheckoutapp.R;

import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressLint("ValidFragment")
public class VistaDepartamentosDialogFrangment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ListView lvdepartamentos;
    private EditText etBuscadorDepartamento;

    //Declaración de la variables del DialogFragment
    List<String> departamentosList;
    List<String> departamentoList = new ArrayList<>();
    List<String> filtroList = new ArrayList<>();
    List<String> listAuxBuscador = new ArrayList<>();
    List<String> listAuxClickBuscador = new ArrayList<>();
    List<Buscador> listAuxFiltroBuscador = new ArrayList<>();
    List<String> listAuxFiltro = new ArrayList<>();
    List<String> listAuxClickFiltroBuscador = new ArrayList<>();

    public VistaDepartamentosDialogFrangment(List<String> departamentosList) {
        this.departamentosList = departamentosList;
    }

    public interface OnInputListener{
        void sendInputDepatamentosDialogFrangment(String departamento);
    }

    public VistaDepartamentosDialogFrangment.OnInputListener mOnInputListener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.departamentos);
        builder.setMessage(R.string.select_departamento)
                .setIcon(R.mipmap.mapa)
                .setNegativeButton(R.string.volver, (dialog, id) -> {
                    dialog.dismiss();
                });

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    @SuppressLint("InflateParams")
    private void inicializar() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        //Asignacion de Referencias
        view = inflater.inflate(R.layout.vista_dialog_fragment_depatamentos, null);
        lvdepartamentos = view.findViewById(R.id.lvDDF);
        etBuscadorDepartamento = view.findViewById(R.id.etBuscadorDepartamento);
    }

    private void cargarDatos() {
        for(String str : departamentosList){
            departamentoList.add(str);
            filtroList.add(str);
        }

        Set<String> set = new HashSet<>(departamentoList);
        departamentoList.clear();
        departamentoList.addAll(set);
        Collections.sort(departamentoList);

        //Adaptador
        ListaSimpleRecyclerViewAdapter adapterDepatamentos =
                new ListaSimpleRecyclerViewAdapter(requireContext(),departamentoList, true);
        lvdepartamentos.setAdapter(adapterDepatamentos);
    }

    private void eventos() {
        //Seleccion el departamento
        lvdepartamentos.setOnItemClickListener((parent, view, position, id) -> {
            if(!listAuxBuscador.isEmpty()){
                listAuxClickBuscador = listAuxBuscador;
                listAuxClickFiltroBuscador = listAuxFiltro;
            }else{
                listAuxClickBuscador = departamentoList;
                listAuxClickFiltroBuscador = filtroList;
            }

            mOnInputListener.sendInputDepatamentosDialogFrangment(listAuxClickBuscador.get(position));
            Objects.requireNonNull(getDialog()).dismiss();
        });

        etBuscadorDepartamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(departamentoList != null){
                    String search = etBuscadorDepartamento.getText().toString();
                    search = search.toUpperCase();
                    listAuxFiltroBuscador.clear();
                    listAuxBuscador.clear();
                    listAuxFiltro.clear();

                    if(!search.isEmpty()){
                        for(int i=0; i< departamentoList.size(); i++) {
                            String buscar = departamentoList.get(i);
                            Buscador buscador;
                            if (buscar.contains(search)) {
                                listAuxBuscador.add(buscar);
                                buscador = new Buscador(buscar, i);
                                listAuxFiltroBuscador.add(buscador);
                            }
                        }

                        for (Buscador dato: listAuxFiltroBuscador) {
                            for(String departamento : filtroList){
                                if(departamento.equals(dato.getDato())){
                                    listAuxFiltro.add(departamento);
                                }
                            }
                        }

                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),listAuxBuscador, true);
                        lvdepartamentos.setAdapter(arrayAdapterCajaTienda);
                    }else{
                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),departamentoList, true);
                        lvdepartamentos.setAdapter(arrayAdapterCajaTienda);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (VistaDepartamentosDialogFrangment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: DepatamentosDialogFrangment: " + e.getMessage() );
        }
    }
}
