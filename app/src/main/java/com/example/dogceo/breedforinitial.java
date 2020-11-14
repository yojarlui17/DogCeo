package com.example.dogceo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dogceo.Entity.Breed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class breedforinitial extends AppCompatActivity {
    ArrayList<String> listbreedM;
    ArrayList<Breed> typebreed;
    ArrayList<String> listsubb;
    ArrayList<Breed> breedcomplete;
    String letterBreed,selectionBreed,subbreed;
    Spinner spBreedMaster,spSubBreed;
    Button btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breedforinitial);
        letterBreed=this.getIntent().getStringExtra("Letter");
        spBreedMaster=(Spinner)findViewById(R.id.sbBreedMaster);
        spSubBreed=(Spinner)findViewById(R.id.spSubBreed);
        btnsearch=(Button)findViewById(R.id.btnSearchImage);
        wwwBreedMaster("https://dog.ceo/api/breeds/list");
        spBreedMaster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos!=0){
                    selectionBreed=(typebreed.get(pos-1).getRaza());
                    wwwSubBreed("https://dog.ceo/api/breed/"+selectionBreed+"/list");
                }
                else{
                    NoSubBreed(2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spSubBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos!=0){
                    subbreed=(breedcomplete.get(pos-1).getSubraza());
                }else{
                    subbreed="";
                    NoSubBreed(3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),imagesbreed.class);
                intent.putExtra("BreedMaster", selectionBreed);
                intent.putExtra("SubBreed", subbreed);
                startActivity(intent);
            }
        });
    }
    private void wwwBreedMaster(String URL) {
        typebreed = new ArrayList<>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("message");
                    for (int i=0;i<jsonArray.length();i++){
                        Breed b = new Breed();
                        b.setRaza(jsonArray.getString(i));
                        if(b.getRaza().substring(0,1).equalsIgnoreCase(letterBreed)){
                            typebreed.add(b);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                loadBreedM();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void wwwSubBreed(String URL) {
        breedcomplete = new ArrayList<Breed>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("message");
                    if(jsonArray.length()!=0){
                        for (int i=0;i<jsonArray.length();i++){
                            Breed b = new Breed();
                            b.setRaza(selectionBreed);
                            b.setSubraza(jsonArray.getString(i));
                            breedcomplete.add(b);
                        }
                    }else{
                        NoSubBreed(1);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                loadSBreed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    void NoSubBreed(int i){
        if(i==1) {
            Toast.makeText(this, "NO EXISTEN RAZAS SECUNDARIAS", Toast.LENGTH_SHORT).show();
        }else if(i==2){
            Toast.makeText(this,"Seleccione una Raza",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Seleccione una Sub-Raza",Toast.LENGTH_SHORT).show();
        }
    }
    void loadBreedM(){
        listbreedM=new ArrayList<String>();
        listbreedM.add("Seleccionar");
        for(int i=0;i<typebreed.size();i++){
            listbreedM.add(typebreed.get(i).getRaza());
        }
        ArrayAdapter<CharSequence>adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listbreedM);
        spBreedMaster.setAdapter(adapter);
    }
    void loadSBreed(){
        listsubb=new ArrayList<String>();
        if(breedcomplete.size()==0){
            listsubb.add("No Existen Sub Razas");
            spSubBreed.setEnabled(false);
        }else{
            spSubBreed.setEnabled(true);
            listsubb.add("Seleccione");
            for(int i=0;i<breedcomplete.size();i++){
                listsubb.add(breedcomplete.get(i).getSubraza());
            }
        }
        ArrayAdapter<CharSequence>adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listsubb);
        spSubBreed.setAdapter(adapter);
    }
}