package com.acme.tictactoe.viewModel;

import java.util.Objects;

public class TagKey {
  private final int row, col;

  public TagKey(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagKey tagKey = (TagKey) o;
    return row == tagKey.row &&
        col == tagKey.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
