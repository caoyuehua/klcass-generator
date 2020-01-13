package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;

public class TotalNumberDetail implements Serializable{
  private static final long serialVersionUID = 1999330763658242546L;
  // 总人数list
  private List<Integer> totalNumberList;
  // 组合的总人数map
  private List<Combination> combinationList;
  public List<Integer> getTotalNumberList() {
    return totalNumberList;
  }
  public void setTotalNumberList(List<Integer> totalNumberList) {
    this.totalNumberList = totalNumberList;
  }
  public List<Combination> getCombinationList() {
    return combinationList;
  }
  public void setCombinationList(List<Combination> combinationList) {
    this.combinationList = combinationList;
  }
}
