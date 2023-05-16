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

public class CPU2 extends AppCompatActivity {
    FloatingActionButton btnAntCPU;


    EditText etObservaMoviliario;
    private String modelo,serie,fecha,largo,ancho,alto,procesador,ram,
    alimentacion,ssd,hdd,vga,hdmi,usb,dvi,dp,dvdr,observaciones;

    Button btnPDFCPU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu2);

        etObservaMoviliario= findViewById(R.id.etObservaMoviliario);
        btnAntCPU= findViewById(R.id.btnAntCPU);

        Bundle b = getIntent().getExtras();
        modelo = b.getString("modelo");
        serie = b.getString("numSerie");
        fecha= b.getString("fecha");
        largo= b.getString("largo");
        ancho= b.getString("ancho");
        alto= b.getString("alto");
        procesador= b.getString("procesador");
        ram= b.getString("ram");
        alimentacion= b.getString("grafica");
        ssd= b.getString("ssd");
        hdd= b.getString("hdd");
        vga= b.getString("vga");
        hdmi= b.getString("hdmi");
        usb= b.getString("usb");
        dvi= b.getString("dvi");
        dp= b.getString("dp");
        dvdr= b.getString("dvdr");



        btnPDFCPU = findViewById(R.id.btnPDFCPU);

        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        btnPDFCPU=findViewById(R.id.btnPDFCPU);

        btnPDFCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observaciones=etObservaMoviliario.getText().toString();

                crearPDFCPU();
                cierre();

            }
        });
        btnAntCPU.setOnClickListener(new View.OnClickListener() {
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

    public void crearPDFCPU(){
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
        canvas.drawText((modelo.toUpperCase()), 107, 144, titulo);

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
        canvas.drawText((serie.toUpperCase()), 346, 144, titulo);

        // FECHA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90, 24, false);
        canvas.drawBitmap(bitmapEscala, 462,129 , paint);

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText(fecha.toUpperCase(), 465, 144, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Largo".toUpperCase(), 43, 174, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 104, 159, paint);

        //Largo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(largo.toUpperCase(), 107, 174, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Ancho".toUpperCase(), 233, 174, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 289, 159, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ancho.toUpperCase(), 292, 174, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alto".toUpperCase(), 420, 174, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 459, 159, paint);

        //Alto IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alto.toUpperCase(), 462, 174, titulo);

        //Procesador
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Procesador".toUpperCase(), 43, 204, titulo);

        // Procesador Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 240, 24, false);
        canvas.drawBitmap(bitmapEscala, 118, 189, paint);

        //Procesador INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText((procesador.toUpperCase()), 121, 204, titulo);

        //RAM
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("RAM".toUpperCase(), 384, 204, titulo);

        // RAM Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 130, 24, false);
        canvas.drawBitmap(bitmapEscala, 422, 189, paint);

        //RAM INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ram.toUpperCase(), 425, 204, titulo);

        //Alimentacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alimentacion".toUpperCase(), 43, 234, titulo);

        //Alimentacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 150, 24, false);
        canvas.drawBitmap(bitmapEscala, 133, 219, paint);

        //Alimentacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alimentacion.toUpperCase(), 136, 234, titulo);

        //SSD
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("SSD".toUpperCase(), 300, 234, titulo);

        // SSD final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 332, 219, paint);

        //SSD INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ssd.toUpperCase(), 335, 234, titulo);

        //HDD final
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("HDD".toUpperCase(), 435, 234, titulo);

        // HDD final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 472, 219, paint);

        //HDD INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(hdd.toUpperCase(), 475, 234, titulo);

        //VGA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("VGA".toUpperCase(), 43, 264, titulo);

        // VGA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 249, paint);

        //VGA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(vga.toUpperCase(), 83, 264, titulo);

        //HDMI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("HDMI".toUpperCase(), 242, 264, titulo);

        // HDMI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 279, 249, paint);

        //HDMI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(hdmi.toUpperCase(), 282, 264, titulo);

        //USB
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("USB".toUpperCase(), 435, 264, titulo);

        // USB Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 472, 249, paint);

        //USB INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(usb.toUpperCase(), 475, 264, titulo);

        //DVI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DVI".toUpperCase(), 43, 294, titulo);

        // DVI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 279, paint);

        //DVI IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dvi.toUpperCase(), 83, 294, titulo);

        //DP
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DP".toUpperCase(), 242, 294, titulo);

        // DP Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 279, 279, paint);

        //DP IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dp.toUpperCase(), 282, 294, titulo);

        //DVDR
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DVDR".toUpperCase(), 435, 294, titulo);

        // DVDR Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 24, 24, false);
        canvas.drawBitmap(bitmapEscala, 500, 279, paint);

        //DVDR INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dvdr.toUpperCase(), 503, 294, titulo);

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Observaciones".toUpperCase(), 43, 333, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 405, 75, false);
        canvas.drawBitmap(bitmapEscala, 147, 309, paint);

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(observaciones.toUpperCase(), 150, 333, titulo);

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("FRONTAL".toUpperCase(), 179, 423, titulo);

        //IMAGEN FRONTAL
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 161, 430, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("N/S".toUpperCase(), 364, 423, titulo);

        //N/S Imagen
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 330, 430, paint);

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
        canvas.drawText("FDO.".toUpperCase(), 255, 680, titulo);


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