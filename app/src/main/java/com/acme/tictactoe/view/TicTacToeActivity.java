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
    /**Cells Views Model for Board Buttons*/
    viewModel.getCells().observe(this, cells -> {
      for (int i = 0; i < buttonGrid.getChildCount(); i++) {
        Button button = (Button) buttonGrid.getChildAt(i);
        button.setText(cells.getOrDefault(button.getId(), ""));
      }
    });

    viewModel.getWinnerPlayerViewGroupVisibility()
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
        viewModel.restart();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void onCellClicked(View v) {
    Button button = (Button) v;
    String tag = button.getTag().toString();
    viewModel.mark(tag, v.getId());
  }
}