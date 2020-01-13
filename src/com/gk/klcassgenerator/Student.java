package com.gk.klcassgenerator;

import java.io.Serializable;

/**
 * 学生
 * @author yvette
 *
 */
public class Student implements Serializable{
  private static final long serialVersionUID = -7584830541528627346L;
  // 学籍号
  private String code;
  // 姓名
  private String name;
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}