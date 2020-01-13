package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;

/**
 * 班级
 * @author yvette
 *
 */
public class Klass implements Serializable{
  private static final long serialVersionUID = -3522779322450927382L;
  // 班级名称
  private String name;
  // 科目
  private Subject subject;
  // 班级人数
  private int klassSize;
  // 课程课时
  private int courseHour;
  // 是否满班 1 满班 0 不满班
  private int isFull;
  // 可再容纳人数
  private int canAgainJoinNumber;
  // 学生分组list
  private List<Group> groupList;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Subject getSubject() {
    return subject;
  }
  public void setSubject(Subject subject) {
    this.subject = subject;
  }
  public int getKlassSize() {
    return klassSize;
  }
  public void setKlassSize(int klassSize) {
    this.klassSize = klassSize;
  }
  public int getCourseHour() {
    return courseHour;
  }
  public void setCourseHour(int courseHour) {
    this.courseHour = courseHour;
  }
  public int getIsFull() {
    return isFull;
  }
  public void setIsFull(int isFull) {
    this.isFull = isFull;
  }
  public int getCanAgainJoinNumber() {
    return canAgainJoinNumber;
  }
  public void setCanAgainJoinNumber(int canAgainJoinNumber) {
    this.canAgainJoinNumber = canAgainJoinNumber;
  }
  public List<Group> getGroupList() {
    return groupList;
  }
  public void setGroupList(List<Group> groupList) {
    this.groupList = groupList;
  }
}