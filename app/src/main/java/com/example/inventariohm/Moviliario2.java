package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Moviliario2 extends AppCompatActivity {
    FloatingActionButton btnAntMovil2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviliario2);

        btnAntMovil2 = findViewById(R.id.btnAntMovil2);

        btnAntMovil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}