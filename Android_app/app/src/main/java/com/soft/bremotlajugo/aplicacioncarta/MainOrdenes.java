package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainOrdenes extends AppCompatActivity implements View.OnClickListener {
    ListView listaOrdenes;
    int numMesa;
    Button botonActualizarordenes;
    Button botonVerorden;
    String[]arrayOrdenes;
    String[]arrayPlatillos;
    String verOrden = "";
    String tipo;
    int posicion;
    private Firebase ordenesRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verOrden=" ";
        Firebase.setAndroidContext(this);
        ordenesRef = new Firebase("https://res-01.firebaseio.com/Ordenes");
        arrayOrdenes = new String[10];
        arrayPlatillos= new String[40];
        botonActualizarordenes= (Button)findViewById(R.id.botonActualizarordenes);
        botonActualizarordenes.setOnClickListener(this);
        botonVerorden=(Button)findViewById(R.id.botonVerorden);
        botonVerorden.setOnClickListener(this);
        for(int i=0;i<10;i++){
            arrayOrdenes[i]="";
        }
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            numMesa = (int) bundle.get("NUMMESA");
            tipo=(String)bundle.get("TIPO");
        }
        listaOrdenes = (ListView)findViewById(R.id.listaOrdenes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrayOrdenes);
        listaOrdenes.setAdapter(adapter);
        final Context context= this;
        listaOrdenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String childMesa = "mesa " + numMesa;
                String pos=arrayOrdenes[position];
                posicion = Integer.parseInt(pos.substring(6,7));
                int orden = posicion;
                final String childOrden = "orden " + orden;
                arrayOrdenes[position] += " *";
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, arrayOrdenes);
                listaOrdenes.setAdapter(adapter);
                for(int j = 0; j < 40; j++) {
                    arrayPlatillos[j] = "";
                }
                Query queryOrden = ordenesRef.child(childMesa).child(childOrden).orderByKey();
                queryOrden.addChildEventListener(new ChildEventListener() {
                    int i = 0;
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayPlatillos[i] = dataSnapshot.getValue().toString();
                        i++;
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Llamando al mesero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Firebase aRef= new Firebase("https://res-01.firebaseio.com/Atiende");
                aRef.child("mesa"+numMesa).setValue("true");
            }
        });

        botonActualizarordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String childMesa = "mesa " + numMesa;
                Query queryOrdenes = ordenesRef.child(childMesa).orderByKey();
                queryOrdenes.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayOrdenes[i++] = dataSnapshot.getKey();
                        for (int j = i; j < 10; j++) {
                            arrayOrdenes[j] = "";
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, arrayOrdenes);
                listaOrdenes.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonVerorden:
                if(tipo.compareTo("ordenes")==0){
                    Intent intentorden = new Intent(this,MainVerorden.class);
                    intentorden.putExtra("NUMORDEN", posicion);
                    intentorden.putExtra("NUMMESA",numMesa);
                    intentorden.putExtra("ORDEN", arrayPlatillos);
                    startActivity(intentorden);
                }
                else{
                    String orden="";
                    int cont=0;
                    for(int j = 0; j < 40; j++) {

                        if(arrayPlatillos[j].compareTo("")!=0){
                            orden+=arrayPlatillos[j] + "\n";
                            cont++;
                        }
                    }
                    int pos=0,ant=0,precio,total=0;
                    for(int i=0;i<cont;i++){
                        ant=orden.indexOf('$');
                        pos=orden.indexOf('\n');
                        precio= Integer.parseInt(orden.substring(ant + 1, pos - 1));
                        total+=precio;
                    }
                    int mesa=numMesa;
                    Intent intentPagar = new Intent(this,MainPagar.class);
                    intentPagar.putExtra("ORDEN", orden);
                    intentPagar.putExtra("TOTAL", total);
                    intentPagar.putExtra("CONT", cont);
                    intentPagar.putExtra("NUMMESA",mesa);
                    startActivity(intentPagar);
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
