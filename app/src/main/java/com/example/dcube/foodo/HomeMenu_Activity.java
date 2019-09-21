package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeMenu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu_);
    }

    public void homeFridge(View view){
        Intent intent = new Intent(HomeMenu_Activity.this , FridgeMenu_Activity.class);
        startActivity(intent);
    }

    public void comFridge(View view){
        Intent intent = new Intent(HomeMenu_Activity.this , CommunityFridge_Activity.class);
        startActivity(intent);
    }

    public void donateFood(View view){
        Intent intent = new Intent(HomeMenu_Activity.this , DonateFood_Activity.class);
        startActivity(intent);
    }

    public void nearbyNgo(View view){
        Intent intent = new Intent(HomeMenu_Activity.this , NearbyNgo_Activity.class);
        startActivity(intent);
    }
}
