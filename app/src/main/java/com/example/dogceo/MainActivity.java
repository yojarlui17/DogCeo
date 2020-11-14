package com.example.dogceo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Spinner cboinitial;
    Button btnEnter;
    String letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cboinitial = (Spinner) findViewById(R.id.cboinitial);
        btnEnter = (Button) findViewById(R.id.btnEnter);
    }


    public void Navigate(View view) {
        letter = cboinitial.getSelectedItem().toString();
        if (letter.equals("Seleccionar")) {
            Toast.makeText(this, "DEBE ESCOGER UNA LETRA", Toast.LENGTH_SHORT).show();
        } else if (letter.equals("I") || letter.equals("J") || letter.equals("Q") || letter.equals("U") || letter.equals("X") || letter.equals("Y") || letter.equals("Z")) {
            Toast.makeText(this, "NO HAY RAZAS CON ESA LETRA", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, breedforinitial.class);
            intent.putExtra("Letter", letter);
            startActivity(intent);
        }
    }
}