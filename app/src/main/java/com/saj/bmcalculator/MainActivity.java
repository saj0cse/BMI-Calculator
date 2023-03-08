package com.saj.bmcalculator;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView bmiResultTxVId, textViewId;
    private EditText weightId, feetId, incId, ageId;
    private Button calculateBtnId, clearBtnId;
    LinearLayout bannerAds;
    private long pressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmiResultTxVId = findViewById(R.id.bmiResultTxVId);
        textViewId = findViewById(R.id.textViewId);
        weightId = findViewById(R.id.weightId);
        feetId = findViewById(R.id.feetId);
        incId = findViewById(R.id.incId);
        ageId = findViewById(R.id.ageId);
        calculateBtnId = findViewById(R.id.calculateBtnId);
        clearBtnId = findViewById(R.id.clearBtnId);

        bannerAds = findViewById(R.id.bannerAds);
        BannerAds.loadAds(bannerAds, MainActivity.this);



        clearBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weightId.setText("");
                feetId.setText("");
                incId.setText("");
                ageId.setText("");
                bmiResultTxVId.setText("0");
                textViewId.setText("BMI");
                ageId.requestFocus();
            }
        });

        calculateBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calculateBMI();

            }
        });

    }

    //----------------------Method start-----------------------------//
    public void calculateBMI() {
        //----------------------start-----------------------------//
        String ws = weightId.getText().toString();
        String fs = feetId.getText().toString();
        String is = incId.getText().toString();
        String as = ageId.getText().toString();


        if (as.isEmpty()) {
            ageId.setError("age?");
            ageId.requestFocus();
            return;
        } else if (ws.isEmpty()) {
            weightId.setError("weight?");
            weightId.requestFocus();
            return;
        } else if (fs.isEmpty()) {
            feetId.setError("feet?");
            feetId.requestFocus();
            return;
        } else if (is.isEmpty()) {
            incId.setError("inc?");
            incId.requestFocus();
            return;
        }

        float weightF = Float.parseFloat(ws);
        float feetF = Float.parseFloat(fs);
        float incF = Float.parseFloat(is);

        float height = (float) ((feetF * 0.3048) + (incF * 0.0254));
        float bmiCalculationResult = weightF / (height * height);
        String result = String.format("%.1f", bmiCalculationResult);
        float finalResult = Float.parseFloat(result);


        bmiResultTxVId.setText("" + finalResult);


        if (finalResult < 18.5) {
            textViewId.setText("BMI Result\n\n'Underweight'");
        } else if (finalResult == 18.5 || finalResult <= 24.9) {
            textViewId.setText("BMI Result\n\n'Normal weight' ");
        } else if (finalResult == 25 || finalResult <= 29.9) {
            textViewId.setText("BMI Result\n\n'Overweight' ");
        } else if (finalResult >= 30) {
            textViewId.setText("BMI Result\n\n'Obesity' ");
        } else {
            textViewId.setText("");
        }

        //----------------------end-----------------------------//

    } //----------------------Method end-----------------------------//





    //----------------------Banner Method start-----------------------------//
    public static class BannerAds {
        public static void loadAds(LinearLayout container, Context context) {
            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            AdView adView = new AdView(context);
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            container.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdSize(AdSize.BANNER);
            adView.loadAd(adRequest);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("ads");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String setting = snapshot.child("setting").getValue(String.class);
                    if (setting.contains("ON")){
                        new BannerAds();
                        adView.setVisibility(VISIBLE);

                    }else if (setting.contains("OFF")){
                        adView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }
    }//----------------------Banner Method end-----------------------------//

    //----------------------onBackPressed Method start-----------------------------//
    public void onBackPressed(){


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("WARNING")
                            .setMessage("Are you sure?You want to exit!")
                                    .setIcon(R.drawable.ic_warning)
                                            .setNeutralButton("CANCLE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            })
                                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.dismiss();
                                                        }
                                                    })
                                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    dialogInterface.dismiss();
                                                                  finish();
                                                                }
                                                            }).show();


            // super.onBackPressed();
            // finish();

    } //----------------------onBackPressed Method end-----------------------------//


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.aboutAppMenuId){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        } else if (item.getItemId()==R.id.shareAppMenuId){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plan");
            String shareSub = "BMI Calculator";
            String shareBody = "Use BMI calculator and check you BMI\nDeveloped by: SAJ Developer\n\nDownload Apk\nhttps://drive.google.com/file/d/1SoWddXN6vOamOQsVK0GdfCowvQiZJmtL/view?usp=sharing";
            intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent, "Share Using"));

        }
        return super.onOptionsItemSelected(item);
    }


}