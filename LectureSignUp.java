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

public class LectureSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_sign_up);
    }
    //have an arrayList that takes in both the student number and password
    public void doPass(View v){
        //this is the first password in the xml
        EditText passw1 = (EditText) findViewById(R.id.editText5);
        final String pass1 = passw1.getText().toString();

        //this is the second password in the xml
        EditText passw2 = (EditText) findViewById(R.id.editText6);
        String pass2 = passw2.getText().toString();

        //this is the student number in the xml
        final EditText lectNum = (EditText) findViewById(R.id.editText2);
        final String lectNumber = lectNum.getText().toString();

        //do the thing to push password data base but only do so if the confirmed password is the same as the initial one
        if((pass1).equals(pass2)){
            //params takes in the parameters of the
            ContentValues params = new ContentValues();
            params.put("LECTURER_NO",lectNumber);
            params.put("LECTURER_PASSWORD",pass1);
            @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                    "http://lamp.ms.wits.ac.za/~s1408579/insertLecturer.php",params) {
                @Override
                protected void onPostExecute(String output) {
                }
            };
            asyncHttpPost.execute();
            SharedPreferences.Editor editor = getSharedPreferences("LECTNUM", MODE_PRIVATE).edit();
            editor.putString("lectNum",lectNumber);
            editor.apply();
            Intent yourIntent = new Intent(LectureSignUp.this,AfterLectureSignInSignUp.class);
            startActivity(yourIntent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Passwords don't match",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
