package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
    }

    public void workMode(View view){
        Intent intent = new Intent(Menu_Activity.this ,WorkMenu_Activity.class );
        startActivity(intent);
    }

    public void homeMode(View view){
        Intent intent = new Intent(Menu_Activity.this ,HomeMenu_Activity.class );
        startActivity(intent);

    }
}
