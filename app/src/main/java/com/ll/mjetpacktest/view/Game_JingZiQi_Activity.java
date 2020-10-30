package com.ll.mjetpacktest.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.ll.mjetpacktest.R;
import com.ll.mjetpacktest.model.PlayerModel;
import com.ll.mjetpacktest.presenter.JingZiQiPresenter;

import androidx.annotation.Nullable;

/**
 * @Auther kylin
 * @Data 2020/10/22 - 22:50
 * @Package com.ll.mjetpacktest.view
 * @Description
 */
public class Game_JingZiQi_Activity extends Activity implements JingZiQiIView{
    private static final String TAG = "JingZiQiMVC";
    //-----------------
    //--view
    //-----------------
    private TextView winnerLbl;
    private TextView winnerDesc;
    private GridLayout gridLayout;
    //-----------------
    //--presenter
    //-----------------
    private JingZiQiPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_simple_jingziqi_layout);
        init();
    }

    public void onClickReset(View view){
        log("click button reset");
        presenter.onClickReset();
    }

    public void onCellClicked(View view){
        Button button = (Button)view;
        String tag = (String) button.getTag();
        int row = Integer.parseInt(tag.substring(0,1));
        int col = Integer.parseInt(tag.substring(1));
        presenter.progress(row, col);
    }

    private void init() {
        initPresenter();
        initView();
        resetView();
    }

    private void initPresenter(){
        presenter = new JingZiQiPresenter(this);
    }

    private void initView() {
        winnerLbl = findViewById(R.id.winner_lbl);
        winnerDesc = findViewById(R.id.winner_desc);
        gridLayout = findViewById(R.id.grid_layout);
    }

    @Override
    public void resetView(){
        if (gridLayout != null){
            for (int i = 0 ;i < gridLayout.getChildCount(); i++){
                Button btn = (Button)gridLayout.getChildAt(i);
                btn.setText("");
            }
        }

        if (winnerLbl != null){
            winnerLbl.setText("");
            winnerLbl.setVisibility(View.GONE);
        }
        if (winnerDesc != null){
            winnerDesc.setText("");
            winnerDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public void markStepView(int row, int col, PlayerModel player){
        Button btn = (Button) gridLayout.getChildAt(row * 3 + col);
        btn.setText(player.toString());
    }

    @Override
    public void setWinnerView(PlayerModel player){
        winnerLbl.setText(player.toString());
        winnerDesc.setText("赢了");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFinsihNoWinnerView(){
        winnerLbl.setText("X 和 O");
        winnerDesc.setText("平局");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    private void log(String msg){
        Log.d(TAG, msg);
    }
}
