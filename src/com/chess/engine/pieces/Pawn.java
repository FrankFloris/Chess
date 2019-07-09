package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;
import static com.chess.engine.pieces.Piece.PieceType.PAWN;

public class Pawn extends Piece {

    public final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance, PAWN, true);
    }

    public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(piecePosition, pieceAlliance, PAWN, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {


        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                } else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }

            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))){
                final int behindCandidateDestination = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindCandidateDestination).isTileOccupied() &&
                !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.H_COLUMN[piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.A_COLUMN[piecePosition] && this.pieceAlliance.isBlack()))){
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceAtDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (pieceAtDestination.pieceAlliance != this.pieceAlliance){
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                    }
                } else if(board.getEnPassantPawn() != null){
                    if((board.getEnPassantPawn().getPiecePosition()) +
                       (board.getEnPassantPawn().getPieceAlliance().getOppositeDirection() * 8) == candidateDestinationCoordinate) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
//                else if(board.getEnPassantPawn() != null){
//                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection())));{
//                        final Piece pieceOnCandidate = board.getEnPassantPawn();
//                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
//                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
//                        }
//                    }
//                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.A_COLUMN[piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.H_COLUMN[piecePosition] && this.pieceAlliance.isBlack()))){
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceAtDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (pieceAtDestination.pieceAlliance != this.pieceAlliance){
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                    }
                } else if(board.getEnPassantPawn() != null){
                    if((board.getEnPassantPawn().getPiecePosition()) +
                            (board.getEnPassantPawn().getPieceAlliance().getOppositeDirection() * 8) == candidateDestinationCoordinate) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
//                else if(board.getEnPassantPawn() != null){
//                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection())));{
//                        final Piece pieceOnCandidate = board.getEnPassantPawn();
//                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
//                            legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
//                        }
//                    }
//                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString(){
        return PAWN.toString();
    }

    //TODO now only promoting to queen, underpromotion not yet implemented.
    public Piece getPromotionPiece(){
        return new Queen(this.piecePosition, this.pieceAlliance, false);
    }
}
