package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Pantalla extends AppCompatActivity {
    private EditText etLargoPantalla;

    FloatingActionButton btnSigMovil3;
    private EditText etAnchoPantalla;
    private EditText etAltoPantalla;
    private Spinner spConexion;
    private EditText etPulgadas;
    private Spinner spConectividad;
    private Spinner spIdioma;
    private EditText etVGA;
    private EditText etHDMI;
    private EditText etUSB;
    private EditText etDVI;
    private EditText etDP;
    FloatingActionButton btnAntMovil4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla);

        Bundle b = getIntent().getExtras();
        String modelo2 = b.getString("modelo");
        String numSerie2 = b.getString("numSerie");
        String fecha2= b.getString("fecha");

        btnSigMovil3 = findViewById(R.id.btnSigMovil3);

        etLargoPantalla = findViewById(R.id.etLargoPantalla);
        btnAntMovil4 = findViewById(R.id.btnAntMovil4);
        etAnchoPantalla = findViewById(R.id.etAnchoPantalla);
        etAltoPantalla = findViewById(R.id.etAltoPantalla);
        spConexion = findViewById(R.id.spConexion);
        etPulgadas = findViewById(R.id.etPulgadas);
        spConectividad = findViewById(R.id.spConectividad);
        spIdioma = findViewById(R.id.spIdioma);
        etVGA = findViewById(R.id.etVGA);
        etHDMI = findViewById(R.id.etHDMI);
        etUSB = findViewById(R.id.etUSB);
        etDVI = findViewById(R.id.etDVI);
        etDP = findViewById(R.id.etDP);


        btnSigMovil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modelo = modelo2;
                String fecha=fecha2;
                String numSerie = numSerie2;
                String largo = etLargoPantalla.getText().toString();
                String ancho = etAnchoPantalla.getText().toString();
                String alto = etAltoPantalla.getText().toString();
                String conexion = spConexion.getSelectedItem().toString();
                String pulgadas= etPulgadas.getText().toString();
                String conectividad = spConectividad.getSelectedItem().toString();
                String tipo = spIdioma.getSelectedItem().toString();
                String vga= etVGA.getText().toString();
                String hdmi= etHDMI.getText().toString();
                String usb= etUSB.getText().toString();
                String dvi= etDVI.getText().toString();
                String dp= etDP.getText().toString();

                Intent i = new Intent(Pantalla.this, Pantalla2.class);
                Bundle b = new Bundle();
                b.putString("modelo",modelo);
                b.putString("numSerie",numSerie);
                b.putString("fecha",fecha);
                b.putString("largo",largo);
                b.putString("ancho",ancho);
                b.putString("alto",alto);
                b.putString("conexion",conexion);
                b.putString("pulgadadas",pulgadas);
                b.putString("conectividad",conectividad);
                b.putString("tipopantalla",tipo);
                b.putString("vga",vga);
                b.putString("hdmi",hdmi);
                b.putString("usb",usb);
                b.putString("dvi",dvi);
                b.putString("dp",dp);

                i.putExtras(b);
                startActivity(i);
            }
        });

        btnAntMovil4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
