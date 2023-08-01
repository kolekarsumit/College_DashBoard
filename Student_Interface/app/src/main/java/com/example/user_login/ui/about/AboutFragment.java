package com.example.user_login.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user_login.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        list=new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_comp,"Computer Engineering","Computer engineering (CoE or CpE) is a branch of electrical engineering and computer science that integrates several fields of computer science and electronic engineering required to develop computer hardware and software."));
        list.add(new BranchModel(R.drawable.ic_comp,"Information Technology","the study or use of electronic equipment, especially computers, for collecting, storing and sending out information."));
        list.add(new BranchModel(R.drawable.ic_mech,"Electronics And Telecomunication","This field comprises of the combination of electronics and communication applications. Electronics and telecommunication engineering is designing, research and verifying the electronic systems, they also look into the concept of broadcasting signals."));
        list.add(new BranchModel(R.drawable.ic_mech,"Mechanical Engineering","Mechanical engineering is one of the broadest engineering disciplines. Mechanical engineers design, develop, build, and test. They deal with anything that moves, from components to machines to the human body."));
        list.add(new BranchModel(R.drawable.ic_comp,"Civil Engineering","Civil engineering is a professional engineering discipline that deals with the design, construction, and maintenance of the physical and naturally built ."));

        adapter=new BranchAdapter(getContext(),list);
        viewPager=view.findViewById(R.id.cources);
        viewPager.setAdapter(adapter);

        ImageView imageView=view.findViewById(R.id.collageimage);
        Glide.with(getContext()).load(R.drawable.coe).into(imageView);
        return view;
    }

}