package com.chess.gui.table;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.moves.Move;
import com.chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

class TilePanel extends JPanel {

    private Table table;
    private final int tileId;
    private static String defaultPieceImagesPath = "art/holywarriors/";

    TilePanel(Table table, final BoardPanel boardPanel,
              final int tileId){
        super(new GridBagLayout());
        this.table = table;
        this.tileId = tileId;
        setPreferredSize(Table.TILE_PANEL_DIMENSION);
        assignTileColor();
        assignTilePieceIcon(table.chessBoard);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {

                if(isRightMouseButton(e)){
                    table.sourceTile = null;
                    table.destinationTile = null;
                    table.humanMovedPiece = null;
                } else if(isLeftMouseButton(e)){
                    if(table.sourceTile == null) {
                        //first click
                        table.sourceTile = table.chessBoard.getTile(tileId);
                        table.humanMovedPiece = table.sourceTile.getPiece();
                        if (table.humanMovedPiece == null) {
                            table.sourceTile = null;
                        }
                    } else {
                        table.destinationTile = table.chessBoard.getTile(tileId);
                        final Move move = Move.MoveFactory.createMove(table.chessBoard, table.sourceTile.getTileCoordinate(), table.destinationTile.getTileCoordinate());
                        final MoveTransition transition = table.chessBoard.currentPlayer().makeMove(move);
                        if(transition.getMoveStatus().isDone()){
                            table.chessBoard = transition.getTransitionboard();
                            table.moveLog.addMove(move);
                        }
                        table.sourceTile = null;
                        table.destinationTile = null;
                        table.humanMovedPiece = null;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            table.gameHistoryPanel.redo(table.chessBoard, table.moveLog);
                            table.takenPiecesPanel.redo(table.moveLog);

                            if(table.gameSetup.isAIPlayer(table.chessBoard.currentPlayer())){
                                Table.get().moveMadeUpdate(Table.PlayerType.HUMAN);
                            }
                            boardPanel.drawBoard(table.chessBoard);
                        }
                    });
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }
        });
        validate();
    }

    public void drawTile(final Board board){
        assignTileColor();
        assignTilePieceIcon(board);
        highlightLegals(board);
        validate();
        repaint();
    }

    private void assignTilePieceIcon(final Board board){
        this.removeAll();
        if(board.getTile(this.tileId).isTileOccupied()){
            try {
                final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                        board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1) +
                        board.getTile(this.tileId).getPiece().toString() + ".gif"));
                add(new JLabel(new ImageIcon(image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void highlightLegals(final Board board){
        if(table.highlightLegalMoves){
            for(final Move move : pieceLegalMoves(board)){
                if(move.getDestinationCoordinate() == this.tileId){
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Collection<Move> pieceLegalMoves(final Board board){
        if (table.humanMovedPiece != null && table.humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {

            return table.humanMovedPiece.calculateLegalMoves(board);
        }
        return Collections.emptyList();
    }

    private void assignTileColor() {
        if(BoardUtils.EIGHTH_RANK[this.tileId] ||
                BoardUtils.SIXTH_RANK[this.tileId] ||
                BoardUtils.FOURTH_RANK[this.tileId] ||
                BoardUtils.SECOND_RANK[this.tileId]){
            setBackground(this.tileId % 2 == 0 ? table.lightTileColor : table.darkTileColor);
        } else if (BoardUtils.SEVENTH_RANK[this.tileId] ||
                BoardUtils.FIFTH_RANK[this.tileId] ||
                BoardUtils.THIRD_RANK[this.tileId] ||
                BoardUtils.FIRST_RANK[this.tileId]){
            setBackground(this.tileId % 2 != 0 ? table.lightTileColor : table.darkTileColor);
        }
    }

}
