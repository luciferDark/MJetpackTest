package com.ll.mjetpacktest.GameJingZiQi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ll.mjetpacktest.R;

import androidx.annotation.Nullable;

/**
 * @Auther kylin
 * @Data 2020/10/15 - 23:16
 * @Package com.ll.mjetpacktest.GameJingZiQi
 * @Description
 */
public class Game_SimpleActivity_JingZiQi extends Activity {

    private static final String TAG = "JingZiQi";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_simple_jingziqi_layout);
    }

    private void log(String msg){
        Log.d(TAG, "create: KylinInitializer");
    }
    public void onCellClicked(View view){
        Log.d(TAG, "create: KylinInitializer");
        Button button = (Button)view;
        log("click button tag=" + button.getTag());
    }


}
