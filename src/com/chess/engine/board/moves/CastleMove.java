package com.chess.engine.board.moves;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class CastleMove extends Move{

    private final Rook castleRook;
    private final int castleRookStartPosition;
    private final int castleRookDestination;

    private CastleMove(final Board board,
                      final Piece movedPiece,
                      final int destinationCoordinate,
                      final Rook castleRook,
                      final int castleRookStartPosition,
                      final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStartPosition = castleRookStartPosition;
        this.castleRookDestination = castleRookDestination;
    }

    private Rook getCastleRook(){
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove(){
        return true;
    }

    @Override
    public Board execute(){

        final Board.Builder builder = new Board.Builder();
        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        //TODO look into the first move on normal pieces
        builder.setPiece(new Rook(this.castleRookDestination,this.castleRook.getPieceAlliance()));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.castleRook.hashCode();
        result = prime * result + this.castleRookDestination;
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if(!(other instanceof CastleMove)){
            return false;
        }
        final CastleMove otherCastleMove = (CastleMove)other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }


    public static final class KingSideCastleMove extends CastleMove{

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

    public static final class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStartPosition,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStartPosition, castleRookDestination);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
        }

        @Override
        public String toString(){
            return "0-0-0";
        }

    }
}
