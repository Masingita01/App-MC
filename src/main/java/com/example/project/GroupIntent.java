package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class GroupIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_intent);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_Group);
        toolbar.findViewById(R.id.toolbar_textG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yourIntent = new Intent(GroupIntent.this,InsertGroup.class);
                startActivity(yourIntent);
            }
        });
        Toolbar toolbar1 = findViewById(R.id.toolbar_Group2);
        toolbar1.findViewById(R.id.toolbar_textG2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = new Intent(GroupIntent.this,JoinGroup.class);
                startActivity(thisIntent);
            }
        });
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
                Intent thisIntent = new Intent(GroupIntent.this,LecturerOrStudent.class);
                thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thisIntent);
                GroupIntent.this.finish();
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "This tells you nothing about the app", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
