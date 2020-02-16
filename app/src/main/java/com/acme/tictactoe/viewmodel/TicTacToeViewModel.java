package com.acme.tictactoe.viewmodel;

import androidx.lifecycle.ViewModel;

import com.acme.tictactoe.livedata.BoardLiveData;

public class TicTacToeViewModel extends ViewModel {

    private BoardLiveData boardLiveData;

    public TicTacToeViewModel() {
        boardLiveData = new BoardLiveData();
    }


    public BoardLiveData getBoardLiveData() {
        return boardLiveData;
    }


}
