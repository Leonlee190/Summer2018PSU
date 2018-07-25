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
  private String startTime = "not implemented";       // Phone call's starting date and time
  private String endTime = "not implemented";         // Phone call's ending date and time
  private Date startT = null;                         // Date class for starting time
  private Date endT = null;                           // Date calss for ending time
  private SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); // Date formatter

  /**
   * Initializes the PhoneCall's field with checked but fully parsed fields
   *
   * @param Caller
   *         String array of caller's number in three sections
   * @param Callee
   *         String array of callee's number in three sections
   * @param sDate
   *         String array of starting date which has month, date, and year value
   * @param eDate
   *         String array of ending date which has month, date, and year value
   * @param sTime
   *         String array of starting time which has hour and min
   * @param eTime
   *         String array of ending time which has hour and min
   */
  public void initCall(String[] Caller, String[] Callee, String[] sDate, String[] eDate, String[] sTime, String[] eTime, String sAP, String eAP){
    // Initialize caller and callee's phone number with correct format
    this.caller = Caller[0] + "-" + Caller[1] + "-" + Caller[2];
    this.callee = Callee[0] + "-" + Callee[1] + "-" + Callee[2];

    // Initialize starting and ending date and time with correct format
    this.startTime = sDate[0] + "/" + sDate[1] + "/" + sDate[2] + " " + sTime[0] + ":" + sTime[1] + " " + sAP;
    this.endTime = eDate[0] + "/" + eDate[1] + "/" + eDate[2] + " " + eTime[0] + ":" + eTime[1] + " " + eAP;

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
