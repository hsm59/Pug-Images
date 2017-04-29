package com.hussain.pugimages_debug.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hussain.pugimages_debug.R;

/**
 * Created by hussain on 4/20/17.
 */

public class SlideshowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slideshow_fragment, container, false);
        return view;
    }
}
