package com.example.inventariohm;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Environment;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Moviliario2 extends AppCompatActivity {
    FloatingActionButton btnAntMovil2;

    Button btnPDFmoviliario;

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

       /* ActivityResultLauncher<String> requestPermisionLauncher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isAceptado ->{
                    if (isAceptado) Toast.makeText(this,"",Toast.LENGTH_LONG).show();
                    else Toast.makeText(this,"",Toast.LENGTH_LONG).show();
                }
        );*/

        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        btnPDFmoviliario=findViewById(R.id.btnPDFmoviliario);

        btnPDFmoviliario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDF();
            }
        });
    }
    public void crearPDF(){
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(2100, 2970, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        canvas.drawBitmap(bitmapEscala, 368, 20, paint);

        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(20);
        canvas.drawText("TITULO", 100, 150, titulo);

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14);

        /*
        String[] arrDescripcion = descripcionText.split("\n");
        int y = 200;
        for(int i = 0 ; i < arrDescripcion.length ; i++) {
            canvas.drawText(arrDescripcion[i], 10, y, descripcion);
            y += 15;
        }
        */


        pdfDocument.finishPage(pagina1);

        File file = new File(Environment.getExternalStorageDirectory(), "Archivo.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Se creo el PDF correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfDocument.close();

    }
    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                   // finish();
                }
            }
        }
    }
}