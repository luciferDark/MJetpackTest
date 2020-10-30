package com.ll.mjetpacktest.view;

import com.ll.mjetpacktest.model.PlayerModel;

/**
 * @Auther Kylin
 * @Data 2020/10/30 - 22:08
 * @Package com.ll.mjetpacktest.view
 * @Description
 */
public interface JingZiQiIView {
    void resetView();

    void markStepView(int row, int col, PlayerModel player);

    void setWinnerView(PlayerModel player);

    void setFinsihNoWinnerView();
}
