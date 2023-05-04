package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Moviliario extends AppCompatActivity {
    FloatingActionButton btnSigMovil;
    FloatingActionButton btnAntMovil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviliario);

        btnSigMovil = findViewById(R.id.btnSigMovil);
        btnAntMovil = findViewById(R.id.btnAntMovil);

        btnSigMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Moviliario.this, Moviliario2.class);
                startActivity(i);
            }
        });
        btnAntMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}