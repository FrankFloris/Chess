package com.tests.chess.engine.board;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
import com.chess.engine.board.Board;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class TestBoard {
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(com.chess.engine.board.Board.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
    //    assertTrue(board.currentPlayer().isKingSideCastleCapable());
    //    assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
    //    assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
    //    assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
//        assertEquals("White", board.whitePlayer().toString());
//        assertEquals("Black", board.blackPlayer().toString());

//    final Iterable<Piece> allPieces = board.getAllPieces();
//    final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
//    for(final Move move : allMoves) {
//        assertFalse(move.isAttack());
//        assertFalse(move.isCastlingMove());
//        assertEquals(MoveUtils.exchangeScore(move), 1);
//    }
//
//    assertEquals(Iterables.size(allMoves), 40);
//    assertEquals(Iterables.size(allPieces), 32);
//    assertFalse(BoardUtils.isEndGame(board));
//    assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
//    assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
//    assertEquals(board.getPiece(35), null);
    }

//    @Test

}
