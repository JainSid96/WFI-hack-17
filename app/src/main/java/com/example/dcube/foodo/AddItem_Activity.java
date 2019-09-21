package com.example.dcube.foodo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class AddItem_Activity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    EditText e1,e2;
    Button ib;
    Button b1;
    ImageView im1;
    private Spinner spinner2;

    byte imageInByte[];
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri; // file url to store image/video

    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tieup);
        e1 = (EditText) findViewById(R.id.ed1);
        spinner2 = (Spinner) findViewById(R.id.spinner);

        //if (getIntent().getExtras() != null) {
        //Bundle extras = getIntent().getExtras();
        //String n = (String) extras.get("name");
        // Toast.makeText(getApplicationContext(), n, Toast.LENGTH_SHORT).show();
        // imageInByte = (byte[]) extras.get("image");
        //int d = (int) extras.get("days");
        //e1.setText(n);
        //spinner2.setSelection(0);
        //ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
        //Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        //im1.setImageBitmap(theImage);
        // } else {

        // the drawable (Captain Obvious, to the rescue!!!)
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.qwe);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageInByte = stream.toByteArray();
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        // Spinner element

        //spinner2 = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("7");
        list.add("10");
        list.add("12");
        list.add("15");
        list.add("18");
        list.add("20");
        list.add("25");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        // e1 = (EditText) findViewById(R.id.ed1);
        //e2 = (EditText) findViewById(R.id.editText2);
        im1 = (ImageView) findViewById(R.id.imageView2);
        ib = (Button) findViewById(R.id.imgbutton);
        b1 = (Button) findViewById(R.id.button1);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = e1.getText().toString();
                String days = spinner2.getSelectedItem().toString();
                int d = Integer.parseInt(days);

                db.addItem(new Item(name, imageInByte, d));
                scheduleNotification(getNotification("Please consume " + name + ".It will Expire Soon."), 10000);
                Toast.makeText(getApplicationContext(), "Item " + name + " is added in your fridge", Toast.LENGTH_LONG).show();
                Intent ab = new Intent(getApplicationContext(),CreateFridge_Activity.class);
                startActivity(ab);
            }
        });
    }
    //  }
   /* public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Grid.class);
        startActivityForResult(myIntent, 0);
        return true;

    } */
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Smart Fridge");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.alert);
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.siri);
        builder.setSound(sound);

        return builder.build();
    }


    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void previewCapturedImage() {
        try {

            im1.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            im1.setImageBitmap(bitmap);

            //convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            imageInByte = stream.toByteArray();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
