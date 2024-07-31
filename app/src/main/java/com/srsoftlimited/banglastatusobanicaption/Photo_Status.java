package com.srsoftlimited.banglastatusobanicaption;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Photo_Status extends Fragment {

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    InterstitialAd mInterstitialAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_photo__status, container, false);
        GridView gridView = myView.findViewById(R.id.gridView);

        LottieAnimationView animationView = myView.findViewById(R.id.progressBar);
        LottieAnimationView no_internet = myView.findViewById(R.id.no_internet);


        //=.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ads>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        Fullscreen_addLoad();
        //=.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ads>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        String url = "https://soybur123.000webhostapp.com/Status_image/Status.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int x = 0; x < response.length(); x++) {
                                JSONObject jsonObject = response.getJSONObject(x);

                                String iamge_link = jsonObject.getString("iamge_link");

                                hashMap = new HashMap<>();
                                hashMap.put("iamge_link", iamge_link);
                                arrayList.add(hashMap);


                            }

                            if (arrayList.size() > 0) {

                                gridView.setAdapter(new MyAdapter());
                                animationView.setVisibility(View.GONE);
                                no_internet.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                animationView.setVisibility(View.GONE);
                no_internet.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Please Internet Connection On", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

        if (container != null) {
            container.removeAllViews();
        }

        return myView;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void Fullscreen_addLoad() {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(getActivity(), "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        AdscallBack();

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }

    private void AdscallBack() {

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.

            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                mInterstitialAd = null;
                Fullscreen_addLoad();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.

                mInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.

            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.


            }
        });
    }

    private class MyAdapter extends BaseAdapter {


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

        @SuppressLint("ResourceType")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();

            View myView = layoutInflater.inflate(R.layout.photo_itam, parent, false);
            ImageView photo = myView.findViewById(R.id.photo);


            hashMap = arrayList.get(position);
            String image_link = hashMap.get("iamge_link");

            Picasso.get().load(image_link)
                    .placeholder(R.drawable.soybur)
                    .into(photo);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("adsP");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String setting = snapshot.child("settingP").getValue(String.class);

                    if (setting.contains("ON")) {

                        photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), Full_photo.class));
                                Bitmap bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                                Full_photo.MYBITMAP = bitmap;

                                if (mInterstitialAd == null) {
                                    startActivity(new Intent(getActivity(), Full_photo.class));

                                } else {
                                    mInterstitialAd.show(getActivity());
                                }

                            }
                        });


                    } else {


                        if (setting.contains("OFF")) {

                            photo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bitmap bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                                    Full_photo.MYBITMAP = bitmap;
                                    startActivity(new Intent(getActivity(), Full_photo.class));
                                }
                            });
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            return myView;


        }
    }

}