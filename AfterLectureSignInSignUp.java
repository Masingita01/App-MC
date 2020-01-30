package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AfterLectureSignInSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_lecture_sign_in_sign_up);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_Lect);
        toolbar.findViewById(R.id.toolbar_title_Lect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yourIntent = new Intent(AfterLectureSignInSignUp.this,CreateAssignment.class);
                startActivity(yourIntent);
            }
        });
        Toolbar toolbar1 = findViewById(R.id.toolbar_id_Lect);
        toolbar1.findViewById(R.id.toolbar_text_Lect2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = new Intent(AfterLectureSignInSignUp.this,SelectAssignment.class);
                startActivity(thisIntent);
            }
        });
        SharedPreferences prefs = getSharedPreferences("LECTNUM", MODE_PRIVATE);
        //@SuppressLint("ResourceType") String defaultString = getResources().getString(R.id.editTextFirstStu);
        String lectNum = prefs.getString("lectNum",String.valueOf(0));
        setTitle("Hello lecturer "+lectNum);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Intent thisIntent = new Intent(AfterLectureSignInSignUp.this,LecturerOrStudent.class);
                thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thisIntent);
                AfterLectureSignInSignUp.this.finish();
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "This tells you nothing about the app", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
