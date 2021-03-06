package com.example.myapplication;

import static android.graphics.BlendMode.COLOR;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<NewsInformation> {


    public CustomAdapter(Context context, ArrayList<NewsInformation> news) {

        super(context, 0, news);



    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        NewsInformation item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.text1);
        title.setText(item.title);

        TextView description = (TextView) convertView.findViewById(R.id.text2);
        description.setText(item.description);

        TextView pubDate = (TextView) convertView.findViewById(R.id.text3);
        pubDate.setText(item.pubDate);

        String imageUri = item.image;
        ImageView ivBasicImage = (ImageView) convertView.findViewById(R.id.image);
        Picasso.get().load(imageUri).into(ivBasicImage);

        Button checkButton = (Button) convertView.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(item);

            }
        });



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Uri uri = Uri.parse(item.link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

            }
        });
        return convertView;

    }
}
