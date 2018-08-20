package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MainCuentas extends AppCompatActivity implements View.OnClickListener{
    Button botonOrdenar;
    String mesa;
    EditText textoNumcuentas;
    EditText textoNummesa;
    String orden = new String();
    String [] arrayEntradas;
    String []arrayPrecios;
    private Firebase meseroRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        botonOrdenar =(Button) findViewById(R.id.botonOrdenar);
        botonOrdenar.setOnClickListener(this);

        textoNumcuentas = (EditText)findViewById(R.id.numeroCuentas);
        textoNummesa = (EditText)findViewById(R.id.numeroMesa);

        Firebase.setAndroidContext(this);
        arrayEntradas =new String[20];
        arrayPrecios =new String[20];
        mesa=  textoNummesa.getText().toString();
        meseroRef = new Firebase("https://res-01.firebaseio.com/Meseros/");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Llamando al mesero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Firebase aRef= new Firebase("https://res-01.firebaseio.com/Atiende");
                aRef.child("mesa"+mesa).setValue("true");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonOrdenar:
                Intent intent = new Intent(this,MainMenuplatillos.class);
                String textCuentas = textoNumcuentas.getText().toString();
                String textMesa=textoNummesa.getText().toString();
                Toast.makeText(getApplicationContext(),textCuentas+"cuentas "+textMesa,Toast.LENGTH_SHORT).show();
                int cuentas = Integer.parseInt(textoNumcuentas.getText().toString());
                int mesa = Integer.parseInt(textoNummesa.getText().toString());
                intent.putExtra("CUENTAS", cuentas);
                intent.putExtra("NUMCUENTA", 1);
                intent.putExtra("NUMMESA",mesa);
                intent.putExtra("ORDEN",orden);
                intent.putExtra("CONTADOR", 0);
                if(textCuentas.compareTo("")==0 || textMesa.compareTo("")==0){
                    Toast.makeText(getApplicationContext(),"No a completado los campos",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(0<cuentas&&cuentas<11&&0<mesa&&mesa<11){
                    startActivity(intent);
                }else if(cuentas<0 || cuentas>10){
                    Toast.makeText(getApplicationContext(),"el numero de cuentas no es valido",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"el numero de mesa no es valido",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Intent intentrestart= new Intent(this,MainActivity.class);
        startActivity(intentrestart);
        // Activity being restarted from stopped state
    }
}
