package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callee;
  private String startTime;
  private String endTime;

  public void initCall(String[] Caller, String[] Callee, String[] sDate, String[] eDate, String[] sTime, String[] eTime){
    this.caller = Caller[0] + "-" + Caller[1] + "-" + Caller[2];
    this.callee = Callee[0] + "-" + Callee[1] + "-" + Callee[2];
    this.startTime = sDate[0] + "/" + sDate[1] + "/" + sDate[2] + " " + sTime[0] + ":" + sTime[1];
    this.endTime = eDate[0] + "/" + eDate[1] + "/" + eDate[2] + " " + eTime[0] + ":" + eTime[1];
  }

  @Override
  public String getCaller() {
    return this.caller;
  }

  @Override
  public String getCallee() {
    return this.callee;
  }

  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endTime;
  }
}
