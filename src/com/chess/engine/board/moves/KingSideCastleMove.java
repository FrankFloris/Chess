package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public final class KingSideCastleMove extends CastleMove{

    public KingSideCastleMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Rook castleRook,
                              final int castleRookStartPosition,
                              final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate, castleRook, castleRookStartPosition, castleRookDestination);
    }

    @Override
    public boolean equals(final Object other){
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
    }

    @Override
    public String toString(){
        return "0-0";
    }

}
