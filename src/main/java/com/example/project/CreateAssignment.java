package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAssignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
    }

    public void doCreate(View v){
        ContentValues params = new ContentValues();

        EditText AssigName = findViewById(R.id.editText10);
        final String AssignNameStr = AssigName.getText().toString();

        EditText CourNo = findViewById(R.id.editText11);
        final String CourNoStr = CourNo.getText().toString();
        //System.out.print("Assign name: "+AssignNameStr+"\n");
        //System.out.print("Cour num: "+CourNoStr+"\n");
        params.put("ASSIGNMENT_NAME",AssignNameStr);
        params.put("COURSE_NO",CourNoStr);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/insertAssignment.php",params) {
            @Override
            protected void onPostExecute(String output) {
                if((output).equals("Not")){
                    Toast.makeText(getApplicationContext(), "Assignment Exists", Toast.LENGTH_LONG).show();
                }else{
                    Intent yourIntent = new Intent(CreateAssignment.this,AfterLectureSignInSignUp.class);
                    yourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(yourIntent);
                    CreateAssignment.this.finish();
                }
            }
        };
        asyncHttpPost.execute();
    }
}
