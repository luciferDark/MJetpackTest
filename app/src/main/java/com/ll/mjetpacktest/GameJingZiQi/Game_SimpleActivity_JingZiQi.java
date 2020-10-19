package com.ll.mjetpacktest.GameJingZiQi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

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

        initView();
        initData();
        resetView();
    }

    private void initView() {
        winnerLbl = findViewById(R.id.winner_lbl);
        winnerDesc = findViewById(R.id.winner_desc);
        gridLayout = findViewById(R.id.grid_layout);
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
    //--model
    //-----------------
    // 下棋的人
    public enum Player {X,O}
    // 棋盘的格子
    public class Cell {
        private Player player = null;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
    // 游戏状态
    public enum GameState {
        PLAYING,
        FINISHED
    }

    private Player mCurrentPlayer;
    private Player mWinner;
    private GameState mGameState;
    private Cell[][] panel;
    private int stepCount = 0;
    //-----------------
    //--view
    //-----------------
    private TextView winnerLbl;
    private TextView winnerDesc;
    private GridLayout gridLayout;
    //-----------------
    //--controller
    //-----------------
    private void initData(){
        mCurrentPlayer = Player.X;
        mWinner = null;
        mGameState = GameState.PLAYING;
        panel = null;
        panel = new Cell[3][3];
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
        if (row < 0 || row >= 3 ||
                col < 0 || col >= 3){
            return true;
        }
        if (panel == null){
            return true;
        }
        if (mGameState == GameState.FINISHED){
            return true;
        }
        if (panel[row][col] != null && panel[row][col].getPlayer() != null){
            return true;
        } else {
            return false;
        }
    }

    private boolean isGameFinished(int row, int col, Player player){
        if (panel == null){
            return false;
        }
        // 行有一样的
        if (panel[row][0] != null && panel[row][0].getPlayer() == player &&
                panel[row][1] != null && panel[row][1].getPlayer() == player &&
                panel[row][2] != null && panel[row][2].getPlayer() == player){
            mGameState = GameState.FINISHED;
            return true;
        }
        // 列有一样的
        if (panel[0][col] != null && panel[0][col].getPlayer() == player &&
                panel[1][col] != null && panel[1][col].getPlayer() == player &&
                panel[2][col] != null && panel[2][col].getPlayer() == player){
            mGameState = GameState.FINISHED;
            return true;
        }
        // 反斜杠一样 \
        if (row == col &&
                panel[0][0] != null && panel[0][0].getPlayer() == player &&
                panel[1][1] != null && panel[1][1].getPlayer() == player &&
                panel[2][2] != null && panel[2][2].getPlayer() == player){
            mGameState = GameState.FINISHED;
            return true;
        }
        // 正斜杠一样 /
        if ((row + col == 2) &&
                panel[0][2] != null && panel[0][2].getPlayer() == player &&
                panel[1][1] != null && panel[1][1].getPlayer() == player &&
                panel[2][0] != null && panel[2][0].getPlayer() == player){
            mGameState = GameState.FINISHED;
            return true;
        }
        return false;
    }

    private void markStep(int row, int col, Player player){
        if (null == panel || null == player){
            return;
        }
        Cell cell = new Cell();
        cell.setPlayer(player);
        panel[row][col] = cell;
        stepCount ++;

        Button btn = (Button) gridLayout.getChildAt(row * 3 + col);
        btn.setText(player.toString());
    }

    private void switchPlayer(){
        if (mCurrentPlayer == Player.X){
            mCurrentPlayer = Player.O;
        } else if (mCurrentPlayer == Player.O){
            mCurrentPlayer = Player.X;
        }
    }

    private void setWinner(Player player){
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
                    mGameState = GameState.FINISHED;
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
