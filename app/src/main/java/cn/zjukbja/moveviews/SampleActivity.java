package cn.zjukbja.moveviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Created by zhou on 16/5/13.
 */
public class SampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        View view;
        try {
            view  = findViewById(R.id.first_line);
            new MoveView(view, this);
            view = findViewById(R.id.second_line);
            new MoveView(view,this);
            view = findViewById(R.id.image1);
            new MoveView(view,this);
            view = findViewById(R.id.third_line_1);
            Log.e("1234",view.getHeight()+"****");
            Log.e("1234",((View)view.getParent()).getHeight()+"****");
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
