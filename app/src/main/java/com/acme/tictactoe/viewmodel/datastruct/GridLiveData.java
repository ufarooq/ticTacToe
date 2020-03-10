package com.acme.tictactoe.viewmodel.datastruct;

import androidx.lifecycle.LiveData;
import java.util.HashMap;

/** Based on 1-N mapping for Handler to Views */
public class GridLiveData extends LiveData<HashMap<Integer, String>> {

  private HashMap<Integer, String> stringPlayerHashMap;

  public GridLiveData() {
    this.stringPlayerHashMap = new HashMap<>();
    setValue(stringPlayerHashMap);
  }

  public void addPlayer(int viewId, String text) {
    this.stringPlayerHashMap.put(viewId, text);
    setValue(this.stringPlayerHashMap);
  }
}
