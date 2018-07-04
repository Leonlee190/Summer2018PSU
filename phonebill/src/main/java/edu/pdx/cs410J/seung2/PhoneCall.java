package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller;
  private String callee;
  private String startTime;
  private String endTime;

  public void setCall(String[] args, int i){
    if(i == 1) {
      caller = args[0] + "-" + args[1] + "-" + args[2];
    }
    else{
      callee = args[0] + "-" + args[1] + "-" + args[2];
    }
  }

  public void setDate(String[] args, int i){
    if(i == 1){
      startTime = args[0] + "/" + args[1] + "/" + args[2];
    }
    else{
      endTime = args[0] + "/" + args[1] + "/" + args[2];
    }
  }

  public void setTime(String[] args, int i){
    if(i == 1){
      startTime += " " + args[0] + ":" + args[1];
    }
    else{
      endTime += " " + args[0] + ":" + args[1];
    }
  }
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
