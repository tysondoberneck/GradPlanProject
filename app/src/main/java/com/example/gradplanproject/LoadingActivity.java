package com.example.gradplanproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * This is the first activity of the app. For now it's only a small loading screen.
 * In the future we might some more features to it.
 */
public class LoadingActivity extends AppCompatActivity {

    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d("Handler", "Running handler...");
                Intent intent = new Intent(activity, CourseViewActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}