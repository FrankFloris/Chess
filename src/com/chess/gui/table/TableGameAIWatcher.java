package com.chess.gui.table;

import java.util.Observable;
import java.util.Observer;

public class TableGameAIWatcher implements Observer {

    @Override
    public void update(final Observable o, final Object arg) {

        if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) &&
        !Table.get().getGameBoard().currentPlayer().isInCheckMate() &&
        !Table.get().getGameBoard().currentPlayer().isInStaleMate()){
            //create and AI thread
            //execute ai work
            final AIThinkTank thinkTank = new AIThinkTank();
            thinkTank.execute();
        }

        if(Table.get().getGameBoard().currentPlayer().isInCheckMate()){
//                JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
//                        "Game Over: Player" + Table.get().getGameBoard().currentPlayer()
//                         + " is in checkmate!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Game Over, " + Table.get().getGameBoard().currentPlayer() + " is in checkmate!");
        }
        if(Table.get().getGameBoard().currentPlayer().isInStaleMate()){
//                JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
//                        "Game Over: Player" + Table.get().getGameBoard().currentPlayer()
//                                + " is in stalemate!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Game Over, " + Table.get().getGameBoard().currentPlayer() + " is in stalemate!");
        }
    }
}
