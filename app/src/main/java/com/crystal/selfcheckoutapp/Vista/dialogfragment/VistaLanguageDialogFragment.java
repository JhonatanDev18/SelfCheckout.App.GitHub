package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.clases.Configuraciones;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaConfiguracionRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.adapters.ListaSimpleRecyclerViewAdapter;

import java.util.List;
import java.util.Locale;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VistaLanguageDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private List<String> listaIdiomas;
    private ListView lvConfiguracionesSelect;
    private ImageView ivCloseConfiguracion, ivConfiguracionAP;
    private TextView tvTituloConfiguracion, tvTextoConfiguracion;

    public VistaLanguageDialogFragment(List<String> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_configuracion_opcion, null);

        log("Abriendo POP-UP");

        inicializar();
        cargarDatos();
        eventos();

        setCancelable(false);
        builder.setView(view);

        return builder.create();
    }

    private void inicializar() {
        ivCloseConfiguracion = view.findViewById(R.id.ivCloseConfiguracion);
        ivConfiguracionAP = view.findViewById(R.id.ivConfiguracionAP);
        ivConfiguracionAP.setImageResource(R.drawable.idiomas_512);
        tvTituloConfiguracion = view.findViewById(R.id.tvTituloConfiguracion);
        tvTituloConfiguracion.setText(getResources().getString(R.string.cambiar_idioma_titulo));
        tvTextoConfiguracion = view.findViewById(R.id.tvTextoConfiguracion);
        tvTextoConfiguracion.setText(getResources().getString(R.string.seleccion_lenguaje));
        lvConfiguracionesSelect = view.findViewById(R.id.lvConfiguracionesSelect);
    }

    private void cargarDatos() {
        ListaSimpleRecyclerViewAdapter adapter =
                new ListaSimpleRecyclerViewAdapter(requireContext(),listaIdiomas, true);
        lvConfiguracionesSelect.setAdapter(adapter);
    }

    private void eventos() {
        lvConfiguracionesSelect.setOnItemClickListener((parent, view, position, id) -> {
            try{
                setLanguage(position);
            }catch (Exception e){
                Utilidades.mjsToast(e.getMessage(),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, requireContext());
            }
        });

        ivCloseConfiguracion.setOnClickListener(this::cerrar);
    }

    private void cerrar(View view){
        log("Cerrar");
        dismiss();
    }

    private void setLanguage(int selectedLanguage) {
        String languageCode;
        if (selectedLanguage == 0) {
            languageCode = Constantes.LENGUAJE_ESPANOL;
        } else if(selectedLanguage == 1){
            languageCode = Constantes.LENGUAJE_INGLES;
        }else if(selectedLanguage == 2){
            languageCode = Constantes.LENGUAJE_FRANCES;
        }else if(selectedLanguage == 3){
            languageCode = Constantes.LENGUAJE_RUSO;
        }else{
            languageCode = Constantes.LENGUAJE_MANDARIN;
        }

        String lenguaje;

        switch (languageCode){
            case Constantes.LENGUAJE_INGLES:
                lenguaje = "Ingles";
                break;
            case Constantes.LENGUAJE_FRANCES:
                lenguaje = "Frances";
                break;
            case Constantes.LENGUAJE_RUSO:
                lenguaje = "Ruso";
                break;
            case Constantes.LENGUAJE_MANDARIN:
                lenguaje = "Mandarin";
                break;
            default:
                lenguaje = "Español";
                break;
        }

        log(lenguaje);

        // Save selected language preference
        saveLanguagePreference(languageCode);

        // Change app language
        updateAppLanguage(languageCode);
    }

    private void saveLanguagePreference(String languageCode) {
        // Save user's language preference (e.g., in SharedPreferences)
        SPM.setString(Constantes.LENGUAJE_APP, languageCode);
    }

    private void updateAppLanguage(String languageCode) {
        // Change app's language
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        Resources resources = getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Restart current activity to apply language changes
        dismiss();
        Intent intent = requireActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        requireActivity().finish();
        startActivity(intent);
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaLanguageDialogFragment] - Cambiar idioma", "Selecciono: "+ opcion);
    }
}