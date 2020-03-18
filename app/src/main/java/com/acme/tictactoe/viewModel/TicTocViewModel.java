package com.acme.tictactoe.viewModel;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import java.util.HashMap;

public class TicTocViewModel extends ViewModel {
  private MutableLiveData<Integer> winnerPlayerViewGroupVisibility;
  private MutableLiveData<String> winnerPlayerLabelText;
  private MutableLiveData<HashMap<TagKey, String>> gridViewsText;
  private Board model;

  public TicTocViewModel() {
    winnerPlayerLabelText = new MutableLiveData<>("");
    winnerPlayerViewGroupVisibility = new MutableLiveData<>(View.INVISIBLE);
    gridViewsText = new MutableLiveData<>(new HashMap<TagKey, String>());
    this.model = new Board();
  }

  public MutableLiveData<Integer> getWinnerPlayerViewGroupVisibility() {
    return winnerPlayerViewGroupVisibility;
  }

  public MutableLiveData<String> getWinnerPlayerLabelText() {
    return winnerPlayerLabelText;
  }

  public MutableLiveData<HashMap<TagKey, String>> getGridViewsText() {
    return gridViewsText;
  }

  public void onButtonSelected(int row, int col) {
    Player playerThatMoved = model.mark(row, col);

    if (playerThatMoved != null) {
      HashMap<TagKey, String> existing = gridViewsText.getValue();
      existing.put(new TagKey(row, col), playerThatMoved.toString());
      gridViewsText.setValue(existing);
      // view.setButtonText(row, col, playerThatMoved.toString());
      if (model.getWinner() != null) {
        winnerPlayerLabelText.setValue(playerThatMoved.toString());
        winnerPlayerViewGroupVisibility.setValue(View.VISIBLE);
      }
    }
  }

  public void onResetSelected() {
    winnerPlayerLabelText.setValue("");
    winnerPlayerViewGroupVisibility.setValue(View.GONE);
    for (TagKey key : gridViewsText.getValue().keySet()) {
      HashMap<TagKey, String> existing = gridViewsText.getValue();
      existing.replace(key, "");
      gridViewsText.setValue(existing);
    }
    model.restart();
  }
}
