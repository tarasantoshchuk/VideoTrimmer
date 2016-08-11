package com.tarasantoshchuk.videotrimmer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class TrimControlsView extends View {
    private static final String TAG = TrimControlsView.class.getSimpleName();

    private Listener mListener;
    private Config mConfig;

    public TrimControlsView(Context context) {
        this(context, null);
    }

    public TrimControlsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrimControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setTrimListener(Listener listener) {
        mListener = listener;
    }

    public void setConfig(Config config) {
        mConfig = config;
    }


    private void init(Context context) {

    }

    public static final class Config {
        public final float minTrimAreaWidth;
        public final float maxTrimAreaWidth;

        public final float initialLeftPosition;
        public final float initialRightPosition;

        public final float animationDurationMs;

        public final int zoomFactor;

        public Config(
                float minTrimAreaWidth,
                float maxTrimAreaWidth,
                float initialLeftPosition,
                float initialRightPosition,
                float animationDurationMs,
                int zoomFactor
        ) {
            this.minTrimAreaWidth = minTrimAreaWidth;
            this.maxTrimAreaWidth = maxTrimAreaWidth;

            this.initialLeftPosition = initialLeftPosition;
            this.initialRightPosition = initialRightPosition;

            this.animationDurationMs = animationDurationMs;

            this.zoomFactor = zoomFactor;
        }
    }

    public interface Listener {
        void onTrimPositionChanged(float left, float right);
        void onZoomIn(float pivotX);
        void onZoomOut();
    }
}
