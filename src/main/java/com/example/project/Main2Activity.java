package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        final EditText stuNum = (EditText) findViewById(R.id.editText2);
        final String stNumber = stuNum.getText().toString();

        //do the thing to push password data base but only do so if the confirmed password is the same as the initial one
        if((pass1).equals(pass2)){
            //params takes in the parameters of the
            ContentValues params = new ContentValues();
            //Ssending names to database
            params.put("STUDENT_NO",stNumber);
            params.put("STUDENT_PASSWORD",pass1);
            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
            editor.putString("stuNum",stNumber);
            editor.apply();
            @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                    "http://lamp.ms.wits.ac.za/~s1408579/insertStudents.php",params) {
                        @Override
                        protected void onPostExecute(String output) {
                        }
                    };
            asyncHttpPost.execute();
            Intent yourIntent = new Intent(Main2Activity.this,AfterStudentSignInSignUp.class);
            yourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(yourIntent);
            Main2Activity.this.finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Passwords don't match",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
