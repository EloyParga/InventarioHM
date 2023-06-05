package com.example.inventariohm;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Environment;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Moviliario2 extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    FloatingActionButton btnAntMovil2;
    public String descripcionMueble,largo,ancho,alto,cantidad,fecha,
            ubicacion,precioI,precioA,notas,observaciones,numSerie;
    private ImageView ivFoto1,ivFoto2,ivFoto3,ivFoto4,ivFoto5;
    private Uri UriImagen1,UriImagen2,UriImagen3,UriImagen4,UriImagen5;
    Button btnPDFmoviliario;

    private File f;

    private int selector;
    private EditText etObservaMoviliario2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviliario2);

        etObservaMoviliario2 = findViewById(R.id.etObservaMoviliario2);
        btnAntMovil2 = findViewById(R.id.btnAntMovil2);
        ivFoto1= findViewById(R.id.ivFoto1);
        ivFoto2= findViewById(R.id.ivFoto2);
        ivFoto3= findViewById(R.id.ivFoto3);
        ivFoto4= findViewById(R.id.ivFoto4);
        ivFoto5= findViewById(R.id.ivFoto5);

         Bundle b = getIntent().getExtras();
         numSerie = b.getString("numSerie");
         fecha = b.getString("fecha" );
         descripcionMueble = b.getString("descripcion" );
         largo = b.getString("largo" );
         ancho = b.getString("ancho" );
         alto = b.getString("alto" );
         cantidad = b.getString("cantidad" );
         ubicacion = b.getString("ubicacion" );
         precioI = b.getString("precioInicial" );
         precioA = b.getString("precioActual" );
         notas = b.getString("notas" );

        ivFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=1;
                if (ContextCompat.checkSelfPermission(Moviliario2.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Moviliario2.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                } else {
                    abrirGaleria();
                }
            }
        });

        ivFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Moviliario2.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Moviliario2.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=2;
            }
        });
        ivFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Moviliario2.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Moviliario2.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=3;
            }
        });
        ivFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Moviliario2.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Moviliario2.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=4;
            }
        });
        ivFoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Moviliario2.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Moviliario2.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    abrirGaleria();
                }
                selector=5;
            }
        });



        btnAntMovil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
                observaciones = etObservaMoviliario2.getText().toString().toUpperCase();
                try {
                    crearPDF();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                finish();
                cierre();
            }
        });
    }

    private void abrirGaleria(){
            Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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


    public void crearPDF() {

        int m=3;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();


        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(595*m, 842*m, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();





        // Caja inicio
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595*m, 70*m, false);
        canvas.drawBitmap(bitmapEscala, 0*m, 43*m, paint);

        //LOGO
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 60*m, 60*m, false);
        canvas.drawBitmap(bitmapEscala, 492*m, 48*m, paint);



        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Cl. Gran Capitán, 52. Gijón".toUpperCase(), 60*m, 79*m, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("+34 985 66 54 41   hm@hazmaker.org".toUpperCase(), 222*m, 79*m, titulo);



        //Descripcion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Descripcion".toUpperCase(), 43*m, 135*m, titulo);

        // Descripcion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 290*m, 23*m, false);
        canvas.drawBitmap(bitmapEscala, 129*m, 120*m, paint);

        // Fecha Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 453*m, 120*m, paint);

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText(fecha+"", 459*m, 135*m, titulo);

        //Descripcion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(descripcionMueble.toUpperCase()+"", 132*m, 135*m, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Largo".toUpperCase(), 43*m, 162*m, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 105*m, 151*m, paint);

        // LARGO INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(largo+" cm", 108*m, 162*m, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Ancho".toUpperCase(), 210*m, 162*m, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 277*m, 151*m, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ancho+" cm", 280*m, 162*m, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Alto".toUpperCase(), 389*m, 162*m, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 453*m, 151*m, paint);

        //Alto Info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(alto+" cm", 456*m, 162*m, titulo);

        //Cantidad
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Cantidad".toUpperCase(), 43*m, 191*m, titulo);

        // Cantidad Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 123*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 108*m, 180*m, paint);

        //Cantidad INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(cantidad+"", 111*m, 191*m, titulo);

        //Ubicacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Ubicacion".toUpperCase(), 240*m, 191*m, titulo);

        // Ubicacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 231*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 315*m, 180*m, paint);

        //Ubicacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(ubicacion.toUpperCase()+"", 318*m, 191*m, titulo);

        //Precio inicial
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Precio Inicial".toUpperCase(), 43*m, 222*m, titulo);

        // Precio inicial Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 152*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 135*m, 209*m, paint);

        //Precio inicial INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(precioI+"", 138*m, 222*m, titulo);

        //Precio final
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Precio final".toUpperCase(), 300*m, 222*m, titulo);

        // Precio final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 152*m, 22*m, false);
        canvas.drawBitmap(bitmapEscala, 394*m, 209*m, paint);

        //Precio final INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        canvas.drawText(precioA+"", 397*m, 222*m, titulo);

        //Notas
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Notas".toUpperCase(), 43*m, 250*m, titulo);

        // Notas Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 447*m, 70*m, false);
        canvas.drawBitmap(bitmapEscala, 99*m, 239*m, paint);

        //Notas info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        String[] arrDescripcion2 = notas.split("\n");
        int y2 = 251*m;
        for(int i = 0 ; i < arrDescripcion2.length&&i<=3 ; i++) {
            canvas.drawText(arrDescripcion2[i], 102*m, y2, titulo);
            y2 += 15*m;
        }

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("Observaciones".toUpperCase(), 43*m, 330*m, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 400*m, 75*m, false);
        canvas.drawBitmap(bitmapEscala, 146*m, 318*m, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10*m);
        String[] arrDescripcion = observaciones.split("\n");
        int y = 330*m;
        for(int i = 0 ; i < arrDescripcion.length&&i<=4 ; i++) {
            canvas.drawText(arrDescripcion[i], 149*m, y, titulo);
            y += 15*m;
        }


        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("FRONTAL", 60*m, 420*m, titulo);

        //IMAGEN FRONTAL

        if (UriImagen1 != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 40*m, 437*m, paint);



        //Trasera
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("TRASERA", 277*m, 420*m, titulo);

        //IMAGEN Trasera

        if (UriImagen2 != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 249*m, 437*m, paint);

        //Interna
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("INTERNA", 473*m, 420*m, titulo);

        //IMAGEN Interna

        if (UriImagen3 != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 449*m, 437*m, paint);

        //Ubicacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("UBICACION", 160*m, 539*m, titulo);

        //IMAGEN Ubicacion

        if (UriImagen4 != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 144*m, 548*m, paint);

        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10*m);
        canvas.drawText("INCIDENCIAS", 364*m, 539*m, titulo);

        //IMAGEN incidencias

        if (UriImagen5 != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100*m, 100*m, false);
        canvas.drawBitmap(bitmapEscala, 348*m, 548*m, paint);

        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595*m, 20*m, false);
        canvas.drawBitmap(bitmapEscala, 0*m, 663*m, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13*m);
        canvas.drawText("FDO. OPERADOR", 258*m, 678*m, titulo);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_dark);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 255*m, 114*m, false);
        canvas.drawBitmap(bitmapEscala, 174*m, 685*m, paint);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pixelblanco);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 253*m, 112*m, false);
        canvas.drawBitmap(bitmapEscala, 175*m, 686*m, paint);


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


        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), descripcionMueble.toUpperCase()+".pdf");


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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    // finish();
                }
            }
        }
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
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
                    ivFoto1.setImageURI(UriImagen1);
                    comrpimidor(UriImagen1);

                    break;
                case 2:
                    UriImagen2 = selectedImage;
                    ivFoto2.setImageURI(UriImagen2);
                    break;
                case 3:
                    UriImagen3 = selectedImage;
                    ivFoto3.setImageURI(UriImagen3);
                    break;
                case 4:
                    UriImagen4 = selectedImage;
                    ivFoto4.setImageURI(UriImagen4);
                    break;
                case 5:
                    UriImagen5 = selectedImage;
                    ivFoto5.setImageURI(UriImagen5);
                    break;
            }

        }
    }
    public void comrpimidor(Uri direccion){
        String ruta=getRealPathFromUri(direccion);
        Bitmap bimap=BitmapFactory.decodeFile(ruta);
        Bitmap resizedBitmap=Bitmap.createScaledBitmap(bimap,100,100,false);
        ByteArrayOutputStream bytearrayoutputstream=new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG,50,bytearrayoutputstream);

    }

    private String getRealPathFromUri(Uri contentUri) {
        String result;
        Cursor cursor=getContentResolver().query(contentUri,null,null,null,null);
        if(cursor == null){
            result=contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result=cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}