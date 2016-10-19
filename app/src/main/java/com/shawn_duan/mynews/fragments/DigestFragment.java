package com.shawn_duan.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawn_duan.mynews.R;

/**
 * Created by sduan on 10/18/16.
 */

public class DigestFragment extends Fragment {
    private final static String TAG = DialogFragment.class.getSimpleName();

    public DigestFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digest, container, false);
        return view;
    }
}
