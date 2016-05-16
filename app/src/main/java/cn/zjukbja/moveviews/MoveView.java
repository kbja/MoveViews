package cn.zjukbja.moveviews;

import android.app.Fragment;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by zhou on 16/5/11.
 */
public class MoveView {
    private DisplayMetrics dm;
    private final int screenWidth;
    private final int screenHeight;
    private int parentWidth;
    private int parentHeight;

    private final int IDLE = 0;//the moving status
    private final int MOVING = 1;
    private int moveStatus = IDLE;


    private final int inParent = 0;//whether allow the view over the parent;
    private final int inWindow = 1;
    private int viewBorder = inParent;

    private View mView = null;
    private final View mRoot;
    private Context mContext;
    private float lastX, lastY;
    private ChangeAttr changeAttr;
    private String TAG = getClass().getName();
    private final View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    lastY = event.getY();
//                    Log.e(TAG+"   down : ","lastX : "+lastX+" lastY : "+lastY);
                    return false;
                case MotionEvent.ACTION_UP:
                    if (moveStatus == MOVING) {
                        afterMove();
                        lastX = event.getX();
                        lastY = event.getY();
//                        Log.e(TAG+"  up","lastX : "+lastX+" lastY : "+lastY);
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_MOVE:
                    if (moveStatus == MOVING) {
                        moving(event);
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }
    };
    private final View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (moveStatus) {
                case IDLE:
                    beforeMove();
                    return true;
                case MOVING:
                    return false;
                default:
                    return false;
            }
        }
    };
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    //move layouts
    public MoveView(ViewGroup view, Context context) throws Exception {
        disableViewsFocusable(view);
        ViewGroup group = (ViewGroup) view.getParent();
        mRoot = group;
        initViewAndParent(view, context);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    //move widgets
    public MoveView(View view, Context context) throws Exception {
        ViewGroup group = (ViewGroup) view.getParent();
        mRoot = group;
        initViewAndParent(view, context);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
//        check whether root is view's parent;
//        for (int i = 0; i <= group.getChildCount(); i++) {
//            View child = group.getChildAt(i);
//            if (view.equals(child)) {
//                mView = view;
//                mContext = context;
//                break;
//            }
//        }
//        if (mView == null) {
//            Exception e = new Exception("root is not the father of the view");
//            throw e;
//        }
//        Log.e(TAG,"screenHeight : "+screenHeight+" screenWidth : "+screenWidth);
    }

    private void initViewAndParent(View view, Context context) throws Exception {
        mView = view;
        mContext = context;
        mView.setOnLongClickListener(longClickListener);
        mView.setOnTouchListener(touchListener);
        mView.setOnClickListener(onClickListener);
        dm = context.getResources().getDisplayMetrics();
        if (mRoot == null) {
            Exception e = new Exception("view's parent is null");
            throw e;
        }
        changeAttr = new ChangeAttr();

    }


    private void disableViewsFocusable(ViewGroup root) {
        int numChild = root.getChildCount();
        if (numChild != 0) {
            for (int i = 0; i <= numChild; i++) {
                View view = root.getChildAt(i);
                if (view != null) {
                    view.setFocusable(false);
                    try {
                        disableViewsFocusable((ViewGroup) view);
                    } catch (ClassCastException e) {
                        continue;
                    }
                }
            }
        }
    }

    private void moving(MotionEvent event) {//while moving event;
        changeAttr.reLayout(event);
    }

    private void afterMove() { //after up event;
        moveStatus = IDLE;
        changeAttr.recover();
    }

    private void beforeMove() {//after long click and before move;
        moveStatus = MOVING;
        changeAttr.initMoving();
    }
    //not useful yet
    public void setIsInParent(boolean b) {
        if (b)
            viewBorder = inParent;
        else
            viewBorder = inWindow;

    }


    private class ChangeAttr {
        private View tmpView;    //save the temp value of mView,cause mView'attrs will be changed while moving

        public ChangeAttr() {
            tmpView = mView;
        }

        private void setAlpha(float alpha) {
            mView.setAlpha(alpha);
        }

        public void setSize(ViewGroup.LayoutParams layoutParams) {
        }

        public void scale() {
            Animation scaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.scale);
            scaleAnim.setFillAfter(true);
            mView.startAnimation(scaleAnim);//begin animation
        }

        public void reLayout(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            int right = mView.getRight();
            int left = mView.getLeft();
            int top = mView.getTop();
            int bottom = mView.getBottom();

            parentHeight = mRoot.getHeight();
            parentWidth = mRoot.getWidth();
//
//            Log.e(TAG,mRoot.getWidth()+"  *****  "+mRoot.getHeight());
//            Log.e(TAG,mView.getWidth()+"  *****  "+mView.getHeight());

//            Log.e(TAG,"right : "+right+" left : "+left+" top :"+top+" bottom :"+bottom);
//            Log.e(TAG,"-----------------------------------------------------");
//            Log.e(TAG+"  reLayout : ","lastX : "+lastX+" lastY : "+lastY);

            float deltaX = x - lastX;
            float deltaY = y - lastY;
//            Log.e(TAG,"x : "+x+" lastX : "+lastX+" deltaX : "+ deltaX+" y : "+y+" lastY :"+lastY+" deltaY : "+deltaY);
//            Log.e(TAG,"-----------------------------------------------------");

            int nRight = (int) (right + deltaX);
            int nLeft = (int) (left + deltaX);
            int nTop = (int) (top + deltaY);
            int nBottom = (int) (bottom + deltaY);
//            Log.e(TAG,"nright : "+nRight+" nleft : "+nLeft+" ntop :"+nTop+" nbottom :"+nBottom);
//            Log.e(TAG,"-----------------------------------------------------");

            switch (viewBorder) {
                case inParent:
                    calInParent(nRight, nLeft, nTop, nBottom);
                    break;
                case inWindow:
                    calInWindow(nRight, nLeft, nTop, nBottom);
                    break;
                default:
            }
        }

        private void calInParent(int nRight, int nLeft, int nTop, int nBottom) {
            if (nTop < 0) {
                nTop = 0;
                nBottom = mView.getHeight();
            }
            if (nLeft < 0) {
                nLeft = 0;
                nRight = mView.getWidth();
            }
            if (nBottom > parentHeight) {
                nBottom = parentHeight;
                nTop = nBottom - mView.getHeight();
            }
            if (nRight > parentWidth) {
                nRight = parentWidth;
                nLeft = nRight - mView.getWidth();
            }
            mView.layout(nLeft, nTop, nRight, nBottom);

        }

        //view can't be seen when it's over its parent
        private void calInWindow(int nRight, int nLeft, int nTop, int nBottom) {
            if (nTop < 0) {
                nTop = 0;
                nBottom = mView.getHeight();
            }
            if (nLeft < 0) {
                nLeft = 0;
                nRight = mView.getWidth();
            }
            if (nBottom > screenHeight) {
                nBottom = screenHeight;
                nTop = nBottom - mView.getHeight();
            }
            if (nRight > screenWidth) {
                nRight = screenWidth;
                nLeft = nRight - mView.getWidth();
            }
            mView.layout(nLeft, nTop, nRight, nBottom);
        }


        private void clearAnimation() {
            mView.clearAnimation();
        }

        public void recover() {
            clearAnimation();
            setAlpha(1);
        }

        public void initMoving() {
            scale();
            setAlpha(0.3f);
        }
    }
}
