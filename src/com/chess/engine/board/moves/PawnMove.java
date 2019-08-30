package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;

public final class PawnMove extends Move{

    public PawnMove(final Board board,
                    final Piece movedPiece,
                    final int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnMove && super.equals(other);
    }

    @Override
    public String toString(){
        return movedPiece.getPieceType().toString() +
               BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }

}
