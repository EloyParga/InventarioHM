package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Hardware extends AppCompatActivity {
    Spinner spHardware;
    FloatingActionButton btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        spHardware =findViewById(R.id.spHardware);
        btnSiguiente = findViewById(R.id.btnSiguiente);


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spHardware.getSelectedItemPosition()==0) { //Posicion 0 Array = Item Pantalla
                    Intent i= new Intent(Hardware.this, Pantalla.class);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==1) { //Posicion 1 Array = Item CPU
                    Intent i= new Intent(Hardware.this, CPU.class);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==2) { //Posicion 2 Array = Item Raton
                    Intent i= new Intent(Hardware.this, Raton.class);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==3) { //Posicion 3 Array = Item Teclado
                    Intent i= new Intent(Hardware.this, Teclado.class);
                    startActivity(i);
                }else if(spHardware.getSelectedItemPosition()==4) { //Posicion 4 Array = Item Portatil
                    Intent i= new Intent(Hardware.this, Portatil.class);
                    startActivity(i);
                }else{
                    Toast.makeText(Hardware.this,"Debes seleccionar un dispositivo hardware", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }




}