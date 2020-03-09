package com.acme.tictactoe.viewmodel.datastruct;

import com.acme.tictactoe.model.Player;
import java.util.Objects;

/**
 * Based on Control and Data dependence
 */
public class PlayerState {

  private final Player playerThatMoved;
  private final Player winner;

  public PlayerState(Player playerThatMoved, Player winner) {
    this.playerThatMoved = playerThatMoved;
    this.winner = winner;
  }

  public Player getPlayerThatMoved() {
    return playerThatMoved;
  }


  public Player getWinner() {
    return winner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerState that = (PlayerState) o;
    return playerThatMoved == that.playerThatMoved &&
        winner == that.winner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerThatMoved, winner);
  }
}
