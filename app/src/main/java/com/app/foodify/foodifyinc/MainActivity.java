package com.app.foodify.foodifyinc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton;
    private ImageView imageView;
    private static final int CAM_REQUEST = 1001;
    private static final int IMAGE_PERMISSION = 4 ;
    private String mCurrentPhotoPath;
    private File imageFile;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.cameraButton);
        imageView = findViewById(R.id.mainView);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraIntent();
            }
        });

    }

    private void startCameraIntent() {
        //Required camera permission
        String[] permissions = {"android.permission.CAMERA"};
        //Intent to startCamera
        Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Ask for permissions
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager
                .PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, IMAGE_PERMISSION);
        }
        //If permission is already granted
        else if(startCameraIntent.resolveActivity(getPackageManager()) != null){
            imageFile = createImageFile();
            if(imageFile != null){
                Uri photoURI = FileProvider.getUriForFile(this, "com.app.foodify.FileProvider", imageFile);
                startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(startCameraIntent, CAM_REQUEST);
            }
        }
    }

    private File createImageFile() {
        //Create image filename
        String imageFileName = "JPEG_00";

        //Access storage directory for photos and create temporary image file
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,".jpg",storageDir);
            Log.w("APP", "File created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Store file path for usage with intents
        assert image != null;
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            imageView.setImageURI(Uri.fromFile(imageFile));
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(mCurrentPhotoPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            VisualRecognition visualRecognitionService = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
//            visualRecognitionService.setApiKey("8d7aced8efa9ce11cca985d203dce5989cc20148");
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(inputStream)
                    .imagesFilename(mCurrentPhotoPath)
                    .build();

            new WatsonDownloader(this).execute(classifyOptions);
//            ClassifiedImages result = visualRecognitionService.classify(classifyOptions).execute();
//            Log.w("FOODIFY", "Result: " + result.toString());
        }
    }

    public void onFinishWatson(String result) {
        Log.i(TAG, "onFinishWatson: " + result);
    }
}
