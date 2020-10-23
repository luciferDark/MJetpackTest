package com.ll.mjetpacktest.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.ll.mjetpacktest.R;
import com.ll.mjetpacktest.model.GameStateModel;
import com.ll.mjetpacktest.model.PanelModel;
import com.ll.mjetpacktest.model.PlayerModel;

import androidx.annotation.Nullable;

/**
 * @Auther kylin
 * @Data 2020/10/22 - 22:50
 * @Package com.ll.mjetpacktest.view
 * @Description
 */
public class Game_JingZiQi_Activity extends Activity {
    private static final String TAG = "JingZiQiMVC";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_simple_jingziqi_layout);
        init();
    }

    private void log(String msg){
        Log.d(TAG, msg);
    }

    public void onClickReset(View view){
        log("click button reset");
        initData();
        resetView();
    }

    public void onCellClicked(View view){
        Button button = (Button)view;
        log("click button tag=" + button.getTag());
        progress(button);
    }
    //-----------------
    //--view
    //-----------------
    private TextView winnerLbl;
    private TextView winnerDesc;
    private GridLayout gridLayout;

    //-----------------
    //--model
    //-----------------
    private PanelModel model;

    private void init() {
        initData();
        initView();
        resetView();
    }

    private void initData(){
        model = null;
        model = new PanelModel();
        model.initPanel();
    }

    private void initView() {
        winnerLbl = findViewById(R.id.winner_lbl);
        winnerDesc = findViewById(R.id.winner_desc);
        gridLayout = findViewById(R.id.grid_layout);
    }

    private void resetView(){
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

    private void markStepView(int row, int col, PlayerModel player){
        Button btn = (Button) gridLayout.getChildAt(row * 3 + col);
        btn.setText(player.toString());
    }

    private void setWinnerView(PlayerModel player){
        winnerLbl.setText(player.toString());
        winnerDesc.setText("赢了");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    private void setFinsihNoWinnerView(){
        winnerLbl.setText("X 和 O");
        winnerDesc.setText("平局");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    //-----------------
    //--control
    //-----------------
    private void progress(Button button) {
        String tag = (String) button.getTag();
        int row = Integer.parseInt(tag.substring(0,1));
        int col = Integer.parseInt(tag.substring(1));

        log("row" +  row);
        log("col" +  col);
        if (!model.isStepInvalid(row,col)){
            if (null == model){
                return;
            }
            model.markStep(row, col);
            markStepView(row, col, model.getCurrentPlayer());
            if (model.isGameFinished(row, col)){
                if (model.getGameState() == GameStateModel.FINISHED){
                    setWinnerView(model.getWinner());
                } else if (model.getGameState() == GameStateModel.FINISHED_NO_WINNER){
                    setFinsihNoWinnerView();
                }
            }
        }
    }
}
