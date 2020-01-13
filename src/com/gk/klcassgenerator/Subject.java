package com.gk.klcassgenerator;

/**
 * 科目
 * @author yvette
 *
 */
public enum Subject {
  PHYSICS_SELECTED(1,"物理选考"),
  CHEMISTRY_SELECTED(2,"化学选考"),
  BIOLOGY_SELECTED(3,"生物选考"),
  HISTORY_SELECTED(4,"历史选考"),
  GEOGRAPHY_SELECTED(5,"地理选考"),
  POLITICS_SELECTED(6,"政治选考"),
  PHYSICS_UNSELECTED(7,"物理学考"),
  CHEMISTRY_UNSELECTED(8,"化学学考"),
  BIOLOGY_UNSELECTED(9,"生物学考"),
  HISTORY_UNSELECTED(10,"历史学考"),
  GEOGRAPHY_UNSELECTED(11,"地理学考"),
  POLITICS_UNSELECTED(12,"政治学考")
  ;
  // 成员变量
  private int index;
  private String name;
  // 构造方法
  private Subject(int index, String name) {
    this.index = index;
    this.name = name;
  }
  // 普通方法
  public static String getName(int index) {
    for (Subject s : Subject.values()) {
      if (s.getIndex() == index) {
        return s.name;
      }
    }
    return null;
  }
  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}