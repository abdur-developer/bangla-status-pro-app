package com.srsoftlimited.banglastatusobanicaption.ads;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srsoftlimited.banglastatusobanicaption.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyAdapter extends BaseAdapter {
    ArrayList<String> arrayList;
    private Context context;
    private String name;
    final OfflineStorage storage;



    public MyAdapter(Context context, String name, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.name = name;
        storage = new OfflineStorage(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View Myview = layoutInflater.inflate(R.layout.status_itam, parent, false);

        LinearLayout laylike = Myview.findViewById(R.id.laylike);
        ImageView like = Myview.findViewById(R.id.like);
        ImageView status_image = Myview.findViewById(R.id.status_image);
        RelativeLayout clicklayout = Myview.findViewById(R.id.clicklayout);
        TextView sstatus = Myview.findViewById(R.id.status);
        LinearLayout laycopy = Myview.findViewById(R.id.laycopy);
        LinearLayout layshare = Myview.findViewById(R.id.layshare);





        String status = arrayList.get(position);
        sstatus.setText(status);
        int id = storage.getIdByTitleAndDescription(name, status);

        if (id != -1) {
            like.setImageResource(R.drawable.redlove);
        } else {
            like.setImageResource(R.drawable.love);
        }

        clicklayout.setOnClickListener(v -> {

            if (clicklayout.getTag() == null || clicklayout.getTag() == "Play") {
                status_image.setImageResource(R.drawable.image1);
                clicklayout.setTag("No");
            } else {
                status_image.setImageResource(R.drawable.image2);
                clicklayout.setTag("Play");
            }

        });

        laylike.setOnClickListener(v -> {

            if (id != -1) {
                storage.deleteRowById(id);
                like.setImageResource(R.drawable.love);
            } else {
                like.setImageResource(R.drawable.redlove);
                storage.Insert(name, status);
            }


        });
        laycopy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copy", sstatus.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
        });

        layshare.setOnClickListener(v -> {

            Intent myintent = new Intent(Intent.ACTION_SEND);
            myintent.setType("text/plian");
            myintent.putExtra(Intent.EXTRA_TEXT, sstatus.getText().toString().trim());
            Intent.createChooser(myintent, "sharing");
            context.startActivity(myintent);

            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

        });


        return Myview;
    }
}