package com.seafile.seadroid2.cameraupload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.hangox.seafile.R;
import com.seafile.seadroid2.ui.fragment.SeafileFragment;

;

/**
 * Welcome fragment for camera upload configuration helper
 */
public class ConfigWelcomeFragment extends SeafileFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.cuc_welcome_fragment, null);

        return rootView;
    }

}

