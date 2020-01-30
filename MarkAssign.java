package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MarkAssign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_assign);
    }
    public void doMark(View v){
        SharedPreferences prefs = getSharedPreferences("GROUPNAME", MODE_PRIVATE);
        String GrpName = prefs.getString("GroupName", String.valueOf(0));
        //now i need to access the database and set the GroupName to the desired value
        EditText Marks = findViewById(R.id.editText12);
        String Mark = Marks.getText().toString();
        //System.out.print("Mark: "+Mark+"\n");
        //System.out.print("Group Name: "+GrpName+"\n");
        ContentValues params = new ContentValues();
        params.put("GROUP_NAME",GrpName);
        params.put("GROUP_MARK",Mark);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/insertGroupMark.php",params) {
            @Override
            protected void onPostExecute(String output) {
                //System.out.print("Mark output: "+output+"\n");
                //setGroups(output);
                Intent thisIntent = new Intent(MarkAssign.this,AfterLectureSignInSignUp.class);
                startActivity(thisIntent);
            }
        };
        asyncHttpPost.execute();
    }
}
