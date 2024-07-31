package com.srsoftlimited.banglastatusobanicaption;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.srsoftlimited.banglastatusobanicaption.ads.FavoriteActivity;
import com.srsoftlimited.banglastatusobanicaption.ads.MyModel;
import com.srsoftlimited.banglastatusobanicaption.ads.OfflineStorage;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    AdView mAdView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar materialToolbar;


    LinearLayout bannar_background;
    TextView ADSOFF;
    Animation myanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout = findViewById(R.id.tabLayout);
        mAdView = findViewById(R.id.madView);
        navigationView = findViewById(R.id.navigationView);
        materialToolbar = findViewById(R.id.materialToolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ADSOFF = findViewById(R.id.welcome);
        bannar_background = findViewById(R.id.bannar_background);
        myanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);
        ADSOFF.startAnimation(myanim);


        //=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        final String appPakege = getPackageName();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, materialToolbar, R.string.Drawer_close, R.string.Drawer_open
        );


        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.rat_us) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id" + appPakege)));
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.share) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
                    intent.putExtra(Intent.EXTRA_TEXT, "Your Application Link Here\n" + "https://play.google.com/store/apps/details?id" + appPakege);
                    startActivity(Intent.createChooser(intent, "Share Via"));
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.privacy) {
                    startActivity(new Intent(MainActivity.this, Privacy_Polacy.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.favorite) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                }

                return true;
            }
        });
/////////////////////////////////////////////////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, new Home_Status());
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);


                } else if (item.getItemId() == R.id.Photo_status) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, new Photo_Status());
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.share) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
                    intent.putExtra(Intent.EXTRA_TEXT, "Your Application Link Here\n" + "https://play.google.com/store/apps/details?id" + appPakege);
                    startActivity(Intent.createChooser(intent, "Share Via"));
                    drawerLayout.closeDrawer(GravityCompat.START);


                } else if (item.getItemId() == R.id.ratting) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id" + appPakege)));
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.moreapp) {

                    //startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("https://play.google.com/store/apps/details?id=developer acuunt number")));
                    Toast.makeText(getApplicationContext(), "Developer accounts aga khulo", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (item.getItemId() == R.id.privacy) {
                    startActivity(new Intent(MainActivity.this, Privacy_Polacy.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.gmail) {

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"soyburrahman364@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Select Gmail"));

                } else if (item.getItemId() == R.id.facebook) {

                    String fb_Uid = "61555673836715";
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + fb_Uid));
                        startActivity(intent);
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + fb_Uid)));
                    }


                } else if (item.getItemId() == R.id.youtube) {

                    gotUrl("https://www.youtube.com/@SoyburReaction");

                } else if (item.getItemId() == R.id.instagram) {
                    gotUrl("https://www.instagram.com/mdsoybur_rahman12e/?igsh=ZWl1aGYyMXI1dmN6&fbclid=IwAR1XGzIcE5Ved84XqjpwropM2EAmwu1tqoU7E-OYtzth2F8jsUm56sEg2hs");

                }
                return true;
            }
        });

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Tablayout >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new Home_Status());
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();

                if (tabPosition == 0) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, new Home_Status());
                    fragmentTransaction.commit();
                } else if (tabPosition == 1) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, new Photo_Status());
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        adSetting();


//>

    }


    private void AdsBannar() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        final int[] BANNER_AD_CLICK_COUNT = {0};
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (BANNER_AD_CLICK_COUNT[0] >= 3) {
                    if (mAdView != null) {
                        mAdView.setVisibility(View.GONE);
                        bannar_background.setVisibility(View.VISIBLE);


                    }
                } else {
                    if (mAdView != null) {
                        mAdView.setVisibility(View.VISIBLE);
                        bannar_background.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                BANNER_AD_CLICK_COUNT[0]++;

                if (BANNER_AD_CLICK_COUNT[0] >= 6) {
                    if (mAdView != null) {
                        mAdView.setVisibility(View.GONE);
                        bannar_background.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }


    private void adSetting() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ads");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String setting = snapshot.child("setting").getValue(String.class);

                if (setting.contains("ON")) {

                    AdsBannar();
                    mAdView.setVisibility(View.VISIBLE);


                } else {
                    mAdView.setVisibility(View.VISIBLE);
                    bannar_background.setVisibility(View.GONE);
                    if (setting.contains("OFF")) {
                        mAdView.setVisibility(View.GONE);
                        bannar_background.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private void gotUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void ShowDialogBox() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        mView.findViewById(R.id.chancelBTN).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        mView.findViewById(R.id.okBTN).setOnClickListener(v -> {
            finishAndRemoveTask();
            alertDialog.dismiss();

        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        ShowDialogBox();
    }
}