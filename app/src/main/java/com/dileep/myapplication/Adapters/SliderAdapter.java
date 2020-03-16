package com.dileep.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dileep.myapplication.R;
import com.dileep.myapplication.pojos.OffersPojo;


import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    ArrayList<OffersPojo> offersPojoArrayList;
    Context context;
    private LayoutInflater inflater;
    RequestOptions requestOptions;
    ArrayList<Integer> images;


    public SliderAdapter(ArrayList<OffersPojo> offersPojoArrayList, Context context, ArrayList<Integer> images) {
        this.offersPojoArrayList = offersPojoArrayList;
        this.context=context;
        this.images=images;
        inflater = LayoutInflater.from(context);
        requestOptions=new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(R.drawable.image);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.layout_slide, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

 /*       final TextView slidepercent=(TextView) imageLayout.findViewById(R.id.slidepercent);
        slidepercent.setText(offersPojoArrayList.get(position).getOfferPercent()+"%"+" OFF");


        Glide.with(context)
                .load(offersPojoArrayList.get(position).getImageUrls().get(0))
                .apply(requestOptions)
                .into(imageView);*/


        Glide.with(context).load(images.get(position))
                .into(imageView);
        //imageView.setImageResource(images.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;

    }

    @Override
    public int getCount() {

        return images.size();

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view.equals(o);
    }
}
