package com.ghiath.magmatask;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ghiath.magmatask.databinding.FragmentImageBinding;
import com.ghiath.magmatask.utils.AutoClearedValue;


public class ImageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";



    private String mParam1;



    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }
    FragmentImageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentImageBinding dataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_image,container,false);
        binding = new AutoClearedValue<>(this, dataBinding).get();
        Glide.with(this).load(mParam1).into(binding.singleIm);
        return binding.getRoot();
    }

    }


