package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button botonMenu,botonOrdenes,botonPagar;
    private Firebase meseroRef;
    int numMesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.content_main);

        botonMenu =(Button) findViewById(R.id.botonMenu);
        botonMenu.setOnClickListener(this);
        botonOrdenes=(Button)findViewById(R.id.botonOrdenes);
        botonOrdenes.setOnClickListener(this);
        botonPagar=(Button)findViewById(R.id.botonPagar);
        botonPagar.setOnClickListener(this);
        Firebase.setAndroidContext(this);
        numMesa=0;
        meseroRef = new Firebase("https://res-01.firebaseio.com/Meseros/");



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            numMesa = (int) bundle.get("NUMMESA");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonMenu:
                Intent intentMenu = new Intent(this,MainCuentas.class);
                startActivity(intentMenu);
                break;
            case R.id.botonOrdenes:
                Intent intentOrdenes = new Intent(this,MainOrdenes.class);
                intentOrdenes.putExtra("NUMMESA", numMesa);
                intentOrdenes.putExtra("TIPO","ordenes");
                startActivity(intentOrdenes);
                break;
            case R.id.botonPagar:
                Intent intentPagar = new Intent(this,MainOrdenes.class);
                intentPagar.putExtra("NUMMESA", numMesa);
                intentPagar.putExtra("TIPO", "pagar");
                startActivity(intentPagar);
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
