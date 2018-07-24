package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * PhoneCall class that stores and initialize customer's call information
 */
public class PhoneCall extends AbstractPhoneCall {
  private String caller = "not implemented";          // Caller's number
  private String callee = "not implemented";          // Callee's number
  private String startTime = "not implemented";       // Phone call's starting date and time
  private String endTime = "not implemented";         // Phone call's ending date and time
  private Date startT;
  private Date endT;
  private DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);

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

  @Override
  public Date getStartTime() {
    return super.getStartTime();
  }

  /**
   * Returns the starting date and time
   *
   * @return
   *          Returns the starting date and time stored in PhoneCall class
   */
  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  @Override
  public Date getEndTime() {
    return super.getEndTime();
  }

  /**
   * Returns ending date and time
   *
   * @return
   *          Returns the ending date and time stored in PhoneCall class
   */
  @Override
  public String getEndTimeString() {
    return this.endTime;
  }
}
