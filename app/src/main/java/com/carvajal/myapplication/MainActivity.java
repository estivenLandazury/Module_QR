package com.carvajal.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_cedula;
    private Button btn_verifcar;
    private ImageView ima_qr;
    private final static int QRcodeWidth = 500;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_cedula = findViewById(R.id.txt_cedu);
        btn_verifcar = findViewById(R.id.btn_verificar);
        ima_qr = findViewById(R.id.qr_view);

        btn_verifcar.setOnClickListener(this);

    }


//    public void verificar() {
//        String ced = txt_cedula.getText().toString();
//
//        new Thread(()->{
//
//            URL page = null;
//            try {
//                page = new URL(CODIGOSHASH_URL+ced);
//                HttpURLConnection connection = (HttpURLConnection)page.openConnection();
//                InputStream inputStream= connection.getInputStream();
//                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
//                BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
//                String line= bufferedReader.readLine();
//                Log.e(">>>>",line);
//
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Log.e(">>>>","mal papi");
//
//            } catch (IOException e) {
//                Log.e(">>>>","mal perro");
//
//                e.printStackTrace();
//            }
//
//
//        }).start();
//
//    }


    public Bitmap TextoImageEncode(String Value) throws WriterException {

        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(Value, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null);
        } catch (IllegalArgumentException e) {
            return null;

        }

        int bitMattrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMattrixWidth * bitMatrixHeight];
        for (int i = 0; i < bitMatrixHeight; i++) {
            int offset = i * bitMattrixWidth;

            for (int y = 0; y < bitMattrixWidth; y++) {
                pixels[offset + y] = bitMatrix.get(y, i) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }

        bitmap = Bitmap.createBitmap(bitMattrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMattrixWidth, bitMatrixHeight);

        return bitmap;
    }

    public void verificar() {
        String ced = txt_cedula.getText().toString();
//        try {
//            bitmap= TextoImageEncode(ced);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        ima_qr.setImageBitmap(bitmap);

        new Thread(() -> {

            new ServiceManager.hashGET(ced, response -> {


                Gson g = new Gson();
                Token n = g.fromJson(response, Token.class);
                try {
                  bitmap = TextoImageEncode(n.getDni().toString());


                } catch (WriterException e) {
                    e.printStackTrace();
                }

                Log.e(">>>>", "hola  " + n.getDni());
            });

        }).start();

        ima_qr.setImageBitmap(bitmap);
    }


    public void mensaje(final String m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, m, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (btn_verifcar.getId() == v.getId()) {
            verificar();



        }

    }
}
