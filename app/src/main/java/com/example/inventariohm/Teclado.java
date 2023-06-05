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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Teclado extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    private int selector;
    private ImageView ivTecladoFrontal,ivTecladoSerie,ivTecladoIncidencia;
    private Uri UriImagen1,UriImagen2,UriImagen3;

    Button btnPDFmoviliario4;
    FloatingActionButton btnAntMovil6;
    EditText etLargoPantalla,etAnchoPantalla,etAltoPantalla,etObservaMoviliario5;
    Spinner spConectividad,spIdioma;
    private String modelo,serie,fecha,largo,ancho,alto,idioma,conexion,observacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teclado);
        etObservaMoviliario5 = findViewById(R.id.etObservaMoviliario5);
        btnPDFmoviliario4=findViewById(R.id.btnPDFmoviliario4);
        etLargoPantalla = findViewById(R.id.etLargoPantalla);
        etAnchoPantalla = findViewById(R.id.etAnchoPantalla);
        etAltoPantalla = findViewById(R.id.etAltoPantalla);
        spConectividad = findViewById(R.id.spConectividad);
        spIdioma = findViewById(R.id.spIdioma);
        btnAntMovil6 = findViewById(R.id.btnAntMovil6);

        ivTecladoFrontal = findViewById(R.id.ivTecladoFrontal);
        ivTecladoSerie = findViewById(R.id.ivTecladoSerie);
        ivTecladoIncidencia = findViewById(R.id.ivTecladoIncidencia);


        Bundle b = getIntent().getExtras();
        String modelo2 = b.getString("modelo");
        String numSerie2 = b.getString("numSerie");
        String fecha2= b.getString("fecha");





        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"PRUEBA",Toast.LENGTH_LONG).show();
            requestPermissions();
        }

        ivTecladoFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Teclado.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Teclado.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=1;
            }
        });
        ivTecladoSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Teclado.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Teclado.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=2;
            }
        });
        ivTecladoIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Teclado.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Teclado.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=3;
            }
        });

        btnPDFmoviliario4=findViewById(R.id.btnPDFmoviliario4);

        btnPDFmoviliario4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largo=etLargoPantalla.getText().toString().toUpperCase();
                ancho=etAnchoPantalla.getText().toString().toUpperCase();
                alto=etAltoPantalla.getText().toString().toUpperCase();
                conexion=spConectividad.getSelectedItem().toString().toUpperCase();
                idioma=spIdioma.getSelectedItem().toString().toUpperCase();
                observacion=etObservaMoviliario5.getText().toString().toUpperCase();
                modelo=modelo2.toUpperCase();
                serie=numSerie2.toUpperCase();
                fecha=fecha2.toUpperCase();

                try {
                    crearPDFTeclado();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                cierre();


            }
        });
        btnAntMovil6.setOnClickListener(new View.OnClickListener() {
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

    public void crearPDFTeclado() throws IOException {
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
        canvas.drawText(modelo.toUpperCase(), 107*m, 144*m, titulo);

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
        canvas.drawText(serie.toUpperCase(), 346*m, 144*m, titulo);

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
        canvas.drawText("Largo".toUpperCase(), 43*m, 194*m, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 104*m, 179*m, paint);

        //Largo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(largo.toUpperCase()+" cm", 107*m, 194*m, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Ancho".toUpperCase(), 233*m, 194*m, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 289*m, 179*m, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ancho.toUpperCase()+" cm", 292*m, 194*m, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alto".toUpperCase(), 420*m, 194*m, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 459*m, 179*m, paint);

        //Alto INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alto.toUpperCase()+" cm", 462*m, 194*m, titulo);

        //Idioma
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Idioma".toUpperCase(), 43*m, 244*m, titulo);

        // IDIOMA Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 125*m, 229*m, paint);

        //Idioma INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(idioma.toUpperCase(), 128*m, 244*m, titulo);

        //Tipo Conexion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Tipo de Conexion".toUpperCase(), 270*m, 244*m, titulo);

        // Conexion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170*m, 24*m, false);
        canvas.drawBitmap(bitmapEscala, 382*m, 229*m, paint);

        //Tipo Conexion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(conexion.toUpperCase(), 385*m, 244*m, titulo);


        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Observaciones".toUpperCase(), 43*m, 294*m, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 407*m, 147*m, false);
        canvas.drawBitmap(bitmapEscala, 145*m, 281*m, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        String[] arrDescripcion = observacion.split("\n");
        int y = 293*m;
        for(int i = 0 ; i < arrDescripcion.length&&i<=10  ; i++) {
            canvas.drawText(arrDescripcion[i], 150*m, y, titulo);
            y += 15*m;
        }

        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13*m);
        canvas.drawText("Incidencias".toUpperCase(), 253*m, 470*m, titulo);

        //Incidencias FRONTAL
        if (UriImagen3 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen3);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 248*m, 477*m, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13*m);
        canvas.drawText("Nº Serie".toUpperCase(), 48*m, 470*m, titulo);

        //N/S Imagen
        if (UriImagen2 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 43*m, 477*m, paint);

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13*m);
        canvas.drawText("FROTAL".toUpperCase(), 455*m, 470*m, titulo);

        //FRONTAL Puetos
        if (UriImagen1 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 452*m, 477*m, paint);


        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595*m, 23*m, false);
        canvas.drawBitmap(bitmapEscala, 0*m, 665*m, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13*m);
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
                    ivTecladoFrontal.setImageURI(UriImagen1);
                    break;
                case 2:
                    UriImagen2 = selectedImage;
                    ivTecladoSerie.setImageURI(UriImagen2);
                    break;
                case 3:
                    UriImagen3 = selectedImage;
                    ivTecladoIncidencia.setImageURI(UriImagen3);
                    break;

            }

        }
    }
}