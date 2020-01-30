package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertGroup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_group);
        //I need to get input values frommthe user
    }
    public void doInsert(View v){
        SharedPreferences prefs = getSharedPreferences("ASSIGN_NUMBER", MODE_PRIVATE);
        //@SuppressLint("ResourceType") String defaultString = getResources().getString("No name defined");
        String AssignNo = prefs.getString("asiNum", String.valueOf(0));
        String courCode = prefs.getString("courseCode", String.valueOf(0));

        System.out.print("Assign No: "+AssignNo+"\n");
        System.out.print("Cousrse No post: "+courCode+"\n");
        //GETTING GROUP NUMBER FROM STUDENT
        EditText GrpNum = findViewById(R.id.editText7);
        final String GrpNumStr = GrpNum.getText().toString();
        System.out.print("Group No: "+GrpNumStr+"\n");

        ContentValues params = new ContentValues();

        System.out.print("Course Code: "+courCode+"\n");
        //INSERTING INTO COURSE TABLE
        params.put("COURSE_NO",courCode);
        params.put("ASSIGNMENT_NO",AssignNo);
        params.put("GROUP_NO",GrpNumStr);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/insertCourse.php",params) {
            @Override
            protected void onPostExecute(String output) {
                //InsertGroup();
                checkGroup();
                //System.out.println("this should work");
            }
        };
        asyncHttpPost.execute();
    }
    //so i need to create a php that returns whether the group number is taken or not
    //SO THE MAJOR ISSUE NOW IS THAT TWO DIFFERENT GROUPS CAN HAVE THE SAME GROUP NUMBER
    public void checkGroup(){
        ContentValues params = new ContentValues();
        EditText GrpNum = findViewById(R.id.editText7);
        final String GrpNumStr = GrpNum.getText().toString();
        params.put("GROUP_NO",GrpNumStr);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/checkGroup.php",params) {
            @Override
            protected void onPostExecute(String output) {
                if(!(output).equals("Exists")){
                    InsertGroup();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Group number already exists, pick another", Toast.LENGTH_LONG).show();
                }
            }
        };
        asyncHttpPost.execute();
    }

    public void InsertGroup(){
        EditText GrpName = findViewById(R.id.editText8);
        final String GrpNameStr = GrpName.getText().toString();

        EditText GrpNum = findViewById(R.id.editText7);
        final String GrpNumStr = GrpNum.getText().toString();

        ContentValues params = new ContentValues();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        //@SuppressLint("ResourceType") String defaultString = getResources().getString(R.id.editTextFirstStu);
        String stuNumber = prefs.getString("stuNum",String.valueOf(0));
        System.out.print(stuNumber+"\n");
        params.put("GROUP_NO",GrpNumStr);
        params.put("GROUP_NAME",GrpNameStr);
        params.put("STUDENT_NO",stuNumber);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/insertGroup.php",params) {
            @Override
            protected void onPostExecute(String output) {
                Intent thisIntent = new Intent(InsertGroup.this,GroupIntent.class);
                thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thisIntent);
                InsertGroup.this.finish();
                Toast.makeText(getApplicationContext(), "Group has been made", Toast.LENGTH_LONG).show();
            }
        };
        asyncHttpPost.execute();
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
                Intent thisIntent = new Intent(InsertGroup.this,LecturerOrStudent.class);
                thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thisIntent);
                InsertGroup.this.finish();
                return true;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "This tells you nothing about the app", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
