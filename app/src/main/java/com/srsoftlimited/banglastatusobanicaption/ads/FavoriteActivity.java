package com.srsoftlimited.banglastatusobanicaption.ads;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.srsoftlimited.banglastatusobanicaption.MainActivity;
import com.srsoftlimited.banglastatusobanicaption.R;

import java.util.ArrayList;
import android.database.Cursor;

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<MyModel> historyList = new ArrayList<>();
    OfflineStorage storage;
    //====================
    ImageView back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        back_home = findViewById(R.id.back_home);
        storage = new OfflineStorage(this);

        back_home.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        addingValue();
    }

    private void addingValue() {

        // Query to retrieve data
        SQLiteDatabase db = storage.getReadableDatabase();

        Cursor cursor = db.query("status",null,null,null,null,null,null);

        if(historyList != null) historyList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            historyList.add(new MyModel(title, description, id));
        }

        cursor.close();
        db.close();


        if (historyList.isEmpty()) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    VibrationEffect effect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE);
                    vibrator.vibrate(effect);
                }else {
                    vibrator.vibrate(300);
                }
            }

            Toast.makeText(this, "No Favorite available!", Toast.LENGTH_LONG).show();
            onBackPressed();
            this.finish();
        } else {
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(new Adapter());
        }
    }

    public void allClear(View view) {
        storage.deleteAllData();
        addingValue();
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.d("xx", "count = " + historyList.size());
            return historyList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(FavoriteActivity.this).inflate(R.layout.status_itam, viewGroup, false);
            if (historyList.isEmpty()) {
                Toast.makeText(FavoriteActivity.this, "No History!", Toast.LENGTH_LONG).show();
                onBackPressed();
                FavoriteActivity.this.finish();
            } else {
                Log.d("xx", "" + historyList.size());
            }
            MyModel model = historyList.get(position);
            TextView status = view.findViewById(R.id.status);
            LinearLayout laylike = view.findViewById(R.id.laylike);
            ImageView like = view.findViewById(R.id.like);
            like.setImageResource(R.drawable.redlove);

            status.setText(model.getStatus());
            laylike.setOnClickListener(v -> {
                storage.deleteRowById(model.getPosition());
                addingValue();
            });


            return view;
        }
    }
}