package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callee;
  private String startTime;
  private String endTime;

  @Override
  public String getCaller() {
    return caller;
  }

  @Override
  public String getCallee() {
    return callee;
  }

  @Override
  public String getStartTimeString() {
    return startTime;
  }

  @Override
  public String getEndTimeString() {
    return endTime;
  }
}
