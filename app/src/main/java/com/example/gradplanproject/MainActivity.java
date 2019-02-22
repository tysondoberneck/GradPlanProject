package com.example.gradplanproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testStuff() {
        System.out.println("Android Studio marks 'Evgeniy' as a spelling mistake :D");
        System.out.println("It marks Bekker the same way...");
        System.out.println("Doberneck is a real word guys");
        System.out.println("What if I join the mege conflict party");

    }
}
// Test comment