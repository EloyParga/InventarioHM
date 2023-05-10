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
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Teclado extends AppCompatActivity {

    Button btnPDFmoviliario4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teclado);
        btnPDFmoviliario4 = findViewById(R.id.btnPDFmoviliario4);




        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        btnPDFmoviliario4=findViewById(R.id.btnPDFmoviliario4);

        btnPDFmoviliario4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFTeclado();
            }
        });
    }

    public void crearPDFTeclado(){
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();


        //Banner Superior
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 70, false);
        canvas.drawBitmap(bitmapEscala, 0, 43, paint);



        //LOGO
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        canvas.drawBitmap(bitmapEscala, 492, 48, paint);


        //DIRECCION
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("C. Gran Capitán, 52, Gijón", 43, 80, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("985 66 54 41   hm@hazmaker.org", 222, 80, titulo);

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("00/00/00", 420, 80, titulo);

        //Modelo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Modelo", 43, 144, titulo);

        // Modelo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 230, 24, false);
        canvas.drawBitmap(bitmapEscala, 104,129 , paint);

        //NUMERO SERIE
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Nº Serie", 360, 144, titulo);

        // Numero Serie Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135, 24, false);
        canvas.drawBitmap(bitmapEscala, 417,129 , paint);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Largo", 43, 194, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 104, 179, paint);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Ancho", 233, 194, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 289, 179, paint);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Alto", 420, 194, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 459, 179, paint);

        //Idioma
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Idioma", 43, 244, titulo);

        // Botones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135, 24, false);
        canvas.drawBitmap(bitmapEscala, 125, 229, paint);

        //Tipo Conexion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Tipo de Conexion", 270, 244, titulo);

        // Conexion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170, 24, false);
        canvas.drawBitmap(bitmapEscala, 382, 229, paint);


        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Observaciones", 43, 294, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 407, 147, false);
        canvas.drawBitmap(bitmapEscala, 145, 281, paint);

        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Incidencias", 253, 470, titulo);

        //Incidencias FRONTAL
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 248, 477, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Nº Serie", 48, 470, titulo);

        //N/S Imagen
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 43, 477, paint);

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("FROTAL", 455, 470, titulo);

        //FRONTAL Puetos
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 452, 477, paint);


        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 23, false);
        canvas.drawBitmap(bitmapEscala, 0, 665, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("FDO. Operador", 255, 680, titulo);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_dark);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 255, 114, false);
        canvas.drawBitmap(bitmapEscala, 170, 693, paint);


        // Espacio Blanco Firma
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pixelblanco);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 253, 112, false);
        canvas.drawBitmap(bitmapEscala, 171, 694, paint);


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