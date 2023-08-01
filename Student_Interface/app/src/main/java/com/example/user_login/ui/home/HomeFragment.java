package com.example.user_login.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user_login.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);

        sliderLayout=view.findViewById(R.id.slider);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1);


        setSliderView();


        map=view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap();
            }
        });


        return view;

    }

    private  void openmap(){

        Uri uri=Uri.parse("geo:0,0?q=Pune District Education Association's College of Engineering");

        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }


    private void setSliderView() {
        for (int i=0;i<5;i++){
            DefaultSliderView sliderView=new DefaultSliderView(getContext());

            switch (i){
                case  0:
                    sliderView.setImageDrawable(R.drawable.img1);
                    break;
                case  1:
                    sliderView.setImageDrawable(R.drawable.img2);
                    break;
                case  2:
                    sliderView.setImageDrawable(R.drawable.img3);
                    break;
                case  3:
                    sliderView.setImageDrawable(R.drawable.img4);
                    break;
                case  4:
                    sliderView.setImageDrawable(R.drawable.img5);
                    break;
            }


            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout.addSliderView(sliderView);
        }
    }
}