package com.app.foodify.foodifyinc;

import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;

/**
 * Created by agoston on 11/18/17.
 */

public class WatsonDownloader extends AsyncTask<ClassifyOptions, Void, String> {

    private final MainActivity activity;

    public WatsonDownloader(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(ClassifyOptions... classifyOptions) {
        ClassifyOptions options = classifyOptions[0];

        VisualRecognition visualRecognitionService = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        visualRecognitionService.setApiKey("8d7aced8efa9ce11cca985d203dce5989cc20148");

        ClassifiedImages result = visualRecognitionService.classify(options).execute();
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        activity.onFinishWatson(s);
    }
}
