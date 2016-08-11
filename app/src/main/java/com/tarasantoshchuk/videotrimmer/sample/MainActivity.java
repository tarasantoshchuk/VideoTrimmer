package com.tarasantoshchuk.videotrimmer.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.tarasantoshchuk.videotrimmer.R;
import com.tarasantoshchuk.videotrimmer.VideoTrimmerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.et_video_path);
        final VideoTrimmerView trimmer = (VideoTrimmerView) findViewById(R.id.video_trimmer);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                trimmer.set
            }
        });
    }
}
