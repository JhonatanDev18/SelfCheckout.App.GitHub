package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.crystal.selfcheckoutapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

public class VistaInsertarEditTextDialogFragment extends BottomSheetDialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    View view;
    EditText etTexto;
    TextInputLayout tliTexto;
    ImageView ivCerrarFragment;
    TextView tvTituloFragment;
    TextInputLayout tliTextoEditar;
    EditText etEditarTexto;
    Button btnIngresarTexto;
    boolean isNumerico;

    public VistaInsertarEditTextDialogFragment(TextInputLayout tliTexto, EditText etTexto, boolean isNumerico) {
        this.etTexto = etTexto;
        this.tliTexto = tliTexto;
        this.isNumerico = isNumerico;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vista_dialog_fragment_editar_texto, container, false);

        inicializar();
        eventos();

        setCancelable(false);
        return view;
    }

    private void inicializar() {
        ivCerrarFragment = view.findViewById(R.id.ivCloseButton);
        tvTituloFragment = view.findViewById(R.id.tvTituloFragment);
        tliTextoEditar = view.findViewById(R.id.tliTextoEditar);
        etEditarTexto = view.findViewById(R.id.etEditarTexto);
        btnIngresarTexto = view.findViewById(R.id.btnIngresarTexto);

        tliTextoEditar.setHint(tliTexto.getHint());
        String texto = etTexto.getText().toString();
        if(!texto.isEmpty()){
            etEditarTexto.setText(texto);
            btnIngresarTexto.setVisibility(View.INVISIBLE);
        }
        tvTituloFragment.setText(String.format(getResources().getString(R.string.editar_texto_fragment),
                tliTexto.getHint().toString().toLowerCase()));

        if(isNumerico){
            etEditarTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        etEditarTexto.requestFocus();
    }

    private void eventos() {
        ivCerrarFragment.setOnClickListener(this::cerrarFragment);

        etEditarTexto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String texto = etEditarTexto.getText().toString();
                if(texto.isEmpty()){
                    btnIngresarTexto.setVisibility(View.INVISIBLE);
                }else{
                    btnIngresarTexto.setVisibility(View.VISIBLE);
                }
            }
        });

        btnIngresarTexto.setOnClickListener(this::ingresarTexto);
    }

    private void cerrarFragment(View view){
        dismiss();
    }

    private void ingresarTexto(View view){
        String texto = etEditarTexto.getText().toString();
        etTexto.setText(texto);
        dismiss();
    }
}