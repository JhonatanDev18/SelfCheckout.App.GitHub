package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductosRecyclerViewAdapter extends RecyclerView.Adapter<ProductosRecyclerViewAdapter.ProductosViewHolder>{

    private List<Producto> listaProductos;
    private IVistaCompraCliente.Vista vista;

    public ProductosRecyclerViewAdapter(List<Producto> listaProductos, IVistaCompraCliente.Vista vista) {
        this.listaProductos = listaProductos;
        this.vista = vista;
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recyclerview_productos, parent, false);
        return new ProductosRecyclerViewAdapter.ProductosViewHolder(view, vista);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductosRecyclerViewAdapter.ProductosViewHolder holder, int position) {
        holder.tvCodigoBarras.setText(listaProductos.get(position).getEan());
        holder.tvDescripcion.setText(listaProductos.get(position).getNombre());
        holder.tvPrecio.setText(Utilidades.formatearPrecio(listaProductos.get(position).getPrecioBase()));

        if(listaProductos.get(position).getDescuentoTarifa() != null){
            holder.tvDescuentoTarifa.setText(listaProductos.get(position).getDescuentoTarifa());
            holder.tvDescuentoTarifa.setVisibility(View.VISIBLE);
        }else{
            holder.tvDescuentoTarifa.setVisibility(View.GONE);
        }

        if(listaProductos.get(position).isBolsa()){
            holder.ivEliminar.setVisibility(View.VISIBLE);
        }else{
            holder.ivEliminar.setVisibility(View.GONE);
        }

        holder.tvValorFinal.setText(listaProductos.get(position).getValorFinal());
    }

    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView tvCodigoBarras, tvDescripcion, tvPrecio, tvDescuentoTarifa, tvValorFinal;
        public final ImageView ivEliminar;
        public Context contexto;
        public IVistaCompraCliente.Vista vista;

        public ProductosViewHolder(@NonNull View view, IVistaCompraCliente.Vista vista) {
            super(view);
            this.view = view;
            this.vista = vista;
            contexto = view.getContext();
            tvCodigoBarras = view.findViewById(R.id.tvCodigoBarras);
            tvDescripcion = view.findViewById(R.id.tvDescripcion);
            tvPrecio = view.findViewById(R.id.tvPrecio);
            tvDescuentoTarifa = view.findViewById(R.id.tvDescuentoTarifa);
            tvValorFinal = view.findViewById(R.id.tvValorFinal);
            ivEliminar = view.findViewById(R.id.ivEliminar);

            eventos();
        }

        private void eventos() {
            ivEliminar.setOnClickListener(this::eliminarProducto);
        }

        private void eliminarProducto(View view){
            vista.eliminarProducto(getLayoutPosition(), true);
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }
}
