package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

    }

    public void BtnHardware(View v){
        Intent i = new Intent(this, Hardware.class);
        startActivity(i);
    }
    public void BtnMueble(View v){
        Intent i = new Intent(this, Moviliario.class);
        startActivity(i);

    }
    public void cerrar(){
        finish();
    }
    public void onBackPressed() {
        // No hacer nada para evitar que se cierre la aplicación
    }

}