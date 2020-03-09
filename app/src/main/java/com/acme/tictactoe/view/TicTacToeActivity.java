package com.acme.tictactoe.view;

import android.os.Bundle;
import android.util.Log;
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

  private static String TAG = TicTacToeActivity.class.getName();

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
            stringPlayerStateHashMap -> {
              stringPlayerStateHashMap.forEach(
                  (viewId, state) -> {
                    Button button = findViewById(viewId);
                    /** Branch from Click handler with same dependencies */
                    if (state.getPlayerThatMoved() != null) {
                      button.setText(state.getPlayerThatMoved().toString());
                      if (state.getWinner() != null) {
                        viewModel
                            .getWinnerPlayerLabelText()
                            .setValue(state.getPlayerThatMoved().toString());
                        viewModel.getWinnerPlayerViewGroupVisibility().setValue(View.VISIBLE);
                      }
                    } else {
                      /** No Dependencies under reset */
                      button.setText("");
                    }
                  });
            });
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
    int row = Integer.valueOf(tag.substring(0, 1));
    int col = Integer.valueOf(tag.substring(1, 2));
    Log.i(TAG, "Click Row: [" + row + "," + col + "]");
    /**
     * Update on Views inside Callback handler -> 1-1 mapping of handler for 1 view, Update on Views
     * inside Callback handler -> 1-N mapping will require ViewId 1. Control Dependence ->
     * playerThatMoved, getWinner(), 2. Data Dependence -> playerThatMoved 3. Data Changed (Model)
     * -> cells, winner, currentTurn, state
     */
    viewModel.mark(row, col, v.getId());
  }

  private void reset() {

    /**
     * Update on Views inside Callback handler 1. Data Changed (Model) -> cells, winner,
     * currentTurn, state. Changed variables met the pre-condition.
     */
    viewModel.restart();
    /**
     * 1. Data Dependence: NULL, Data Dependence: NULL, Different dependencies for same View updates
     * 2. Branch out, but Can be only 1 state under one branch
     */
    for (int i = 0; i < buttonGrid.getChildCount(); i++) {
      viewModel.resetGridView(buttonGrid.getChildAt(i).getId());
    }

    /** State may effect during onChange */
    viewModel.getWinnerPlayerViewGroupVisibility().setValue(View.GONE);
    viewModel.getWinnerPlayerLabelText().setValue("");
  }
}
