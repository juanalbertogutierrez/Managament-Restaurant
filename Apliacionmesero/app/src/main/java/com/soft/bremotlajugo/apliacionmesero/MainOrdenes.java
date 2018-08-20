package com.soft.bremotlajugo.apliacionmesero;

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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainOrdenes extends AppCompatActivity implements View.OnClickListener{
    ListView listaOrdenes;
    Button botonAct;
    String [] ordenes;
    String nummesa,creaRef,nommesero,mesero;
    Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        botonAct=(Button)findViewById(R.id.botoActordenes);
        botonAct.setOnClickListener(this);
        ordenes= new String[10];
        ordenes[0]="Actualiza lista";
        for(int i=1;i<10;i++){
            ordenes[i]="";
        }


        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        creaRef = ("https://res-01.firebaseio.com/Ordenes");
        if (bundle != null) {
            nommesero = bundle.get("NUMMESERO").toString();
            nummesa =bundle.get("NUMMESA").toString();
            mesero=bundle.get("MESERO").toString();
            creaRef+="/"+nummesa;
        }
        mRef=new Firebase(creaRef);
        Query queryRef=mRef.orderByKey();
        final Context context=this;
        queryRef.addChildEventListener(new ChildEventListener() {
            int i=0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ordenes[i++]=dataSnapshot.getKey();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, ordenes);
        listaOrdenes=(ListView)findViewById(R.id.listaOrdenes);
        listaOrdenes.setAdapter(adapter);
        listaOrdenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentMesa = new Intent(context, MainPlatillos.class);
                intentMesa.putExtra("NOMMESERO", nommesero);
                intentMesa.putExtra("NUMMESA",nummesa);
                intentMesa.putExtra("NUMORDEN",ordenes[position]);
                intentMesa.putExtra("MESERO",mesero);
                startActivity(intentMesa);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentreturn = new Intent(context,MainMesas.class);
                intentreturn.putExtra("MESERO",mesero);
                startActivity(intentreturn);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botoActordenes:
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, ordenes);
                listaOrdenes.setAdapter(adapter);
                break;

        }
    }
}
