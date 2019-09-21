package com.example.dcube.foodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class DefoAdd extends AppCompatActivity {
    EditText e1;
    TextView t1;
    Button b1;
    ImageView im1;

    byte imageInByte[];
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.defauchange);
        e1 = (EditText) findViewById(R.id.et);
        b1 = (Button)findViewById(R.id.button16);
        t1 = (TextView)findViewById(R.id.t3);
        Bundle extras = getIntent().getExtras();
        String n = (String) extras.get("name1");

        //Toast.makeText(getApplicationContext(), n, Toast.LENGTH_SHORT).show();

        imageInByte = (byte[]) extras.get("image");
        int d = (int) extras.get("days");
        e1.setText(Integer.toString(d));
        t1.setText(n);
        //ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
        //Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        //im1.setImageBitmap(theImage);
        //Resources res = getResources();
        //Drawable drawable = res.getDrawable(R.drawable.idea);
        //Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //imageInByte = stream.toByteArray();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = t1.getText().toString();
                String days = e1.getText().toString();
                int d = Integer.parseInt(days);

                db.addItem(new Item(name, imageInByte, d));
                //scheduleNotification(getNotification("Please consume " + name + ".It will Expire Soon."), 5000);
                Toast.makeText(getApplicationContext(), "Item " + name + " is added in your fridge", Toast.LENGTH_LONG).show();
                Intent ab = new Intent(getApplicationContext(),AddItem_Activity.class);
                startActivity(ab);
            }
        });

    }
}

