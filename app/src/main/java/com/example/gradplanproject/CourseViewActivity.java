package com.example.gradplanproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class CourseViewActivity extends AppCompatActivity {
    public Toolbar toolBar; //Temporary code till toolbar works

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_view);

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

    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
