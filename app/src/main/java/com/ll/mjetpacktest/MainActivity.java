package com.ll.mjetpacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ll.mjetpacktest.GameJingZiQi.Game_SimpleActivity_JingZiQi;
import com.ll.mjetpacktest.view.Game_JingZiQi_Activity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate");
    }

    public void onClickedGameJingZiQi(View view){
//        Intent intent = new Intent(this, Game_SimpleActivity_JingZiQi.class);
        Intent intent = new Intent(this, Game_JingZiQi_Activity.class);
        startActivity(intent);
    }
}