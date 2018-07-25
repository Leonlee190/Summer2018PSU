package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
  // README option printer
  public static String README = "\nCourse: CS 401J\nProject 1: Designing a Phone Bill Application\nProgrammer: SeungJun Lee" +
          "\nDescription: This project parses the user's command line arguments and initialize PhoneCall class and PhoneBill class or " +
          "executes options given by the user.\n             PhoneBill will store the customer's name and collection of PhoneCall data." +
          "\n             PhoneCall will store caller and callee's number and starting and ending date and time.\n\nUsage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n\n" +
          "Arguments are in this order: with example format\n" +
          "   - Customer: \"First Last\" or \"First Middle Last\"\n   - Caller Number: XXX-XXX-XXXX\n   - Callee Number: XXX-XXX-XXXX\n   - Start Time: MM/DD/YYYY HH:MM\n   - End Time: MM/DD/YYYY HH:MM" +
          "\n\nOptions:\n    -print : Prints a description of the new phone call\n          -> If no information has been provided with print function then it will print error" +
          "\n    -README : Prints a README for this project and exits\n          -> Which you have currently!";

  // File name for -textFile and -pretty option
  public static String fileName = null;

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();                   // Refer to one of Dave's classes so that we can be sure it is on the classpath
    PhoneBill bill = new PhoneBill();                   // PhoneBill class to contain customer name and PhoneCall data
    TextDumper dumper = new TextDumper();               // TextDumper class for -textFile option
    TextParser parser = new TextParser();               // TextParser class for -textFile option
    PrettyPrinter printer = new PrettyPrinter();        // PrettyPrinter class for -pretty option

    // If it's empty command line then exit
    if(args.length < 1){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    // Go through the command line and execute
    runOp(args, bill, call, dumper, parser, printer);

    System.exit(0);
  }


  /**
   * runOp method parse through command line argument and executes in recursive method
   *
   * @param args
   *         String array of command line argument
   * @param bill
   *         PhoneBill object to be initialized
   * @param call
   *         PhoneCall object to be initialized
   * @param dumper
   *         TextDumper object for dumping the PhoneBill data
   * @param parser
   *         TextParser object for reading from text file
   * @param printer
   *         PrettyPrinter object for writing human readable file of PhoneBill data
   * @return
   *         Return 1 or 0 for error checking and to return to previous stack
   */
  public static int runOp(String[] args, PhoneBill bill, PhoneCall call, TextDumper dumper, TextParser parser, PrettyPrinter printer){
    // if argument is empty then send out error
    if(args.length < 1){
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    // Retrieve option
    String option = args[0];
    int check = 1;

    // -README option
    if(option.equals("-README")){
      System.out.println(README);
      System.exit(0);
    }

    // -print option
    else if(option.equals("-print")){
      // Remove option String
      String[] nArgs = new String[args.length - 1];
      shrinkArray(args, nArgs);

      // Send it back into the recursion
      check = runOp(nArgs, bill, call, dumper, parser, printer);
      if(check > 0){
        System.err.println("-print option didn't work correctly");
        System.exit(1);
      }

      // Print the value
      System.out.println(call.toString());

      return 0;
    }

    // -textFile option
    else if(option.equals("-textFile")){
      // Remove option
      String[] nArgs = new String[args.length - 1];
      shrinkArray(args, nArgs);

      // if the file name is right after the option
      if(args[1].charAt(0) != '-') {
        // retrieve file name
        fileName = args[1];

        // Shrink the args again because it retrieved the file name
        String[] nnArgs = new String[nArgs.length - 1];
        shrinkArray(nArgs, nnArgs);

        // back into the recursion
        check = runOp(nnArgs, bill, call, dumper, parser, printer);
      }
      // if another option is followed
      else {
        // back into the recursion
        check = runOp(nArgs, bill, call, dumper, parser, printer);
      }
      if(check > 0) {
        System.err.println("-textFile option didn't work correctly");
        System.exit(1);
      }

      // Check file name and send it into the textOption method for parsing/dumping
      checkFilename(fileName);
      textOption(fileName, dumper, parser, bill, call);

      return 0;
    }

    // -pretty option
    else if(option.equals("-pretty")){
      int p = 0;    // Standard out option checker

      // Shrink args
      String[] nArgs = new String[args.length - 1];
      shrinkArray(args, nArgs);

      // File name is followed immediately
      if(args[1].charAt(0) != '-' && args[1].length() > 1) {
        // retrieve file name -> shrink array -> back into recursion
        fileName = args[1];
        String[] nnArgs = new String[nArgs.length - 1];
        shrinkArray(nArgs, nnArgs);
        check = runOp(nnArgs, bill, call, dumper, parser, printer);
      }
      // if it's standard output option
      else if(args[1].equals("-")){
        p = 1;      // set up flag

        // Shrink array and send it back to recursion
        String[] nnArgs = new String[nArgs.length - 1];
        shrinkArray(nArgs, nnArgs);
        check = runOp(nnArgs, bill, call, dumper, parser, printer);
      }
      // more options to be done
      else{
        check = runOp(nArgs, bill, call, dumper, parser, printer);
      }

      if(check > 0) {
        System.err.println("-pretty option didn't work correctly");
        System.exit(1);
      }

      // if std out flag is not set
      if(p == 0) {
        // Check file name and send it into prettyParse method for reading dumping
        checkFilename(fileName);
        prettyParse(fileName, printer, parser, bill, call);
      }
      // If std out flag is set
      else{
        // Call prettyPrint method for std out
        prettyPrint(bill);
      }

      return 0;
    }

    // No option just PhoneCall argument
    else if(args.length == 9){
      // Initialize PhoneBill with command line argument
      initBill(args, bill, call);

      return 0;
    }

    // Nothing then error
    else{
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    return 0;
  }

  /**
   * Shrink the array because option needs to be removed for the next recursion
   *
   * @param args
   *         Original argument array
   * @param nArgs
   *         Shrinked argument array
   */
  public static void shrinkArray(String[] args, String[] nArgs){
    // if original arg is null then error
    if(args == null){
      System.err.println("Argument is empty");
      System.exit(1);
    }

    // traverse and copy
    for(int i = 1; i < args.length; i++){
      nArgs[i-1] = args[i];
    }
  }

  /**
   * Initializing PhoneBill object with the PhoneCall object from command line argument
   *
   * @param args
   *         Command line arguments
   * @param bill
   *         PhoneBill object to be initialized
   * @param call
   *         PhoneCall object from args
   */
  public static void initBill(String[] args, PhoneBill bill, PhoneCall call){
    // Grab all the PhoneBill and PhoneCall arguments
    String[] customer = args[0].split(" ");
    String caller = args[1];
    String callee = args[2];
    String sDate = args[3];
    String sTime = args[4];
    String sAMPM = args[5];
    String eDate = args[6];
    String eTime = args[7];
    String eAMPM = args[8];

    // Date class for PhoneCall
    Date start = null;
    Date end = null;

    // Check validity of caller, callee, dates
    checkNumber(caller, "caller");
    checkNumber(callee, "callee");
    start = initDate(sDate, sTime, sAMPM);
    end = initDate(eDate, eTime, eAMPM);

    // Check if end time is latter than start time
    int order = start.compareTo(end);

    if(order > 0){
      System.err.println("Starting time is latter than ending time");
      System.exit(1);
    }

    // Sets PhoneBill's customer
    bill.setCustomer(customer);
    // Initialize PhoneCall object
    call.init(caller, callee, start, end);
    // Add PhoneCall to the PhoneBill Array list
    bill.addPhoneCall(call);
  }

  /**
   * Initializing only PhoneCall because it doesn't want Customer name
   *
   * @param args
   *         PhoneCall data from command line argument
   * @return
   *         Returns the initialized PhoneCall object
   */
  public static PhoneCall initCall(String[] args){
    // Grab the PhoneCall data from argument
    String caller = args[0];
    String callee = args[1];
    String sDate = args[2];
    String sTime = args[3];
    String sAMPM = args[4];
    String eDate = args[5];
    String eTime = args[6];
    String eAMPM = args[7];

    Date start = null;
    Date end = null;

    // Check validity
    checkNumber(caller, "caller");
    checkNumber(callee, "callee");
    start = initDate(sDate, sTime, sAMPM);
    end = initDate(eDate, eTime, eAMPM);

    // check start and end time order
    int order = start.compareTo(end);

    if(order > 0){
      System.err.println("Starting time is latter than ending time");
      System.exit(1);
    }

    // Initialize a new PhoneCall object and return it
    PhoneCall c = new PhoneCall();
    c.init(caller, callee, start, end);

    return c;
  }

  /**
   * Check if it's int or not
   *
   * @param input
   *         String for validating if it's int or not
   * @return
   *         return boolean for result
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
   * Use isInt for the whole array of String
   *
   * @param args
   *         Array of String to be validated
   * @param name
   *         name of the value we're testing
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
   * Check the caller/callee number's validity
   *
   * @param number
   *         String of number combination
   * @param name
   *         Name of the tested
   */
  public static void checkNumber(String number, String name){
    boolean numFormat;

    // Check if it's separated via -
    numFormat = number.contains("-");

    if(!numFormat){
      System.err.println("Incorrect " + name + " number format");
      System.exit(1);
    }

    // Split the number
    String[] call = number.split("-");

    // if it's not in 3 section then error
    if(call.length != 3){
      System.err.println("Incorrect " + name + " number format");
      System.exit(1);
    }

    // check if it's integer values
    checkInt(call, name);
  }

  /**
   * Check Date's validity
   *
   * @param date
   *         String of date for testing
   * @param name
   *         Name of the tested
   */
  public static void checkDate(String date, String name){
    boolean dateFormat;

    // Check if "/" is contained and if not then error
    dateFormat = date.contains("/");

    if(!dateFormat){
      System.err.println("Incorrect " + name + " date format");
      System.exit(1);
    }

    String[] args = date.split("/");
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
   * Check time value
   *
   * @param time
   *         String of time for testing
   * @param name
   *         Name of the tested
   */
  public static void checkTime(String time, String name){
    boolean timeFormat;

    // Check if ":" is contained and if not then error
    timeFormat = time.contains(":");

    if (!timeFormat) {
      System.err.println("Incorrect " + name + " time format");
      System.exit(1);
    }

    String[] args = time.split(":");

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
    if(hour < 0 || hour > 12){
      System.err.println("Incorrect " + name + " hour value!");
      System.exit(1);
    }

    // If Min is outside the bound then error
    if(min < 0 || min >= 60){
      System.err.println("Incorrect " + name + " minute value!");
      System.exit(1);
    }
  }

  // Check AM and PM
  public static void checkAMPM(String ampm, String name){
    // if it's not am or pm then error
    if(!ampm.equals("AM") && !ampm.equals("am") && !ampm.equals("pm") && !ampm.equals("PM")){
      System.err.println(name + " is not in am / AM / pm / PM format");
      System.exit(1);
    }
  }

  /**
   * Initialize the Date class object via argument
   *
   * @param day
   *         String of MM/DD/YYYY
   * @param hour
   *         String of HH:MM
   * @param ampm
   *         String of AM or PM
   * @return
   *         Return the initialized Date class object
   */
  public static Date initDate(String day, String hour, String ampm){
    Date date = null;

    // Check for error
    checkDate(day,  "date");
    checkTime(hour, "time");
    checkAMPM(ampm, "am/pm");

    String input = day + " " + hour + " " + ampm;

    // Using SimpleDateFormat Class to give format and parse through the String and initialize Date class
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    try{
      date = sdf.parse(input);
    }catch(ParseException e){
      System.err.println("Setting Date class was unsuccessful");
      System.exit(1);
    }

    return date;
  }


  /**
   * -textFile option
   *
   * 1. Create new file if it doesn't already exist
   * 2. Parse from the file and initialize PhoneBill
   * 3. Add the PhoneCall from command line argument into PhoneBill
   * 4. Dump all the PhoneBill data into the file
   *
   * @param fileName
   *         Name of the file accessing
   * @param dumper
   *         TextDumper class object for using dump method
   * @param parser
   *         TextParser class object for using parse method
   * @param bill
   *         PhoneBill class object for copying the retrieved AbstractPhoneBill data
   * @param call
   *         PhoneCall class object for grabbing command line argument
   */
  public static void textOption(String fileName, TextDumper dumper, TextParser parser, PhoneBill bill, PhoneCall call){
    try {
      // Open up the file
      File fl = new File(fileName);

      // Create new file
      boolean checker = fl.createNewFile();

      // If file didn't exist previously and has created new file
      if(checker){
        try {
          // Pass in the file name to the TextDumper
          dumper.setFl(fileName);

          // Dump all the information onto the file
          dumper.dump(bill);
        }catch(IOException e){
          // Catch the IOException thrown from the dump method
          System.err.println("Creating new file and writing has failed");
          System.exit(1);
        }
      }
      // If the text file already exist with that name
      else{
        // If the file exists but empty then error
        if(fl.length() == 0){
          System.err.println("Nothing in the file. Invalid file.");
          System.exit(1);
        }

        try {
          // Set up the parser with the file name
          parser.setFl(fileName);

          // Retrieve the AbstractPhoneBill returned by file parsing
          AbstractPhoneBill getBill = parser.parse();

          // If text file's customer and command line's customer does not match then error
          if(!getBill.getCustomer().equals(bill.getCustomer())){
            System.err.println("Input Phone Bill customer name does not match the file's customer name");
            System.exit(1);
          }

          // Check if the command line's PhoneCall already exist
          checkCallDupli(getBill.getPhoneCalls(), call);

          // Add the command line's PhoneCall to the retrieved PhoneBill
          getBill.addPhoneCall(call);

          // Sort out
          Collections.sort((ArrayList)getBill.getPhoneCalls());

          // Copy the PhoneBill so that next recursion can also use it
          bill.setCustomer(getBill.getCustomer().split(" "));
          bill.setPhoneCalls(getBill.getPhoneCalls());

          // Catch IOException because it's dump not parse
          try {
            // Set up the dumper with the file name and dump
            dumper.setFl(fileName);
            dumper.dump(getBill);
          }catch(IOException e){
            System.err.println("Writing onto file failed after reading from " + fileName);
            System.exit(1);
          }
        }catch(ParserException e){          // If Parsing was invalid
          System.err.println("Couldn't parse the " + fileName + " file");
          System.exit(1);
        }
      }
    }catch(IOException e){      // If initial file I/O was a failure
      System.err.println("File I/O has failed");
      System.exit(1);
    }
  }

  /**
   * Check if the certain PhoneCall object already exist in the list
   *
   * @param calls
   *         PhoneCall class object checking for duplication
   * @param calling
   *         List we're checking from
   */
  public static void checkCallDupli(Collection<PhoneCall> calls, PhoneCall calling){
    Iterator<PhoneCall> iter = calls.iterator();      // Set up Iterator to go through all the PhoneCalls

    // Keep going until no object is left
    while(iter.hasNext()){
      PhoneCall obj = iter.next();

      // If current iteration of PhoneCall is exactly same as PhoneCall from command line, then send out error
      if(obj.getCaller().equals(calling.getCaller()) && obj.getCallee().equals(calling.getCallee())){
        if(obj.getEndTimeString().equals(calling.getEndTimeString()) && obj.getStartTimeString().equals(calling.getStartTimeString())){
          System.err.println("Inputted Phone Call already exists");
          System.exit(1);
        }
      }
    }
  }

  /**
   * Checking duplication for pretty print because it doesn't System.exit
   *
   * @param calls
   *         PhoneCall class object checking for duplication
   * @param calling
   *         List we're checking from
   * @return
   *         Return 0 or 1 for result
   */
  public static int checkPrettyCallDupli(Collection<PhoneCall> calls, PhoneCall calling){
    Iterator<PhoneCall> iter = calls.iterator();      // Set up Iterator to go through all the PhoneCalls

    // Keep going until no object is left
    while(iter.hasNext()){
      PhoneCall obj = iter.next();

      // If current iteration of PhoneCall is exactly same as PhoneCall from command line, then send out error
      if(obj.getCaller().equals(calling.getCaller()) && obj.getCallee().equals(calling.getCallee())){
        if(obj.getEndTimeString().equals(calling.getEndTimeString()) && obj.getStartTimeString().equals(calling.getStartTimeString())){
          return 1;
        }
      }
    }

    return 0;
  }

  /**
   * Check if file name is valid
   *
   * @param fileName
   *         File name from command line argument
   */
  public static void checkFilename(String fileName){
    // if the name is empty
    if(fileName == null){
      System.err.println("File name is empty");
      System.exit(1);
    }

    // if name doesn't have .txt extension
    if(!fileName.endsWith(".txt")){
      System.err.println("Incorrect File Name. Does not contain .txt format");
      System.exit(1);
    }
  }

  /**
   * -pretty option
   *
   * 1. Parse via TextParser class
   * 2. Check if duplicate
   *      - if duplicate then don't add PhoneCall
   *      - if not then add PhoneCall
   * 3. Sort the parsed PhoneBill's PhoneCall list
   * 4. Dump it with PrettyPrinter class's dump method
   *
   * @param fileName
   *         File name from command line argument
   * @param printer
   *         PrettyPrinter class object for dump method
   * @param parser
   *         TextParser class object for parse method
   * @param bill
   *         PhoneBill class initialized from previous recursion or to set up for next recursion
   * @param call
   *         PhoneCall class initialized from command line
   */
  public static void prettyParse(String fileName, PrettyPrinter printer, TextParser parser, PhoneBill bill, PhoneCall call){
    try{
      // Parse the file
      parser.setFl(fileName);
      AbstractPhoneBill getBill = parser.parse();

      // If the customer name doesn't match then error
      if(!getBill.getCustomer().equals(bill.getCustomer())){
        System.err.println("Input PhoneBill customer name does not match the file's customer name");
        System.exit(1);
      }

      // Check duplicate
      int ch = checkPrettyCallDupli(getBill.getPhoneCalls(), call);

      if(ch == 0) {
        getBill.addPhoneCall(call);
      }

      // Copy the parsed PhoneBill data for next recursion
      bill.setCustomer(getBill.getCustomer().split(" "));
      bill.setPhoneCalls(getBill.getPhoneCalls());

      // sort order
      Collections.sort((ArrayList)getBill.getPhoneCalls());

      try{
        // use pretty printer's dump
        printer.dump(getBill);
      }
      catch(IOException e){
        System.err.println("Pretty Print's dumping failed");
        System.exit(1);
      }

    }catch(ParserException e){
      System.err.println("Pretty Print's file reading failed");
      System.exit(1);
    }
  }

  /**
   * Standard out version of the Pretty Printer when the file name is "-"
   *
   * @param bill
   *         PhoneBill data for printing out
   */
  public static void prettyPrint(PhoneBill bill){
    int n = 1;        // Printing index
    String output = "\nPhone bill records for ";        // String field for gathering all the std output data

    // set up for iteration
    Collection<PhoneCall> calls = bill.getPhoneCalls();

    Iterator<PhoneCall> iter = calls.iterator();

    output += "Customer: " + bill.getCustomer() + "\n\n";
    output += "******************** List of Calls *******************\n\n";

    // Loop through and gather all the PhoneCall information
    while (iter.hasNext()) {
      PhoneCall obj = iter.next();

      output += n + ".\n\n";
      output += "Caller #: " + obj.getCaller() + "\n";
      output += "Callee #: " + obj.getCallee() + "\n";
      output += "Start of the call: " + obj.getStartTimeString() + "\n";
      output += "End of the call: " + obj.getEndTimeString() + "\n";

      long dur = obj.getEndTime().getTime() - obj.getStartTime().getTime();
      dur /= 60000;

      output += "Duration of the call: " + dur + " min\n";
      output += "\n******************************************************\n\n";
      n++;
    }

    // Print
    System.out.println(output);
  }
}
