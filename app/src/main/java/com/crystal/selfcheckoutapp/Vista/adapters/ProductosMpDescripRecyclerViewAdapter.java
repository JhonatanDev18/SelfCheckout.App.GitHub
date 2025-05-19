package com.crystal.selfcheckoutapp.Vista.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.clases.DescuentoSimple;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.List;

public class ProductosMpDescripRecyclerViewAdapter extends RecyclerView.Adapter<ProductosMpDescripRecyclerViewAdapter.ProductosViewHolder>{
    private List<Producto> productos;

    public ProductosMpDescripRecyclerViewAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductosMpDescripRecyclerViewAdapter.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_view_productos_descrip, parent, false);
        return new ProductosMpDescripRecyclerViewAdapter.ProductosViewHolder(view, productos);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.tvNombreProductoDescrip.setText(producto.construirNombreProducto(100));
        holder.tvCantProductoDescrip.setText(Integer.toString(producto.getCantidad()));
        holder.tvPrecioProductoDescrip.setText(Utilidades.formatearPrecio(producto.getPrecioBase()));

        if(producto.getListaDescuentoSimple().size()>0){
            holder.ivInfoProductoDescrip.setVisibility(View.VISIBLE);
        }

        holder.tvDctoProductoDescrip.setText(Utilidades.formatearPrecio(producto.getDescuentoNeto()*-1));
        holder.tvValorPagarProductoDescrip.setText(Utilidades.formatearPrecio(producto.getPrecio()*producto.getCantidad()));
    }

    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public Context contexto;
        public List<Producto> productos;
        public TextView tvNombreProductoDescrip, tvCantProductoDescrip, tvPrecioProductoDescrip,
                tvDctoProductoDescrip, tvValorPagarProductoDescrip;
        public ImageView ivInfoProductoDescrip;

        public ProductosViewHolder(@NonNull View view, List<Producto> productos) {
            super(view);
            this.view = view;
            this.productos = productos;
            contexto = view.getContext();
            tvNombreProductoDescrip = view.findViewById(R.id.tvNombreProductoDescrip);
            tvCantProductoDescrip = view.findViewById(R.id.tvCantProductoDescrip);
            tvPrecioProductoDescrip = view.findViewById(R.id.tvPrecioProductoDescrip);
            tvDctoProductoDescrip = view.findViewById(R.id.tvDctoProductoDescrip);
            tvValorPagarProductoDescrip = view.findViewById(R.id.tvValorPagarProductoDescrip);
            ivInfoProductoDescrip = view.findViewById(R.id.ivInfoProductoDescrip);

            ivInfoProductoDescrip.setOnClickListener(v -> {
                Producto producto = productos.get(getLayoutPosition());
                StringBuilder descuentos = new StringBuilder();
                for(DescuentoSimple descuentoSimple: producto.getListaDescuentoSimple()){
                    descuentos.append(descuentoSimple.getNombre()).append(" (")
                            .append(Utilidades.formatearPrecio(descuentoSimple.getValor()*-1)).append(")").append("\n");
                }

                Utilidades.sweetAlertCustom(String.format(contexto.getResources().getString(R.string.descuentos)
                                , producto.construirNombreProducto(100)), descuentos.toString(), contexto,
                        R.drawable.informacion_descuento);
            });
        }
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
