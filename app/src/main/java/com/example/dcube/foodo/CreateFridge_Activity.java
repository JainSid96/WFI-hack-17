package com.example.dcube.foodo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class CreateFridge_Activity extends AppCompatActivity {

    FloatingActionButton b12;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fridge_);

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        b12  =  (FloatingActionButton) findViewById(R.id.fab);
        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(),AddItem_Activity.class);
                startActivity(a);
                Toast.makeText(getApplicationContext(),"Hello there !",Toast.LENGTH_LONG).show();
            }
        });

        b12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent b = new Intent(getApplicationContext(),FridgeMenu_Activity.class);
                startActivity(b);
                Toast.makeText(getApplicationContext(),"Fridge Saved !",Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }
}