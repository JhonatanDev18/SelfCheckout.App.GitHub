package com.crystal.selfcheckoutapp.Vista.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.clases.MenuImagenes;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfiguracionRecyclerViewAdapter extends
        RecyclerView.Adapter<ConfiguracionRecyclerViewAdapter.ConfiguracionViewHolder>{

    private List<MenuImagenes> imagenes;
    private IVistaConfiguracion.Vista vista;

    public ConfiguracionRecyclerViewAdapter(List<MenuImagenes> imagenes, IVistaConfiguracion.Vista vista) {
        this.imagenes = imagenes;
        this.vista = vista;
    }

    @NotNull
    @Override
    public ConfiguracionRecyclerViewAdapter.ConfiguracionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recyclerview_imagenes, parent, false);
        return new ConfiguracionRecyclerViewAdapter.ConfiguracionViewHolder(view, vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ConfiguracionRecyclerViewAdapter.ConfiguracionViewHolder holder, int position) {
        holder.ivMenu.setImageResource(imagenes.get(position).getFoto());
        holder.tvNombreMenu.setText(imagenes.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public static class ConfiguracionViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvNombreMenu;
        public final ImageView ivMenu;
        public final ConstraintLayout clFondoMenuMedioPago;
        public Context contexto;
        public IVistaConfiguracion.Vista vista;

        public ConfiguracionViewHolder(@NonNull View itemView, IVistaConfiguracion.Vista vista) {
            super(itemView);
            this.vista = vista;
            contexto = itemView.getContext();
            ivMenu = itemView.findViewById(R.id.ivImagenRecy);
            tvNombreMenu = itemView.findViewById(R.id.tvNombreRecy);
            clFondoMenuMedioPago = itemView.findViewById(R.id.clFondoMenuMedioPago);

            eventos();
        }

        private void eventos() {
            clFondoMenuMedioPago.setOnClickListener(v -> {
                vista.seleccionConfiguracion(getLayoutPosition(), ivMenu);
                applyClickAnimation(ivMenu);
            });
        }

        private void applyClickAnimation(View view) {
            AlphaAnimation clickAnimation = new AlphaAnimation(1.0f, 0.5f);
            clickAnimation.setDuration(100); // Duración de la animación en milisegundos
            view.startAnimation(clickAnimation);
        }
    }
}
