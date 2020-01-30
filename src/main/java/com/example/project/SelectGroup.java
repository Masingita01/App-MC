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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        getGroups();
        setTitle("Please pick a group");
        //i should probably get something that prints the
    }
    //now i need yo add a class that does something with the output
    public void GetGroupName(final ArrayList<String>GrpName){
        final android.widget.ListView simplerList = (android.widget.ListView)findViewById(R.id.simplerListView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view,R.id.textView14,GrpName);
        simplerList.setAdapter(adapter);
        simplerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("GROUPNAME", MODE_PRIVATE).edit();
                editor.putString("GroupName",GrpName.get(position));
                editor.apply();
                Intent thisIntent = new Intent(SelectGroup.this,MarkAssign.class);
                startActivity(thisIntent);
            }
        });
    }

    public void viewGroups(ArrayList<Integer> GrpNum){
        final ArrayList<String> GrpName = new ArrayList<String>();
        for(int i = 0; i < GrpNum.size();i++){
            System.out.print("GrpNUm: "+GrpNum.get(i)+"\n");
            ContentValues params = new ContentValues();
            params.put("GROUP_NO",GrpNum.get(i));
            @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                    "http://lamp.ms.wits.ac.za/~s1408579/getGroupName.php",params) {
                @Override
                protected void onPostExecute(String output) {
                    //selectAssign(output);
                    //System.out.print("Group name output: "+output+"\n");
                    //setGroups(output);
                    try {
                        JSONArray ja = new JSONArray(output);
                        JSONObject jo = (JSONObject)ja.get(0);
                        System.out.print("JSON: "+jo.getString("GROUP_NAME")+"\n");
                        GrpName.add(jo.getString("GROUP_NAME"));
                        GetGroupName(GrpName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            asyncHttpPost.execute();
        }
    }
    public void getGroups(){
        SharedPreferences prefs = getSharedPreferences("SELECT", MODE_PRIVATE);
        int AssignNum = prefs.getInt("AssignNum",0);
        ContentValues params = new ContentValues();
        params.put("ASSIGNMENT_NO",AssignNum);
        @SuppressLint("StaticFieldLeak") AsyncHttpPost asyncHttpPost = new AsyncHttpPost(
                "http://lamp.ms.wits.ac.za/~s1408579/getCourse.php",params) {
            @Override
            protected void onPostExecute(String output) {
                //System.out.print("Select group output: "+output+"\n");
                setGroups(output);
            }
        };
        asyncHttpPost.execute();
    }
    public void setGroups(String output){

        ArrayList<Integer> GrpNum = new ArrayList<Integer>();
        try {
            JSONArray ja = new JSONArray(output);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = (JSONObject)ja.get(i);
                GrpNum.add(jo.getInt("GROUP_NO"));
            }
            viewGroups(GrpNum);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
