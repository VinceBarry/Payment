package com.example.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.google.zxing.BarcodeFormat.QR_CODE;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private final String TAG = "SimpleScannerActivity";
    public final String QR_CODE = "QR_CODE";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.i(TAG, rawResult.getText()); // Prints scan results
        Log.i(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

        if (rawResult.getBarcodeFormat().toString().equals(QR_CODE)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("payment", rawResult.getText());
            SimpleScannerActivity.this.setResult(Constants.RESULT_CODE, intent);
            SimpleScannerActivity.this.finish();
        }


    }
}