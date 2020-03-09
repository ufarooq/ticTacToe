package com.acme.tictactoe.viewmodel.datastruct;

import androidx.lifecycle.LiveData;
import java.util.HashMap;

/**
 * Based on 1-N mapping for Handler to Views
 */
public class GridLiveData extends LiveData<HashMap<Integer, PlayerState>> {

  private HashMap<Integer, PlayerState> stringPlayerHashMap;

  public GridLiveData() {
    this.stringPlayerHashMap = new HashMap<>();
    setValue(stringPlayerHashMap);
  }

  public void addPlayer(int viewId, PlayerState state) {
    this.stringPlayerHashMap.put(viewId, state);
    setValue(this.stringPlayerHashMap);
  }

  public void resetPlayerState(int viewId) {
    if (this.stringPlayerHashMap.containsKey(viewId)) {
      this.stringPlayerHashMap.replace(viewId, new PlayerState(null, null));
      setValue(this.stringPlayerHashMap);
    }
  }
}
