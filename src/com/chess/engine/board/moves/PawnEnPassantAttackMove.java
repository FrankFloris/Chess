package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

public final class PawnEnPassantAttackMove extends PawnAttackMove{

    public PawnEnPassantAttackMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
    }

    @Override
    public Board execute(){
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            if(!piece.equals(this.attackedPiece)){
                builder.setPiece(piece);
            }
        }
        builder.setPiece((this.movedPiece.movePiece(this)));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}
