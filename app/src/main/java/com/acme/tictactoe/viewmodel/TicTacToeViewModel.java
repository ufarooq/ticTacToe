package com.acme.tictactoe.viewmodel;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import java.util.HashMap;

public class TicTacToeViewModel extends ViewModel {

  private MutableLiveData<Integer> winnerPlayerViewGroupVisibility;
  private MutableLiveData<String> winnerPlayerLabelText;
  private MutableLiveData<HashMap<Integer, String>> gridViewsText;
  private Board board;
  private static String TAG = TicTacToeViewModel.class.getName();

  public TicTacToeViewModel() {
    gridViewsText = new MutableLiveData<HashMap<Integer, String>>(new HashMap<>());
    winnerPlayerViewGroupVisibility = new MutableLiveData<>(View.INVISIBLE);
    winnerPlayerLabelText = new MutableLiveData<>("");
    board = new Board();
  }

  public MutableLiveData<HashMap<Integer, String>> getGridViewsText() {
    return gridViewsText;
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
    if (playerThatMoved != null) {
      HashMap<Integer, String> exisiting = gridViewsText.getValue();
      exisiting.put(viewId, playerThatMoved.toString());
      gridViewsText.setValue(exisiting);
      if (board.getWinner() != null) {
        winnerPlayerLabelText.setValue(playerThatMoved.toString());
        winnerPlayerViewGroupVisibility.setValue(View.VISIBLE);
      }
    }
  }

  public void setTextGridViews(int viewId, String text) {
    HashMap<Integer, String> exisiting = gridViewsText.getValue();
    exisiting.put(viewId, text);
    gridViewsText.setValue(exisiting);
  }

  public void restart() {
    winnerPlayerViewGroupVisibility.setValue(View.GONE);
    winnerPlayerLabelText.setValue("");
    board.restart();
  }
}
