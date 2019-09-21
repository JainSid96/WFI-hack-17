package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FridgeMenu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_menu_);
    }

    public void viewFridge(View view){
        Intent intent = new Intent(FridgeMenu_Activity.this , ViewFridge_Activity.class);
        startActivity(intent);
    }

    public void createFridge(View view){
        Intent intent = new Intent(FridgeMenu_Activity.this , CreateFridge_Activity.class);
        startActivity(intent);
    }

    public void fridgeReminder(View view){
        Intent intent = new Intent(FridgeMenu_Activity.this , FridgeReminder_Activity.class);
        startActivity(intent);
    }
}
