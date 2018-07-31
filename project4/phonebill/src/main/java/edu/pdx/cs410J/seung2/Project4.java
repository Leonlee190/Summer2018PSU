package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        PhoneBill bill = null;
        PhoneCall call = null;
        String first = args[0];
        String hostName = args[2];
        String portNum = args[4];

        if(first.equals("abc")) {
            String name = args[5];
            String caller = args[6];
            String callee = args[7];
            String sDate = args[8];
            String sTime = args[9];
            String sAP = args[10];
            String eDate = args[11];
            String eTime = args[12];
            String eAP = args[13];

            Date start = initDate(sDate, sTime, sAP);
            Date end = initDate(eDate, eTime, eAP);

            call = new PhoneCall(caller, callee, start, end);

            bill = new PhoneBill();
            bill.setCustomer(name.split(" "));
        }

        int port = Integer.parseInt(portNum);

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        try {
            if(first.equals("abc")) {
                client.addPhoneCall(bill.getCustomer(), call);
            }
            else{
                Collection<PhoneCall> temp = client.getAllPhoneCalls();
                StringWriter sw = new StringWriter();
                Messages.formatPrettyBill(new PrintWriter(sw, true), temp);
                String msg = sw.toString();
                System.out.println(msg);
            }
        }catch(IOException e){
            e.printStackTrace();
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

}