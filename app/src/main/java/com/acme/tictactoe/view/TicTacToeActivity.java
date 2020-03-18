package com.acme.tictactoe.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import com.acme.tictactoe.R;
import com.acme.tictactoe.viewModel.TicTocViewModel;

public class TicTacToeActivity extends AppCompatActivity {

  private static String TAG = TicTacToeActivity.class.getName();

  private ViewGroup buttonGrid;
  private View winnerPlayerViewGroup;
  private TextView winnerPlayerLabel;
  private TicTocViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tictactoe);
    winnerPlayerLabel = findViewById(R.id.winnerPlayerLabel);
    winnerPlayerViewGroup = findViewById(R.id.winnerPlayerViewGroup);
    buttonGrid = findViewById(R.id.buttonGrid);
    viewModel = new ViewModelProvider(this).get(TicTocViewModel.class);

    viewModel
        .getGridViewsText()
        .observe(
            this,
            tagKeyStringHashMap -> {
              tagKeyStringHashMap.forEach(
                  (tag, text) -> {
                    Button btn = buttonGrid.findViewWithTag("" + tag.getRow() + tag.getCol());
                    if (btn != null) {
                      btn.setText(text);
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
        viewModel.onResetSelected();
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

    viewModel.onButtonSelected(row, col);
  }
}
