package com.acme.tictactoe.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.acme.tictactoe.R;
import com.acme.tictactoe.livedata.BoardLiveData;
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.viewmodel.TicTacToeViewModel;

public class TicTacToeActivity extends AppCompatActivity {

    private TicTacToeViewModel viewModel;
    private TextView winnerPlayerLabel;
    private LinearLayout winnerPlayerViewGroup;
    private ViewGroup buttonGrid;
    private Button cellClickButton;
    private int cellClickButtonId;
    private static String TAG = TicTacToeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe);

        // Get the ViewModel.
        viewModel = new ViewModelProvider(this).get(TicTacToeViewModel.class);
        winnerPlayerLabel = findViewById(R.id.winnerPlayerLabel);
        buttonGrid = findViewById(R.id.buttonGrid);
        winnerPlayerViewGroup = findViewById(R.id.winnerPlayerViewGroup);

        viewModel.getBoardLiveData().observe(this, new Observer<Board>() {
            @Override
            public void onChanged(Board board) {

                String action = viewModel.getBoardLiveData().currentAction();
                Log.i(TAG, action + "");
                if (action != null) {
                    if (action.equals(BoardLiveData.ACTION_RESTART)) {
                        winnerPlayerViewGroup.setVisibility(View.GONE);
                        winnerPlayerLabel.setText("");
                        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
                            ((Button) buttonGrid.getChildAt(i)).setText("");
                        }
                    } else if (action.equals(BoardLiveData.ACTION_MARK)) {
                        Player playerThatMoved = viewModel.getBoardLiveData().getPlayerThatMoved();
                        if (playerThatMoved != null && cellClickButtonId != 0) {
                            Button button = findViewById(cellClickButtonId);
                            button.setText(playerThatMoved.toString());
                            if (viewModel.getBoardLiveData().getWinner() != null) {
                                winnerPlayerLabel.setText(playerThatMoved.toString());
                                winnerPlayerViewGroup.setVisibility(View.VISIBLE);
                            }
                            cellClickButtonId = 0;
                            viewModel.getBoardLiveData().clearPlayerThatMoved();
                        }
                    }
                }else{
                    int btnIndex = 0;
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 3; col++) {
                            Player playerThatMoved = board.valueAtCell(row, col);
                            ((Button) buttonGrid.getChildAt(btnIndex)).setText(playerThatMoved == null ? "" : playerThatMoved.toString());
                            btnIndex++;
                        }
                    }
                }
            }
        });
    }

    public void onCellClicked(View v) {
        Button button = (Button) v;

        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Log.i(TAG, "Click Row: [" + row + "," + col + "]");
        cellClickButtonId = button.getId();
        Player playerThatMoved = viewModel.getBoardLiveData().mark(row, col);

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

    private void reset() {
        viewModel.getBoardLiveData().restart();
    }

}
