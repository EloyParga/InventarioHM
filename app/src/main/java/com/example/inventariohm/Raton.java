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

public class Raton extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    FloatingActionButton btnAntMovil2;
    private int selector;
    Button btnPDFmoviliario2;
    FloatingActionButton btnAntMovil3;
    private ImageView ivRatonFrontal,ivRatonSerie,ivRatonIncidencias;
    private Uri UriImagen1,UriImagen2,UriImagen3;
    EditText etObservaMoviliario4,etPulgadas,etLargoPantalla,etAnchoPantalla,etAltoPantalla;
    Spinner spConexion;
    private String modelo,fecha,serie,largo,ancho,alto,botones,conexion,observaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raton);
        btnPDFmoviliario2 = findViewById(R.id.btnPDFmoviliario2);
        etPulgadas = findViewById(R.id.etPulgadas);
        etObservaMoviliario4 = findViewById(R.id.etObservaMoviliario4);
        etLargoPantalla = findViewById(R.id.etLargoPantalla);
        etAnchoPantalla = findViewById(R.id.etAnchoPantalla);
        etAltoPantalla = findViewById(R.id.etAltoPantalla);
        spConexion = findViewById(R.id.spConexion);
        btnAntMovil3 = findViewById(R.id.btnAntMovil3);

        ivRatonFrontal = findViewById(R.id.ivRatonFrontal);
        ivRatonSerie = findViewById(R.id.ivRatonSerie);
        ivRatonIncidencias = findViewById(R.id.ivRatonIncidencias);


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

        ivRatonFrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Raton.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Raton.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=1;
            }
        });
        ivRatonSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Raton.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Raton.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=2;
            }
        });
        ivRatonIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Raton.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Raton.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=3;
            }
        });

        btnPDFmoviliario2=findViewById(R.id.btnPDFmoviliario2);

        btnPDFmoviliario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largo=etLargoPantalla.getText().toString().toUpperCase();
                ancho=etAnchoPantalla.getText().toString().toUpperCase();
                alto=etAltoPantalla.getText().toString().toUpperCase();
                botones=etPulgadas.getText().toString().toUpperCase();
                conexion=spConexion.getSelectedItem().toString().toUpperCase();
                observaciones=etObservaMoviliario4.getText().toString().toUpperCase();
                modelo=modelo2.toUpperCase();
                serie=numSerie2.toUpperCase();
                fecha=fecha2.toUpperCase();

                try {
                    crearPDFRaton();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                cierre();

            }
        });
        btnAntMovil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void abrirGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_CODE_GALLERY);
    }
    public void cierre(){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra("EXIT", true);
        startActivity(i);
        finish();
    }

    public void crearPDFRaton() throws IOException {
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
        canvas.drawText("Cl. Gran Capitán, 52. Gijón".toUpperCase(), 43, 80, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("+34 985 66 54 41   hm@hazmaker.org".toUpperCase(), 222, 80, titulo);

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

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText(fecha+"", 465, 144, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Largo".toUpperCase(), 43, 194, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 104, 179, paint);

        //Largo INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(largo+"", 107, 194, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Ancho".toUpperCase(), 233, 194, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 289, 179, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ancho+"", 292, 194, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alto".toUpperCase(), 420, 194, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 24, false);
        canvas.drawBitmap(bitmapEscala, 459, 179, paint);

        //Alto IFNO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alto+"", 462, 194, titulo);

        //Botones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Nº Botones".toUpperCase(), 43, 244, titulo);

        // Botones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 135, 24, false);
        canvas.drawBitmap(bitmapEscala, 125, 229, paint);

        //Botones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(botones+"", 128, 244, titulo);

        //Tipo Conexion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Tipo de Conexion".toUpperCase(), 270, 244, titulo);

        // Conexion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 170, 24, false);
        canvas.drawBitmap(bitmapEscala, 382, 229, paint);

        //Tipo Conexion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(conexion+"", 385, 244, titulo);


        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Observaciones".toUpperCase(), 43, 294, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 407, 147, false);
        canvas.drawBitmap(bitmapEscala, 145, 281, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(observaciones+"", 148, 294, titulo);

        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Incidencias".toUpperCase(), 253, 470, titulo);

        //Incidencias
        if (UriImagen3 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen3);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 248, 477, paint);

        //N/S
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("Nº Serie".toUpperCase(), 48, 470, titulo);

        //N/S Imagen
        if (UriImagen2 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 43, 477, paint);

        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("FROTAL".toUpperCase(), 455, 470, titulo);

        //FRONTAL Puetos
        if (UriImagen1 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 452, 477, paint);


        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 23, false);
        canvas.drawBitmap(bitmapEscala, 0, 665, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("FDO. OPERADOR", 255, 680, titulo);


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
                    ivRatonFrontal.setImageURI(UriImagen1);
                    break;
                case 2:
                    UriImagen2 = selectedImage;
                    ivRatonSerie.setImageURI(UriImagen2);
                    break;
                case 3:
                    UriImagen3 = selectedImage;
                    ivRatonIncidencias.setImageURI(UriImagen3);
                    break;

            }

        }
    }
}
