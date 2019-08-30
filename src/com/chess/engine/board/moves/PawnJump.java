package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

public final class PawnJump extends Move{

    public PawnJump(final Board board,
                    final Piece movedPiece,
                    final int destinationCoordinate) {
        super(board, movedPiece, destinationCoordinate);
    }

    @Override
    public Board execute(){
        final Board.Builder builder = new Board.Builder();

        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return  builder.build();

    }

    @Override
    public String toString(){
        return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
    }

}
