package com.github.florent37.wearviewstub;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import pl.tajchert.shapewear.ShapeWear;

/**
 * Created by florentchampigny on 02/04/15.
 */
public class WearViewStub extends FrameLayout implements ShapeWear.OnShapeChangeListener {

    private int layoutRectId;
    private int layoutRoundId;
    private int layoutRoundMotoId;

    private View viewToInflate;

    private static ShapeWear shapeWear = null;
    private OnLayoutInflatedListener mOnLayoutInflatedListener;

    //region construct

    private void handleAttributes(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            if (shapeWear == null)
                ShapeWear.initShapeWear(context);
        }

        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.WearViewStub);
            {
                layoutRectId = styledAttrs.getResourceId(R.styleable.WearViewStub_wearRectLayout, -1);
            }
            {
                layoutRoundId = styledAttrs.getResourceId(R.styleable.WearViewStub_wearRoundLayout, -1);
            }
            {
                layoutRoundMotoId = styledAttrs.getResourceId(R.styleable.WearViewStub_wearRoundLayout, -1);
            }
            styledAttrs.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WearViewStub(Context context) {
        super(context);
    }

    public WearViewStub(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs);
    }

    public WearViewStub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WearViewStub(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        handleAttributes(context, attrs);
    }

    //endregion


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            if (isInEditMode()) {
                inflateView(this.layoutRectId);
                return;
            }

            ShapeWear.setOnShapeChangeListener(this);
        }
    }

    private void inflateView(int viewId) {
        if (viewId != -1) {
            if (viewToInflate != null)
                removeView(viewToInflate);

            viewToInflate = LayoutInflater.from(getContext()).inflate(viewId, WearViewStub.this, false);
            viewToInflate.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addView(viewToInflate);
        }
    }

    @Override
    public void shapeDetected(ShapeWear.ScreenShape screenShape) {
        int layoutId = -1;
        switch (screenShape) {
            case RECTANGLE:
                layoutId = layoutRectId;
                break;
            case ROUND:
                layoutId = layoutRoundId;
                break;
            case MOTO_ROUND:
                layoutId = layoutRoundMotoId;
                break;
        }

        if (layoutId != -1) {
            inflateView(layoutId);
            if (mOnLayoutInflatedListener != null) {
                mOnLayoutInflatedListener.onLayoutInflated(WearViewStub.this);
            }
        }

        ShapeWear.setOnShapeChangeListener(null);
    }

    //region getters/setters

    public int getLayoutRectId() {
        return layoutRectId;
    }

    public void setLayoutRectId(int layoutRectId) {
        this.layoutRectId = layoutRectId;
    }

    public int getLayoutRoundId() {
        return layoutRoundId;
    }

    public void setLayoutRoundId(int layoutRoundId) {
        this.layoutRoundId = layoutRoundId;
    }

    public int getLayoutRoundMotoId() {
        return layoutRoundMotoId;
    }

    public void setLayoutRoundMotoId(int layoutRoundMotoId) {
        this.layoutRoundMotoId = layoutRoundMotoId;
    }

    public void setOnLayoutInflatedListener(OnLayoutInflatedListener onLayoutInflatedListener) {
        this.mOnLayoutInflatedListener = onLayoutInflatedListener;
        if (viewToInflate != null) {
            mOnLayoutInflatedListener.onLayoutInflated(WearViewStub.this);
        }
    }

    public OnLayoutInflatedListener getOnLayoutInflatedListener() {
        return mOnLayoutInflatedListener;
    }

//endregion

    public interface OnLayoutInflatedListener {
        public void onLayoutInflated(WearViewStub wearViewStub);
    }
}
