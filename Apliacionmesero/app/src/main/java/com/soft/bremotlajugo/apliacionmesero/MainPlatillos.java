package com.soft.bremotlajugo.apliacionmesero;

import android.content.Context;
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
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainPlatillos extends AppCompatActivity implements View.OnClickListener{

    ListView listaElementos;
    String [] elementos;
    Button botonActelem,botonStatus;
    Firebase mRef;
    Firebase sRef;
    String mesero,nommesero,nummesa,numorden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platillos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        botonActelem=(Button)findViewById(R.id.botonActelem);
        botonStatus=(Button)findViewById(R.id.botonStatus);
        botonActelem.setOnClickListener(this);
        elementos= new String[20];
        elementos[0]="Actualiza lista";
        for(int i=0;i<20;i++){
            elementos[i]="";
        }
        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            nommesero= bundle.get("NOMMESERO").toString();
            nummesa= bundle.get("NUMMESA").toString();
            numorden= bundle.get("NUMORDEN").toString();
            mesero= bundle.get("MESERO").toString();

        }
        String ref="https://res-01.firebaseio.com/Ordenes/"+nummesa+"/"+numorden;
        mRef =new Firebase(ref);
        ref ="https://res-01.firebaseio.com/Status"+nummesa+"/"+numorden;
        sRef =new Firebase(ref);
        Query querySts=sRef.orderByKey();
        querySts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                botonStatus.setText(dataSnapshot.getValue().toString());
                Toast.makeText(getApplicationContext(), "status " + dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
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
        Query queryRef=mRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            int i=0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                elementos[i++]=dataSnapshot.getValue().toString();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, elementos);
        listaElementos=(ListView)findViewById(R.id.listaElementos);
        listaElementos.setAdapter(adapter);

        final Context context= this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentreturn = new Intent(context,MainMesas.class);
                intentreturn.putExtra("MESERO", mesero);
                startActivity(intentreturn);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonActelem:
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, elementos);
                listaElementos.setAdapter(adapter);
                break;

        }
    }
}
