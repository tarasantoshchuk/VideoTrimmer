package com.tarasantoshchuk.videotrimmer;

public interface PlayerListener {
    void onPlayerPositionChanged(float positionMs);
    void onPause();
}
