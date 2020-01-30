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

import org.json.JSONArray;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button signUpButton = (Button)findViewById(R.id.listButtonStu);
        signUpButton.setOnClickListener(signUpStu);
        Button signInButton = (Button) findViewById(R.id.AddButtonStu);
        signInButton.setOnClickListener(LoginStu);
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private View.OnClickListener signUpStu = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent yourIntent = new Intent(SignInActivity.this,Main2Activity.class);
            yourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(yourIntent);
            SignInActivity.this.finish();
        }
    };
    private View.OnClickListener LoginStu = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ContentValues params = new ContentValues();
            @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                    "http://lamp.ms.wits.ac.za/~s1408579/getStudent.php",params) {
                @Override
                protected void onPostExecute(String output) {
                    processCars(output);
                    //System.out.print(output+"\n");
                }
            };
            asyncHttpPost.execute();
        }
    };
    public void processCars(String output){
        EditText passw1 = (EditText) findViewById(R.id.editTextSecondStu);
        final String pass1 = passw1.getText().toString();
        //System.out.print(output+"\n");
        EditText stuNum = (EditText) findViewById(R.id.editTextFirstStu);
        final String stuNumber = stuNum.getText().toString();
        try {
            JSONArray ja = new JSONArray(output);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = (JSONObject)ja.get(i);
                if((stuNumber).equals(jo.getString("STUDENT_NO"))&&(pass1).equals(jo.getString("STUDENT_PASSWORD"))){
                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                    editor.putString("stuNum",stuNumber);
                    editor.apply();
                    Intent bigIntent = new Intent(SignInActivity.this,AfterStudentSignInSignUp.class);
                    bigIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(bigIntent);
                    SignInActivity.this.finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
