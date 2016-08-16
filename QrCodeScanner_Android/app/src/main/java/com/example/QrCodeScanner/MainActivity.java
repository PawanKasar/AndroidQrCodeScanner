package com.example.QrCodeScanner;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    ToneGenerator tone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tone = new ToneGenerator(AudioManager.STREAM_MUSIC,100);

    }
    //Creating a method to perform onClick() method
    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);          // Register ourselves as a handler for scan results.
        mScannerView.startCamera();                   // Start camera
    }

    public void onPause(){
        super.onPause();
        mScannerView.stopCamera();  // Stop camera on pause
    }

    //Implement Result Handler
    @Override
    public void handleResult(Result rawResult) {
        Log.e("MainActivity",rawResult.getText());                      // Prints scan results
        Log.e("MainActivity",rawResult.getBarcodeFormat().toString());  // Prints the scan format (qrcode)

        tone.startTone(ToneGenerator.TONE_PROP_BEEP,150);

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        mScannerView.resumeCameraPreview(this);  //resume scanning
    }
}
