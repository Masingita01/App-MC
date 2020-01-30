package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        //so all i need is to enter the group name and student number
    }

    public void doJoin(View v){
        ContentValues params = new ContentValues();

        SharedPreferences prefs = getSharedPreferences("ASSIGN_NUMBER", MODE_PRIVATE);
        String courCode = prefs.getString("courseCode", String.valueOf(0));
        EditText GrpNum = findViewById(R.id.editTextJoGr);
        final String GrpNumStr = GrpNum.getText().toString();

        EditText GrpName = findViewById(R.id.editText9);
        final String GrpNameStr = GrpName.getText().toString();

        params.put("GROUP_NO",GrpNumStr);
        params.put("GROUP_NAME",GrpNameStr);
        //params.put("COUR",courCode);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/checkGroupNameNo.php",params) {
            @Override
            protected void onPostExecute(String output) {
                if((output).equals("Exists")){
                    InsertGroup();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Group name or number does not exist.", Toast.LENGTH_LONG).show();
                }
            }
        };
        asyncHttpPost.execute();
    }

    public void InsertGroup(){
        //i need a button and a need a place to enter group name.
        //i should be able to get student number from shared preferences
        EditText GrpName = findViewById(R.id.editText9);
        final String GrpNameStr = GrpName.getText().toString();

        EditText GrpNum = findViewById(R.id.editTextJoGr);
        final String GrpNumStr = GrpNum.getText().toString();

        ContentValues params = new ContentValues();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        @SuppressLint("ResourceType") String defaultString = getResources().getString(R.id.editTextFirstStu);
        String stuNumber = prefs.getString("stuNum",defaultString);
        params.put("STUDENT_NO",stuNumber);
        params.put("GROUP_NO",GrpNumStr);
        params.put("GROUP_NAME",GrpNameStr);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/insertGroup.php",params) {
            @Override
            protected void onPostExecute(String output) {
                System.out.print("out join: "+output+"\n");
                if((output).equals("else")) {
                    Intent thisIntent = new Intent(JoinGroup.this, GroupIntent.class);
                    Toast.makeText(getApplicationContext(), "You have joined group" + GrpNameStr, Toast.LENGTH_LONG).show();
                    thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(thisIntent);
                    JoinGroup.this.finish();
                }else if((output).equals("Something")){
                    Toast.makeText(getApplicationContext(), "Your attempt to join group failed" + GrpNameStr, Toast.LENGTH_LONG).show();
                }
            }
        };
        asyncHttpPost.execute();
    }
}
