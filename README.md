# QR-code-scanner



### Preview
![Imgur](https://i.imgur.com/6hoqoP3l.png)
![Imgur](https://i.imgur.com/6x6UhTAl.png)

## Installation
Add the following dependency to your build.gradle

**build.gradle (Project:qr_code_scanner)**
```
repositories {
   jcenter()
}
```

**build.gradle (Module:app)**
```
android {
   compileOptions {
      sourceCompatibility JavaVersion.VERSION_1_8
      targetCompatibility JavaVersion.VERSION_1_8
   }
}

dependencies {
   implementation 'me.dm7.barcodescanner:zxing:1.9.8'

   implementation 'com.jakewharton:butterknife:10.1.0'
   annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'
}
```

**activity_main.xml**
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/frame_scanner"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:layout_centerHorizontal="true"
        android:text="Direct the QR Code to\nthe specified area"
        android:textColor="@android:color/white"
        android:textAlignment="center"
        android:textSize="20sp"
        android:alpha="0.7"
        android:lineSpacingExtra="3dp"
        android:fontFamily="sans-serif-medium"/>

    <ImageView
        android:id="@+id/image_flash"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_marginStart="20dp"
        android:layout_marginBottom="40dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:rotation="-45"
        android:src="@drawable/ic_flashlight"/>

</RelativeLayout>
```

**Icon for button flashlight**

[ic_flashlight.xml](https://github.com/mhmdJalal/QR-code-scanner/blob/master/app/src/main/res/drawable/ic_flashlight.xml)


**MainActivity.java**
```
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
```

**Last, add permissions to your AndroidManifest.xml**
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature
  android:name="android.hardware.camera"
  android:required="true" />
 
```

Reference
[here](https://github.com/dm77/barcodescanner)
