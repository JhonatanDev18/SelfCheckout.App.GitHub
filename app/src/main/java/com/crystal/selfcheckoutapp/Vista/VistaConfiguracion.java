package com.crystal.selfcheckoutapp.Vista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.MenuImagenes;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseTiendas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.Parametro;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseConsultarPerifericos;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaConfiguracion;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ConfiguracionRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCajaSelectDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaConfigurarPerifericosDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaTiendaSelectDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VistaConfiguracion extends AppCompatActivity implements IVistaConfiguracion.Vista,
        VistaTiendaSelectDialogFragment.OnInputListener,
        VistaCajaSelectDialogFragment.OnInputListener{

    private Button btnSalirConfiguracion;
    private Context contexto;
    private Factura factura;
    private ConfiguracionRecyclerViewAdapter adaptador;
    private RecyclerView rvMenuConfiguracion;
    private List<MenuImagenes> menuImagenes;
    private PresentadorVistaConfiguracion presentador;
    private VistaConfigurarPerifericosDialogFragment vistaConfigurarPerifericosDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.vista_configuracion);

            LogFile.adjuntarLogTitulo("Abriendo [Pantalla Configuración]");

            /*Esta acción permite que la pantalla no se apague sin importar que el dispositivo
            tenga una suspencion minima ejemplo (15 segundos), la pantalla seguira activa.
            Tener en cuenta que en el manifest debe existir el siguiente permiso
            (<uses-permission android:name="android.permission.WAKE_LOCK"/>)*/
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            //Ocultar el Action bar del aplicativo
            Objects.requireNonNull(getSupportActionBar()).hide();

            //Ocultar la barra de estado donde se ve lo siguiente(Wifi, Notificaciones, batería etc...)
            Utilidades.ocultarBarraEstado(getWindow());

            //Ocultar la barra de navegación, que son los 3 botones (Atras, Inicio y Recientes)
            Utilidades.ocultarBarraNavegacion(getWindow());

            incializar();
            eventos();

            presentador.consultarParametros();
        }catch (Exception e){
            Toast.makeText(VistaConfiguracion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void incializar() {
        contexto = VistaConfiguracion.this;
        factura = new Factura();
        presentador = new PresentadorVistaConfiguracion(contexto, this, getSupportFragmentManager());
        btnSalirConfiguracion = findViewById(R.id.btnSalirConfiguracion);

        rvMenuConfiguracion = findViewById(R.id.rvMenuConfiguracion);
        rvMenuConfiguracion.setLayoutManager(new GridLayoutManager(this, 2));

        cargarMenuConfiguracion();

        adaptador = new ConfiguracionRecyclerViewAdapter(menuImagenes, this);
        rvMenuConfiguracion.setAdapter(adaptador);
    }

    private void cargarMenuConfiguracion() {
        menuImagenes = new ArrayList<>();

        menuImagenes.add(new MenuImagenes(R.drawable.impresora_menu,
                getResources().getString(R.string.impresora_configuracion)));
        menuImagenes.add(new MenuImagenes(R.drawable.tef_menu,
                getResources().getString(R.string.tef_configuracion)));
        menuImagenes.add(new MenuImagenes(R.drawable.exportar_configuracion,
                getResources().getString(R.string.perifericos_configuracion)));
        menuImagenes.add(new MenuImagenes(R.drawable.archivo_de_texto,
                getResources().getString(R.string.log_configuracion)));
    }

    private void eventos() {
        btnSalirConfiguracion.setOnClickListener(this::cerrarConfiguracion);
    }

    private void cerrarConfiguracion(View view){
        irLogin();
    }

    private void irLogin(){
        Intent intent = new Intent(contexto, VistaLogin.class);
        startActivity(intent);
        finish();
    }

    private void vistaTirilla(){
        Intent intent = new Intent(contexto, VistaImpresionTirilla.class);
        intent.putExtra(getResources().getString(R.string.key_intent_factura), factura);
        startActivity(intent);
    }

    private void vistaDatafono(){
        Intent intent = new Intent(contexto, VistaDatafono.class);
        startActivity(intent);
    }

    @Override
    public void seleccionConfiguracion(int configuracion, View view) {
        switch (configuracion){
            case Constantes.MENU_IMPRESORA:
                showPopupMenu(view);
                break;
            case Constantes.MENU_TEF:
                vistaDatafono();
                break;
            case Constantes.MENU_PERIFERICOS:
                abrirConfiguracionPerifericos();
                break;
            case Constantes.MENU_LOG:
                showPopupMenuLog(view);
                break;
        }
    }

    private void abrirConfiguracionPerifericos() {
        vistaConfigurarPerifericosDialogFragment =
                new VistaConfigurarPerifericosDialogFragment(presentador, this);
        vistaConfigurarPerifericosDialogFragment.show(getSupportFragmentManager(),
                "VistaConfigurarPerifericosDialogFragment");
    }

    @Override
    public void respuestaConfigurarPerifericos(ResponseConsultarPerifericos respuesta) {
        if(respuesta.isEsValida()){
            LogFile.adjuntarLog("Consultar Perifericos Response", "Perifericos consultados correctamente");
            if(vistaConfigurarPerifericosDialogFragment != null){
                vistaConfigurarPerifericosDialogFragment.dismiss();
                vistaConfigurarPerifericosDialogFragment = null;
            }

            Utilidades.guardarPerifericos(respuesta.getListPerifericos());
            Utilidades.mjsToast("Parámetros de impresión y pago TEF cargados correctamente.",
                    Constantes.TOAST_TYPE_SUCCESS, Toast.LENGTH_LONG, contexto);
        }else{
            LogFile.adjuntarLog("Consultar Perifericos Error", respuesta.getMensaje());
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                    new VistaMsjCustomUnaAccionDialogFragment(R.drawable.perifericos,
                            "Perifericos",
                            respuesta.getMensaje(), "",
                            getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }
    }

    @Override
    public void respuestaConsultarTiendas(ResponseTiendas respuesta) {
        if(respuesta.getMensaje() != null){
            LogFile.adjuntarLog("Consultar Tiendas Error", respuesta.getMensaje());
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                    new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                            getResources().getString(R.string.ups_algo_mal),
                            respuesta.getMensaje(), "",
                            getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }else{
            LogFile.adjuntarLog("Consultar Tiendas Response", respuesta.getTiendas());
            vistaConfigurarPerifericosDialogFragment.setTiendasSelect(respuesta.getTiendas());
            abrirTiendasSelect(respuesta.getTiendas());
        }
    }

    @Override
    public void respuestaConsultarCajas(ResponseCajas respuesta, String tiendaDefecto) {
        if(respuesta.getEsValida()){
            List<Caja> cajasApi = respuesta.getListado();
            vistaConfigurarPerifericosDialogFragment.setCajasSelect(cajasApi);
            List<Caja> cajas = new ArrayList<>();
            if(!cajasApi.isEmpty()){
                for(Caja caja: cajasApi){
                    if(tiendaDefecto.equals(caja.getCodigoTienda())){
                        cajas.add(caja);
                    }
                }

                if(!cajas.isEmpty()){
                    LogFile.adjuntarLog("Consultar Cajas Response", cajas);
                    abrirCajasSelect(cajas);
                }else{
                    LogFile.adjuntarLog("Consultar Cajas Error", String.format("La tienda %1$s no tiene cajas disponibles.",
                            tiendaDefecto));
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                            Constantes.SERVICIO_CAJAS);
                }
            }else {
                LogFile.adjuntarLog("Consultar Cajas Error", String.format("La tienda %1$s no tiene cajas disponibles.",
                        tiendaDefecto));
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                        Constantes.SERVICIO_CAJAS);
            }
        }else{
            LogFile.adjuntarLog("Consultar Cajas Error", respuesta.getMensaje());
            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                    respuesta.getMensaje(), Constantes.SERVICIO_CAJAS);
        }
    }

    @Override
    public void respuestaParametros(ResponseParametros respuesta, List<RequestParametros> listaParametros) {
        if(respuesta.getEsValida()){
            List<Parametro> parametros = respuesta.getListado();
            if(!parametros.isEmpty()){
                if(listaParametros.size() == parametros.size()){
                    for(Parametro parametro : parametros){
                        /**
                         * Timeout de espera de datafono para consultar la respuesta
                         * mayor o igual a 3 segundos
                         */
                        if(parametro.getCodigo().equals("ZPOSM:DF:TIMEOUT")){
                            SPM.setString(Constantes.TIMEOUT_DATAFONO_RB, parametro.getValor());
                            SPM.setString(Constantes.TIMEOUT_DATAFONO, parametro.getValor());
                        }

                        /**
                         * Timeout de espera de datafono mayor o igual a 3 minutos
                         */
                        if(parametro.getCodigo().equals("ZPOSM:DF:TIMEOUT:ES")){
                            SPM.setString(Constantes.TIMEOUT_ESPERA_DATAFONO_RB, parametro.getValor());
                            SPM.setString(Constantes.TIMEOUT_ESPERA_DATAFONO, parametro.getValor());
                        }
                    }

                    LogFile.adjuntarLog("Consultar Parametros Response", respuesta.getListado());
                    abrirConfiguracionPerifericos();
                    presentador.ocultarDialogProgressBar();
                }else{
                    StringBuilder nombreParametros = new StringBuilder();

                    for(RequestParametros param: listaParametros){
                        boolean encontrado = false;
                        for(Parametro parametro : parametros){
                            if (param.getCodigo().equals(parametro.getCodigo())) {
                                encontrado = true;
                                break;
                            }
                        }
                        if(!encontrado){
                            nombreParametros.append(param.getCodigo()).append(",");
                        }
                    }

                    LogFile.adjuntarLog("Consultar Parametros Error",
                            String.format("Error en la lectura de los siguientes parametros: %1$s Por favor comuníquese inmediatamente con el administrador.",
                            nombreParametros.toString()));
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            String.format(contexto.getResources().getString(R.string.error_lectura_parametros),
                                    nombreParametros.toString()),
                            Constantes.SERVICIO_PARAMETROS);
                }
            }else {
                if(respuesta.getMensaje() == null){
                    LogFile.adjuntarLog("Consultar Parametros Error", "Parametros no encontrados");
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.parametros_no_encontrados),
                            Constantes.SERVICIO_PARAMETROS);
                }else{
                    LogFile.adjuntarLog("Consultar Parametros Error", respuesta.getMensaje());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            respuesta.getMensaje(), Constantes.SERVICIO_PARAMETROS);
                }
            }
        }else{
            LogFile.adjuntarLog("Consultar Parametros Error", respuesta.getMensaje());
            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                    respuesta.getMensaje(), Constantes.SERVICIO_PARAMETROS);
        }
    }

    public void abrirTiendasSelect(List<String> tiendas){
        VistaTiendaSelectDialogFragment vistaTiendaSelectDialogFragment =
                new VistaTiendaSelectDialogFragment(tiendas, contexto);
        vistaTiendaSelectDialogFragment.show(getSupportFragmentManager(), "VistaTiendaSelectDialogFragment");
        presentador.ocultarDialogProgressBar();
    }

    public void abrirCajasSelect(List<Caja> cajas){
        VistaCajaSelectDialogFragment cajaSelectDialogFragment =
                new VistaCajaSelectDialogFragment(cajas, contexto);
        cajaSelectDialogFragment.show(getSupportFragmentManager(), "CajasSelect");
        presentador.ocultarDialogProgressBar();
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        presentador.ocultarDialogProgressBar();

        if(servicio == Constantes.SERVICIO_CONSULTAR_PERIFERICOS ||
        servicio == Constantes.SERVICIO_CONSULTAR_TIENDAS){
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                    new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    String.format(getResources().getString(R.string.ups_algo_mal_msj), servicio), mensaje,
                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_impresora, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.mItemImpresoraEpson) {
                if(SPM.getString(Constantes.IMPRESORA_MAC) != null){
                    factura.setBluetooth(true);
                }

                imprimirTirilla(getResources().getString(R.string.epson));
                return true;
            }else if(menuItem.getItemId() == R.id.mItemImpresoraBixolon){
                imprimirTirilla(getResources().getString(R.string.bixolon));
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void showPopupMenuLog(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        boolean log = SPM.getBoolean(Constantes.IS_ACTIVE_LOG);

        if(log){
            popupMenu.getMenuInflater().inflate(R.menu.menu_desactivarlog, popupMenu.getMenu());
        }else{
            popupMenu.getMenuInflater().inflate(R.menu.menu_activarlog, popupMenu.getMenu());
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.mItemActivarLog) {
                SPM.setBoolean(Constantes.IS_ACTIVE_LOG, true);
                Utilidades.mjsToast("Log activado", Constantes.TOAST_TYPE_SUCCESS, Toast.LENGTH_LONG, contexto);
                return true;
            }else if(menuItem.getItemId() == R.id.mItemDesactivarLog){
                SPM.setBoolean(Constantes.IS_ACTIVE_LOG, false);
                Utilidades.mjsToast("Log desactivado", Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void imprimirTirilla(String impresora){
        factura.setConfiguracion(true);
        factura.setImpresora(impresora);
        vistaTirilla();
    }

    @Override
    public void sendInputItemCajaSelectDialogFragment(Caja caja) {
        vistaConfigurarPerifericosDialogFragment.insertarCaja(caja.getCodigoCaja());
    }

    @Override
    public void sendInputItemTiendaSelectDialogFragment(String tienda) {
        vistaConfigurarPerifericosDialogFragment.insertarTienda(tienda);
    }
}