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
import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.viewmodel.TicTacToeViewModel;

public class TicTacToeActivity extends AppCompatActivity {

    private TicTacToeViewModel viewModel;
    private TextView winnerPlayerLabel;
    private LinearLayout winnerPlayerViewGroup;
    private ViewGroup buttonGrid;
    private Button pendingUpdateBoardLiveDataView;
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
                Player playerThatMoved = board.getPlayerThatMoved();
                if (playerThatMoved != null) {
                    pendingUpdateBoardLiveDataView.setText(playerThatMoved.toString());
                    if (viewModel.getBoardLiveData().getWinner() != null) {
                        winnerPlayerLabel.setText(playerThatMoved.toString());
                        winnerPlayerViewGroup.setVisibility(View.VISIBLE);
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

        viewModel.getBoardLiveData().mark(row, col);
        pendingUpdateBoardLiveDataView = button;
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
        winnerPlayerViewGroup.setVisibility(View.GONE);
        winnerPlayerLabel.setText("");

        viewModel.getBoardLiveData().restart();

        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            ((Button) buttonGrid.getChildAt(i)).setText("");
        }
    }

}
