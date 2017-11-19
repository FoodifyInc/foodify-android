package com.app.foodify.foodifyinc;

//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//
//import io.fotoapparat.Fotoapparat;
//import io.fotoapparat.view.CameraView;

//public class MainActivity extends AppCompatActivity {
//
//    private Button cameraButton;
//    private ImageView imageView;
//    private Button recipeHistory;
//    private static final int CAM_REQUEST = 1001;
//    private static final int IMAGE_PERMISSION = 4 ;
//    private String mCurrentPhotoPath;
//    private File imageFile;
//
//    // ME
//    private CameraView cameraView;
//
//    private static final String TAG = "MainActivity";
//
//
//
//    private RecipeDownloadCompleted recipeDownloadCompleted;
//    private WatsonDownloadCompleted watsonDownloadCompleted;
//
//    private Fotoapparat fotoapparat;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        cameraButton = findViewById(R.id.cameraButton);
//        imageView = findViewById(R.id.mainView);
//        recipeHistory = findViewById(R.id.recipeBtn);
//
//        cameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startCameraIntent();
//            }
//        });
//
//        recipeDownloadCompleted = new RecipeDownloadCompleted();
//        watsonDownloadCompleted = new WatsonDownloadCompleted();
//
//        // ME
//
//        cameraView = (CameraView) findViewById(R.id.camera_view);
//        Fotoapparat.with(MainActivity.this)
//                    .into(cameraView)
//                    .build();
//    }
//
//    private void startCameraIntent() {
//        //Required camera permission
//        fotoapparat.start();
//        String[] permissions = {"android.permission.CAMERA"};
//        //Intent to startCamera
//
//
//        Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        //Ask for permissions
//        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager
//                .PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this, permissions, IMAGE_PERMISSION);
//        }
//        //If permission is already granted
//        else if(startCameraIntent.resolveActivity(getPackageManager()) != null){
//            imageFile = createImageFile();
//            if(imageFile != null){
//                Uri photoURI = FileProvider.getUriForFile(this, "com.app.foodify.FileProvider", imageFile);
//                startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(startCameraIntent, CAM_REQUEST);
//            }
//        }
//    }
//
//    //@Override
////    protected void onStart() {
////        super.onStart();
////        fotoapparat.start();
////    }
////
////    @Override
////    protected void onStop() {
////        super.onStop();
////        fotoapparat.stop();
////    }
//
//    private File createImageFile() {
//        //Create image filename
//        String imageFileName = "JPEG_00";
//
//        //Access storage directory for photos and create temporary image file
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(imageFileName,".jpg",storageDir);
//            Log.w("APP", "File created");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Store file path for usage with intents
//        assert image != null;
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
//            Toast.makeText(this, "Picture taken", Toast.LENGTH_SHORT).show();
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
//
//            imageView.setImageURI(Uri.fromFile(imageFile));
//            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
//                    .imagesFile(inputStream)
//                    .imagesFilename(mCurrentPhotoPath)
//                    .build();
//
//            new WatsonDownloader(watsonDownloadCompleted).execute(classifyOptions);
//        }
//    }
//
//    /**
//     * Contains a callback that's used to display recipes after they're downloaded.
//     */
//    private class RecipeDownloadCompleted implements AsyncTaskCompleted<ArrayList<Recipe>> {
//
//        @Override
//        public void onTaskCompleted(ArrayList<Recipe> val) {
//            for(Recipe recipe : val) {
//                Log.i(TAG, "recipe: " + recipe.getRecipeName());
//            }
//        }
//    }
//
//    /**
//     * Contains a callback that's used to pass the food name to the RecipeDownloader
//     */
//    private class WatsonDownloadCompleted implements AsyncTaskCompleted<String> {
//
//        @Override
//        public void onTaskCompleted(String val) {
//            Log.i(TAG, "onFinishWatson: " + val);
//
//            try {
//                JSONObject json = new JSONObject(val);
//                JSONArray imgArray = json.getJSONArray("images");
//
//                JSONArray classifiers = imgArray.getJSONObject(0).getJSONArray("classifiers");
//                JSONArray classes = classifiers.getJSONObject(0).getJSONArray("classes");
//                String foodClass = classes.getJSONObject(0).getString("class");
//
//                Log.i(TAG, "foodClass: " + foodClass);
//
//                new RecipeDownloader(recipeDownloadCompleted).execute(foodClass);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.FotoapparatSwitcher;
import io.fotoapparat.error.CameraErrorCallback;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.provider.CameraProviders;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.update.UpdateRequest;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.log.Loggers.fileLogger;
import static io.fotoapparat.log.Loggers.logcat;
import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.AspectRatioSelectors.standardRatio;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoFlash;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoRedEye;
import static io.fotoapparat.parameter.selector.FlashSelectors.off;
import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.lensPosition;
import static io.fotoapparat.parameter.selector.PreviewFpsRangeSelectors.rangeWithHighestFps;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SensorSensitivitySelectors.highestSensorSensitivity;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;
import static io.fotoapparat.result.transformer.SizeTransformers.scaled;

