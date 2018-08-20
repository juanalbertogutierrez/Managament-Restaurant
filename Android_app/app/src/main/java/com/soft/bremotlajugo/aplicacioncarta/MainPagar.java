package com.soft.bremotlajugo.aplicacioncarta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainPagar extends AppCompatActivity implements View.OnClickListener{
    TextView textoPagar,textoElementos,textoTotal;
    String orden;
    int total,cont,numMesa;
    Button botonirpagar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textoPagar=(TextView)findViewById(R.id.textoPagar);
        textoElementos=(TextView)findViewById(R.id.textoElementos);
        textoTotal=(TextView)findViewById(R.id.textoTotal);
        botonirpagar=(Button)findViewById(R.id.botonirpagar);
        botonirpagar.setOnClickListener(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            orden=(String)bundle.get("ORDEN");
            total=(int)bundle.get("TOTAL");
            cont=(int)bundle.get("CONT");
            numMesa=(int)bundle.get("NUMMESA");
            textoPagar.setText(orden);
            textoElementos.setText("Consumo total : "+cont+" platillos");
            textoTotal.setText("Total a pagar : "+total+" $");
        }
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
            case R.id.botonirpagar:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("NUMMESA",numMesa);
                startActivity(intent);
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
