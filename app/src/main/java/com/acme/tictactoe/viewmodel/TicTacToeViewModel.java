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
    if (playerThatMoved != null) {
      gridLiveData.addPlayer(viewId, playerThatMoved.toString());
      if (board.getWinner() != null) {
        winnerPlayerLabelText.setValue(playerThatMoved.toString());
        winnerPlayerViewGroupVisibility.setValue(View.VISIBLE);
      }
    }
  }

  public void setTextGridViews(int viewId, String text) {
    gridLiveData.addPlayer(viewId, text);
  }

  public void restart() {
    winnerPlayerViewGroupVisibility.setValue(View.GONE);
    winnerPlayerLabelText.setValue("");
    board.restart();
  }
}
