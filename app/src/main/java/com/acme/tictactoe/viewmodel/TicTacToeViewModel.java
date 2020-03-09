package com.acme.tictactoe.viewmodel;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.viewmodel.datastruct.GridLiveData;
import com.acme.tictactoe.viewmodel.datastruct.PlayerState;

public class TicTacToeViewModel extends ViewModel {

  private MutableLiveData<Integer> winnerPlayerViewGroupVisibility;
  private MutableLiveData<String> winnerPlayerLabelText;
  private GridLiveData gridLiveData;
  private Board board;

  public TicTacToeViewModel() {
    gridLiveData = new GridLiveData();
    winnerPlayerViewGroupVisibility = new MutableLiveData<>(View.INVISIBLE);
    winnerPlayerLabelText = new MutableLiveData<>("");
    board = new Board();
  }

  public GridLiveData getGridLiveData() {
    return gridLiveData;
  }

  public MutableLiveData<Integer> getWinnerPlayerViewGroupVisibility() {
    return winnerPlayerViewGroupVisibility;
  }

  public MutableLiveData<String> getWinnerPlayerLabelText() {
    return winnerPlayerLabelText;
  }

  public void mark(int row, int col, int viewId) {
    Player playerThatMoved = board.mark(row, col);
    Player winner = board.getWinner();
    PlayerState playerState = new PlayerState(playerThatMoved, winner);
    gridLiveData.addPlayer(viewId, playerState);
  }

  public void resetGridView(int viewId){
    gridLiveData.resetPlayerState(viewId);
  }
  public void restart() {
    board.restart();
  }
}
