package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class MainVerorden extends AppCompatActivity implements View.OnClickListener {
    int numOrden,numMesa;
    String verOrden,ordenStatus;
    String[]arrayPlatillos;
    String[]arrayOrden;
    int longArray;
    private Firebase ordenRef;
    private Firebase statusRef;
    Button botonAñadirplatillo, botonBorrarplatillo,botonCancelarorden,botonStatus,botonVisualizarorden;
    ListView listaOrdenvisual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verorden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        ordenRef = new Firebase("https://res-01.firebaseio.com/Ordenes");
        arrayPlatillos= new String[40];
        arrayOrden= new String[40];
        arrayPlatillos[0]="pulsa en Ver orden para vizualizar";
        for(int i=1;i<40;i++){
            arrayPlatillos[i]="";
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, arrayPlatillos);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            numOrden = (int) bundle.get("NUMORDEN");
            numMesa = (int) bundle.get("NUMMESA");
            arrayOrden=(String[])bundle.get("ORDEN");
        }
        botonAñadirplatillo= (Button)findViewById(R.id.botonAñadirplatillo);
        botonAñadirplatillo.setOnClickListener(this);
        botonVisualizarorden=(Button)findViewById(R.id.botonVisualizarorden);
        botonVisualizarorden.setOnClickListener(this);
        botonCancelarorden= (Button)findViewById(R.id.botonCancelarorden);
        botonCancelarorden.setOnClickListener(this);
        botonBorrarplatillo =(Button)findViewById(R.id.botonBorrarplatillo);
        botonBorrarplatillo.setOnClickListener(this);
        botonStatus=(Button)findViewById(R.id.botonStatus);
        listaOrdenvisual =(ListView)findViewById(R.id.listaOrdenvisual);
        statusRef= new Firebase("https://res-01.firebaseio.com/Status");
        Query queryStatus = statusRef.child("mesa " + numMesa).child("orden"+ numOrden);
        queryStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordenStatus= dataSnapshot.getValue().toString();
                botonStatus.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Llamando al mesero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Firebase aRef= new Firebase("https://res-01.firebaseio.com/Atiende");
                aRef.child("mesa" + numMesa).setValue("true");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonAñadirplatillo:
                if(ordenStatus.compareTo("Pendiente") ==0 ) {
                    String orden="";
                    int cont=0;
                    for(int i=0;i<40;i++){
                        if(arrayOrden[i].compareTo("") != 0){
                            orden+=arrayOrden[i] + " #";
                            cont++;

                        }
                    }
                    Toast.makeText(getApplicationContext(), "Modificar orden", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,MainMenuplatillos.class);
                    intent.putExtra("CUENTAS", 1);
                    intent.putExtra("NUMCUENTA", numOrden);
                    intent.putExtra("NUMMESA",numMesa);
                    intent.putExtra("ORDEN", orden);
                    intent.putExtra("CONTADOR", cont);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "La orden ya no se puede modificar", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.botonBorrarplatillo:
                if(ordenStatus.compareTo("Pendiente") ==0 ) {
                    Toast.makeText(getApplicationContext(), "Modificar orden ", Toast.LENGTH_SHORT).show();
                    String orden="";
                    int cont=0;
                    for(int i=0;i<40;i++){
                        if(arrayOrden[i].compareTo("") != 0){
                            orden+=arrayOrden[i] + " #";
                            cont++;

                        }
                    }
                    Intent intent = new Intent(this,MainConfirmarorden.class);
                    intent.putExtra("CUENTAS", 1);
                    intent.putExtra("NUMCUENTA", numOrden);
                    intent.putExtra("NUMMESA",numMesa);
                    intent.putExtra("ORDEN",orden);
                    intent.putExtra("CONTADOR", cont);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "La orden ya no se puede modificar", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.botonCancelarorden:
                if(ordenStatus.compareTo("Pendiente") ==0 ) {
                    ordenRef.child("mesa " + numMesa).child("orden " + numOrden).removeValue();
                    Toast.makeText(getApplicationContext(), "La orden esta cancelada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra("NUMMESA",numMesa);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "La orden ya no se puede modificar", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.botonVisualizarorden:
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, arrayOrden);
                listaOrdenvisual.setAdapter(adapter);
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
