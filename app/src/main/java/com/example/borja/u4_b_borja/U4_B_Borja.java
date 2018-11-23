package com.example.borja.u4_b_borja;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class U4_B_Borja extends AppCompatActivity {
    private static String nomeFicheiro = "Datos.txt";
    public static String nomeChecks = "Checks.txt";
    private String[] elementosLista=new String[]{"Lugo","Ourense","Vigo","Pontevedra","Santiago","Coruña"};
    private static ArrayList<String> listaDatos=new ArrayList<>();
    private static ArrayList<Boolean> listaChecks=new ArrayList<>();

    //--------------------------------------------Iniciar Proyecto------------------------------------------------------\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u4__b__borja);
        limpiarArrListDatos();
        limpiarArrListCheck();
        tieneDatos();
        cargarListaChecks();
    }

    //--------------------------------------------Limpiar Datos------------------------------------------------------\\
    public void limpiarArrListDatos(){
        listaDatos.clear();
    }


    public void limpiarArrListCheck(){
        listaChecks.clear();
    }

    //--------------------------------------------Comprobar Datos------------------------------------------------------\\
    public void tieneDatos(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomeFicheiro)));
            cargarListaDatos();
            br.close();
        } catch (Exception ex) {
            crearDatosDefault();
        }
    }
    /*
    public void tieneChecks(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomeFicheiro)));
            br.close();
        } catch (Exception ex) {
            crearChecksDefault();
        }
    }*/

    //--------------------------------------------Crear Datos Default------------------------------------------------------\\

    public void crearDatosDefault(){
        for(int i=0;i<elementosLista.length;i++){
            Log.i("prueba",elementosLista[i]);
            guardarDatos(elementosLista[i]);
        }
    }

    /*
    public void crearChecksDefault(){
        for(int i=0;i<elementosLista.length;i++){
            guardarChecks(Boolean.FALSE);
        }
    }*/

    //--------------------------------------------Cargar ArrayList------------------------------------------------------\\

    public void cargarListaDatos(){
        String linea="";
        TextView tv=findViewById(R.id.tvDialogo);
        tv.setText("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomeFicheiro)));
            while ((linea=br.readLine())!=null){
                listaDatos.add(linea);
                tv.append(linea+"\n");
            }
            br.close();
        } catch (Exception ex) {
            crearDatosDefault();
        }
    }

    public void cargarListaChecks(){
        String linea="";
        boolean x;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(nomeChecks)));
            while ((linea=br.readLine())!=null){
                if(linea.equals("false")){
                    x=false;
                }else{
                    x=true;
                }
                listaChecks.add(x);
            }
            br.close();
        } catch (Exception ex) {
            Log.e("Error","FicheroCheck");
        }
    }



    //--------------------------------------------Convertir ArrayList en Arrays------------------------------------------------------\\

    public static String[] convertirDatos(){
        String[] datos=new String[listaDatos.size()];
        datos=listaDatos.toArray(datos);
        return datos;
    }

    public static boolean[] obtenerBoolean(){
        Boolean[] checks=new Boolean[listaChecks.size()];
        checks=listaChecks.toArray(checks);
        boolean[] arr=new boolean[checks.length];
        for(int i=0;i<checks.length;i++){
            arr[i]=checks[i].booleanValue();
        }
        return arr;
    }

    //--------------------------------------------Cambiar Checks------------------------------------------------------\\

    public static void cambiarChecks(boolean[] check){
        listaChecks.clear();
        for(int i=0;i<check.length;i++){
            listaChecks.add(check[i]);
        }
        //iniciarSobreescribir();

    }
    /*
    public static void iniciarSobreescribir(){
        sobreescribirFicheroCheck(obtenerBoolean());
    }*/

    //--------------------------------------------Sobreescribir Checks------------------------------------------------------\\

    public  void sobreescribirFicheroCheck(boolean[] arr){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nomeChecks,MODE_PRIVATE));
            osw.write(arr[0] + "\n");
            osw.close();
            OutputStreamWriter osw1= new OutputStreamWriter(openFileOutput(nomeChecks,MODE_APPEND));
            for(int i=1;i<arr.length;i++){
                osw1.write(arr[i]+"\n");
            }
            osw1.close();
        }catch (Exception e){
            Log.e("Error","Fallo Sobreescribir checks");
        }
    }

    //--------------------------------------------Guardar en ficheros TXT------------------------------------------------------\\

    public void guardarChecks(boolean x) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nomeChecks, MODE_APPEND));
            osw.write(x + "\n");
            listaChecks.add(x);
            osw.close();
        } catch (Exception ex) {
            Log.e("INTERNA", "Error escribindo no ficheiro");
        }
    }

    public void guardarDatos(String dato) {
        EditText et= findViewById(R.id.etElementosLista);
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nomeFicheiro, MODE_APPEND));
            osw.write(dato + "\n");
            osw.close();
            et.setText("");
            guardarChecks(false);
            limpiarArrListDatos();
            cargarListaDatos();
        } catch (Exception ex) {
            Log.e("INTERNA", "Error escribindo no ficheiro");
        }
    }

    //--------------------------------------------Show Dialog------------------------------------------------------\\

    public void crearShowDialog(View v){
        final boolean[] check = U4_B_Borja.obtenerBoolean();
        final String[] datos = U4_B_Borja.convertirDatos();

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("DIALOGO");


        builder.setMultiChoiceItems(datos, check, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int opcion, boolean isChecked) {
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getApplicationContext(), "Premches 'Aceptar'", Toast.LENGTH_SHORT).show();
                U4_B_Borja.cambiarChecks(check);
                sobreescribirFicheroCheck(check);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                Toast.makeText(getApplicationContext(), "Premeches 'Cancelar'", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    //--------------------------------------------Botones------------------------------------------------------\\

    public void btnShow(View v){
        crearShowDialog(v);
    }

    public void btnDialog(View v){
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment df = new dialogFragment();
         df.show(fm, "fragment");

    }



    public void btnClick(View v){
        EditText et= findViewById(R.id.etElementosLista);
        if(TextUtils.isEmpty(et.getText())){
            Toast.makeText(getApplicationContext(), "Meta un valor para insertar", Toast.LENGTH_LONG).show();
        }
        else {
            String dato= String.valueOf(et.getText());
            guardarDatos(dato);
        }
    }

}
