package com.example.inventariohm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CPU extends AppCompatActivity {
private FloatingActionButton floatingActionButton2;
private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
        floatingActionButton2=findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avance();
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

    public void avance(){
       Intent i = new Intent(this, CPU2.class);
        startActivity(i);
    }
    public void retroceder(){
        finish();
    }
}