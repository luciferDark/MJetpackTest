package com.ll.mjetpacktest.model;

/**
 * @Auther kylin
 * @Data 2020/10/22 - 23:05
 * @Package com.ll.mjetpacktest.model
 * @Description
 */
public class PanelModel {
    private CellModel cellModels[][];
    //-----------------
    //--model
    //-----------------
    private PlayerModel mCurrentPlayer;
    private PlayerModel mWinner;
    private GameStateModel mGameState;
    private int stepCount = 0;
    /**
     *  初始化棋盘数据
     */
    public void initPanel(){
        cellModels = new CellModel[3][3];
        mCurrentPlayer = PlayerModel.X;
        mWinner = null;
        mGameState = GameStateModel.PLAYING;
        stepCount = 0;
    }

    /**
     * 获取棋盘单元格
     * @param row
     * @param col
     * @return
     */
    public CellModel getCell(int row, int col){
        if (cellModels== null ||
                row < 0 || row >= 3 ||
                col < 0 || col >= 3){
            return null;
        }
        return cellModels[row][col];
    }

    /**
     *  设置棋盘单元格
     * @param row
     * @param col
     * @param playerModel
     */
    public void setCell(int row, int col, PlayerModel playerModel){
        if (cellModels== null || playerModel== null){
            return;
        }
        CellModel cellModel = new CellModel();
        cellModel.setPlayer(playerModel);
        cellModels[row][col] = cellModel;
    }

    /**
     *  判断当前下棋步骤是否是无效的
     * @param row
     * @param col
     * @return
     */
    public boolean isStepInvalid(int row, int col){
        if (mGameState == GameStateModel.FINISHED){
            return true;
        }
        CellModel cellModel = getCell(row, col);
        if (cellModel != null && cellModel.getPlayer() != null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断棋局是否结束
     * @param row
     * @param col
     * @return
     */
    public boolean isGameFinished(int row, int col){
        // 行有一样的
        CellModel cellModelR0 = getCell(row, 0);
        CellModel cellModelR1 = getCell(row, 1);
        CellModel cellModelR2 = getCell(row, 2);
        if (isLineSamePlayer(cellModelR0, cellModelR1, cellModelR2)){
            mGameState = GameStateModel.FINISHED;
            mWinner = mCurrentPlayer;
            return true;
        }
        // 列有一样的
        CellModel cellModelC0 = getCell(0, col);
        CellModel cellModelC1 = getCell(1, col);
        CellModel cellModelC2 = getCell(2, col);
        if (isLineSamePlayer(cellModelC0, cellModelC1, cellModelC2)){
            mGameState = GameStateModel.FINISHED;
            mWinner = mCurrentPlayer;
            return true;
        }
        // 反斜杠一样 \
        CellModel cellModel00 = getCell(0, 0);
        CellModel cellModel11 = getCell(1, 1);
        CellModel cellModel22 = getCell(2, 2);
        if (row == col  && isLineSamePlayer(cellModel00, cellModel11, cellModel22)){
            mGameState = GameStateModel.FINISHED;
            mWinner = mCurrentPlayer;
            return true;
        }
        // 正斜杠一样 /
        CellModel cellModel02 = getCell(0, 2);
        CellModel cellModel20 = getCell(2, 0);
        if ((row + col == 2) && isLineSamePlayer(cellModel02, cellModel11, cellModel20)){
            mGameState = GameStateModel.FINISHED;
            mWinner = mCurrentPlayer;
            return true;
        }
        mWinner = null;
        if (stepCount == 9){
            //平局
            mGameState = GameStateModel.FINISHED_NO_WINNER;
            return true;
        }

        //否者没有结束，切换棋手
        switchPlayer();
        return false;
    }

    /**
     *  判断一条直线上的是不是同一个棋手下的棋子
     * @param cell1
     * @param cell2
     * @param cell3
     * @return
     */
    public boolean isLineSamePlayer(CellModel cell1,CellModel cell2,CellModel cell3){
        if (cell1 != null && cell2 != null && cell3 != null &&
                cell1.getPlayer().toString().equals(cell2.getPlayer().toString()) &&
                cell2.getPlayer().toString().equals(cell3.getPlayer().toString())){
            return true;
        }
        return false;
    }

    /**
     * 下棋
     * @param row
     * @param col
     */
    public void markStep(int row, int col){
        if (null == mCurrentPlayer){
            return;
        }
        setCell(row, col, mCurrentPlayer);
        stepCount ++;
    }

    /**
     * 切换下棋人
     */
    private void switchPlayer(){
        if (mCurrentPlayer == PlayerModel.X){
            mCurrentPlayer = PlayerModel.O;
        } else if (mCurrentPlayer == PlayerModel.O){
            mCurrentPlayer = PlayerModel.X;
        }
    }

    public PlayerModel getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public PlayerModel getWinner() {
        return mWinner;
    }

    public GameStateModel getGameState() {
        return mGameState;
    }

    public int getStepCount() {
        return stepCount;
    }

    public CellModel[][] getCellModels() {
        return cellModels;
    }
}


