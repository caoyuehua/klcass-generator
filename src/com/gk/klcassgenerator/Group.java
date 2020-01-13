package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;

/**
 * 学生分组
 * @author yvette
 *
 */
public class Group implements Serializable{
  private static final long serialVersionUID = -1965069071729940819L;
  // 组号
  private int id;
  // 学生人数
  private int stuNumber;
  // 学生list
  private List<Student> stuList;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public int getStuNumber() {
    return stuNumber;
  }
  public void setStuNumber(int stuNumber) {
    this.stuNumber = stuNumber;
  }
  public List<Student> getStuList() {
    return stuList;
  }
  public void setStuList(List<Student> stuList) {
    this.stuList = stuList;
  }
}