package com.example.dcube.foodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class ContactImageAdapter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    // BcardImage data[] = null;
    ArrayList<Item> data=new ArrayList<Item>();
    public ContactImageAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.b = (Button)row.findViewById(R.id.button2);
            holder.b1 =(Button)row.findViewById(R.id.button6);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        Item picture = data.get(position);
        holder.txtTitle.setText(picture._name);
        //convert byte to bitmap take from contact class

        byte[] outImage=picture._Image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        holder.b.setTag(picture);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = (Item)view.getTag();
                //int id = item.getID();
                //int d = item.getdays();
                //String name = item.getName();
                //Bundle ab =new Bundle();
                //ab.putInt("id",id);
                //ab.putString("name",name);
                //ab.putInt("days",d);


                Toast.makeText(view.getContext(),"Update",Toast.LENGTH_LONG).show();
                Intent ch = new Intent(view.getContext(),updateDb.class);
                //ch.putExtras(ab);
                view.getContext().startActivity(ch);
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item item = (Item)view.getTag();
                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteContact(item.getID());//item.getId() refers to your row id that you have fetched from Sqlite
                data.remove(position);
                notifyDataSetChanged();
                //int id = getPosition(getItem(position));
                //Object toRemove = data.get(id);
                //DatabaseHandler db = new DatabaseHandler(context);
                // db.deleteContact(1);
                // data.remove(position);
                //notifyDataSetChanged();


                //Toast.makeText(context,"Removed",Toast.LENGTH_LONG).show();
            }
        });
        return row;

    }

    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        Button b;
        Button b1;
    }
}
