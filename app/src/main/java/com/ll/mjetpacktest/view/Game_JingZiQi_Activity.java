package com.ll.mjetpacktest.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.ll.mjetpacktest.R;
import com.ll.mjetpacktest.model.CellModel;
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

    private void initView() {
        winnerLbl = findViewById(R.id.winner_lbl);
        winnerDesc = findViewById(R.id.winner_desc);
        gridLayout = findViewById(R.id.grid_layout);
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
    //--model
    //-----------------
    private PlayerModel mCurrentPlayer;
    private PlayerModel mWinner;
    private GameStateModel mGameState;
    private PanelModel panel;
    private int stepCount = 0;
    //-----------------
    //--view
    //-----------------
    private TextView winnerLbl;
    private TextView winnerDesc;
    private GridLayout gridLayout;

    private void init() {
        initView();
        initData();
        resetView();
    }
    //-----------------
    //--controller
    //-----------------
    private void initData(){
        mCurrentPlayer = PlayerModel.X;
        mWinner = null;
        mGameState = GameStateModel.PLAYING;
        panel = null;
        panel = new PanelModel();
        panel.initPanel();
        stepCount = 0;
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

    private boolean isStepValid(int row, int col){
        if (panel == null){
            return true;
        }
        if (mGameState == GameStateModel.FINISHED){
            return true;
        }
        CellModel cellModel = panel.getCell(row, col);
        if (cellModel != null && cellModel.getPlayer() != null){
            return true;
        } else {
            return false;
        }
    }

    private boolean isGameFinished(int row, int col, PlayerModel player){
        if (panel == null){
            return false;
        }
        // 行有一样的
        CellModel cellModelR0 = panel.getCell(row, 0);
        CellModel cellModelR1 = panel.getCell(row, 1);
        CellModel cellModelR2 = panel.getCell(row, 2);
        if (cellModelR0 != null && cellModelR0.getPlayer() == player &&
                cellModelR1 != null && cellModelR1.getPlayer() == player &&
                cellModelR2 != null && cellModelR2.getPlayer() == player){
            mGameState = GameStateModel.FINISHED;
            return true;
        }
        // 列有一样的
        CellModel cellModelC0 = panel.getCell(0, col);
        CellModel cellModelC1 = panel.getCell(1, col);
        CellModel cellModelC2 = panel.getCell(2, col);
        if (cellModelC0 != null && cellModelC0.getPlayer() == player &&
                cellModelC1 != null && cellModelC1.getPlayer() == player &&
                cellModelC2 != null && cellModelC2.getPlayer() == player){
            mGameState = GameStateModel.FINISHED;
            return true;
        }
        // 反斜杠一样 \
        CellModel cellModel00 = panel.getCell(0, 0);
        CellModel cellModel11 = panel.getCell(1, 1);
        CellModel cellModel22 = panel.getCell(2, 2);
        if (row == col &&
                cellModel00 != null && cellModel00.getPlayer() == player &&
                cellModel11 != null && cellModel11.getPlayer() == player &&
                cellModel22 != null && cellModel22.getPlayer() == player){
            mGameState = GameStateModel.FINISHED;
            return true;
        }
        // 正斜杠一样 /
        CellModel cellModel02 = panel.getCell(0, 0);
        CellModel cellModel20 = panel.getCell(2, 2);
        if ((row + col == 2) &&
                cellModel02 != null && cellModel02.getPlayer() == player &&
                cellModel11 != null && cellModel11.getPlayer() == player &&
                cellModel20 != null && cellModel20.getPlayer() == player){
            mGameState = GameStateModel.FINISHED;
            return true;
        }
        return false;
    }

    private void markStep(int row, int col, PlayerModel player){
        if (null == panel || null == player){
            return;
        }
        panel.setCell(row, col, player);
        stepCount ++;

        Button btn = (Button) gridLayout.getChildAt(row * 3 + col);
        btn.setText(player.toString());
    }

    private void switchPlayer(){
        if (mCurrentPlayer == PlayerModel.X){
            mCurrentPlayer = PlayerModel.O;
        } else if (mCurrentPlayer == PlayerModel.O){
            mCurrentPlayer = PlayerModel.X;
        }
    }

    private void setWinner(PlayerModel player){
        winnerLbl.setText(player.toString());
        winnerDesc.setText("赢了");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    private void setFinsihNoWinner(){
        winnerLbl.setText("X 和 O");
        winnerDesc.setText("平局");
        winnerLbl.setVisibility(View.VISIBLE);
        winnerDesc.setVisibility(View.VISIBLE);
    }

    private void progress(Button button) {
        String tag = (String) button.getTag();
        int row = Integer.parseInt(tag.substring(0,1));
        int col = Integer.parseInt(tag.substring(1));

        log("row" +  row);
        log("col" +  col);
        if (!isStepValid(row,col)){
            markStep(row, col, mCurrentPlayer);
            if (!isGameFinished(row, col, mCurrentPlayer)){
                if (stepCount == 9){
                    //平局
                    mGameState = GameStateModel.FINISHED;
                    setFinsihNoWinner();
                    return;
                }
                switchPlayer();
            } else {
                setWinner(mCurrentPlayer);
            }
        }
    }
}
