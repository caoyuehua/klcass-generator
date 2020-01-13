package com.gk.klcassgenerator;

/**
 * 错误码
 * @author yvette
 *
 */
public enum ErrorCode {
  
  UNSPECIFIED("5000", "未预料的异常发生，请联系我们！"),
  OPTION_NOTHING("1101","没有发现选科数据，请检查数据！"),
  OPTION_SUBJECT_NULL("1102","发现没有科目的选科数据，请检查数据！"),
  OPTION_TOTALNUMBER_LE_ZERO("1103","发现总人数不大于0的选科数据，请检查数据！"),
  OPTION_DEFAULTKLASSSIZE_LE_ZERO("1104","发现预设班级人数不大于0的选科数据，请检查数据！"),
  OPTION_DEFAULTSHIFTNUMBER_LE_ZERO("1105","发现预设插班人数不大于0的选科数据，请检查数据！"),
  OPTION_COURSEHOUR_LE_ZERO("116","发现课程课时不大于0的选科数据，请检查数据！"),
  COMBINATION_NOTHING("1201","没有发现组合数据，请检查数据！"),
  COMBINATION_OPTION_NOTHING("1101","发现组合数据中没有选科数据，请检查数据！"),
  COMBINATION_OPTION_SUBJECT_NULL("1202","发现组合数据中存在没有科目的选科数据，请检查数据！"),
  COMBINATION_OPTION_TOTALNUMBER_LE_ZERO("1203","发现组合数据中存在总人数不大于0的选科数据，请检查数据！"),
  ;
  /**
  lt （less than）               小于
  le （less than or equal to）   小于等于
  eq （equal to）                等于
  ne （not equal to）            不等于
  ge （greater than or equal to）大于等于
  gt （greater than）            大于
   */
  // 成员变量
  private final String code;
  private final String description;
  // 构造方法
  private ErrorCode(String code, String description) {
    this.code = code;
    this.description = description;
  }
  // 普通方法
  public static String getDescription(String code) {
    for (ErrorCode s : ErrorCode.values()) {
      if (code.equals(s.getCode())) {
        return s.description;
      }
    }
    return null;
  }
  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}