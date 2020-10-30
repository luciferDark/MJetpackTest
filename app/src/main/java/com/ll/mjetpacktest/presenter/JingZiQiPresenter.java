package com.ll.mjetpacktest.presenter;

import com.ll.mjetpacktest.model.GameStateModel;
import com.ll.mjetpacktest.model.PanelModel;
import com.ll.mjetpacktest.view.JingZiQiIView;

/**
 * @Auther Kylin
 * @Data 2020/10/30 - 22:07
 * @Package com.ll.mjetpacktest.presenter
 * @Description
 */
public class JingZiQiPresenter {
    //-----------------
    //--view
    //-----------------
    private JingZiQiIView view;
    //-----------------
    //--model
    //-----------------
    private PanelModel model;

    public JingZiQiPresenter(JingZiQiIView view) {
        this.view = view;

        initData();
    }

    private void initData(){
        model = null;
        model = new PanelModel();
        model.initPanel();
    }

    public void onClickReset(){
        initData();
        view.resetView();
    }

    public void progress(int row, int col){
        if (!model.isStepInvalid(row,col)){
            if (null == model){
                return;
            }
            model.markStep(row, col);
            view.markStepView(row, col, model.getCurrentPlayer());
            if (model.isGameFinished(row, col)){
                if (model.getGameState() == GameStateModel.FINISHED){
                    view.setWinnerView(model.getWinner());
                } else if (model.getGameState() == GameStateModel.FINISHED_NO_WINNER){
                    view.setFinsihNoWinnerView();
                }
            }
        }
    }
}
