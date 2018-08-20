package com.soft.bremotlajugo.apliacionmesero;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainMesas extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    TextView textView;
    Button boton,boton2;
    ListView listView;
    private Firebase mRef;
    String[] mesas;
    String texto;
    int sig=0;
    Firebase aRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.editText2);
        boton = (Button) findViewById(R.id.button2);
        boton.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(R.id.listView);
        boton2 = (Button)findViewById(R.id.button3);
        boton2.setOnClickListener(this);
        mesas = new String[10];
        for(int i=0;i<10;i++){
            mesas[i]=" ";
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String creaRef;
        Firebase.setAndroidContext(this);
        creaRef = ("https://res-01.firebaseio.com/Meseros/");
        if (bundle != null) {
            texto = bundle.get("MESERO").toString();
            creaRef+=texto;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, mesas);
        listView.setAdapter(adapter);
        mRef = new Firebase(creaRef);
        Query queryRef = mRef.orderByKey();
        queryRef.addChildEventListener(new ChildEventListener() {
            int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mesas[i++] = dataSnapshot.getKey();
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
        final Context context=this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentMesa = new Intent(context, MainOrdenes.class);
                intentMesa.putExtra("NUMMESA", mesas[position]);
                intentMesa.putExtra("NUMMESERO", texto);
                intentMesa.putExtra("MESERO", texto);
                startActivity(intentMesa);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        aRef= new Firebase("https://res-01.firebaseio.com/Atiende");
        Query queryAtiende = aRef.orderByKey();
        queryAtiende.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue().toString().equals("true")){
                    Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    v.vibrate(3000);
                    final String mesa=dataSnapshot.getKey();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("LLamado de la mesa "+ dataSnapshot.getKey())
                            .setTitle("Atención!!")
                            .setCancelable(true)
                            .setNeutralButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Firebase aRef = new Firebase("https://res-01.firebaseio.com/Atiende");
                                            aRef.child(mesa).setValue("false");
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                String mesa = editText.getText().toString();
                mRef.child("mesa "+ mesa).setValue(false);
                editText.setText(null);
                break;
            case R.id.button3:
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, mesas);
                listView.setAdapter(adapter);
                break;
            default:
                break;
        }
    }


}
