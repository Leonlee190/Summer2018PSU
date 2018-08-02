package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static String README = "\nCourse: CS 401J\nProject 1: Designing a Phone Bill Application\nProgrammer: SeungJun Lee" +
            "\nDescription: This project parses the user's command line arguments and initialize PhoneCall class and PhoneBill class or " +
            "executes options given by the user.\n             PhoneBill will store the customer's name and collection of PhoneCall data." +
            "\n             PhoneCall will store caller and callee's number and starting and ending date and time.\n\nUsage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n\n" +
            "Arguments are in this order: with example format\n" +
            "   - Customer: \"First Last\" or \"First Middle Last\"\n   - Caller Number: XXX-XXX-XXXX\n   - Callee Number: XXX-XXX-XXXX\n   - Start Time: MM/DD/YYYY HH:MM\n   - End Time: MM/DD/YYYY HH:MM" +
            "\n\nOptions:\n    -print : Prints a description of the new phone call\n          -> If no information has been provided with print function then it will print error" +
            "\n    -README : Prints a README for this project and exits\n          -> Which you have currently!";

    public static final String MISSING_ARGS = "Missing command line arguments";
    public static String hostName = null;
    public static String portNum = null;
    static int print = 0;
    static int search = 0;
    static String name = null;

    public static void main(String... args) {
        PhoneBill bill = null;
        PhoneCall call = null;
        Date start = null;
        Date end = null;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-README")){
                System.out.println(README);
                System.exit(0);
            }
            else if(args[i].equals("-print")){
                print = 1;
            }
            else if(args[i].equals("-host")){
                hostName = args[i + 1];
                i++;
            }
            else if(args[i].equals("-port")){
                portNum = args[i + 1];
                boolean intCheck = isInt(portNum);

                if(!intCheck){
                    System.err.println("Port number isn't an integer");
                    System.exit(1);
                }

                if(args.length == (i+3)){
                    name = args[i+2];
                    break;
                }

                i++;
            }
            else if(args[i].equals("-search")){
                search = 1;
            }
            else if((args.length - i) == 7){
                name = args[i];
                String sDate = args[i+1];
                String sTime = args[i+2];
                String sAP = args[i+3];
                String eDate = args[i+4];
                String eTime = args[i+5];
                String eAP = args[i+6];

                start = initDate(sDate, sTime, sAP);
                end = initDate(eDate, eTime, eAP);
                checkTimeOrder(start, end);

                bill = new PhoneBill();
                bill.setCustomer(name.split(" "));

                break;
            }
            else if((args.length - i) == 9){
                name = args[i];
                String caller = args[i+1];
                String callee = args[i+2];
                String sDate = args[i+3];
                String sTime = args[i+4];
                String sAP = args[i+5];
                String eDate = args[i+6];
                String eTime = args[i+7];
                String eAP = args[i+8];

                start = initDate(sDate, sTime, sAP);
                end = initDate(eDate, eTime, eAP);
                checkTimeOrder(start, end);

                call = new PhoneCall(caller, callee, start, end);
                bill = new PhoneBill();
                bill.setCustomer(name.split(" "));

                break;
            }
            else{
                System.out.println(MISSING_ARGS);
                System.exit(1);
            }
        }

        if(print == 1){
            System.out.println(call.toString());
        }

        int port = Integer.parseInt(portNum);

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        try {
            if(call != null) {
                client.addPhoneCall(bill.getCustomer(), call);
            }
            else{
                if(search == 0) {
                    Collection<PhoneCall> temp = client.getAllPhoneCalls(name);
                    StringWriter sw = new StringWriter();
                    Messages.formatPrettyBill(new PrintWriter(sw, true), "All list for " + name,temp);
                    String msg = sw.toString();
                    System.out.println(msg);
                }
                else{
                    Collection<PhoneCall> temp = client.getSearchCalls(bill.getCustomer(), start, end);
                    StringWriter sw = new StringWriter();
                    Messages.formatPrettyBill(new PrintWriter(sw, true), "Searched list for " + name,temp);
                    String msg = sw.toString();
                    System.out.println(msg);
                }
            }
        }catch(IOException e){
            System.err.println("Connection Error!");
            System.exit(1);
        }

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
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

    /**
     * Check AMPM format of command line argument
     *
     * @param ampm
     *         String argument from command line
     * @param name
     *         Name of the tested
     */
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
        checkInt(day.split("/"), "date");
        checkDate(day,  "date");
        checkInt(hour.split(":"), "time");
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

    public static void checkTimeOrder(Date start, Date end){
        if(start.getTime() > end.getTime()){
            System.err.println("Start time starts latter than end time");
            System.exit(1);
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
}