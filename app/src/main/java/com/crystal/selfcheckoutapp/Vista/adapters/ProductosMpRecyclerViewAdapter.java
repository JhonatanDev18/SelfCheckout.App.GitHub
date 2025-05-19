package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.DetalleDescuento;
import com.crystal.selfcheckoutapp.R;

import java.util.List;

public class ProductosMpRecyclerViewAdapter extends RecyclerView.Adapter<ProductosMpRecyclerViewAdapter.ProductosViewHolder>{

    private List<DetalleDescuento> detalle;

    public ProductosMpRecyclerViewAdapter(List<DetalleDescuento> detalle) {
        this.detalle = detalle;
    }

    @NonNull
    @Override
    public ProductosMpRecyclerViewAdapter.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recyclerview_productos_mp, parent, false);
        return new ProductosMpRecyclerViewAdapter.ProductosViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "RtlHardcoded"})
    @Override
    public void onBindViewHolder(@NonNull ProductosMpRecyclerViewAdapter.ProductosViewHolder holder, int position) {
        holder.detalleDes = detalle.get(position);
        holder.tvDescripcion.setText(detalle.get(position).getNombre());
        holder.tvPrecio.setText(detalle.get(position).getMonto());

        if(detalle.get(position).isDescuento()){
            holder.tvDescripcion.setGravity(Gravity.RIGHT);
        }else{
            holder.tvDescripcion.setGravity(Gravity.LEFT);
        }

        if(holder.tvPrecio.getText().toString().contains("-")){
            holder.tvDescripcion.setTextColor(Color.RED);
            holder.tvPrecio.setTextColor(Color.RED);
        }
    }

    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView tvDescripcion, tvPrecio;
        public Context contexto;
        public IVistaCompraCliente.Vista vista;
        public DetalleDescuento detalleDes;
        public RelativeLayout rlFondoDestalleDescuentos;
        public ConstraintLayout rlFondoBono;

        public ProductosViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            contexto = view.getContext();
            tvDescripcion = view.findViewById(R.id.tvDescripcionMp);
            tvPrecio = view.findViewById(R.id.tvPrecioMp);
            rlFondoBono = view.findViewById(R.id.rlFondoBono);
            rlFondoDestalleDescuentos = view.findViewById(R.id.rlFondoDestalleDescuentos);
        }
    }

    @Override
    public int getItemCount() {
        return detalle.size();
    }
}
