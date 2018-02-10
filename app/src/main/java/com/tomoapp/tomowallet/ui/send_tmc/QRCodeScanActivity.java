package com.tomoapp.tomowallet.ui.send_tmc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.BaseActivity;
import com.tomoapp.tomowallet.base.BaseSocketActivity;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.LogUtil;
import com.tomoapp.tomowallet.helper.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbook on 12/27/17.
 */


public class QRCodeScanActivity extends BaseSocketActivity implements QRCodeReaderView.OnQRCodeReadListener {


    @BindView(R.id.qrdecoderview)
    QRCodeReaderView qrdecoderview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan_qr_code);
        ButterKnife.bind(this);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setStatusbarColor(R.color.color_3_3);
        qrdecoderview.setOnQRCodeReadListener(this);
        requestPermission();
    }




    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void requestPermission() {
        LogUtil.d("requestPermission: " + Build.VERSION.SDK_INT);
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                setContent();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    setContent();
                } else {
                    requestCameraPermission();
                }
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            /*Snackbar.make(activityMain, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();*/
            ActivityCompat.requestPermissions(QRCodeScanActivity.this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_ALL);
            /*new MaterialDialog.Builder(this)
                    .title(getString(R.string.ban_can_cap_quyen_3))
                    .content(getString(R.string.app_can_run_without_cam_permission))
                    .cancelable(false)
                    .positiveText(getString(R.string.cap_quyen))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(QRCodeScanActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, PERMISSION_ALL);
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                    finish();
                }
            }).show();*/
        } else {
            /*Snackbar.make(activityMain, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();*/
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_ALL);
        }
    }

    final int PERMISSION_ALL = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_ALL) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate();
        } else {
            ToastUtil.show(getString(R.string.app_can_run_without_cam_permission));
            finish();
        }
    }



    private void setContent(){
        qrdecoderview.setAutofocusInterval(500L);
        qrdecoderview.setOnQRCodeReadListener(this);
        qrdecoderview.setBackCamera();
        qrdecoderview.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setCurrentActivity(this

        );
        qrdecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrdecoderview.stopCamera();
    }



    private boolean isQRCodeReaded = false;
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        LogUtil.d("QR CODE: " + text);
        if (isQRCodeReaded) return;
        isQRCodeReaded = true;
        Intent intent = new Intent();
        intent.putExtra("QR", text);
        setResult(RESULT_OK, intent);
        finish();
    }

}

