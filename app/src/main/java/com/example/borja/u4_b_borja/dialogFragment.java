package com.example.borja.u4_b_borja;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class dialogFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle SaveInstanceState) {
        AlertDialog.Builder venta=new AlertDialog.Builder(getActivity());

        switch (getTag()) {
            case "fragment":
                venta.setTitle("Selecciona modos de transporte");
                Log.i("LLega", "1");
                final boolean[] check = U4_B_Borja.obtenerBoolean();
                final String[] datos = U4_B_Borja.convertirDatos();

                venta.setMultiChoiceItems(datos, check, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int opcion, boolean isChecked) {
                    }
                });

                venta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        Toast.makeText(getActivity(), "Premches 'Aceptar'", Toast.LENGTH_SHORT).show();
                        U4_B_Borja.cambiarChecks(check);
                        sobreescribirFichero(check);
                    }
                });

                venta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        Toast.makeText(getActivity(), "Premeches 'Cancelar'", Toast.LENGTH_SHORT).show();
                    }
                });

                return venta.create();
        }
        return null;
    }

    private void sobreescribirFichero(boolean[] arr){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(getActivity().openFileOutput(U4_B_Borja.nomeChecks,Context.MODE_PRIVATE));
            osw.write(arr[0] + "\n");
            osw.close();
            OutputStreamWriter osw1= new OutputStreamWriter(getActivity().openFileOutput(U4_B_Borja.nomeChecks,Context.MODE_APPEND));
            for(int i=1;i<arr.length;i++){
                osw1.write(arr[i]+"\n");
            }
            osw1.close();
        }catch (Exception e){
            Log.e("Error","Fallo Sobreescribir checks");
        }
    }

}
