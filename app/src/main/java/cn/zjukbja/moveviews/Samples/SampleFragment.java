package cn.zjukbja.moveviews.Samples;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zjukbja.moveviews.R;

/**
 * Created by zhou on 16/5/16.
 */
public class SampleFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_fragment,container,true);
        return view;
    }
}
