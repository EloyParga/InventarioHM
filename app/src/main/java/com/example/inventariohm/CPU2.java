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

public class CPU2 extends AppCompatActivity {
    //Variables

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    private int selector;
    private Uri UriImagen1,UriImagen2,UriImagen3,UriImagen4,UriImagen5;
    FloatingActionButton btnAntCPU;
    EditText etObservaMoviliario;
    ImageView ivCPUFrontal,ivCPUSerie,ivCPUPuertos,ivCPUIncidencias;
    private String modelo,serie,fecha,largo,ancho,alto,procesador,ram,
    alimentacion,ssd,hdd,vga,hdmi,usb,dvi,dp,dvdr,observaciones;
    Button btnPDFCPU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu2);

        //Vincular Variables
        etObservaMoviliario= findViewById(R.id.etObservaMoviliario);
        btnAntCPU= findViewById(R.id.btnAntCPU);
        btnPDFCPU = findViewById(R.id.btnPDFCPU);
        btnPDFCPU=findViewById(R.id.btnPDFCPU);
        ivCPUFrontal=findViewById(R.id.ivCPUFrontal);
        ivCPUSerie=findViewById(R.id.ivCPUSerie);
        ivCPUPuertos=findViewById(R.id.ivCPUPuertos);
        ivCPUIncidencias=findViewById(R.id.ivCPUIncidencias);

        //Extrae datos de la Activity Anterior
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

        //Mensaje en pantalla al aceptar los permisos
        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        ivCPUFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=1;
                if (ContextCompat.checkSelfPermission(CPU2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CPU2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivCPUSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=2;
                if (ContextCompat.checkSelfPermission(CPU2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CPU2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivCPUPuertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=3;
                if (ContextCompat.checkSelfPermission(CPU2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CPU2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });
        ivCPUIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=4;
                if (ContextCompat.checkSelfPermission(CPU2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CPU2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });

        btnPDFCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observaciones=etObservaMoviliario.getText().toString();
                try {
                    crearPDFCPU();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    //Funcion para cerrar la vida de las activitys e ir al Main
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

    //Metodo que crea el archivo .PDF dandole un formato y cargando el contenido de las variables
    public void crearPDFCPU() throws IOException {
        int m=3;
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
        canvas.drawText((modelo.toUpperCase()), 107*m, 144*m, titulo);

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
        canvas.drawText((serie.toUpperCase()), 346*m, 144*m, titulo);

        // FECHA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 90*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 462*m,129*m , paint);

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText(fecha.toUpperCase(), 465*m, 144*m, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Largo".toUpperCase(), 43*m, 174*m, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 104*m, 159*m, paint);

        //Largo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(largo.toUpperCase()+" cm", 107*m, 174*m, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Ancho".toUpperCase(), 233*m, 174*m, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 289*m, 159*m, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ancho.toUpperCase()+" cm", 292*m, 174*m, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alto".toUpperCase(), 420*m, 174*m, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 459*m, 159*m, paint);

        //Alto IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alto.toUpperCase()+" cm", 462*m, 174*m, titulo);

        //Procesador
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Procesador".toUpperCase(), 43*m, 204*m, titulo);

        // Procesador Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 240*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 118*m, 189*m, paint);

        //Procesador INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText((procesador.toUpperCase()), 121*m, 204*m, titulo);

        //RAM
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("RAM".toUpperCase(), 384*m, 204*m, titulo);

        // RAM Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 130*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 422*m, 189*m, paint);

        //RAM INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ram.toUpperCase(), 425*m, 204*m, titulo);

        //Alimentacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alimentacion".toUpperCase(), 43*m, 234*m, titulo);

        //Alimentacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 150*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 133*m, 219*m, paint);

        //Alimentacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alimentacion.toUpperCase(), 136*m, 234*m, titulo);

        //SSD
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("SSD".toUpperCase(), 300*m, 234*m, titulo);

        // SSD final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 332*m, 219*m, paint);

        //SSD INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ssd.toUpperCase(), 335*m, 234*m, titulo);

        //HDD final
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("HDD".toUpperCase(), 435*m, 234*m, titulo);

        // HDD final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 472*m, 219*m, paint);

        //HDD INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(hdd.toUpperCase(), 475*m, 234*m, titulo);

        //VGA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("VGA".toUpperCase(), 43*m, 264*m, titulo);

        // VGA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 80*m, 249*m, paint);

        //VGA INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(vga.toUpperCase(), 83*m, 264*m, titulo);

        //HDMI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("HDMI".toUpperCase(), 242*m, 264*m, titulo);

        // HDMI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 279*m, 249*m, paint);

        //HDMI INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(hdmi.toUpperCase(), 282*m, 264*m, titulo);

        //USB
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("USB".toUpperCase(), 435*m, 264*m, titulo);

        // USB Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 472*m, 249*m, paint);

        //USB INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(usb.toUpperCase(), 475*m, 264*m, titulo);

        //DVI
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DVI".toUpperCase(), 43*m, 294*m, titulo);

        // DVI Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 80*m, 279*m, paint);

        //DVI IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dvi.toUpperCase(), 83*m, 294*m, titulo);

        //DP
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DP".toUpperCase(), 242*m, 294*m, titulo);

        // DP Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 279*m, 279*m, paint);

        //DP IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dp.toUpperCase(), 282*m, 294*m, titulo);

        //DVDR
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("DVDR".toUpperCase(), 435*m, 294*m, titulo);

        // DVDR Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 24*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 500*m, 279*m, paint);

        //DVDR INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(dvdr.toUpperCase(), 503*m, 294*m, titulo);

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Observaciones".toUpperCase(), 43*m, 333*m, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 405*m, 75*m, false);
        canvas.drawBitmap(bitmapEscala, 147*m, 309*m, paint);

        //Observaciones Info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        String[] arrDescripcion = observaciones.split("\n");
        int y = 321*m;
        for(int i = 0 ; i < arrDescripcion.length&&i<=4  ; i++) {
            canvas.drawText(arrDescripcion[i], 150*m, y, titulo);
            y += 15*m;
        }

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("FRONTAL".toUpperCase(), 179*m, 423*m, titulo);

        //IMAGEN FRONTAL
        if (UriImagen1 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 161*m, 430*m, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("N/S".toUpperCase(), 364*m, 423*m, titulo);

        //N/S Imagen
        if (UriImagen2 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 330*m, 430*m, paint);

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
        canvas.drawText("FDO. OPERADOR".toUpperCase(), 255*m, 680*m, titulo);


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

        //Crear fichero
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), modelo.toUpperCase()+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Se creo el PDF correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfDocument.close();

    }

    //Funciones para los Permisos
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
                    ivCPUFrontal.setImageURI(UriImagen1);
                    break;
                case 2:
                    UriImagen2 = selectedImage;
                    ivCPUSerie.setImageURI(UriImagen2);
                    break;
                case 3:
                    UriImagen3 = selectedImage;
                    ivCPUPuertos.setImageURI(UriImagen3);
                    break;
                case 4:
                    UriImagen4 = selectedImage;
                    ivCPUIncidencias.setImageURI(UriImagen4);
                    break;

            }

        }
    }
}