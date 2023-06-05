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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Portatil2 extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    private ImageView ivPortatilFrontal,ivPortatilSerie,ivPortatilPuertos,ivPortatilIncidencias;
    private Uri UriImagen1,UriImagen2,UriImagen3,UriImagen4;
    private int selector;
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
        ivPortatilFrontal=findViewById(R.id.ivPortatilFrontal);
        ivPortatilSerie=findViewById(R.id.ivPortatilSerie);
        ivPortatilPuertos=findViewById(R.id.ivPortatilPuertos);
        ivPortatilIncidencias=findViewById(R.id.ivPortatilIncidencias);

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

        ivPortatilFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=1;
                if (ContextCompat.checkSelfPermission(Portatil2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Portatil2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivPortatilSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=2;
                if (ContextCompat.checkSelfPermission(Portatil2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Portatil2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivPortatilPuertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=3;
                if (ContextCompat.checkSelfPermission(Portatil2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Portatil2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivPortatilIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=4;
                if (ContextCompat.checkSelfPermission(Portatil2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Portatil2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });



        btnPDFCPU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    crearPDFPortatil();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    private void abrirGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_CODE_GALLERY);
    }
    public void crearPDFPortatil() throws IOException {
        int m=3;
        observaciones=etObservaMoviliario6.getText().toString();
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(595*m, 842*m, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();


        //Banner Superior
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595*m, 70*m, false);
        canvas.drawBitmap(bitmapEscala, 0*m, 43*m, paint);



        //LOGO
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 60*m, 60*m, false);
        canvas.drawBitmap(bitmapEscala, 492*m, 48*m, paint);


        //DIRECCION
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Cl. Gran Capitán, 52. Gijón".toUpperCase(), 43*m, 80*m, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("+34 985 66 54 41   hm@hazmaker.org".toUpperCase(), 222*m, 80*m, titulo);


        //Modelo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Modelo".toUpperCase(), 43*m, 144*m, titulo);

        // Modelo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 104*m,129*m , paint);

        //Modelo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(modelo+"", 107*m, 144*m, titulo);

        //NUMERO SERIE
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Nº Serie".toUpperCase(), 286*m, 144*m, titulo);

        // Numero Serie Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 343*m,129*m , paint);

        //NUMERO SERIE INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(serie+"", 346*m, 144*m, titulo);

        // FECHA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 462*m,129*m , paint);

        //FECHA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(fecha+"", 465*m, 144*m, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Largo".toUpperCase(), 43*m, 174*m, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 104*m, 159*m, paint);

        //Largo Info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(largo+" cm", 107*m, 174*m, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Ancho".toUpperCase(), 233*m, 174*m, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 289*m, 159*m, paint);

        //Ancho info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ancho+" cm", 292*m, 174*m, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alto".toUpperCase(), 420*m, 174*m, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 459*m, 159*m, paint);

        //Alto INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alto+" cm", 462*m, 174*m, titulo);

        //Procesador
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Procesador".toUpperCase(), 43*m, 206*m, titulo);

        // Procesador Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 123*m, 189*m, paint);

        //Procesador INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(procesador+"", 126*m, 206*m, titulo);

        //Pulgadas
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Pulgadas".toUpperCase(), 269*m, 206*m, titulo);

        // Pulgadas Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 88*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 338*m, 189*m, paint);

        //Pulgadas INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(pulgadas+"", 341*m, 206*m, titulo);

        //RAM
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("RAM".toUpperCase(), 442*m, 206*m, titulo);

        // RAM Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 68*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 484*m, 189*m, paint);

        //RAM INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(RAM+"", 487*m, 206*m, titulo);

        //Grafica
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Grafica".toUpperCase(), 43*m, 236*m, titulo);

        //Grafica Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 228*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 106*m, 219*m, paint);

        //Grafica INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(grafica+"", 109*m, 236*m, titulo);

        //SO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("S.O.:".toUpperCase(), 360*m, 234*m, titulo);

        // SO  Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 160*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 392*m, 219*m, paint);

        //SO INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(so+"", 395*m, 234*m, titulo);

        //Alimentacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alimentacion".toUpperCase(), 43*m, 264*m, titulo);

        // Alimentacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 137*m, 249*m, paint);

        //Alimentacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alimentacion+"", 140*m, 264*m, titulo);

        //Idioma
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Idioma".toUpperCase(), 238*m, 264*m, titulo);

        // Idioma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 289*m, 249*m, paint);

        //Idioma INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(idioma+"", 292*m, 264*m, titulo);

        //Pantalla
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Pantalla".toUpperCase(), 393*m, 264*m, titulo);

        // Pantalla Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 97*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 455*m, 249*m, paint);

        //Pantalla INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(pantalla+"", 458*m, 264*m, titulo);

        //Almacenamiento
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Almacenamiento".toUpperCase(), 43*m, 294*m, titulo);

        //Almacenamiento Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 125*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 159*m, 279*m, paint);

        //Almacenamiento INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(almacenamiento+"", 162*m, 294*m, titulo);

        //Voltaje
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Voltaje".toUpperCase(), 289*m, 294*m, titulo);

        // Voltaje Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 65*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 338*m, 279*m, paint);

        //Voltaje INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(voltaje+"", 341*m, 294*m, titulo);

        //Intensidad
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Intensidad".toUpperCase(), 411*m, 294*m, titulo);

        // Intensidad Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 472*m, 279*m, paint);

        //Intensidad INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(intensidad+"", 475*m, 294*m, titulo);

        //VGA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("VGA".toUpperCase(), 43*m, 324*m, titulo);

        // VGA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 80*m, 309*m, paint);

        //VGA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(vga+"", 83*m, 324*m, titulo);

        //HDMI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("HDMI".toUpperCase(), 242*m, 324*m, titulo);

        // HDMI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 279*m, 309*m, paint);

        //HDMI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(hdmi+"", 282*m, 324*m, titulo);

        //USB
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("USB".toUpperCase(), 435*m, 324*m, titulo);

        // USB Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 472*m, 309*m, paint);

        //USB INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(usb+"", 475*m, 324*m, titulo);

        //DVI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DVI".toUpperCase(), 43*m, 354*m, titulo);

        // DVI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 80*m, 339*m, paint);

        //DVI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dvi+"", 83*m, 354*m, titulo);

        //DP
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DP".toUpperCase(), 242*m, 354*m, titulo);

        // DP Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 279*m, 339*m, paint);

        //DP INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dp+"", 282*m, 354*m, titulo);

        //DVDR
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DVDR".toUpperCase(), 435*m, 354*m, titulo);

        // DVDR Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 24*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 500*m, 339*m, paint);

        //DVDR info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dvdr+"", 503*m, 354*m, titulo);

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Observaciones".toUpperCase(), 43*m, 393*m, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 405*m, 44*m, false);
        canvas.drawBitmap(bitmapEscala, 147*m, 369*m, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        String[] arrDescripcion = observaciones.split("\n");
        int y = 381*m;
        for(int i = 0 ; i < arrDescripcion.length&&i<=2 ; i++) {
            canvas.drawText(arrDescripcion[i], 150*m, y, titulo);
            y += 15*m;
        }

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("FRONTAL".toUpperCase(), 179*m, 433*m, titulo);

        //IMAGEN FRONTAL
        if (UriImagen1 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 161*m, 440*m, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("N/S".toUpperCase(), 364*m, 433*m, titulo);

        //N/S Imagen
        if (UriImagen2 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 339*m, 440*m, paint);

        //Puertos
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("INTERNA".toUpperCase(), 62*m, 544*m, titulo);

        //IMAGEN Puetos
        if (UriImagen3 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen3);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 43*m, 550*m, paint);


        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("INCIDENCIAS".toUpperCase(), 453*m, 544*m, titulo);

        //IMAGEN incidencias
        if (UriImagen4 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen4);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 444*m, 550*m, paint);

        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595*m, 23*m, false);
        canvas.drawBitmap(bitmapEscala, 0*m, 665*m, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("FDO. OPERADOR", 255*m, 680*m, titulo);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_dark);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 255*m, 114*m, false);
        canvas.drawBitmap(bitmapEscala, 170*m, 693*m, paint);


        // Espacio Blanco Firma
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pixelblanco);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 253*m, 112*m, false);
        canvas.drawBitmap(bitmapEscala, 171*m, 694*m, paint);


        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(14*m);

        /*
        String[] arrDescripcion = descripcionText.split("\n");
        int y = 200;
        for(int i = 0 ; i < arrDescripcion.length ; i++) {
            canvas.drawText(arrDescripcion[i], 10, y, descripcion);
            y += 15;
        }
        */


        pdfDocument.finishPage(pagina1);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), modelo.toUpperCase()+".pdf");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            switch (selector) {
                case 1:
                    UriImagen1 = selectedImage;
                    ivPortatilFrontal.setImageURI(UriImagen1);
                    break;
                case 2:
                    UriImagen2 = selectedImage;
                    ivPortatilSerie.setImageURI(UriImagen2);
                    break;
                case 3:
                    UriImagen3 = selectedImage;
                    ivPortatilPuertos.setImageURI(UriImagen3);
                    break;
                case 4:
                    UriImagen4 = selectedImage;
                    ivPortatilIncidencias.setImageURI(UriImagen4);
                    break;

            }

        }
    }
    }
