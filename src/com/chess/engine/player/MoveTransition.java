package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.moves.Move;

public class MoveTransition {

    private final Board transitionboard;
    private final Move move;
    private final MoveStatus moveStatus;

    MoveTransition(final Board transitionboard, final Move move, final MoveStatus moveStatus){
        this.transitionboard = transitionboard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }

    public Board getTransitionboard(){
        return this.transitionboard;
    }

}
