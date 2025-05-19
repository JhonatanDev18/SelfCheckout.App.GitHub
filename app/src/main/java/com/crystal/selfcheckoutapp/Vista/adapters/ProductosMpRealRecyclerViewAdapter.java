package com.crystal.selfcheckoutapp.Vista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.clases.DescuentoSimple;
import com.crystal.selfcheckoutapp.Modelo.clases.DetalleDescuento;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosMpRealRecyclerViewAdapter extends RecyclerView.Adapter<ProductosMpRealRecyclerViewAdapter.ProductosViewHolder>{

    private List<Producto> productos;
    private int cantidadBolsas;

    public ProductosMpRealRecyclerViewAdapter(List<Producto> productos, int cantidadBolsas) {
        this.productos = productos;
        this.cantidadBolsas = cantidadBolsas;
    }

    @NonNull
    @Override
    public ProductosMpRealRecyclerViewAdapter.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_view_productos_mp, parent, false);
        return new ProductosMpRealRecyclerViewAdapter.ProductosViewHolder(view, productos);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        Producto producto = productos.get(position);

        String nombre;
        double precio;
        if(producto.isBolsa()){
            nombre = "X"+cantidadBolsas+" "+producto.construirNombreProducto(100);
            precio = producto.getPrecio()*cantidadBolsas;
        }else{
            nombre = producto.construirNombreProducto(100);
            precio = producto.getPrecio();
        }

        holder.tvNombreProductoMp.setText(nombre);
        holder.tvPrecioBaseProductoMp.setText(Utilidades.formatearPrecio(producto.getPrecioBase()));

        if(producto.getListaDescuentoSimple().size()>0){
            producto.setListaDescuentoSimple(Utilidades.distintDescuento(producto.getListaDescuentoSimple()));

            for(DescuentoSimple descuentoSimple: producto.getListaDescuentoSimple()){
                holder.detalleDescuentos.add(new DetalleDescuento(descuentoSimple.getNombre(),
                        Utilidades.formatearPrecio(descuentoSimple.getValor()*-1), true));
            }

            holder.adapterDescuentos();
        }

        holder.tvValorFinalMp.setText(Utilidades.formatearPrecio(precio));
    }

    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public Context contexto;
        public List<Producto> productos;
        public List<DetalleDescuento> detalleDescuentos;
        public ProductosMpRecyclerViewAdapter adaptadorDescuentos;
        public RecyclerView rvProductosDescuentosRealMP;
        public TextView tvNombreProductoMp, tvPrecioBaseProductoMp, tvValorFinalMp;

        public ProductosViewHolder(@NonNull View view, List<Producto> productos) {
            super(view);
            this.view = view;
            this.productos = productos;
            contexto = view.getContext();

            inicializar();
        }

        private void inicializar() {
            detalleDescuentos = new ArrayList<>();
            rvProductosDescuentosRealMP = view.findViewById(R.id.rvProductosDescuentosRealMP);
            rvProductosDescuentosRealMP.setLayoutManager(new LinearLayoutManager(contexto));
            tvNombreProductoMp = view.findViewById(R.id.tvNombreProductoMp);
            tvPrecioBaseProductoMp = view.findViewById(R.id.tvPrecioBaseProductoMp);
            tvValorFinalMp = view.findViewById(R.id.tvValorFinalMp);

            adapterDescuentos();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void adapterDescuentos(){
            adaptadorDescuentos = new ProductosMpRecyclerViewAdapter(detalleDescuentos);
            rvProductosDescuentosRealMP.setAdapter(adaptadorDescuentos);
            adaptadorDescuentos.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
