package com.tarasantoshchuk.videotrimmer;

import android.content.Context;
import android.database.Observable;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ZoomableFramesStrip extends ViewGroup {
    private static final String TAG = ZoomableFramesStrip.class.getSimpleName();

    public ZoomableFramesStrip(Context context) {
        super(context);
    }

    private Callback mCallback;
    private Config mConfig;

    public ZoomableFramesStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomableFramesStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setConfig(Config config) {
        mConfig = config;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    public static final class Config {
        public final int mainFramesCount;
        public final int zoomFactor;
        public final float animationDurationMs;

        public Config(int mainFramesCount, float animationDurationMs, int zoomFactor) {
            this.mainFramesCount = mainFramesCount;
            this.animationDurationMs = animationDurationMs;
            this.zoomFactor = zoomFactor;
        }
    }

    public interface Callback {
        Observable<Bitmap> getBitmapAt(float positionPx, int mainFrameIndex);
    }
}
