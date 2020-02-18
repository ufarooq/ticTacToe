package com.acme.tictactoe.livedata;

import androidx.lifecycle.LiveData;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;

import java.util.LinkedList;
import java.util.Queue;

public class BoardLiveData extends LiveData<Board> {
    private Board model;
    private Queue<String> pendingActions;
    private Player playerThatMoved;

    public static final String ACTION_INIT = "init";
    public static final String ACTION_RESTART = "restart";
    public static final String ACTION_MARK = "mark";

    public BoardLiveData() {
        this.model = new Board();
        pendingActions = new LinkedList<>();
        setValue(model, ACTION_INIT);
    }


    public void restart() {
        model.restart();
        setValue(model, ACTION_RESTART);
    }

    public Player mark(int row, int col) {
        this.playerThatMoved = model.mark(row, col);
        if (this.playerThatMoved != null)
            setValue(model, ACTION_MARK);
        return this.playerThatMoved;
    }

    public Player getPlayerThatMoved() {
        return playerThatMoved;
    }

    public void clearPlayerThatMoved() {
        playerThatMoved = null;
    }

    public Player getWinner() {
        return model.getWinner();
    }

    protected void setValue(Board value, String action) {
        pendingActions.offer(action);
        super.setValue(value);
    }

    public String currentAction() {
        return pendingActions.poll();
    }
}
