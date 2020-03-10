package com.acme.tictactoe.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.viewmodel.datastruct.GridLiveData;
import java.util.HashMap;

public class TicTacToeViewModel extends ViewModel {

  private MutableLiveData<Integer> winnerPlayerViewGroupVisibility;
  private MutableLiveData<String> winnerPlayerLabelText;
  private MutableLiveData<HashMap<Integer, String>> cells;
  private Board board;

  public TicTacToeViewModel() {
    winnerPlayerViewGroupVisibility = new MutableLiveData<>(View.INVISIBLE);
    cells = new MutableLiveData<>(new HashMap<>());
    winnerPlayerLabelText = new MutableLiveData<>("");
    board = new Board();
  }

  public MutableLiveData<Integer> getWinnerPlayerViewGroupVisibility() {
    return winnerPlayerViewGroupVisibility;
  }

  public MutableLiveData<String> getWinnerPlayerLabelText() {
    return winnerPlayerLabelText;
  }

  public void mark(String tag, int viewId) {
    int row = Integer.valueOf(tag.substring(0, 1));
    int col = Integer.valueOf(tag.substring(1, 2));
    Log.i(TAG, "Click Row: [" + row + "," + col + "]");
    Player playerThatMoved = board.mark(row, col);
    HashMap<Integer, String> currentCells = cells.getValue();
    if (playerThatMoved != null) {
      currentCells.put(viewId, playerThatMoved.toString());
      cells.setValue(currentCells);
      if (board.getWinner() != null) {
        winnerPlayerLabelText.setValue(playerThatMoved.toString());
        winnerPlayerViewGroupVisibility.setValue(View.VISIBLE);
      }
    }
  }

  public MutableLiveData<HashMap<Integer, String>> getCells() {
    return cells;
  }

  public void restart() {
    board.restart();
    winnerPlayerViewGroupVisibility.setValue(View.GONE);
    winnerPlayerLabelText.setValue("");
    cells.setValue(new HashMap<>());
  }
}
