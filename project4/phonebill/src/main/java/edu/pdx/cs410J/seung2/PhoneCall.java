package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PhoneCall class that stores and initialize customer's call information
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{
  private String caller = "not implemented";          // Caller's number
  private String callee = "not implemented";          // Callee's number
  private Date startT = null;                         // Date class for starting time
  private Date endT = null;                           // Date calss for ending time
  private SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); // Date formatter

  public PhoneCall(String caller, String callee, Date start, Date end) {
    this.caller = caller;
    this.callee = callee;
    this.startT = start;
    this.endT = end;
  }

  /**
   * Initializing the PhoneCall class with Date class
   *
   * @param caller
   *         String of caller's number
   * @param callee
   *         String of callee's number
   * @param start
   *         Date class for starting time
   * @param end
   *         Date class for ending time
   */
  public void init(String caller, String callee, Date start, Date end){
    this.caller = caller;
    this.callee = callee;
    this.startT = start;
    this.endT = end;
  }

  /**
   * Returns the caller's number
   *
   * @return
   *          Returns the caller's number stored in PhoneCall class
   */
  @Override
  public String getCaller() {
    return this.caller;
  }

  /**
   * Returns the callee's number
   *
   * @return
   *          Returns the callee's number stored in PhoneCall class
   */
  @Override
  public String getCallee() {
    return this.callee;
  }

  /**
   * Return the Date class for start time
   *
   * @return
   *          Return the PhoneCall's start time in Date object
   */
  @Override
  public Date getStartTime() {
    return this.startT;
  }

  /**
   * Returns the starting date and time
   *
   * @return
   *          Returns the starting date and time stored in PhoneCall class
   */
  @Override
  public String getStartTimeString() {
    if(startT == null){
      return "not implemented";
    }
    return this.ft.format(this.startT);
  }

  /**
   * Returns the ending time with Date class
   *
   * @return
   *          Returns the Date object of ending time
   */
  @Override
  public Date getEndTime() {
    return this.endT;
  }

  /**
   * Returns ending date and time
   *
   * @return
   *          Returns the ending date and time stored in PhoneCall class
   */
  @Override
  public String getEndTimeString() {
    if(endT == null){
      return "not implemented";
    }
    return this.ft.format(this.endT);
  }

  /**
   * Overriding Comparable interface for the PhoneCall list to be organized
   *    - Compare start time
   *    - if start time is equal then compare caller number
   *
   * @param o
   *         Object of PhoneCall to be compared to
   * @return
   *         Return the compared number
   *         1 if dest is bigger than source
   *         -1 if dest is smaller than source
   */
  @Override
  public int compareTo(PhoneCall o) {
    if(this.getStartTime().getTime() > o.getStartTime().getTime()){
      return 1;
    }
    else if(this.getStartTime().getTime() < o.getStartTime().getTime()){
      return -1;
    }
    else{
      if(this.getCaller().compareTo(o.getCaller()) < 0){
        return -1;
      }
      else{
        return 1;
      }
    }
  }
}
