package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.BoringLayout;
import android.view.KeyEvent;
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

public class MainMenuplatillos extends AppCompatActivity implements View.OnClickListener{

    Button botonOrdenar,botonEntradas,botonSopas,botonQuesos,botonEnsaladas,botonFiletes,botonMenudia;
    int cuentas,numCuenta,mesa,cont;
    ListView listaPlatillos;
    String orden ;
    String []arrayEntradas = new String[20];
    String []arrayPrecios = new String[20];
    private Firebase meseroRef;
    private Firebase platillosRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_menuplatillos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        botonOrdenar = (Button) findViewById(R.id.buttonOrdenar);
        botonEntradas = (Button)findViewById(R.id.buttonEntradas);
        botonSopas = (Button)findViewById(R.id.buttonSopas);
        botonQuesos = (Button) findViewById(R.id.buttonQuesos);
        botonEnsaladas = (Button) findViewById(R.id.buttonEnsaladas);
        botonFiletes = (Button) findViewById(R.id.buttonFiletes);
        botonMenudia = (Button) findViewById(R.id.buttonMenudia);
        meseroRef = new Firebase("https://res-01.firebaseio.com/Meseros/");
        platillosRef = new Firebase("https://res-01.firebaseio.com/Platillos");





        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            cuentas = (int) bundle.get("CUENTAS");
            numCuenta = (int) bundle.get("NUMCUENTA");
            mesa = (int) bundle.get("NUMMESA");
            orden = (String) bundle.get("ORDEN");
            cont = (int)bundle.get("CONTADOR");

                Toast.makeText(getApplicationContext(), "cuenta numero " + numCuenta, Toast.LENGTH_SHORT).show();


        }
        arrayEntradas[0]= "Elige una opcion para ver platillos";
        for(int i=1;i<20;i++){
            arrayEntradas[i]=" ";
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
        listaPlatillos = (ListView) findViewById(R.id.listaPlatillos);
        listaPlatillos.setAdapter(adapter);
        listaPlatillos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orden+= arrayEntradas[position]+" #";
                Toast.makeText(getApplicationContext(),arrayEntradas[position] + " añadido", Toast.LENGTH_SHORT).show();
                cont++;
            }
        });



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

        botonOrdenar.setOnClickListener(this);
        final Context context = (Context) this;


        botonEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Entradas").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);
            }
        });


        botonEnsaladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Ensaladas").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);

            }
        });


        botonMenudia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Menu del dia").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);

            }
        });


        botonFiletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Filetes").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);

            }
        });


        botonQuesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Quesos").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);

            }
        });


        botonSopas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryEntradas = platillosRef.child("Sopas").orderByKey();
                queryEntradas.addChildEventListener(new ChildEventListener() {
                    int i = 0;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        arrayEntradas[i++] = dataSnapshot.getKey()+" $"+dataSnapshot.getValue().toString();
                        for(int j=i;j<20;j++){
                            arrayEntradas[j]=" ";
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
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1, arrayEntradas);
                listaPlatillos.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOrdenar:
                Intent intent = new Intent(this,MainConfirmarorden.class);
                intent.putExtra("CUENTAS", cuentas);
                intent.putExtra("NUMCUENTA", numCuenta);
                intent.putExtra("NUMMESA",mesa);
                intent.putExtra("ORDEN",orden);
                intent.putExtra("CONTADOR",cont);
                startActivity(intent);
                break;
            case R.id.buttonEntradas:
                break;
            case R.id.buttonSopas:
                break;
            case R.id.buttonQuesos:
                break;
            case R.id.buttonEnsaladas:
                break;
            case R.id.buttonFiletes:
                break;
            case R.id.buttonMenudia:
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
