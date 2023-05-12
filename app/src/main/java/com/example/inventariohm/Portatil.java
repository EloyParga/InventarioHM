package com.example.inventariohm;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;

public class Portatil extends AppCompatActivity {

    FloatingActionButton btnSigMovil2;
    private EditText etLargoPantalla;
    private EditText etAnchoPantalla;
    private EditText etAltoPantalla;
    private EditText etProcesador;
    private EditText etPulgadas;
    private EditText etGrafica;
    private EditText etRam;
    private EditText etAlimentacionPort;
    private EditText etVoltaje;
    private EditText etIntensidad;
    private EditText etVGA;
    private EditText etUSB;
    private EditText etHDMI;
    private EditText etDP;
    private EditText etAlmacenamiento2;
    private EditText etDVI;
    private CheckBox cbDVD;
    private Spinner spConectividad;
    private Spinner spSO;
    private Spinner spIdioma;
    private Spinner spTipoPantalla;
    private Spinner spConexion;
    String dvd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portatil);

        Bundle b = getIntent().getExtras();
        String modelo2 = b.getString("modelo");
        String numSerie2 = b.getString("numSerie");
        String fecha2= b.getString("fecha");

        btnSigMovil2 = findViewById(R.id.btnSigMovil2);
        cbDVD = findViewById(R.id.cbDVD);
        etLargoPantalla = findViewById(R.id.etLargoPantalla);
        etAnchoPantalla = findViewById(R.id.etAnchoPantalla);
        etAltoPantalla = findViewById(R.id.etAltoPantalla);
        etIntensidad = findViewById(R.id.etIntensidad);
        etGrafica = findViewById(R.id.etGrafica);
        etRam = findViewById(R.id.etRam);
        etAlimentacionPort = findViewById(R.id.etAlimentacionPort);
        etVoltaje = findViewById(R.id.etVoltaje);
        spConexion = findViewById(R.id.spConexion);
        spSO = findViewById(R.id.spSO);
        spTipoPantalla = findViewById(R.id.spTipoPantalla);

        etProcesador = findViewById(R.id.etProcesador);
        etPulgadas = findViewById(R.id.etPulgadas);
        spConectividad = findViewById(R.id.spConectividad);
        spIdioma = findViewById(R.id.spIdioma);
        etVGA = findViewById(R.id.etVGA);
        etHDMI = findViewById(R.id.etHDMI);
        etUSB = findViewById(R.id.etUSB);
        etDVI = findViewById(R.id.etDVI);
        etDP = findViewById(R.id.etDP);
        etAlmacenamiento2=findViewById(R.id.etAlmacenamiento2);

        btnSigMovil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dvd="";
                String modelo = modelo2;
                String fecha=fecha2;
                String alamacenamiento=etAlmacenamiento2.getText().toString();
                String numSerie = numSerie2;

                if(cbDVD.isChecked())
                    dvd="X";
                String largo = etLargoPantalla.getText().toString();
                String ancho = etAnchoPantalla.getText().toString();
                String alto = etAltoPantalla.getText().toString();
                String intensidad = etIntensidad.getText().toString();
                String grafica = etGrafica.getText().toString();
                String ram = etRam.getText().toString();
                String alimentacion = etAlimentacionPort.getText().toString();
                String voltaje = etVoltaje.getText().toString();
                String conexion = spConexion.getSelectedItem().toString();
                String so = spSO.getSelectedItem().toString();
                String idioma = spIdioma.getSelectedItem().toString();
                String conectividad = spConectividad.getSelectedItem().toString();
                String pantalla = spTipoPantalla.getSelectedItem().toString();
                String procesador = etProcesador.getText().toString();
                String pulgadas = etPulgadas.getText().toString();
                String vga = etVGA.getText().toString();
                String hdmi = etHDMI.getText().toString();
                String usb = etUSB.getText().toString();
                String dvi = etDVI.getText().toString();
                String dp = etDP.getText().toString();


                Intent i = new Intent(Portatil.this, Portatil2.class);
                Bundle b = new Bundle();
                b.putString("modelo",modelo);
                b.putString("numSerie",numSerie);
                b.putString("fecha",fecha);
                b.putString("alamacenamiento",alamacenamiento);
                b.putString("dvd",dvd);
                b.putString("largo",largo);
                b.putString("ancho",ancho);
                b.putString("alto",alto);
                b.putString("intensidad",intensidad);
                b.putString("grafica",grafica);
                b.putString("ram",ram);
                b.putString("alimentacion",alimentacion);
                b.putString("voltaje",voltaje);
                b.putString("conexion",conexion);//?
                b.putString("so",so);
                b.putString("idioma",idioma);
                b.putString("conectividad",conectividad);
                b.putString("pantalla",pantalla);
                b.putString("procesador",procesador);
                b.putString("pulgadas",pulgadas);
                b.putString("vga",vga);
                b.putString("hdmi",hdmi);
                b.putString("usb",usb);
                b.putString("dvi",dvi);
                b.putString("dp",dp);


                i.putExtras(b);
                startActivity(i);
            }
        });

    }


}
