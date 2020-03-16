package com.dileep.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.dileep.myapplication.Adapters.DisplaySliderAdapter;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class DisplayActivity extends AppCompatActivity {

    private static ViewPager sliderPager;
    ArrayList<String> images=new ArrayList<>();
    private int currentPage = 0;
    String Name,Cost,Discount,number,id;
    Button submit;
    Toolbar toolbar;
    TextView  productName,productCost,productDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        sliderPager=(ViewPager)findViewById(R.id.pager);
        productName=(TextView)findViewById(R.id.name);
        productCost=(TextView)findViewById(R.id.cost);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        productDiscount=(TextView)findViewById(R.id.discount);
        submit=(Button)findViewById(R.id.submit);
        toolbar.setNavigationIcon(R.drawable.navigation_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Bundle bundle= getIntent().getExtras();
        if (bundle!=null){
            Name=bundle.getString("name");
            Cost=bundle.getString("cost");
            images=bundle.getStringArrayList("images");
            number=bundle.getString("number");
            id=bundle.getString("id");

            productName.setText(Name);
            productCost.setText(Cost);

            init();
        }else {
            Toast.makeText(this, "bundle empty", Toast.LENGTH_SHORT).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp(number,id,images.get(0));
            }
        });

    }

    private void openWhatsApp(String phonenumber,String productId,String link) {
        String smsNumber = "91"+phonenumber; //without '+'
        String dlink="https://www.youtube.com/watch?v=z9_EsRclGL0";
        String message="I want this product"+"\nId="+productId+"\n"+link;
        try {
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ smsNumber +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void init() {
        //  mPager = (ViewPager) findViewById(R.id.pager);
        sliderPager.setAdapter(new DisplaySliderAdapter(images, DisplayActivity.this));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(sliderPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                sliderPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 20000,20000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }
}
