package com.gk.klcassgenerator;

import java.io.Serializable;
import java.util.List;

/**
 * 选科
 * @author yvette
 *
 */
public class Option implements Serializable{
  private static final long serialVersionUID = 8639937720028723164L;
  // 科目
  private Subject subject;
  // 总人数
  private int totalNumber;
  // 预设班级人数
  private int defaultKlassSize;
  // 预设插班人数
  private int defaultShiftNumber;
  // 课程课时
  private int courseHour;
  // 班级个数
  private int klassNumber;
  // 班级人数
  private int klassSize;
  // 学生list
  private List<Student> stuList;
  public Subject getSubject() {
    return subject;
  }
  public void setSubject(Subject subject) {
    this.subject = subject;
  }
  public int getTotalNumber() {
    return totalNumber;
  }
  public void setTotalNumber(int totalNumber) {
    this.totalNumber = totalNumber;
  }
  public int getDefaultKlassSize() {
    return defaultKlassSize;
  }
  public void setDefaultKlassSize(int defaultKlassSize) {
    this.defaultKlassSize = defaultKlassSize;
  }
  public int getDefaultShiftNumber() {
    return defaultShiftNumber;
  }
  public void setDefaultShiftNumber(int defaultShiftNumber) {
    this.defaultShiftNumber = defaultShiftNumber;
  }
  public int getCourseHour() {
    return courseHour;
  }
  public void setCourseHour(int courseHour) {
    this.courseHour = courseHour;
  }
  public int getKlassNumber() {
    return klassNumber;
  }
  public void setKlassNumber(int klassNumber) {
    this.klassNumber = klassNumber;
  }
  public int getKlassSize() {
    return klassSize;
  }
  public void setKlassSize(int klassSize) {
    this.klassSize = klassSize;
  }
  public List<Student> getStuList() {
    return stuList;
  }
  public void setStuList(List<Student> stuList) {
    this.stuList = stuList;
  }
}