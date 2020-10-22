package com.ll.mjetpacktest.model;

/**
 * @Auther kylin
 * @Data 2020/10/22 - 23:05
 * @Package com.ll.mjetpacktest.model
 * @Description
 */
public class PanelModel {
    private CellModel cellModels[][];

    public CellModel[][] getCellModels() {
        return cellModels;
    }

    public void initPanel(){
        cellModels = new CellModel[3][3];
    }

    public CellModel getCell(int row, int col){
        if (cellModels== null ||
                row < 0 || row >= 3 ||
                col < 0 || col >= 3){
            return null;
        }
        return cellModels[row][col];
    }

    public void setCell(int row, int col, PlayerModel playerModel){
        if (cellModels== null || playerModel== null){
            return;
        }
        CellModel cellModel = new CellModel();
        cellModel.setPlayer(playerModel);
        cellModels[row][col] = cellModel;
    }
}


