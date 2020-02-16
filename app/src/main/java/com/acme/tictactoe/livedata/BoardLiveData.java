package com.acme.tictactoe.livedata;

import androidx.lifecycle.LiveData;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;

public class BoardLiveData extends LiveData<Board> {
    private Board model;


    public BoardLiveData() {
        this.model = new Board();
        setValue(model);
    }


    public void restart() {
        model.restart();
        setValue(model);
    }

    public void mark(int row, int col) {
        Player playerThatMoved = model.mark(row, col);
        model.setPlayerThatMoved(playerThatMoved);
        setValue(model);
    }
}
