package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAssignment extends AppCompatActivity {
    //ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_assignment);
        getOutput();
    }

    public void putIn(ArrayList<String>list, final ArrayList<Integer>AsiNum){
        final ListView simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view,R.id.textView14,list);
        simpleList.setAdapter(adapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("SELECT", MODE_PRIVATE).edit();
                editor.putInt("AssignNum",AsiNum.get(position));
                editor.apply();
                Intent thisIntent = new Intent(SelectAssignment.this,SelectGroup.class);
                startActivity(thisIntent);
            }
        });
    }

    public void getOutput(){
        ContentValues params = new ContentValues();
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/getAssignment.php",params) {
            @Override
            protected void onPostExecute(String output) {
                selectAssign(output);
            }
        };
        asyncHttpPost.execute();
    }

    public void selectAssign(String output){
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> AsiNum = new ArrayList<Integer>();
        try {
            JSONArray ja = new JSONArray(output);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = (JSONObject)ja.get(i);
                list.add(jo.getString("ASSIGNMENT_NAME"));
                AsiNum.add(jo.getInt("ASSIGNMENT_NO"));
            }
            putIn(list,AsiNum);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
