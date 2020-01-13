package com.gk.klcassgenerator;

/**
 * 数据异常
 * @author yvette
 *
 */
public class DataException extends RuntimeException{
  private static final long serialVersionUID = -4910154464344323300L;
  protected final ErrorCode errorCode;
  public DataException() {
    super();
    this.errorCode = ErrorCode.UNSPECIFIED;
  }
  public DataException(final ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }
}
