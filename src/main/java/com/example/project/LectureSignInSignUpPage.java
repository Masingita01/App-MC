package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class LectureSignInSignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_sign_in_sign_up_page);
        Button signUpButton = (Button)findViewById(R.id.listButton);
        signUpButton.setOnClickListener(signUp);
        Button signInButton = (Button) findViewById(R.id.AddButton);
        signInButton.setOnClickListener(signIn);
    }
    private View.OnClickListener signUp = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent yourIntent = new Intent(LectureSignInSignUpPage.this,LectureSignUp.class);
            yourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(yourIntent);
            LectureSignInSignUpPage.this.finish();
        }
    };
    private View.OnClickListener signIn = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ContentValues params = new ContentValues();
            @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                    "http://lamp.ms.wits.ac.za/~s1408579/getLecturer.php",params) {
                @Override
                protected void onPostExecute(String output) {
                    processCars(output);
                }
            };
            asyncHttpPost.execute();
        }
    };
    public void processCars(String output){
        EditText passw1 = (EditText) findViewById(R.id.editTextSecond);
        final String pass1 = passw1.getText().toString();
        //System.out.print(output+"\n");
        EditText lectNum = (EditText) findViewById(R.id.editTextFirst);
        final String lectNumber = lectNum.getText().toString();

        try {
            JSONArray ja = new JSONArray(output);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = (JSONObject)ja.get(i);
                if((lectNumber).equals(jo.getString("LECTURER_NO"))&&(pass1).equals(jo.getString("LECTURER_PASSWORD"))){
                    SharedPreferences.Editor editor = getSharedPreferences("LECTNUM", MODE_PRIVATE).edit();
                    editor.putString("lectNum",lectNumber);
                    editor.apply();
                    Intent newIntent = new Intent(LectureSignInSignUpPage.this,AfterLectureSignInSignUp.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(newIntent);
                    LectureSignInSignUpPage.this.finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
