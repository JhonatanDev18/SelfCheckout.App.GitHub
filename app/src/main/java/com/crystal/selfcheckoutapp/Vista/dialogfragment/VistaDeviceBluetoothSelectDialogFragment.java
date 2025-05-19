package com.crystal.selfcheckoutapp.Vista.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.R;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class VistaDeviceBluetoothSelectDialogFragment extends DialogFragment {
    //Declaración de los objetos de la interfaz del DialogFragment
    private View view;
    private ListView lvdivicebluetooth;
    private TextView tvmostrartodos;

    //Declaración de la variables del DialogFragment
    ArrayList<String> bluetoothNameList;
    ArrayList<String> bluetoothMacList;
    BluetoothAdapter mBtAdapter;

    public VistaDeviceBluetoothSelectDialogFragment(){
    }

    public interface OnInputListener{
        void sendInputListSelectDeviceBluetoothDialogFragment(String mac);
    }
    public VistaDeviceBluetoothSelectDialogFragment.OnInputListener mOnInputListener;

    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use la clase Builder para la construcción conveniente del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dispositivo_bluetooth);
        builder.setMessage(getResources().getString(R.string.select_impresora_blue))
                .setIcon(R.mipmap.bluetooth)
                .setNegativeButton(R.string.cancelar, (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                });

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.vista_dialog_fragment_divice_bluetooth_select, null);

        log("Abriendo POP-UP");

        tvmostrartodos = view.findViewById(R.id.tvMostrarTodosDBSDF);

        lvdivicebluetooth = view.findViewById(R.id.lvDeviceDBSDF);
        bluetoothNameList = new ArrayList<>();
        bluetoothMacList = new ArrayList<>();
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Obtenga un conjunto de dispositivos actualmente emparejados
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // Si hay dispositivos emparejados, agregue cada uno al ArrayAdapter
        if (!pairedDevices.isEmpty()) {
            //Vaciar las listas
            bluetoothNameList.clear();
            bluetoothMacList.clear();

            //Añadir al RadioGroup los dispositivos
            for (BluetoothDevice device : pairedDevices) {
                if (isAPrinter(device)) {
                    bluetoothNameList.add(device.getName() + "\n" + device.getAddress());
                    bluetoothMacList.add(device.getAddress());
                }
            }
        }

        //Adaptador con la lista de descuento
        ArrayAdapter adapterDeviceBluetooth = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                bluetoothNameList
        );
        lvdivicebluetooth.setAdapter(adapterDeviceBluetooth);

        //Seleccion del descuento
        lvdivicebluetooth.setOnItemClickListener((parent, view, position, id) -> {
            String mac = bluetoothMacList.get(position);
            mOnInputListener.sendInputListSelectDeviceBluetoothDialogFragment(mac);
            Objects.requireNonNull(getDialog()).dismiss();
        });

        tvmostrartodos.setOnClickListener(view -> {
            // Obtenga un conjunto de dispositivos actualmente emparejados
            Set<BluetoothDevice> pairedDevices1 = mBtAdapter.getBondedDevices();

            // Si hay dispositivos emparejados, agregue cada uno al ArrayAdapter
            if (!pairedDevices1.isEmpty()) {
                //Vaciar las listas
                bluetoothNameList.clear();
                bluetoothMacList.clear();

                //Añadir al RadioGroup los dispositivos
                for (BluetoothDevice device : pairedDevices1) {
                    bluetoothNameList.add(device.getName() + "\n" + device.getAddress());
                    bluetoothMacList.add(device.getAddress());
                }
            }
            //Adaptador con la lista de descuento
            ArrayAdapter adapterDeviceBluetooth1 = new ArrayAdapter(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    bluetoothNameList
            );
            lvdivicebluetooth.setAdapter(adapterDeviceBluetooth1);
            tvmostrartodos.setVisibility(View.GONE);
        });

        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private static boolean isAPrinter(BluetoothDevice device){
        int priterMask = 0b000001000000011010000000;
        int fullCod = device.getBluetoothClass().hashCode();
        return (fullCod & priterMask) == priterMask;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("logcat", "onAttach: DeviceBluetoothSelectDialogFragment: " + e.getMessage() );
        }
    }

    private void log(String opcion){
        LogFile.adjuntarLog("POP-UP [VistaDeviceBluetoothSelectDialogFragment] - Seleccionar Dispositivo", "Acción: "+ opcion);
    }
}