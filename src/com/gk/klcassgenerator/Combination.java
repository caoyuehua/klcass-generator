package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 组合
 * @author yvette
 *
 */
public class Combination implements Serializable{
  private static final long serialVersionUID = -9093032880030531380L;
  // 选科
  private List<Option> optionList;
  // 总人数map
  private Map<Integer, Integer> totalNumberMap;
  // 当前总人数
  private int totalNumberNumber;
  // 处理顺序
  private int orderOfProcessing;
  // 学生list
  private List<Student> stuList;
  public List<Option> getOptionList() {
    return optionList;
  }
  public void setOptionList(List<Option> optionList) {
    this.optionList = optionList;
  }
  public Map<Integer, Integer> getTotalNumberMap() {
    return totalNumberMap;
  }
  public void setTotalNumberMap(Map<Integer, Integer> totalNumberMap) {
    this.totalNumberMap = totalNumberMap;
  }
  public int getOrderOfProcessing() {
    return orderOfProcessing;
  }
  public void setOrderOfProcessing(int orderOfProcessing) {
    this.orderOfProcessing = orderOfProcessing;
  }
  public int getTotalNumberNumber() {
    return totalNumberNumber;
  }
  public void setTotalNumberNumber(int totalNumberNumber) {
    this.totalNumberNumber = totalNumberNumber;
  }
  public List<Student> getStuList() {
    return stuList;
  }
  public void setStuList(List<Student> stuList) {
    this.stuList = stuList;
  }
}