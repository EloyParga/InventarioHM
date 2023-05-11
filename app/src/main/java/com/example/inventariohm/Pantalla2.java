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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Pantalla2 extends AppCompatActivity {
    Button btnPDFmoviliario3;
    EditText etObservaMoviliario3;
    private String modelo,serie,fecha,largo,ancho,alto, resolucion,pulgadas,
            alimentacion,tipopantalla,vga,hdmi,usb,dvi,dp,observaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        btnPDFmoviliario3 = findViewById(R.id.btnPDFmoviliario3);
        etObservaMoviliario3 =findViewById(R.id.etObservaMoviliario3);

        Bundle b = getIntent().getExtras();
        modelo = b.getString("modelo");
        serie=b.getString("numSerie");
        fecha=b.getString("fecha");
        largo=b.getString("largo");
        ancho=b.getString("ancho");
        alto=b.getString("alto");
        resolucion=b.getString("conexion");
        pulgadas=b.getString("pulgadadas");
        alimentacion=b.getString("conectividad");
        tipopantalla=b.getString("tipopantalla");
        vga=b.getString("vga");
        hdmi=b.getString("hdmi");
        usb=b.getString("usb");
        dvi=b.getString("dvi");
        dp=b.getString("dp");





        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        btnPDFmoviliario3=findViewById(R.id.btnPDFmoviliario3);

        btnPDFmoviliario3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFPantalla();
            }
        });
    }

    public void crearPDFPantalla(){
        observaciones=etObservaMoviliario3.getText().toString();
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
        canvas.drawText(modelo.toUpperCase(), 107, 144, titulo);

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
        canvas.drawText(serie.toUpperCase(), 346, 144, titulo);

        // FECHA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90, 24, false);
        canvas.drawBitmap(bitmapEscala, 462,129 , paint);

        //FECHA INFO
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

        //Alto INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alto.toUpperCase(), 462, 174, titulo);

        //RESOLUCION
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Resolucion".toUpperCase(), 43, 204, titulo);

        // Resolucion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170, 24, false);
        canvas.drawBitmap(bitmapEscala, 125, 189, paint);

        //RESOLUCION INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(resolucion.toUpperCase(), 128, 204, titulo);

        //Pulgadas
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Pulgadas".toUpperCase(), 315, 204, titulo);

        // Pulgadas Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170, 24, false);
        canvas.drawBitmap(bitmapEscala, 382, 189, paint);

        //Pulgadas ifno
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(pulgadas.toUpperCase(), 385, 204, titulo);

        //Alimentacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alimentacion".toUpperCase(), 43, 234, titulo);

        //Alimentacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 150, 24, false);
        canvas.drawBitmap(bitmapEscala, 130, 219, paint);

        //Alimentacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alimentacion.toUpperCase(), 133, 234, titulo);

        //Tipo final
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Tipo de Pantalla".toUpperCase(), 300, 234, titulo);

        // Tipo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 150, 24, false);
        canvas.drawBitmap(bitmapEscala, 402, 219, paint);

        //Tipo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(tipopantalla.toUpperCase(), 405, 234, titulo);


        //VGA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("VGA".toUpperCase(), 43, 264, titulo);

        // VGA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 249, paint);

        //VGA Info
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

        //HDMI info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(hdmi.toUpperCase(), 282, 264, titulo);

        //USB
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("USB".toUpperCase(), 435, 279, titulo);

        // USB Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 472, 264, paint);

        //USB INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(usb.toUpperCase(), 475, 279, titulo);

        //DVI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("DVI".toUpperCase(), 43, 294, titulo);

        // DVI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 24, false);
        canvas.drawBitmap(bitmapEscala, 80, 279, paint);

        //DVI INFO
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

        //DP INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(dp+"".toUpperCase(), 282, 294, titulo);


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
        titulo.setTextSize(13);
        canvas.drawText("FRONTAL", 179, 423, titulo);

        //IMAGEN FRONTAL
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 161, 430, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("N/S", 364, 423, titulo);

        //N/S Imagen
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 330, 430, paint);

        //Puertos
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("INTERNA", 62, 544, titulo);

        //IMAGEN Puetos
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 43, 550, paint);


        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("INCIDENCIAS", 453, 544, titulo);

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
        titulo.setTextSize(13);
        canvas.drawText("FDO.", 260, 680, titulo);


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
