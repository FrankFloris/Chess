package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;
import com.chess.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;
    private long boardsEvaluated;


    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

//    @Override
//    public long getNumBoardsEvaluated(){
//        return this.boardsEvaluated;
//    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " THINKING with depth = " + searchDepth);

        int numMoves = board.currentPlayer().getLegalMoves().size();

        for(final Move move : board.currentPlayer().getLegalMoves()){

            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){

                currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getTransitionboard(), searchDepth -1) :
                        max(moveTransition.getTransitionboard(), searchDepth -1);

                if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("This took " + executionTime + " milliseconds to evaluate. " + boardsEvaluated + " boards were evaluated." );

        return bestMove;
    }

    private static boolean isEndGameScenario(final Board board){
        return board.currentPlayer().isInCheck() ||
               board.currentPlayer().isInCheckMate();
    }

    public int min(final Board board,
                   final int depth){

        if(depth == 0 || isEndGameScenario(board)){
            this.boardsEvaluated++;
            return this.boardEvaluator.evaluate(board, depth);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionboard(), depth -1);
                if(currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;

    }

    public int max(final Board board,
                   final int depth){

        if(depth == 0 /* || game over*/){
            this.boardsEvaluated++;
            return this.boardEvaluator.evaluate(board, depth);
        }

        int highestSeenValue = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionboard(), depth -1);
                if(currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

}
