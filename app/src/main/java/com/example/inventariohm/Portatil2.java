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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;

public class Portatil2 extends AppCompatActivity {
    private Button btnPDFCPU2;
    FloatingActionButton btnAntCPU2;
    private EditText etObservaMoviliario6;
    private String modelo,serie,fecha,largo,ancho,alto,procesador,pulgadas,RAM,
            grafica,so,alimentacion,idioma,pantalla,almacenamiento,voltaje,
            intensidad,vga, hdmi,usb,dvi,dp,dvdr,observaciones,ram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portatil2);
        btnPDFCPU2 = findViewById(R.id.btnPDFCPU2);

        btnAntCPU2=findViewById(R.id.btnAntCPU2);
        etObservaMoviliario6=findViewById(R.id.etObservaMoviliario6);

        Bundle b = getIntent().getExtras();
        modelo = b.getString("modelo").toUpperCase();
        serie=b.getString("numSerie").toUpperCase();
        fecha=b.getString("fecha").toUpperCase();
        largo=b.getString("largo").toUpperCase();
        ancho=b.getString("ancho").toUpperCase();
        alto=b.getString("alto").toUpperCase();
        alto=b.getString("intensidad").toUpperCase();
        procesador=b.getString("procesador").toUpperCase();
        pulgadas=b.getString("pulgadas").toUpperCase();
        RAM=b.getString("ram").toUpperCase();
        grafica=b.getString("grafica").toUpperCase();
        so=b.getString("so").toUpperCase();
        alimentacion=b.getString("alimentacion").toUpperCase();
        pantalla=b.getString("pantalla").toUpperCase();
        voltaje=b.getString("voltaje").toUpperCase();
        idioma=b.getString("idioma").toUpperCase();
        vga=b.getString("vga").toUpperCase();
        hdmi=b.getString("hdmi").toUpperCase();
        usb=b.getString("usb").toUpperCase();
        dvi=b.getString("dvi").toUpperCase();
        dp=b.getString("dp").toUpperCase();
        dvdr=b.getString("dvd").toUpperCase();
        ram=b.getString("ram").toUpperCase();

        almacenamiento=b.getString("alamacenamiento").toUpperCase();
        intensidad=b.getString("intensidad").toUpperCase();


        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        btnPDFCPU2=findViewById(R.id.btnPDFCPU2);

        btnPDFCPU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFPortatil();
                cierre();


            }
        });
        btnAntCPU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void cierre(){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }

    public void crearPDFPortatil(){
        observaciones=etObservaMoviliario6.getText().toString();
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
        titulo.setTextSize(10);
        canvas.drawText("Cl. Gran Capitán, 52, Gijón".toUpperCase(), 43, 80, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("985 66 54 41   hm@hazmaker.org".toUpperCase(), 222, 80, titulo);


        //Modelo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Modelo".toUpperCase(), 43, 144, titulo);

        // Modelo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170, 24, false);
        canvas.drawBitmap(bitmapEscala, 104,129 , paint);

        //Modelo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(modelo+"", 107, 144, titulo);

        //NUMERO SERIE
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Nº Serie".toUpperCase(), 286, 144, titulo);

        // Numero Serie Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 24, false);
        canvas.drawBitmap(bitmapEscala, 343,129 , paint);

        //NUMERO SERIE INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(serie+"", 346, 144, titulo);

        // FECHA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90, 24, false);
        canvas.drawBitmap(bitmapEscala, 462,129 , paint);

        //FECHA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(fecha+"", 465, 144, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Largo".toUpperCase(), 43, 174, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 104, 159, paint);

        //Largo Info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(largo+"", 107, 174, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Ancho".toUpperCase(), 233, 174, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 289, 159, paint);

        //Ancho info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ancho+"", 292, 174, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alto".toUpperCase(), 420, 174, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 459, 159, paint);

        //Alto INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alto+"", 462, 174, titulo);

        //Procesador
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Procesador".toUpperCase(), 43, 206, titulo);

        // Procesador Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135, 24, false);
        canvas.drawBitmap(bitmapEscala, 123, 189, paint);

        //Procesador INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(procesador+"", 126, 206, titulo);

        //Pulgadas
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Pulgadas".toUpperCase(), 269, 206, titulo);

        // Pulgadas Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 88, 24, false);
        canvas.drawBitmap(bitmapEscala, 338, 189, paint);

        //Pulgadas INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(pulgadas+"", 341, 206, titulo);

        //RAM
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("RAM".toUpperCase(), 442, 206, titulo);

        // RAM Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 68, 24, false);
        canvas.drawBitmap(bitmapEscala, 484, 189, paint);

        //RAM INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(RAM+"", 487, 206, titulo);

        //Grafica
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Grafica".toUpperCase(), 43, 236, titulo);

        //Grafica Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 228, 24, false);
        canvas.drawBitmap(bitmapEscala, 106, 219, paint);

        //Grafica INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(grafica+"", 109, 236, titulo);

        //SO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("S.O.:".toUpperCase(), 360, 234, titulo);

        // SO  Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 160, 24, false);
        canvas.drawBitmap(bitmapEscala, 392, 219, paint);

        //SO INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(so+"", 395, 234, titulo);

        //Alimentacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alimentacion".toUpperCase(), 43, 264, titulo);

        // Alimentacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90, 24, false);
        canvas.drawBitmap(bitmapEscala, 137, 249, paint);

        //Alimentacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alimentacion+"", 140, 264, titulo);

        //Idioma
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Idioma".toUpperCase(), 238, 264, titulo);

        // Idioma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 289, 249, paint);

        //Idioma INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(idioma+"", 292, 264, titulo);

        //Pantalla
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Pantalla".toUpperCase(), 393, 264, titulo);

        // Pantalla Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 97, 24, false);
        canvas.drawBitmap(bitmapEscala, 455, 249, paint);

        //Pantalla INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(pantalla+"", 458, 264, titulo);

        //Almacenamiento
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Almacenamiento".toUpperCase(), 43, 294, titulo);

        //Almacenamiento Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 125, 24, false);
        canvas.drawBitmap(bitmapEscala, 159, 279, paint);

        //Almacenamiento INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(almacenamiento+"", 162, 294, titulo);

        //Voltaje
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Voltaje".toUpperCase(), 289, 294, titulo);

        // Voltaje Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 65, 24, false);
        canvas.drawBitmap(bitmapEscala, 338, 279, paint);

        //Voltaje INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(voltaje+"", 341, 294, titulo);

        //Intensidad
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Intensidad".toUpperCase(), 411, 294, titulo);

        // Intensidad Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 472, 279, paint);

        //Intensidad INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(intensidad+"", 475, 294, titulo);

        //VGA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("VGA".toUpperCase(), 43, 324, titulo);

        // VGA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 309, paint);

        //VGA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(vga+"", 83, 324, titulo);

        //HDMI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("HDMI".toUpperCase(), 242, 324, titulo);

        // HDMI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 279, 309, paint);

        //HDMI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(hdmi+"", 282, 324, titulo);

        //USB
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("USB".toUpperCase(), 435, 324, titulo);

        // USB Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 472, 309, paint);

        //USB INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(usb+"", 475, 324, titulo);

        //DVI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DVI".toUpperCase(), 43, 354, titulo);

        // DVI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 339, paint);

        //DVI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dvi+"", 83, 354, titulo);

        //DP
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DP".toUpperCase(), 242, 354, titulo);

        // DP Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 279, 339, paint);

        //DP INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dp+"", 282, 354, titulo);

        //DVDR
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DVDR".toUpperCase(), 435, 354, titulo);

        // DVDR Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 24, 24, false);
        canvas.drawBitmap(bitmapEscala, 500, 339, paint);

        //DVDR info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dvdr+"", 503, 354, titulo);

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Observaciones".toUpperCase(), 43, 393, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 405, 44, false);
        canvas.drawBitmap(bitmapEscala, 147, 369, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(observaciones+"", 150, 393, titulo);

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("FRONTAL".toUpperCase(), 179, 433, titulo);

        //IMAGEN FRONTAL
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 161, 440, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("N/S".toUpperCase(), 364, 433, titulo);

        //N/S Imagen
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 339, 440, paint);

        //Puertos
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("INTERNA".toUpperCase(), 62, 544, titulo);

        //IMAGEN Puetos
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 43, 550, paint);


        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("INCIDENCIAS".toUpperCase(), 453, 544, titulo);

        //IMAGEN incidencias
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 444, 550, paint);

        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 23, false);
        canvas.drawBitmap(bitmapEscala, 0, 665, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("FDO.", 255, 680, titulo);


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

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), modelo.toUpperCase()+"_"+fecha+".pdf");
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
