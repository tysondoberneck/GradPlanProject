package com.example.gradplanproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        }, 3000);

    }

    protected void testStuff() {
        System.out.println("Android Studio marks 'Evgeniy' as a spelling mistake :D");
        System.out.println("It marks Bekker the same way...");
        System.out.println("Doberneck is a real word guys");
    }

//    Will go through with this tutorial on monday. Too many resolving issues, so Id like to
//    resolve those before pushing large chunks of code. The idea though is we can do a splash screen
//    if the page takes too long. This will be good if our API proves harder to load than we originally
//    thought. The tutorial is here: http://wiki.netbeans.org/Splash_Screen_Beginner_Tutorial

}