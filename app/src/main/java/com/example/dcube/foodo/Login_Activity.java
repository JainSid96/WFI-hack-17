package com.example.dcube.foodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
    }

    public void register(View view){
        Intent intent = new Intent(Login_Activity.this , Menu_Activity.class);
        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
