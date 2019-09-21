package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WorkMenu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_menu_);
    }
    public void nearbyNgo(View view){
        Intent intent = new Intent(WorkMenu_Activity.this , NearbyNgo_Activity.class);
        startActivity(intent);
    }

    public void setupFoodpark(View view){
        Intent intent = new Intent(WorkMenu_Activity.this , FoodPark_Activity.class);
        startActivity(intent);
    }

    public void animalShelter(View view){
        Intent intent = new Intent(WorkMenu_Activity.this , AnimalShelter_Activity.class);
        startActivity(intent);
    }

    public void donateFood(View view){
        Intent intent = new Intent(WorkMenu_Activity.this , DonateFood_Activity.class);
        startActivity(intent);
    }


}
