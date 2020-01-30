package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AfterStudentSignInSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_student_sign_in_sign_up);
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        //@SuppressLint("ResourceType") String defaultString = getResources().getString(R.id.editTextFirstStu);
        String stuNumber = prefs.getString("stuNum",String.valueOf(0));
        setTitle("Hello "+stuNumber+" pick an assignment");
        getList();

    }

    public void makeList(final ArrayList<String> assignmentList,final ArrayList<String> CourseNo,final ArrayList<String> assignmentNumber){
        final android.widget.ListView simpleList = (android.widget.ListView)findViewById(R.id.simpleListView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view,R.id.textView14,assignmentList);

        simpleList.setAdapter(adapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //SharedPreferences.Editor editor = getSharedPreferences("SELECT", MODE_PRIVATE).edit();
                //editor.putInt("AssignNum",AsiNum.get(position));
                //editor.apply();
                SharedPreferences.Editor editor = getSharedPreferences("ASSIGN_NUMBER", MODE_PRIVATE).edit();
                editor.putString("courseCode", CourseNo.get(position));
                editor.putString("asiNum", assignmentNumber.get(position));
                System.out.print("Course number: "+CourseNo.get(position)+"\n");
                System.out.print("Assignment number: "+assignmentNumber.get(position)+"\n");
                editor.apply();
                Intent thisIntent = new Intent(AfterStudentSignInSignUp.this,GroupIntent.class);
                thisIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(thisIntent);
                //LectureSignInSignUpPage.this.finish();
            }
        });
    }

    public void getList(){
        //EditText AssignM = (EditText) findViewById(R.id.editText3);
        //final String assign = AssignM.getText().toString();
        ContentValues params = new ContentValues();
        //params.put("ASSIGNMENT_NAME",assign);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/getAssignment.php",params) {
            @Override
            protected void onPostExecute(String output) {
                processCars(output);
            }
        };
        asyncHttpPost.execute();
    }

    public void processCars(String output){
        ArrayList<String> assignmentName = new ArrayList<String>();
        ArrayList<String> CourseNo = new ArrayList<String>();
        ArrayList<String> AssignmentNo = new ArrayList<String>();
        //EditText passw1 = (EditText) findViewById(R.id.editText3);
        //final String pass1 = passw1.getText().toString();
        try {
            JSONArray ja = new JSONArray(output);
            for(int i =0; i < ja.length();i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                assignmentName.add(jo.getString("ASSIGNMENT_NAME"));
                CourseNo.add(jo.getString("COURSE_NO"));
                AssignmentNo.add(jo.getString("ASSIGNMENT_NO"));
            }
            makeList(assignmentName,CourseNo,AssignmentNo);
        }catch (Exception e){
            e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Assignment does not exist", Toast.LENGTH_LONG).show();
        }
    }
}
