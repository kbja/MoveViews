package cn.zjukbja.moveviews.Samples;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import cn.zjukbja.moveviews.MoveView;
import cn.zjukbja.moveviews.R;


/**
 * Created by zhou on 16/5/13.
 */
public class SampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        View view;
        try {
            //basic view
            view  = findViewById(R.id.first_line);
            new MoveView(view, this);
            view = findViewById(R.id.second_line);
            new MoveView(view,this);
            view = findViewById(R.id.image1);
            new MoveView(view,this);
            view = findViewById(R.id.third_line_1);
            new MoveView(view,this);

            //fragment
            FragmentManager manager = getFragmentManager();
            Fragment sampleFragment = manager.findFragmentById(R.id.sample_fragment);
            view = sampleFragment.getView();
            new MoveView(view,this);
            view = view.findViewById(R.id.fragment_image1);
            new MoveView(view,this);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.button1:
                Log.i("button",1+"");
                break;
            case R.id.button2:
                Log.i("button",2+"");
                break;
            default:
        }
    }
}
