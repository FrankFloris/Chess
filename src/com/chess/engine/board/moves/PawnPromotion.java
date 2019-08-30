package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

public class PawnPromotion extends Move {

    final Move decoratedMove;
    final Pawn promotedPawn;

    public PawnPromotion(final Move decoratedMove) {
        super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
        this.decoratedMove = decoratedMove;
        this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
    }

    @Override
    public Board execute(){

        final Board pawnMovedBoard = this.decoratedMove.execute();
        final Board.Builder builder = new Board.Builder();
        for(final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()){
            if(!this.promotedPawn.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
        builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
        return builder.build();
    }

    @Override
    public boolean isAttack(){
        return this.decoratedMove.isAttack();
    }

    @Override
    public Piece getAttackedPiece(){
        return this.decoratedMove.getAttackedPiece();
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public int hashCode(){
        return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
    }

    @Override
    public boolean equals(final Object other){
        return this == other || other instanceof PawnPromotion && (super.equals(other));
    }
}
