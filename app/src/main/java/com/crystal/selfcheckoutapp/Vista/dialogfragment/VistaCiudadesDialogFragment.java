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

import com.crystal.selfcheckoutapp.Modelo.clases.Buscador;
import com.crystal.selfcheckoutapp.R;

import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.clases.Departamento;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressLint("ValidFragment")
public class VistaCiudadesDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ListView lvdepartamentos;
    private EditText etBuscadorCiudad;

    //Declaración de la variables del DialogFragment
    List<Departamento> departamentosList;
    List<String> ciudadesList = new ArrayList<>();
    List<Departamento> filtroList = new ArrayList<>();
    List<String> listAuxBuscador = new ArrayList<>();
    List<String> listAuxClickBuscador = new ArrayList<>();
    List<Buscador> listAuxFiltroBuscador = new ArrayList<>();
    List<Departamento> listAuxFiltro = new ArrayList<>();
    List<Departamento> listAuxClickFiltroBuscador = new ArrayList<>();

    public VistaCiudadesDialogFragment(List<Departamento> departamentosList) {
        this.departamentosList = departamentosList;
    }

    public interface OnInputListener{
        void sendInputCiudadesDialogFragment(Departamento departamento);
    }

    public VistaCiudadesDialogFragment.OnInputListener mOnInputListener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cuidades);
        builder.setMessage(R.string.select_cuidad)
                .setIcon(R.mipmap.mapa)
                .setNegativeButton(R.string.volver, (dialog, id) -> {
                    // User cancelled the dialog
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
        //Asignacion de Referencias
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_ciudades, null);
        etBuscadorCiudad = view.findViewById(R.id.etBuscadorCiudad);
        lvdepartamentos = view.findViewById(R.id.lvCDF);
    }

    private void cargarDatos() {
        for(Departamento str : departamentosList){
            ciudadesList.add(str.getMunicipio_name());
            filtroList.add(str);
        }

        Set<String> set = new HashSet<>(ciudadesList);
        ciudadesList.clear();
        ciudadesList.addAll(set);
        Collections.sort(ciudadesList);

        //Adaptador
        ListaSimpleRecyclerViewAdapter adapterDepatamentos =
                new ListaSimpleRecyclerViewAdapter(requireContext(),ciudadesList, true);
        lvdepartamentos.setAdapter(adapterDepatamentos);
    }

    private void eventos() {
        //Seleccion de la cuidad
        lvdepartamentos.setOnItemClickListener((parent, view, position, id) -> {
            if(!listAuxBuscador.isEmpty()){
                listAuxClickBuscador = listAuxBuscador;
                listAuxClickFiltroBuscador = listAuxFiltro;
            }else{
                listAuxClickBuscador = ciudadesList;
                listAuxClickFiltroBuscador = filtroList;
            }

            mOnInputListener.sendInputCiudadesDialogFragment(listAuxClickFiltroBuscador.get(position));
            Objects.requireNonNull(getDialog()).dismiss();
        });

        etBuscadorCiudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ciudadesList != null){
                    String search = etBuscadorCiudad.getText().toString();
                    search = search.toUpperCase();
                    listAuxFiltroBuscador.clear();
                    listAuxBuscador.clear();
                    listAuxFiltro.clear();

                    if(!search.isEmpty()){
                        for(int i=0; i< ciudadesList.size(); i++) {
                            String buscar = ciudadesList.get(i);
                            Buscador buscador;
                            if (buscar.contains(search)) {
                                listAuxBuscador.add(buscar);
                                buscador = new Buscador(buscar, i);
                                listAuxFiltroBuscador.add(buscador);
                            }
                        }

                        for (Buscador dato: listAuxFiltroBuscador) {
                            for(Departamento departamento : filtroList){
                                if(departamento.getMunicipio_name().equals(dato.getDato())){
                                    listAuxFiltro.add(departamento);
                                }
                            }
                        }

                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),listAuxBuscador, true);
                        lvdepartamentos.setAdapter(arrayAdapterCajaTienda);
                    }else{
                        ListaSimpleRecyclerViewAdapter arrayAdapterCajaTienda =
                                new ListaSimpleRecyclerViewAdapter(requireContext(),ciudadesList, true);
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
            mOnInputListener = (VistaCiudadesDialogFragment.OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: CiudadesDialogFragment: " + e.getMessage() );
        }
    }
}