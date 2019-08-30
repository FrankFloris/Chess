package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;

public class PawnAttackMove extends AttackMove{

    public PawnAttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnAttackMove && super.equals(other);
    }

    @Override
    public String toString(){
        return movedPiece.getPieceType().toString() +
               BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()) + "x" +
               BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }

}
