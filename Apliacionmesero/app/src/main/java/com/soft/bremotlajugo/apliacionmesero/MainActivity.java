package com.soft.bremotlajugo.apliacionmesero;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editText;
    Button boton;
    private Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(this);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://res-01.firebaseio.com/LoginMeseros");
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button:
                final String texto = editText.getText().toString();
                Query queryRef=mRef.orderByKey();
                final Context context=this;
                queryRef.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String data=dataSnapshot.getKey();
                        if(data.equals(texto)&& dataSnapshot.getValue().toString().equals("salio")){
                            Intent intent = new Intent(context,MainMesas.class);
                            intent.putExtra("MESERO",texto);
                            Toast.makeText(getApplicationContext(), "Hola "+texto+" Bienvenido", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
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
                Snackbar.make(v, "El usuario ya esta registrado o no se encontro", Snackbar.LENGTH_LONG)
                            .show();
                break;

            default:
                break;
        }
    }
}
