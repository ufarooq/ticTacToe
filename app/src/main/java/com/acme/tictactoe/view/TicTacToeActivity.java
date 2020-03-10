package com.acme.tictactoe.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.acme.tictactoe.R;
import com.acme.tictactoe.viewmodel.TicTacToeViewModel;

public class TicTacToeActivity extends AppCompatActivity {

  private ViewGroup buttonGrid;
  private View winnerPlayerViewGroup;
  private TextView winnerPlayerLabel;
  private TicTacToeViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tictactoe);
    winnerPlayerLabel = findViewById(R.id.winnerPlayerLabel);
    winnerPlayerViewGroup = findViewById(R.id.winnerPlayerViewGroup);
    buttonGrid = findViewById(R.id.buttonGrid);

    viewModel = new ViewModelProvider(this).get(TicTacToeViewModel.class);
    viewModel
        .getGridLiveData()
        .observe(
            this,
            stringPlayerStateHashMap ->
                stringPlayerStateHashMap.forEach(
                    (viewId, text) -> {
                      Button button = findViewById(viewId);
                      button.setText(text);
                    }));
    viewModel
        .getWinnerPlayerViewGroupVisibility()
        .observe(this, visible -> winnerPlayerViewGroup.setVisibility(visible));

    viewModel.getWinnerPlayerLabelText().observe(this, winner -> winnerPlayerLabel.setText(winner));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_tictactoe, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_reset:
        reset();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void onCellClicked(View v) {

    Button button = (Button) v;
    String tag = button.getTag().toString();
    /**
     * Update on Views inside Callback handler -> 1-1 mapping of handler for 1 view, Update on Views
     * inside Callback handler -> 1-N mapping will require ViewId 1. Control Dependence ->
     * playerThatMoved, getWinner(), 2. Data Dependence -> playerThatMoved 3. Data Changed (Model)
     * -> cells, winner, currentTurn, state
     */
    viewModel.mark(tag, v.getId());
  }

  private void reset() {

    /**
     * Update on Views inside Callback handler 1. Data Changed (Model) -> cells, winner,
     * currentTurn, state. Changed variables met the pre-condition.
     */
    viewModel.restart();
    /** 1. Apply View Values directly, these views are same as buttons, */
    for (int i = 0; i < buttonGrid.getChildCount(); i++) {
      viewModel.setTextGridViews(buttonGrid.getChildAt(i).getId(), "");
    }
  }
}
