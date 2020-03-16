package com.dileep.myapplication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dileep.myapplication.pojos.OrderListPojo;

import java.util.ArrayList;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderHolder> {
    ArrayList<OrderListPojo> list;
    Context context;

    public OrderListAdapter(ArrayList<OrderListPojo> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.final_list_item, viewGroup, false);

        return new OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder orderHolder, int position) {
                OrderListPojo listPojo=list.get(position);
        Glide.with(context).load(listPojo.getImage())
                .into(orderHolder.image);
        orderHolder.cost.setText(String.valueOf(listPojo.getCost()));
        orderHolder.name.setText(listPojo.getName());
        orderHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positiondil=orderHolder.getAdapterPosition();
                //  AddProduct.selectedpicsList.remove(positiondil);
                removeAt(positiondil);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAt(int position) {
        list.remove(position);
       // OrderListActivity.itemlist.remove(position);
        notifyItemRemoved(position);
        if (list.size()>0){
            OrderListActivity.nodata.setVisibility(View.GONE);
            OrderListActivity.paylayout.setVisibility(View.VISIBLE);
        }else {
            OrderListActivity.nodata.setVisibility(View.VISIBLE);
            OrderListActivity.paylayout.setVisibility(View.GONE);

        }
        OrderListActivity.countTotal(list);

    }


    public class OrderHolder extends RecyclerView.ViewHolder{

        ImageView image,remove;
        TextView name,cost,quantity;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            name=(TextView)itemView.findViewById(R.id.name);
            cost=(TextView)itemView.findViewById(R.id.cost);
            quantity=(TextView)itemView.findViewById(R.id.quantity);
            remove=(ImageView)itemView.findViewById(R.id.remove);
        }
    }
}
