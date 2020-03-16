package com.dileep.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dileep.myapplication.pojos.OrderListPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public  ArrayList<OrderListPojo> itemlist;
    RecyclerView recyclerView;
    OrderListAdapter adapter;
   public static TextView mrp, discount, shipping, totalpay;
    Button confirm;
    public  static int mrp1 = 0, discount1, ttpay;
    ProgressDialog progressDialog;
    Toolbar toolbar;
   public static LinearLayout paylayout;
    public static FrameLayout nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mrp = (TextView) findViewById(R.id.mrp);
        discount = (TextView) findViewById(R.id.discount);
        shipping = (TextView) findViewById(R.id.shipping);
        totalpay = (TextView) findViewById(R.id.totalpay);
        confirm = (Button) findViewById(R.id.confirm);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.navigation_back);
        paylayout=(LinearLayout)findViewById(R.id.paylayout);
        nodata=(FrameLayout)findViewById(R.id.nodata);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loaddata();


        for (int i = 0; i < itemlist.size(); i++) {
            mrp1 = mrp1 + Integer.parseInt(itemlist.get(i).getCost());
        }

        mrp.setText(String.valueOf(mrp1));
        discount1 = Integer.parseInt(discount.getText().toString());
        ttpay = mrp1 - discount1;
        totalpay.setText(String.valueOf(ttpay));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(OrderListActivity.this);
                progressDialog.setMessage("Please wait a sec...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                try {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(OrderListActivity.this, "Order has been successfully placed", Toast.LENGTH_SHORT).show();
                            clear();
                            startActivity(new Intent(OrderListActivity.this, MainActivity.class));
                            finish();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }, 4000);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void clear() {
        Appconst.orderListPojoArrayList.clear();
        sharedPreferences = getSharedPreferences("items", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    public static void countTotal(ArrayList<OrderListPojo> itemlist){
        mrp1=0;
        discount1=0;
        ttpay=0;
        for (int i = 0; i < itemlist.size(); i++) {
            mrp1 = mrp1 + Integer.parseInt(itemlist.get(i).getCost());
        }

        mrp.setText(String.valueOf(mrp1));
        discount1 = Integer.parseInt(discount.getText().toString());
        ttpay = mrp1 - discount1;
        totalpay.setText(String.valueOf(ttpay));
    }
    private void loaddata() {

/*        sharedPreferences = getSharedPreferences("items", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dileep", null);
        Type type = new TypeToken<ArrayList<OrderListPojo>>() {
        }.getType();
        itemlist = gson.fromJson(json, type);
        if (itemlist == null) {
            itemlist = new ArrayList<>();
        }*/
        itemlist=Appconst.orderListPojoArrayList;
        if (itemlist.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            paylayout.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            paylayout.setVisibility(View.VISIBLE);
        }
        adapter = new OrderListAdapter(itemlist, this);
        recyclerView.setAdapter(adapter);

    }

}
