package com.tarasantoshchuk.videotrimmer;

import android.content.Context;
import android.database.Observable;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class VideoTrimmerView extends FrameLayout implements PlayerListener, TrimControlsView.Listener, ZoomableFramesStrip.Callback {
    private static final String TAG = VideoTrimmerView.class.getSimpleName();

    private static final int DEFAULT_MAIN_FRAMES_COUNT = 7;
    private static final int DEFAULT_EXPANSION_FACTOR = 5;
    private static final int DEFAULT_ANIMATION_DURATION_MS = 300;

    private ZoomableFramesStrip mFramesStrip;
    private TrimControlsView mControlsView;

    private MediaMetadataRetriever mMetadataRetriever = new MediaMetadataRetriever();

    private String mVideoPath;

    private int mVideoDurationMs;
    private float mVideoAspectRatio;





    private int mMainFramesCount = DEFAULT_MAIN_FRAMES_COUNT;
    private int mZoomFactor = DEFAULT_EXPANSION_FACTOR;
    private int mAnimationDurationMs = DEFAULT_ANIMATION_DURATION_MS;

    private float mStartTrimPositionMs = -1;
    private float mEndTrimPositionMs = -1;

    private float mMinTrimDurationMs;
    private float mMaxTrimDurationMs;



    public VideoTrimmerView(Context context) {
        this(context, null);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Sets path to source video file.
     * Call this method before view is laid out.
     *
     * @param path - path to video file to be trimmed
     */
    public void setSourceVideo(String path) {
        mVideoPath = path;

        setVideoData();
    }

    /**
     * Sets animation duration for subviews
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     *
     * @param durationMs - animation duration in milliseconds
     */
    public void setAnimationDuration(int durationMs) {
        mAnimationDurationMs = durationMs;
    }

    /**
     * Sets number of video frames shown by {@link #mFramesStrip} in normal state
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setMainFramesCount(int framesCount) {
        mMainFramesCount = framesCount;
    }

    /**
     * Sets zoom factor for subviews
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setZoomFactor(int expansionFactor) {
        mZoomFactor = expansionFactor;
    }

    /**
     * Sets initial position of left control in {@link #mControlsView}
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setStartTrimPosition(float positionMs) {
        mStartTrimPositionMs = positionMs;
    }

    /**
     * Sets initial position of right control in {@link #mControlsView}
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setEndTrimPosition(float positionMs) {
        mEndTrimPositionMs = positionMs;
    }

    /**
     * Sets min duration of trimmed video
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setMinTrimDurationMs(float durationMs) {
        mMinTrimDurationMs = durationMs;
    }

    /**
     * Sets max duration of trimmed video
     * Note that changes are applied only on next call of {@link #onLayout(boolean, int, int, int, int)}
     */
    public void setMaxTrimDurationMs(float durationMs) {
        mMaxTrimDurationMs = durationMs;
    }

    @Override
    public void onPlayerPositionChanged(float positionMs) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onTrimPositionChanged(float left, float right) {

    }

    @Override
    public void onZoomIn(float pivotX) {

    }

    @Override
    public void onZoomOut() {

    }

    @Override
    public Observable<Bitmap> getBitmapAt(float positionPx, int mainFrameIndex) {
        return null;
    }

    /**
     * Returns id of layout with {@link TrimControlsView} and {@link ZoomableFramesStrip}.
     *
     * Override this method to:
     * 1) change subviews positioning
     * 2) use your custom derived views as subviews
     *
     * If you override this method, your will need to override {@link #getFramesStrip()} and {@link #getTrimControls()} too.
     *
     * @see #getFramesStrip()
     * @see #getTrimControls()
     */
    protected int getLayoutId() {
        return R.layout.video_trimmer_layout;
    }

    /**
     * Gets {@link TrimControlsView} from layout.
     *
     * Override this method if you overridden {@link #getLayoutId()}
     */
    protected TrimControlsView getTrimControls() {
        return (TrimControlsView) findViewById(R.id.trim_controls);
    }

    /**
     * Gets {@link ZoomableFramesStrip} from layout.
     *
     * Override this method if you overridden {@link #getLayoutId()}
     */
    protected ZoomableFramesStrip getFramesStrip() {
        return (ZoomableFramesStrip) findViewById(R.id.frames_strip);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        setFrameStripConfig();
        setTrimControlsConfig();
        super.onLayout(changed, left, top, right, bottom);
    }

    private void setTrimControlsConfig() {
        mControlsView.setConfig(new TrimControlsView.Config(
                mMinTrimDurationMs,
                mMaxTrimDurationMs,
                mStartTrimPositionMs,
                mEndTrimPositionMs,
                mAnimationDurationMs,
                mZoomFactor
        ));
    }

    private void setFrameStripConfig() {
        mFramesStrip.setConfig(new ZoomableFramesStrip.Config(
                mMainFramesCount,
                mAnimationDurationMs,
                mZoomFactor
        ));
    }



    private void init(Context context) {
        inflate(context, getLayoutId(), this);

        initTrimControls();
        initFramesStrip();
    }

    private void initFramesStrip() {
        mFramesStrip = getFramesStrip();
        mFramesStrip.setCallback(this);
    }

    private void initTrimControls() {
        mControlsView = getTrimControls();
        mControlsView.setTrimListener(this);
    }

    private void setVideoData() {
        mMetadataRetriever.setDataSource(mVideoPath);
        mVideoDurationMs = Integer.parseInt(mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

        mVideoAspectRatio = getAspectRatio();
    }

    private float getAspectRatio() {
        float width = Float.parseFloat(mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        float height = Float.parseFloat(mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

        return width / height;
    }
}
