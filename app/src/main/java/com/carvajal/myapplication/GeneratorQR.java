package com.carvajal.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class GeneratorQR extends AppCompatActivity {

    private ImageView ima_qr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_qr);
        ima_qr = findViewById(R.id.qr_view);
        Intent recibir= getIntent();
        Bitmap dato= (Bitmap)recibir.getParcelableExtra("BitmapImage");
        ima_qr.setImageBitmap(dato);


    }

}
