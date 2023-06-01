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
                } catch (IOException e) {
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


    public void crearPDF() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();


        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();





        // Caja inicio
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 70, false);
        canvas.drawBitmap(bitmapEscala, 0, 43, paint);

        //LOGO
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hmlogo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        canvas.drawBitmap(bitmapEscala, 492, 48, paint);



        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Cl. Gran Capitán, 52. Gijón".toUpperCase(), 60, 79, titulo);


        //CORREO Y TELEFONO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("+34 985 66 54 41   hm@hazmaker.org".toUpperCase(), 222, 79, titulo);



        //Descripcion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Descripcion".toUpperCase(), 43, 135, titulo);

        // Descripcion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 290, 23, false);
        canvas.drawBitmap(bitmapEscala, 129, 120, paint);

        // Fecha Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 22, false);
        canvas.drawBitmap(bitmapEscala, 453, 120, paint);

        //FECHA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText(fecha+"", 459, 135, titulo);

        //Descripcion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(descripcionMueble.toUpperCase()+"", 132, 135, titulo);

        //Largo
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Largo".toUpperCase(), 43, 162, titulo);

        // Largo Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 22, false);
        canvas.drawBitmap(bitmapEscala, 105, 151, paint);

        // LARGO INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(largo+" cm", 108, 162, titulo);

        //Ancho
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Ancho".toUpperCase(), 210, 162, titulo);

        // Ancho Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 22, false);
        canvas.drawBitmap(bitmapEscala, 277, 151, paint);

        //Ancho INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ancho+" cm", 280, 162, titulo);

        //Alto
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Alto".toUpperCase(), 389, 162, titulo);

        // Alto Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 93, 22, false);
        canvas.drawBitmap(bitmapEscala, 453, 151, paint);

        //Alto Info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(alto+" cm", 456, 162, titulo);

        //Cantidad
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Cantidad".toUpperCase(), 43, 191, titulo);

        // Cantidad Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 123, 22, false);
        canvas.drawBitmap(bitmapEscala, 108, 180, paint);

        //Cantidad INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(cantidad+"", 111, 191, titulo);

        //Ubicacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Ubicacion".toUpperCase(), 240, 191, titulo);

        // Ubicacion Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 231, 22, false);
        canvas.drawBitmap(bitmapEscala, 315, 180, paint);

        //Ubicacion INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(ubicacion.toUpperCase()+"", 318, 191, titulo);

        //Precio inicial
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Precio Inicial".toUpperCase(), 43, 222, titulo);

        // Precio inicial Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 152, 22, false);
        canvas.drawBitmap(bitmapEscala, 135, 209, paint);

        //Precio inicial INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(precioI+"", 138, 222, titulo);

        //Precio final
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Precio final".toUpperCase(), 300, 222, titulo);

        // Precio final Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 152, 22, false);
        canvas.drawBitmap(bitmapEscala, 394, 209, paint);

        //Precio final INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        canvas.drawText(precioA+"", 397, 222, titulo);

        //Notas
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Notas".toUpperCase(), 43, 250, titulo);

        // Notas Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 447, 70, false);
        canvas.drawBitmap(bitmapEscala, 99, 239, paint);

        //Notas info
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        String[] arrDescripcion2 = notas.split("\n");
        int y2 = 251;
        for(int i = 0 ; i < arrDescripcion2.length&&i<=3 ; i++) {
            canvas.drawText(arrDescripcion2[i], 102, y2, titulo);
            y2 += 15;
        }

        //Observaciones
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("Observaciones".toUpperCase(), 43, 330, titulo);

        // Observaciones Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 400, 75, false);
        canvas.drawBitmap(bitmapEscala, 146, 318, paint);

        //Observaciones INFO
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titulo.setTextSize(10);
        String[] arrDescripcion = observaciones.split("\n");
        int y = 330;
        for(int i = 0 ; i < arrDescripcion.length&&i<=4 ; i++) {
            canvas.drawText(arrDescripcion[i], 149, y, titulo);
            y += 15;
        }


        //FRONTAL
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("FRONTAL", 60, 420, titulo);

        //IMAGEN FRONTAL

        if (UriImagen1 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen1);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 40, 437, paint);



        //Trasera
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("TRASERA", 277, 420, titulo);

        //IMAGEN Trasera

        if (UriImagen2 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen2);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 249, 437, paint);

        //Interna
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("INTERNA", 473, 420, titulo);

        //IMAGEN Interna

        if (UriImagen3 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen3);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 449, 437, paint);

        //Ubicacion
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("UBICACION", 160, 539, titulo);

        //IMAGEN Ubicacion

        if (UriImagen4 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen4);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 144, 548, paint);

        //Incidencias
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(10);
        canvas.drawText("INCIDENCIAS", 364, 539, titulo);

        //IMAGEN incidencias

        if (UriImagen5 != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriImagen5);
        } else {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(android.graphics.Color.WHITE);
        }
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        canvas.drawBitmap(bitmapEscala, 348, 548, paint);

        // Firma Banner
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_light);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 595, 20, false);
        canvas.drawBitmap(bitmapEscala, 0, 663, paint);

        //FIRMA
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(13);
        canvas.drawText("FDO. OPERADOR", 258, 678, titulo);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), com.google.android.material.R.drawable.abc_list_selector_disabled_holo_dark);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 255, 114, false);
        canvas.drawBitmap(bitmapEscala, 174, 685, paint);


        // Espacio Firma
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pixelblanco);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 253, 112, false);
        canvas.drawBitmap(bitmapEscala, 175, 686, paint);


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