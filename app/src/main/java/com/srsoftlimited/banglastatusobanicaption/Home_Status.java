package com.srsoftlimited.banglastatusobanicaption;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.srsoftlimited.banglastatusobanicaption.ads.AddingStatus;
import com.srsoftlimited.banglastatusobanicaption.ads.MyModel;

import java.util.ArrayList;


public class Home_Status extends Fragment {

    InterstitialAd mInterstitialAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home__status, container, false);
        GridView gridView = myView.findViewById(R.id.gridView);


        addingCategory();


        Fullscreen_addLoad();


        gridView.setAdapter(new MyAdapter());

        if (container != null) {
            container.removeAllViews();
        }
        return myView;
    }

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
                Go();
                Fullscreen_addLoad();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Go();
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

            return AddingStatus.sl.size();
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
            LayoutInflater layoutInflater = getLayoutInflater();
            View MyView = layoutInflater.inflate(R.layout.home_itam, parent, false);
            RelativeLayout relativelayout = MyView.findViewById(R.id.relativelayout);
            ImageView icon = MyView.findViewById(R.id.icon);
            TextView name = MyView.findViewById(R.id.name);

            MyModel myModel = AddingStatus.sl.get(position);
            icon.setImageResource(myModel.getImgRes());
            name.setText(myModel.getStatus());

            relativelayout.setOnClickListener(v -> {

                ActivityShow.statusList = myModel.getStatusList();
                ActivityShow.title = myModel.getStatus();
                //=============================================================================
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("adsS");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String setting = snapshot.child("settingS").getValue(String.class);

                            if (setting.contains("ON")) {
                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(getActivity());
                                }else Go();
                            }else Go();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Go();
                        }
                    });

                    //=============================================================================


                }else Go();

            });


            return MyView;
        }
    }
    private void Go(){
        startActivity(new Intent(getActivity(), ActivityShow.class));
    }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////


    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    private void addingCategory() {
        if (AddingStatus.sl != null) AddingStatus.sl.clear();
        ArrayList<String> cl;

        cl = new ArrayList<>();
        cl.add("অভিমান কখনো মনে পুষে রাখবেন না.. ভুলে যাবেন!! ক্ষুদ্র ক্ষুদ্র অভিমান থেকেই বৃহৎ দূরত্বের সৃষ্টি হয়।");
        cl.add("অন্যের উপর অভিমান করে নিজেকে কষ্ট দেওয়া মানুষ গুলি খুবই বোকা!");
        cl.add("আমি এক সমুদ্র অভিমান করলে, যে মানুষটি আমাকে জড়িয়ে ধরে এক আকাশ ভালোবাসে… সেই মানুষটি হচ্ছে আমার মা!");
        cl.add("সবার সাথে অভিমান করা সাজে না! কারণ সবাই অভিমানের ভাষা বোঝে না।");
        cl.add("মায়া বাড়লে অভিমানও বেড়ে যায়..!! তাই যে মায়া বোঝে না তার প্রতি অভিমান বাড়াতে নেই।");
        cl.add("যে যতো বেশী রাগ করে অভিমান করে, তার ভিতরে সব থেকে বেশী ভালোবাসা থাকে।");
        AddingStatus.createSub("অভিমান", R.drawable.oviman, cl);

        cl = new ArrayList<>();
        cl.add("বন্ধুত্বের পরে কাউকে ভালোবাসা সম্ভব , কিন্তু ভালোবাসার পর কারও সাথে বন্ধুত্ব সম্ভব নয় ।");
        cl.add("একটা মানুষ তখনই কাঁদে ,যখন তার মনের সঙ্গে লড়াই করে পরাজিত হয়।");
        cl.add("ভাগ্যের কাছে আমি হার মানি নাই হেরে গেছি শুধু বিশ্বাসের কাছে ।");
        cl.add("মৃত্যু শুধু দেহের হয় না কখনও মৃত্যু স্বপ্ন আর ইচ্ছেরও হয় ।");
        AddingStatus.createSub("কষ্ট", R.drawable.kosto, cl);

        cl = new ArrayList<>();
        cl.add("“আমি একাকিত্ব অনুভব না করে একা থাকার চেষ্টা করছি।”");
        cl.add("“আমি একা নই, আমার কল্পনায় অনেক বন্ধুরা আছে যার আমাকে আগে বাড়তে সাহস দায়।”");
        cl.add(" “আমি একা নই, কিন্তু আমি একা – তোমাকে ছাড়া ।”");
        cl.add("“এই সময় শক্তিশালী হওয়ার, একা হাঁটা শুরু করুন…”");
        cl.add("“আপনার আশা হারাবেন না, যতই একাকিত্ব হোক না কেন!”");
        cl.add("“আমার শুধু একটু একা সময় দরকার… রিচার্জ করতে।”");
        AddingStatus.createSub("একাকিত্ব", R.drawable.aka, cl);

        cl = new ArrayList<>();
        cl.add("কেউ এক ফোঁটা ভালোবাসার জন্য আকুল ছিল আর কারো মন সাগর দিয়েও ভরেনি ..!!");
        cl.add("সে যদি আমাকে ভুলে সুখী হয় তবে অভিযোগ কি,আর আমি তাকে সুখী না দেখলে ভালোবাসা কিসের।");
        cl.add("কেন তোমাকে এই আফসোস সারাজীবন থেকে যাবে।");
        cl.add("আজ ছায়াকে জিজ্ঞেস করলাম,তুমি আমার সাথে হাঁটছ কেন,সেও হেসে বলল,তোমার সাথে আর কে আছে?");
        AddingStatus.createSub("ব্রেকআপ", R.drawable.brackup, cl);


    }
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
    //============================================================================================================
}