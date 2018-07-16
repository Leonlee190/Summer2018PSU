package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {
  // README option printer
  public static String README = "\nCourse: CS 401J\nProject 1: Designing a Phone Bill Application\nProgrammer: SeungJun Lee" +
          "\nDescription: This project parses the user's command line arguments and initialize PhoneCall class and PhoneBill class or " +
          "executes options given by the user.\n             PhoneBill will store the customer's name and collection of PhoneCall data." +
          "\n             PhoneCall will store caller and callee's number and starting and ending date and time.\n\nUsage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n\n" +
          "Arguments are in this order: with example format\n" +
          "   - Customer: \"First Last\" or \"First Middle Last\"\n   - Caller Number: XXX-XXX-XXXX\n   - Callee Number: XXX-XXX-XXXX\n   - Start Time: MM/DD/YYYY HH:MM\n   - End Time: MM/DD/YYYY HH:MM" +
          "\n\nOptions:\n    -print : Prints a description of the new phone call\n          -> If no information has been provided with print function then it will print error" +
          "\n    -README : Prints a README for this project and exits\n          -> Which you have currently!";

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    PhoneBill bill = new PhoneBill();  // PhoneBill class to contain customer name and PhoneCall data

    // If no command line argument
    if(args.length < 1){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    if(args[0].equals("-textFile") && args.length == 9){
      parseInput(call, bill, args, 2);
    }

    // If only print option is called without any initialization
    if(args[0].equals("-print")  && args.length == 1){
      System.err.println("No phone call information to print");
      System.exit(1);
    }

    // If -print option with data is read
    if(args[0].equals("-print")  && args.length == 8){
      parseInput(call, bill, args, 1);
    }
    // Only -readme option is called
    else if(args[0].equals("-README") && args.length == 1){
      System.out.println(README);
      System.exit(0);
    }
    // Just call data has been inputted
    else if(args.length == 7){
      parseInput(call, bill, args, 0);
    }
    // Everything else returns error
    else{
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    // In non of the options were followed then exit with invalid
    System.exit(1);
  }

  /**
   * Checks if the String is integer
   *
   * @param input
   *         String to check
   * @return boolean
   *          if the String is int or not with boolean
   */
  public static boolean isInt(String input){
    boolean isIt;

    // Try parsing String to Int, if can't then return false
    try{
      Integer.parseInt(input);
      isIt = true;
    }catch(NumberFormatException e){
      isIt = false;
    }

    return isIt;
  }

  /**
   *  Go through all the String that has been split to check if all of them are integer
   *
   * @param args
   *         String array that was split from command line
   * @param name
   *         Name of the format
   */
  public static void checkInt(String[] args, String name){

    // Go through all the String and check if it's integer or not
    for(String arg : args){
      boolean checker = isInt(arg);

      if(!checker){
        System.err.println("Incorrect " + name + " integer value!");
        System.exit(1);
      }
    }
  }

  /**
   *  Test date format
   *  Valid : MM/DD/YYYY
   *      - Must be using "/" to separate the values
   *      - Month and Day needs to be 1 or 2 digits
   *      - Year needs to be 4 digits only
   *      - Month needs to be between 1 and 12
   *      - Day needs to be between 1 and 31
   *      - Should only have month, day, and year and no more
   *
   * @param args
   *         String array which was split from command line
   * @param arg
   *         Corresponding command line argument
   * @param name
   *         Name of the format
   */
  public static void checkDate(String[] args, String arg, String name) {
    checkDateFormat(arg, name);
    // If there aren't Month, Day, and Year or more than that then error
    if(args.length != 3){
      System.err.println("Incorrect " + name + " date value!");
      System.exit(1);
    }

    // If Month and Day has more than 2 digits then error
    if((args[0].length() > 2) || (args[1].length() > 2)){
      System.err.println("Incorrect " + name + " time value!");
      System.exit(1);
    }

    // Change Month and Day string to int
    int month = Integer.parseInt(args[0]);
    int day = Integer.parseInt(args[1]);

    // If the month value is outside the bound then error
    if (month < 1 || month > 12) {
      System.err.println("Incorrect " + name + " month value!");
      System.exit(1);
    }

    // If the day value is outside the bound then error
    if (day < 1 || day > 31) {
      System.err.println("Incorrect " + name + " date value!");
      System.exit(1);
    }

    // If year isn't in 4 digit then error
    if (args[2].length() != 4) {
      System.err.println("Incorrect " + name + " year value!");
      System.exit(1);
    }
  }

  /**
   *  Check time format
   *
   *  Valid : HH:MM
   *      - Must be using ":" to separate the values
   *      - Hour and Min should be the only input
   *      - Hour and Min is 1 or 2 digits
   *      - Hour needs to be between 0 and 23
   *      - Min needs to be between 0 and 59
   *
   * @param args
   *         String array split from the command line
   * @param arg
   *         Corresponding command line argument
   * @param name
   *         Name of the format
   */
  public static void checkTime(String[] args, String arg, String name){
    checkTimeFormat(arg, name);

    // If there is less or more than 2 inputs (Hour and Min) then error
    if(args.length != 2){
      System.err.println("Incorrect " + name + " time value!");
      System.exit(1);
    }

    // If hour and min is bigger than 2 digits then error
    if((args[0].length() > 2) || (args[1].length() > 2)){
      System.err.println("Incorrect " + name + " time value!");
      System.exit(1);
    }

    // String to Int for hour and min value
    int hour = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);

    // If Hour is outside the bound then error
    if(hour < 0 || hour > 24){
      System.err.println("Incorrect " + name + " hour value!");
      System.exit(1);
    }

    // If Min is outside the bound then error
    if(min < 0 || min >= 60){
      System.err.println("Incorrect " + name + " minute value!");
      System.exit(1);
    }
  }

  /**
   * Check if the caller/callee number format is separated via "-" and has 3 section
   *
   * @param args
   *         String from command line argument
   * @param name
   *         Name of the format
   */
  public static void checkNumberFormat(String args, String name){
    boolean numFormat;

    // Checks if it has "-" if not then error
    numFormat = args.contains("-");

    if(!numFormat){
      System.err.println("Incorrect " + name + " number format");
      System.exit(1);
    }

    String[] call = args.split("-");

    // Check if it's separated by "-" and 3 sections
    if(call.length != 3){
      System.err.println("Incorrect " + name + " number format");
      System.exit(1);
    }
  }

  /**
   * Check if the starting/ending date format is separated via "/"
   *
   * @param args
   *         String from command line argument
   * @param name
   *         Name of the format
   */
  public static void checkDateFormat(String args, String name){
    boolean dateFormat;

    // Check if "/" is contained and if not then error
    dateFormat = args.contains("/");

    if(!dateFormat){
      System.err.println("Incorrect " + name + " date format");
      System.exit(1);
    }
  }

  /**
   * Check if the starting/ending time format is separated via ":"
   *
   * @param args
   *         String from command line argument
   * @param name
   *         Name of the format
   */
  public static void checkTimeFormat(String args, String name) {
    boolean timeFormat;

    // Check if ":" is contained and if not then error
    timeFormat = args.contains(":");

    if (!timeFormat) {
      System.err.println("Incorrect " + name + " time format");
      System.exit(1);
    }
  }

  /**
   * Parses the command line argument and initialize the PhoneCall field and add it to the PhoneBill field
   *    Argument in order of
   *        - Customer Name
   *        - Caller Number
   *        - Callee Number
   *        - Starting Date
   *        - Starting Time
   *        - Ending Date
   *        - Ending Time
   *
   * @param calling
   *         PhoneCall field for the command line input
   * @param billing
   *         PhoneBill field for Customer name and PhoneCall field
   * @param args
   *         Command line argument
   * @param i
   *         Option buffer
   */
  public static void parseInput(PhoneCall calling, PhoneBill billing, String[] args, int i){
    // Split with space and set PhoneBill's customer name
    String[] name = args[i].split(" ");
    billing.setCustomer(name);

    // Split with "-" and check for caller number validity
    String[] caller = args[i+1].split("-");
    checkInt(caller, "caller");
    checkNumberFormat(args[i+1], "caller");

    // Split with "-" and check for callee number validity
    String[] callee = args[i+2].split("-");
    checkInt(callee, "callee");
    checkNumberFormat(args[i+2], "callee");

    // Split with "/" and check for starting date validity
    String[] start = args[i+3].split("/");
    checkInt(start, "starting date");
    checkDate(start, args[i+3], "starting date's");

    // Split with ":" and check for starting time validity
    String[] startTime = args[i+4].split(":");
    checkInt(startTime, "starting time");
    checkTime(startTime, args[i+4], "starting time");

    // Split with "/" and check for ending date validity
    String[] end = args[i+5].split("/");
    checkInt(end, "ending date");
    checkDate(end, args[i+5], "ending date's");

    // Split with ":" and check for ending time validity
    String[] endTime = args[i+6].split(":");
    checkInt(endTime, "ending time");
    checkTime(endTime, args[i+6], "ending time");

    // Initialize PhoneCall class with verified command line input
    calling.initCall(caller, callee, start, end, startTime, endTime);

    // Add initialized PhoneCall class to the PhoneBill class
    billing.addPhoneCall(calling);

    // Output with toString function
    System.out.println(calling.toString());

    // Exit
    System.exit(0);
  }
}
