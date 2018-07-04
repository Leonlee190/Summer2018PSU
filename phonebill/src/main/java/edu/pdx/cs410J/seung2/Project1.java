package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.lang.Object;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args[0].equals("-print") || args[0].equals("-PRINT")){
      System.out.println("Called Print");
    }
    if(args[0].equals("-readme") || args[0].equals("-README")){
      System.out.println("Called README");
    }
    else if(args.length == 7){
      String[] name = args[0].split(" ");

      String[] caller = args[1].split("-");
      checkInt(caller, "Caller");

      String[] callee = args[2].split("-");
      checkInt(callee, "Callee");

      String[] start = args[3].split("/");
      checkInt(start, "starting Date");
      checkDate(start, "starting time's");

      String[] startTime = args[4].split(":");
      checkInt(startTime, "starting Time");
      checkTime(startTime, "starting Time");

      String[] end = args[5].split("/");
      checkInt(end, "ending Date");
      checkDate(start, "ending time's");

      String[] endTime = args[6].split(":");
      checkInt(endTime, "ending Time");
      checkTime(endTime, "ending Time");

      initCall(call, caller, callee, start, startTime, end, endTime);

      System.out.println("Caller: " + call.getCaller());
      System.out.println("Callee: " + call.getCallee());
      System.out.println("Start: " + call.getStartTimeString());
      System.out.println("End: " + call.getEndTimeString());
    }
    else{
      System.err.println("Incorrect command line argument");
      System.exit(1);
    }

    System.exit(1);
  }

  public static boolean isInt(String input){
    boolean isIt;

    try{
      Integer.parseInt(input);
      isIt = true;
    }catch(NumberFormatException e){
      isIt = false;
    }

    return isIt;
  }

  public static void checkInt(String[] args, String name){
    for(String arg : args){
      boolean checker = isInt(arg);

      if(checker == false){
        System.err.println("Incorrect " + name + " integer value!");
        System.exit(1);
      }
    }
  }

  public static void checkDate(String[] args, String name) {
    int month = Integer.parseInt(args[0]);
    int day = Integer.parseInt(args[1]);

    if (month < 1 || month > 12) {
      System.err.println("Incorrect " + name + " month value!");
      System.exit(1);
    }
    if (day < 1 || day > 31) {
      System.err.println("Incorrect " + name + " date value!");
      System.exit(1);
    }
    if (args[2].length() != 4) {
      System.err.println("Incorrect " + name + " year value!");
      System.exit(1);
    }
  }

  public static void checkTime(String[] args, String name){
    int hour = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);

    if(hour < 0 || hour > 24){
      System.err.println("Incorrect " + name + " hour value!");
      System.exit(1);
    }
    if(min < 0 || min >= 60){
      System.err.println("Incorrect " + name + " minute value!");
      System.exit(1);
    }
  }

  public static void initCall(PhoneCall call, String[] caller, String[] callee, String[] start, String[] startTime, String[] end, String[] endTime){
    call.setCall(caller, 1);
    call.setCall(callee, 0);
    call.setDate(start, 1);
    call.setTime(startTime, 1);
    call.setDate(end, 0);
    call.setTime(endTime, 0);
  }
}
