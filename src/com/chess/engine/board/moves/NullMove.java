package com.chess.engine.board.moves;

import com.chess.engine.board.Board;

public final class NullMove extends Move{

    public NullMove() {
        super(null, 65);
    }

    @Override
    public Board execute(){
        throw new RuntimeException("cannot execute the null move!");
    }

    @Override
    public int getCurrentCoordinate(){
        return -1;
    }

}
