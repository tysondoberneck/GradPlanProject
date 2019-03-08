package com.example.gradplanproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Toolbar/NavDrawer
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);

            DrawerUtil.getDrawer(this, toolbar);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        //
    }
}
