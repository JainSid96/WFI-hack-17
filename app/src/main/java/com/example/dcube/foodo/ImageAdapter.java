package com.example.dcube.foodo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    byte imageInByte[];

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.pic1, R.drawable.pic2,
            R.drawable.pic3, R.drawable.pic4,
            R.drawable.pic5, R.drawable.pic6,
            R.drawable.pic7, R.drawable.pic8,
            R.drawable.pic9, R.drawable.pic10,
            R.drawable.pic11, R.drawable.pic12,
            //R.drawable.addi

    };

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageButton btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new ImageButton(mContext);

            btn.setLayoutParams(new GridView.LayoutParams(400, 400));
            btn.setPadding(8, 8, 8, 8);
        }
        else
        {
            btn = (ImageButton) convertView;
        }
        btn.setBackgroundResource(mThumbIds[position]);
        btn.setId(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                int id =  view.getId();
                Resources res= context.getResources();
                DatabaseHandler db = new DatabaseHandler(context.getApplicationContext());
                String name;
                int d;
                if(id == 0)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic1);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Apple";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    // Toast.makeText(context,"0",Toast.LENGTH_LONG).show();
                }
                if(id == 1)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic2);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Milk";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"1",Toast.LENGTH_LONG).show();
                }
                if(id == 2)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic3);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Curd";
                    d = 10;
                    db.addItem(new Item(name,imageInByte,d));
                    scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    // Toast.makeText(context,"2",Toast.LENGTH_LONG).show();
                }
                if(id == 3)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic4);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Beans";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    // Toast.makeText(context,"3",Toast.LENGTH_LONG).show();
                }
                if(id == 4)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic5);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Potato";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    // db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    // Toast.makeText(context,"4",Toast.LENGTH_LONG).show();
                }
                if(id == 5)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic6);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Tomato";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    // db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"5",Toast.LENGTH_LONG).show();
                }
                if(id == 6)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic7);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Onions";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"6",Toast.LENGTH_LONG).show();
                }
                if(id == 7)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic8);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Cabbage";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    // db.addItem(new Item(name,imageInByte,d));
                    // scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"7",Toast.LENGTH_LONG).show();
                }
                if(id == 8)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic9);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Spinach";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"8",Toast.LENGTH_LONG).show();
                }
                if(id == 9)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic10);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Cucumber";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    // db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"9",Toast.LENGTH_LONG).show();
                }
                if(id == 10)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic11);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Strawberry";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    //db.addItem(new Item(name,imageInByte,d));
                    //scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"10",Toast.LENGTH_LONG).show();
                }
                if(id == 11)
                {
                    Drawable drawable = res.getDrawable(R.drawable.pic12);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageInByte = stream.toByteArray();
                    name = "Carrot";
                    d = 10;
                    Intent ab = new Intent(context,DefoAdd.class);
                    Bundle extras = new Bundle();
                    extras.putString("name1",name);
                    extras.putByteArray("image",imageInByte);
                    extras.putInt("days",d);
                    ab.putExtras(extras);
                    context.startActivity(ab);
                    // db.addItem(new Item(name,imageInByte,d));
                    // scheduleNotification(getNotification("Please consume "+name+ ".It will Expire Soon." ), 5000);
                    //Toast.makeText(context,"11",Toast.LENGTH_LONG).show();
                }
              /*  if(id == 12)
                {
                    Intent intent = new Intent(mContext,
                     Add_Item.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } */


            }
        });

        return btn;
        /*ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
        return imageView; */
    }

    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentTitle("Smart Fridge");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.alert);

        // Uri sound = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.siri);
        //builder.setSound(sound);

        return builder.build();
    }


}