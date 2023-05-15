package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CPU extends AppCompatActivity {
private FloatingActionButton floatingActionButton2;
private FloatingActionButton floatingActionButton;
private CheckBox cbDVDR;
Spinner spAlimentacionCPU2,spSOCPU;
EditText etLargoCPU,etAnchoCPU,etAltoCPU,etProcesadorCPU, etRAMCPU,
        etGraficaCPU,etHDD,etSSD,etVGACPU,etHDMICPU,etUSBCPU,etDVICPU,etDPCPU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
        floatingActionButton2=findViewById(R.id.floatingActionButton2);
        etLargoCPU=findViewById(R.id.etLargoCPU);
        etAnchoCPU=findViewById(R.id.etAnchoCPU);
        etAltoCPU=findViewById(R.id.etAltoCPU);
        etProcesadorCPU=findViewById(R.id.etCantidad);
        etRAMCPU=findViewById(R.id.etUbicacion);
        etGraficaCPU=findViewById(R.id.etGraficaCPU);
        etHDD=findViewById(R.id.etHDD);
        etSSD=findViewById(R.id.etSSD);
        etVGACPU=findViewById(R.id.etVGACPU);
        etHDMICPU=findViewById(R.id.etHDMICPU);
        etUSBCPU=findViewById(R.id.etUSBCPU);
        etDVICPU=findViewById(R.id.etDVICPU);
        etDPCPU=findViewById(R.id.etDPCPU);
        spAlimentacionCPU2=findViewById(R.id.spAlimentacionCPU2);
        spSOCPU=findViewById(R.id.spSOCPU);
        cbDVDR=findViewById(R.id.cbDVDR);


        Bundle b = getIntent().getExtras();
        String modelo2 = b.getString("modelo");
        String numSerie2 = b.getString("numSerie");
        String fecha2= b.getString("fecha");


        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String modelo = modelo2;
                String fecha=fecha2;
                String numSerie = numSerie2;
                String largo = etLargoCPU.getText().toString();
                String ancho = etAnchoCPU.getText().toString();
                String alto = etAltoCPU.getText().toString();
                String procesador = etProcesadorCPU.getText().toString();
                String ram = etRAMCPU.getText().toString();
                String grafica = etGraficaCPU.getText().toString();
                String hdd = etHDD.getText().toString();
                String ssd = etSSD.getText().toString();
                String vga = etVGACPU.getText().toString();
                String hdmi = etHDMICPU.getText().toString();
                String usb = etUSBCPU.getText().toString();
                String dvi = etDVICPU.getText().toString();
                String dp = etDPCPU.getText().toString();
                String alimentacion = spAlimentacionCPU2.getSelectedItem().toString();
                String so = spSOCPU.getSelectedItem().toString();
                String dvdr="";
                if (cbDVDR.isChecked())
                    dvdr="X";
                Intent i = new Intent(CPU.this, CPU2.class);
                Bundle b = new Bundle();
                b.putString("modelo",modelo);
                b.putString("fecha",fecha);
                b.putString("numSerie",numSerie);
                b.putString("largo",largo);
                b.putString("ancho",ancho);
                b.putString("alto",alto);
                b.putString("procesador",procesador);
                b.putString("ram",ram);
                b.putString("grafica",grafica);
                b.putString("hdd",hdd);
                b.putString("ssd",ssd);
                b.putString("vga",vga);
                b.putString("hdmi",hdmi);
                b.putString("usb",usb);
                b.putString("dvi",dvi);
                b.putString("dp",dp);
                b.putString("alimentacion",alimentacion);
                b.putString("so",so);
                b.putString("dvdr",dvdr);
                i.putExtras(b);
                startActivity(i);


            };
        });
        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroceder();
            };
        });
    }


    public void retroceder(){
        finish();
    }
}