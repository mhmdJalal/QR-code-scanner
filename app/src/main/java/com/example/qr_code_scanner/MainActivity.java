package com.example.qr_code_scanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.frame_scanner)
    FrameLayout frameLayout;

    private ZXingScannerView mScannerView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                101);

        mScannerView = new ZXingScannerView(this);
        mScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        mScannerView.setAutoFocus(true);
        mScannerView.setAspectTolerance(0.5f);
        frameLayout.addView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @OnClick(R.id.image_flash)
    void onClickFlashlight() {
        if (mScannerView.getFlash()) {
            mScannerView.setFlash(false);
        } else {
            mScannerView.setFlash(true);
        }
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(result.getText());
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.startCamera();
                dialogInterface.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }
}
