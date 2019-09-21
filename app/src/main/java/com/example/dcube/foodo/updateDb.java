package com.example.dcube.foodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class updateDb extends AppCompatActivity {
    EditText e1,e2;
    Button b1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        e1 = (EditText) findViewById(R.id.upname);
        e2 = (EditText) findViewById(R.id.updays);
        b1 = (Button) findViewById(R.id.upbu);
        // Bundle extras = getIntent().getExtras();
        //String name  = extras.getString("name");
        // int id  = extras.getInt("id");
        //int days = extras.getInt("days");
        //e1.setText(name);
        //e2.setText(Integer.toString(days));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String de = e2.getText().toString();
                //int dey = Integer.parseInt(de);

                Toast.makeText(getApplicationContext(),"UPDATED",Toast.LENGTH_LONG).show();
                Intent ab = new Intent(getApplicationContext(),ViewFridge_Activity.class);
                startActivity(ab);


            }
        });
    }
}
