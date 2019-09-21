package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ConfirmDonation_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_donation_);

    }

    public void confirm (View view){
        Toast.makeText(this, "THANK YOU FOR DONATING !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (ConfirmDonation_Activity.this , HomeMenu_Activity.class);
        startActivity(intent);
    }
}