public class MainActivity extends AppCompatActivity {

    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    private boolean hasCameraPermission;
    private CameraView cameraView;

    private FotoapparatSwitcher fotoapparatSwitcher;
    private Fotoapparat frontFotoapparat;
    private Fotoapparat backFotoapparat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (CameraView) findViewById(R.id.camera_view);
        hasCameraPermission = permissionsDelegate.hasCameraPermission();

        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }

        setupFotoapparat();

        takePictureOnClick();
        focusOnLongClick();
        switchCameraOnClick();
        toggleTorchOnSwitch();
        zoomSeekBar();

    }

    private void setupFotoapparat() {
        frontFotoapparat = createFotoapparat(LensPosition.FRONT);
        backFotoapparat = createFotoapparat(LensPosition.BACK);
        fotoapparatSwitcher = FotoapparatSwitcher.withDefault(backFotoapparat);
    }

    private void zoomSeekBar() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.zoomSeekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fotoapparatSwitcher
                        .getCurrentFotoapparat()
                        .setZoom(progress / (float) seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });
    }

    private void toggleTorchOnSwitch() {
        SwitchCompat torchSwitch = (SwitchCompat) findViewById(R.id.torchSwitch);

        torchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fotoapparatSwitcher
                        .getCurrentFotoapparat()
                        .updateParameters(
                                UpdateRequest.builder()
                                        .flash(
                                                isChecked
                                                        ? torch()
                                                        : off()
                                        )
                                        .build()
                        );
            }
        });
    }

    private void switchCameraOnClick() {
        View switchCameraButton = findViewById(R.id.switchCamera);
        switchCameraButton.setVisibility(
                canSwitchCameras()
                        ? View.VISIBLE
                        : View.GONE
        );
        switchCameraOnClick(switchCameraButton);
    }

    private void switchCameraOnClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void focusOnLongClick() {
        cameraView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fotoapparatSwitcher.getCurrentFotoapparat().autoFocus();

                return true;
            }
        });
    }

    private void takePictureOnClick() {

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private boolean canSwitchCameras() {
        return frontFotoapparat.isAvailable() == backFotoapparat.isAvailable();
    }

    private Fotoapparat createFotoapparat(LensPosition position) {
        return Fotoapparat
                .with(this)
                .cameraProvider(CameraProviders.v1()) // change this to v2 to test Camera2 API
                .into(cameraView)
                .previewScaleType(ScaleType.CENTER_CROP)
                .photoSize(standardRatio(biggestSize()))
                .lensPosition(lensPosition(position))
                .focusMode(firstAvailable(
                        continuousFocus(),
                        autoFocus(),
                        fixed()
                ))
                .flash(firstAvailable(
                        autoRedEye(),
                        autoFlash(),
                        torch(),
                        off()
                ))
                .previewFpsRange(rangeWithHighestFps())
                .sensorSensitivity(highestSensorSensitivity())
                .frameProcessor(new SampleFrameProcessor())
                .logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .cameraErrorCallback(new CameraErrorCallback() {
                    @Override
                    public void onError(CameraException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    private void takePicture() {
        PhotoResult photoResult = fotoapparatSwitcher.getCurrentFotoapparat().takePicture();

        photoResult.saveToFile(new File(
                getExternalFilesDir("photos"),
                "photo.jpg"
        ));

//        photoResult
//                .toBitmap(scaled(0.25f))
//                .whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
//                    @Override
//                    public void onResult(BitmapPhoto result) {
//                        ImageView imageView = (ImageView) findViewById(R.id.result);
//
//                        imageView.setImageBitmap(result.bitmap);
//                        imageView.setRotation(-result.rotationDegrees);
//                    }
//                });
    }

    private void switchCamera() {
        if (fotoapparatSwitcher.getCurrentFotoapparat() == frontFotoapparat) {
            fotoapparatSwitcher.switchTo(backFotoapparat);
        } else {
            fotoapparatSwitcher.switchTo(frontFotoapparat);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            fotoapparatSwitcher.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {
            fotoapparatSwitcher.stop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            fotoapparatSwitcher.start();
            cameraView.setVisibility(View.VISIBLE);
        }
    }

    private class SampleFrameProcessor implements FrameProcessor {

        @Override
        public void processFrame(Frame frame) {
            // Perform frame processing, if needed
        }

    }

}
