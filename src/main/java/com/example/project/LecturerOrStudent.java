package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class LecturerOrStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_or_student);
        //first toolbar
        setTitle("Welcome");
        //getActionBar().setIcon(R.drawable.my_icon);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yourIntent = new Intent(LecturerOrStudent.this,SignInActivity.class);
                startActivity(yourIntent);
            }
        });
        Toolbar toolbar1 = findViewById(R.id.toolbar_id);
        toolbar1.findViewById(R.id.toolbar_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = new Intent(LecturerOrStudent.this,LectureSignInSignUpPage.class);
                startActivity(thisIntent);
            }
        });
    }
}
