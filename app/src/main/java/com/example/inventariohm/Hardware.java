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
    Spinner spHardware;
    private EditText etModelo2,etNSerie,etFecha;
    FloatingActionButton btnSiguiente;
    FloatingActionButton btnAtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        spHardware =findViewById(R.id.spHardware);

        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAtras = findViewById(R.id.btnAtras);
        etModelo2=findViewById(R.id.etModelo2);
        etFecha=findViewById(R.id.etFecha);
        etNSerie=findViewById(R.id.etNSerie);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       /*         if(spHardware.getSelectedItemPosition()==0) { //Posicion 0 Array = Item Pantalla
                    Intent i= new Intent(Hardware.this, Pantalla.class);
                    pasarDato(i);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==1) { //Posicion 1 Array = Item CPU
                    Intent i= new Intent(Hardware.this, CPU.class);
                    pasarDato(i);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==2) { //Posicion 2 Array = Item Raton
                    Intent i= new Intent(Hardware.this, Raton.class);
                    pasarDato(i);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==3) { //Posicion 3 Array = Item Teclado
                    Intent i= new Intent(Hardware.this, Teclado.class);
                    pasarDato(i);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==4) { //Posicion 4 Array = Item Portatil
                    Intent i= new Intent(Hardware.this, Portatil.class);
                    pasarDato(i);
                    startActivity(i);
                }else{
                    Toast.makeText(Hardware.this,"Debes seleccionar un dispositivo hardware", Toast.LENGTH_SHORT).show();
                }
*/
                Intent i = null;

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