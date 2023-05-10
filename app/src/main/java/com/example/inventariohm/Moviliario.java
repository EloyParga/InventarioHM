package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Moviliario extends AppCompatActivity {
    private FloatingActionButton btnSigMovil;
    private FloatingActionButton btnAntMovil;

    private EditText etNSMoviliario;
    private EditText etFecha;
    private EditText etDesMoviliario;
    private EditText etLargoCPU;
    private EditText etAnchoCPU;
    private EditText etAltoCPU;
    private EditText etCantidad;
    private EditText etUbicacion;
    private EditText etPIMoviliario;
    private EditText etPAMoviliario;
    private EditText etNotaMoviliario;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviliario);

        btnSigMovil = findViewById(R.id.btnSigMovil);
        btnAntMovil = findViewById(R.id.btnAntMovil);

        etNSMoviliario = findViewById(R.id.etNSMoviliario);
        etFecha = findViewById(R.id.etFecha);
        etDesMoviliario = findViewById(R.id.etDesMoviliario);
        etLargoCPU = findViewById(R.id.etLargoCPU);
        etAnchoCPU = findViewById(R.id.etAnchoCPU);
        etAltoCPU = findViewById(R.id.etAltoCPU);
        etCantidad = findViewById(R.id.etCant);
        etUbicacion = findViewById(R.id.etUbi);
        etPIMoviliario = findViewById(R.id.etPIMoviliario);
        etPAMoviliario = findViewById(R.id.etPAMoviliario);
        etNotaMoviliario = findViewById(R.id.etNotaMoviliario);


        btnSigMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numSerie = etNSMoviliario.getText().toString();
                String fecha = etFecha.getText().toString();
                String descripcion= etDesMoviliario.getText().toString();
                String largo = etLargoCPU.getText().toString();
                String ancho = etAnchoCPU.getText().toString();
                String alto = etAltoCPU.getText().toString();
                String cantidad = etCantidad.getText().toString();
                String ubicacion = etUbicacion.getText().toString();
                String precioInicial= etPIMoviliario.getText().toString();
                String precioActual = etPAMoviliario.getText().toString();
                String notas = etNotaMoviliario.getText().toString();

                Intent i = new Intent(Moviliario.this, Moviliario2.class);

                Bundle b = new Bundle();

                b.putString("numSerie",numSerie);
                b.putString("fecha",fecha);
                b.putString("descripcion",descripcion);
                b.putString("largo",largo);
                b.putString("ancho",ancho);
                b.putString("alto",alto);
                b.putString("cantidad",cantidad);
                b.putString("ubicacion",ubicacion);
                b.putString("precioInicial",precioInicial);
                b.putString("precioActual",precioActual);
                b.putString("notas",notas);

                i.putExtras(b);

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