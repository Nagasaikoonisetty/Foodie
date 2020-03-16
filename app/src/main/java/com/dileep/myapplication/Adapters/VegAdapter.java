package com.dileep.myapplication.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dileep.myapplication.Appconst;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.OrderListPojo;
import com.dileep.myapplication.pojos.UploadProductPojo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class VegAdapter extends RecyclerView.Adapter<VegAdapter.VegHolder> {
    ArrayList<UploadProductPojo> productPojoArrayList;
    Context context;
    RequestOptions requestOptions;
    ArrayList<OrderListPojo> orderbag;
    SharedPreferences sharedPreferences;

    public VegAdapter(ArrayList<UploadProductPojo> productPojoArrayList, Context context, ArrayList<OrderListPojo> itemlist) {
        this.productPojoArrayList = productPojoArrayList;
        this.context = context;
        this.orderbag=itemlist;
        requestOptions=new RequestOptions();
        requestOptions.centerCrop();
    }


    @NonNull
    @Override
    public VegHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rslistitem, parent, false);
        return new VegHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VegHolder holder, int position) {
        final UploadProductPojo pojo=productPojoArrayList.get(position);

        Glide.with(context)
                .load(pojo.getImageUrls().get(0))
                .into(holder.image);
        holder.cost.setText(pojo.getProductCost());
        holder.title.setText(pojo.getProductName());
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addposition= Appconst.orderListPojoArrayList.size();
                Appconst.orderListPojoArrayList.add(addposition,new OrderListPojo(pojo.getImageUrls().get(0),pojo.getProductName(),pojo.getProductCost()));
                sharedPreferences= context.getSharedPreferences("items", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                Gson gson=new Gson();
                String json=gson.toJson(Appconst.orderListPojoArrayList);
                editor.putString("dileep",json);
                editor.apply();
                Toast.makeText(context, "item aded to bag", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return productPojoArrayList.size();
    }

    public class VegHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,cost;
        Button addtocart;
        public VegHolder(@NonNull View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.resname);
            cost=(TextView)itemView.findViewById(R.id.rescost);
            addtocart=(Button)itemView.findViewById(R.id.addcart);
        }
    }
}
