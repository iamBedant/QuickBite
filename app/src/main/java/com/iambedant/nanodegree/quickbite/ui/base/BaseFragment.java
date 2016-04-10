package com.iambedant.nanodegree.quickbite.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.iambedant.nanodegree.quickbite.injection.component.DaggerFragmentComponent;
import com.iambedant.nanodegree.quickbite.injection.component.FragmentComponent;
import com.iambedant.nanodegree.quickbite.injection.module.FragmentModule;
import com.iambedant.nanodegree.quickbite.myApplication;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
public class BaseFragment extends Fragment {
    private FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentComponent getmFragmentComponent() {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(new Fragment()))
                    .applicationComponent(myApplication.get(getActivity()).getComponent())
                    .build();
        }
        return mFragmentComponent;
    }
}
