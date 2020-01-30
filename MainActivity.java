package com.example.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = (Button)findViewById(R.id.listButton);
        signUpButton.setOnClickListener(signUp);
        Button signInButton = (Button) findViewById(R.id.AddButton);
        signInButton.setOnClickListener(signIn);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private View.OnClickListener signUp = new View.OnClickListener(){
        @Override
        public void onClick(View view) {//sign up button
            Intent yourIntent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(yourIntent);
        }
    };
    private View.OnClickListener signIn = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent newIntent = new Intent(MainActivity.this,SignInActivity.class);
            startActivity(newIntent);
        }
    };
}
