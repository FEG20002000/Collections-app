package com.iqcollections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SignUpTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflator.inflate(R.layout.signup_tab_fragment,container, false);

        return root;
    }
}
