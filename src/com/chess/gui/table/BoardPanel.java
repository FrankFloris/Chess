package com.chess.gui.table;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {
    private Table table;
    final List<TilePanel> boardTiles;

    BoardPanel(Table table){
        super(new GridLayout(8,8));
        this.table = table;
        this.boardTiles = new ArrayList<>();
        for(int i = 0; i < BoardUtils.NUM_TILES; i ++){
            final TilePanel tilePanel = new TilePanel(table, this, i);
            this.boardTiles.add(tilePanel);
            add(tilePanel);
        }
        setPreferredSize(Table.BOARD_PANEL_DIMENSION);
        validate();
    }

    void drawBoard(final Board board){
        removeAll();
        table.boardDirection.traverse(boardTiles).forEach(tilePanel -> {
            tilePanel.drawTile(board);
            add(tilePanel);
        });
        validate();
        repaint();
    }
}
