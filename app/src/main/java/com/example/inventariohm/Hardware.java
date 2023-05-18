package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Hardware extends AppCompatActivity {
    //Variables
    Spinner spHardware;
    private EditText etModelo2,etNSerie,etFecha;
    FloatingActionButton btnSiguiente;
    FloatingActionButton btnAtras;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);

        //Vincular variables
        spHardware =findViewById(R.id.spHardware);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAtras = findViewById(R.id.btnAtras);
        etModelo2=findViewById(R.id.etModelo2);
        etFecha=findViewById(R.id.etFecha);
        etNSerie=findViewById(R.id.etNSerie);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                //Inicia una activity distinta depèndiendo del Item del spinner
                switch (spHardware.getSelectedItemPosition()) {
                    case 0:
                        i = new Intent(Hardware.this, Pantalla.class);
                        break;
                    case 1:
                        i = new Intent(Hardware.this, CPU.class);
                        break;
                    case 2:
                        i = new Intent(Hardware.this, Raton.class);
                        break;
                    case 3:
                        i = new Intent(Hardware.this, Teclado.class);
                        break;
                    case 4:
                        i = new Intent(Hardware.this, Portatil.class);
                        break;
                    default:
                        Toast.makeText(Hardware.this, "Debes seleccionar un dispositivo hardware", Toast.LENGTH_SHORT).show();
                        return;
                }

                if (i != null) {
                    pasarDato(i);
                }

        }
        });

        //Boton para ir a la activity anterior
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void pasarDato(Intent i){
        String modelo = etModelo2.getText().toString();
        String numSerie = etNSerie.getText().toString();
        String fecha = etFecha.getText().toString();
        Bundle b = new Bundle();
        b.putString("modelo",modelo);
        b.putString("numSerie",numSerie);
        b.putString("fecha",fecha);
        i.putExtras(b);
        startActivity(i);

    }



}