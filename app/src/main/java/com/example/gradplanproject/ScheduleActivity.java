package com.example.gradplanproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ScheduleActivity extends AppCompatActivity {
    public Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);

            // When Toolbar works, we will use this code.
//            Toolbar toolBar = findViewById(R.id.toolBar);
//            toolBar.setTitle(getResources().getString(R.string.tournament));
//            setSupportActionBar(toolBar);

            DrawerUtil.getDrawer(this, toolBar);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
