package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CPU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
    }

    public void avance(View vista){
       /* Intent i = new Intent(this, CPUdos.class);
        startActivity(i);*/
    }
    public void retroceder(View vista){
        Intent i=new Intent(this, Hardware.class);
        startActivity(i);
    }
}