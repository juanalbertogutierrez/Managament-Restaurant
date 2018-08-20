package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MainConfirmarorden extends AppCompatActivity implements View.OnClickListener {

    Button boton6,boton7,botonCancelar;
    int cuentas,numCuenta,mesa,cont;
    String orden;
    String[] ordenArray;
    TextView textVieworden;
    ListView listViewOrden;
    private Firebase mesaRef;
    private Firebase meseroRef;
    private Firebase statusRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmarorden);
        boton7 = (Button)findViewById(R.id.button7);
        boton7.setOnClickListener(this);
        boton6 =(Button)findViewById(R.id.button6);
        boton6.setOnClickListener(this);
        botonCancelar=(Button)findViewById(R.id.botonCancelarorden);
        botonCancelar.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        textVieworden = (TextView) findViewById(R.id.textoEliminarplatillo);
        Firebase.setAndroidContext(this);
        meseroRef = new Firebase("https://res-01.firebaseio.com/Meseros/");
        mesaRef = new Firebase("https://res-01.firebaseio.com/Ordenes");
        statusRef= new Firebase("https://res-01.firebaseio.com/Status");
        if(bundle!= null) {
            cuentas = (int) bundle.get("CUENTAS");
            numCuenta = (int) bundle.get("NUMCUENTA");
            mesa = (int)bundle.get("NUMMESA");
            orden = (String) bundle.get("ORDEN");
            cont = (int) bundle.get("CONTADOR");
        }
        ordenArray = new String[cont];
        //textVieworden.setText(orden);
        int pos=0,ant=0;
        pos=orden.indexOf('#');
        ordenArray[0]=orden.substring(0, pos);
        ant=pos;
        for(int i=1;i<cont;i++){
            pos = orden.indexOf('#',pos+1);
            ordenArray[i]=orden.substring(ant + 1, pos);
            ant=pos;

        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, ordenArray);

        listViewOrden = (ListView) findViewById(R.id.listViewOrden);
        listViewOrden.setAdapter(adapter);
        listViewOrden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),ordenArray[position]+ " eliminado", Toast.LENGTH_SHORT).show();
                if(ordenArray[position] != "") {
                    if (position != cont - 1) {
                        ordenArray[position] = ordenArray[--cont];
                        ordenArray[cont] = "";
                    } else {
                        ordenArray[position] = "";
                        cont--;
                    }
                }

                listViewOrden.setAdapter(adapter);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Llamando a mesero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Firebase aRef= new Firebase("https://res-01.firebaseio.com/Atiende");
                aRef.child("mesa" + mesa).setValue("true");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button6:
                int numCuentaecha=numCuenta;
                numCuenta++;
                if(cuentas < numCuenta){
                    mesaRef.child("mesa "+ mesa).child("orden "+ numCuentaecha).setValue(ordenArray);
                    statusRef.child("mesa "+mesa).child("orden"+numCuentaecha).setValue("Pendiente");
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra("NUMMESA",mesa);
                    startActivity(intent);
                }else{
                    mesaRef.child("mesa "+mesa).child("orden " + numCuentaecha).setValue(ordenArray);
                    statusRef.child("mesa "+mesa).child("orden"+numCuentaecha).setValue("Pendiente");
                    orden = "";
                    cont = 0;
                    Intent intent = new Intent(this,MainMenuplatillos.class);
                    intent.putExtra("CUENTAS", cuentas);
                    intent.putExtra("NUMCUENTA", numCuenta);
                    intent.putExtra("NUMMESA",mesa);
                    intent.putExtra("ORDEN",orden);
                    intent.putExtra("CONTADOR",0);
                    startActivity(intent);
                }
                break;
            case R.id.button7:
                orden = "";
                for(int i=0;i<cont;i++) {
                    if(ordenArray[i] != " ") {
                        orden += ordenArray[i] + "#";
                    }
                }
                Intent intent = new Intent(this,MainMenuplatillos.class);
                intent.putExtra("CUENTAS", cuentas);
            intent.putExtra("NUMCUENTA", numCuenta);
                intent.putExtra("NUMMESA",mesa);
                intent.putExtra("ORDEN",orden);
                intent.putExtra("CONTADOR",cont);
                startActivity(intent);
                break;
            case R.id.botonCancelarorden:
                Intent intentCancelar = new Intent(this,MainActivity.class);
                startActivity(intentCancelar);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Intent intentrestart= new Intent(this,MainActivity.class);
        startActivity(intentrestart);
        // Activity being restarted from stopped state
    }

}
