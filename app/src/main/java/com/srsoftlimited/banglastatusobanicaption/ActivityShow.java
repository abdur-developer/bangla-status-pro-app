package com.srsoftlimited.banglastatusobanicaption;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.srsoftlimited.banglastatusobanicaption.ads.MyAdapter;

import java.util.ArrayList;

public class ActivityShow extends AppCompatActivity {
    public static ArrayList<String > statusList;
    public static String title;

    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tv_title = findViewById(R.id.title_heder);
        tv_title.setText(title);

        findViewById(R.id.back_home).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(ActivityShow.this, title, statusList));

    }
}