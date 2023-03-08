package com.saj.bmcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private ImageView fbId,instagramId,linkedinId,fbPageId;
    private TextView sajLinkId;
    LinearLayout bannerAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bannerAds = findViewById(R.id.bannerAds);
        MainActivity.BannerAds.loadAds(bannerAds, MainActivity2.this);

        fbId = findViewById(R.id.fbId);
        instagramId = findViewById(R.id.instagramId);
        linkedinId = findViewById(R.id.linkedinId);
        fbPageId = findViewById(R.id.fbPageId);
        sajLinkId = findViewById(R.id.sajLinkId);

        fbId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.facebook.com/saj0cse"));
                startActivity(intent);
            }
        });

        instagramId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.instragram.com/saj0cse"));
                startActivity(intent);
            }
        });

        linkedinId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.linkedin.com/in/saj0cse"));
                startActivity(intent);
            }
        });

        fbPageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.facebook.com/saj.developer"));
                startActivity(intent);
            }
        });

        sajLinkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.facebook.com/saj.developer"));
                startActivity(intent);
            }
        });
    }
}