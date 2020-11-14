package com.example.dogceo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dogceo.Entity.Breed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class imagesbreed extends AppCompatActivity {
    ArrayList<Breed> list;
    String sb, mb;
    TextView txtBreed;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagesbreed);
        txtBreed = (TextView) findViewById(R.id.txtBreed);
        mb = this.getIntent().getStringExtra("BreedMaster");
        sb = this.getIntent().getStringExtra("SubBreed");
        txtBreed.setText(mb + " " + sb);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sb.equals("")) {
            wwwGetImages("https://dog.ceo/api/breed/" + mb + "/images/random");
        } else {
            wwwGetImages("https://dog.ceo/api/breed/" + mb + "/" + sb + "/images/random");
        }
    }

    private void wwwGetImages(String URL) {
        list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Breed b = new Breed();
                try {
                    String x = response.getString("message");
                    //for(int i=0;i<jsonArray.length();i++){
                    b.setImages(x);
                    Log.i("URL DE FOTO", b.getImages());
                    Glide.with(imagesbreed.this).load(b.getImages()).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
                    list.add(b);
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}