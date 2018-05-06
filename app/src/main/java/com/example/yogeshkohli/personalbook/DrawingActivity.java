package com.example.yogeshkohli.personalbook;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class DrawingActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button mSaveButton, mPenButton, mEraserButton, mPenColorButton, mBackgroundColorButton;
    private DrawingView mDrawingView;
    private SeekBar mPenSizeSeekbar, mEraserSeekbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        initializeUI();
        setListeners();
    }

    /* Third Party Library code of drawing view */
    private void setListeners() {
        mSaveButton.setOnClickListener(this);
        mPenButton.setOnClickListener(this);
        mEraserButton.setOnClickListener(this);
        mPenColorButton.setOnClickListener(this);
        mBackgroundColorButton.setOnClickListener(this);
        mPenSizeSeekbar.setOnSeekBarChangeListener(this);
        mEraserSeekbar.setOnSeekBarChangeListener(this);
    }

    /* Third Party Library code of drawing view */
    private void initializeUI() {
        mDrawingView = (DrawingView) findViewById(R.id.scratch_pad);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mPenButton = (Button) findViewById(R.id.pen_button);
        mEraserButton = (Button) findViewById(R.id.eraser_button);
        mPenColorButton = (Button) findViewById(R.id.pen_color_button);
        mBackgroundColorButton = (Button) findViewById(R.id.background_color_button);
        mPenSizeSeekbar = (SeekBar) findViewById(R.id.pen_size_seekbar);
        mEraserSeekbar = (SeekBar) findViewById(R.id.eraser_size_seekbar);
    }

    /* Third Party Library code of drawing view */
    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:

                 /* My code starts here */

                requestPermission();

                //Permission granted
                if (isPermissionGranted()) {
                    saveImage();
                }
                //show toast that permission denied
                else{
                    showToast(Constants.PERMISSION_GRANTE_ERROR);
                }

                break;
            case R.id.pen_button:
                mDrawingView.initializePen();
                break;
            case R.id.eraser_button:
                mDrawingView.initializeEraser();
                break;
            case R.id.pen_color_button:
                final ColorPicker colorPicker = new ColorPicker(DrawingActivity.this, 100, 100, 100);
                colorPicker.setCallback(
                        new ColorPickerCallback() {
                            @Override public void onColorChosen(int color) {
                                mDrawingView.setPenColor(color);
                                colorPicker.dismiss();
                            }
                        });
                colorPicker.show();
                break;
            case R.id.background_color_button:
                final ColorPicker backgroundColorPicker = new ColorPicker(DrawingActivity.this, 100, 100, 100);
                backgroundColorPicker.setCallback(
                        new ColorPickerCallback() {
                            @Override public void onColorChosen(int color) {
                                mDrawingView.setBackgroundColor(color);
                                backgroundColorPicker.dismiss();
                            }
                        });
                backgroundColorPicker.show();
                break;
        }
    }

    public void saveImage(){
        mDrawingView.saveImage(Environment.getExternalStorageDirectory().toString(), randomFileName(),
                Bitmap.CompressFormat.PNG, 100);
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //requsting permission to access storage - because of upper versions requesting permission way
    public void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(DrawingActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DrawingActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DrawingActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_CODE);
        }
    }

    //checking permissions - if permission granted return true else false
    private boolean isPermissionGranted() {
        int result = ContextCompat.checkSelfPermission(DrawingActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Code which generates random file name
    public String randomFileName() {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image"+ n;

        return fname;
    }

    /* My code ends here */

    /* Third Party Library code of drawing view */
    @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.pen_size_seekbar:
                mDrawingView.setPenSize(i);
                break;
            case R.id.eraser_size_seekbar:
                mDrawingView.setEraserSize(i);
                break;
        }
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
