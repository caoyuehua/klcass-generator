package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;

public class NotFullKlassDetail implements Serializable{
  private static final long serialVersionUID = 2348507405494809452L;
  // 不满班list
  private List<Klass> notFullKlassList;
  // 可再容纳总人数
  private int sumCanAgainJoinNumber;
  public List<Klass> getNotFullKlassList() {
    return notFullKlassList;
  }
  public void setNotFullKlassList(List<Klass> notFullKlassList) {
    this.notFullKlassList = notFullKlassList;
  }
  public int getSumCanAgainJoinNumber() {
    return sumCanAgainJoinNumber;
  }
  public void setSumCanAgainJoinNumber(int sumCanAgainJoinNumber) {
    this.sumCanAgainJoinNumber = sumCanAgainJoinNumber;
  }
}
