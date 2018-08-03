package edu.pdx.cs410J.seung2;

import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * prints out the size and the name of the call list
     *
     * @param count
     *          Size of the call list
     * @param name
     *          Name of the customer
     * @return
     *          String for the PrintWriter to print
     */
    public static String formatCallCount(int count, String name)
    {
        return String.format( "%s has %d PhoneCalls in the list\n", name, count);
    }

    /**
     * Prints out the PhoneCall in Pretty format
     *
     * @param call
     *          Call passed in for it to be printed
     * @return
     *          String that contains all the pretty print
     */
    public static String formatPrettyCall(PhoneCall call)
    {
        String format = "";

        // Calculate the duration of the call
        long duration = call.getEndTime().getTime() - call.getStartTime().getTime();
        duration /= 60000;

        format += "Caller #: " + call.getCaller() + "\n";
        format += "Callee #: " + call.getCallee() + "\n";
        format += "Start Time: " + call.getStartTimeString() + "\n";
        format += "End Time: " + call.getEndTimeString() + "\n";
        format += "Duration: " + duration + " min\n";
        format += "\n****************************************************\n\n";

        return format;
    }

    /**
     * If parameter error happens then send out the string message
     *
     * @param parameterName
     *          Name of the missing parameter
     * @return
     *          String that has the parameter name and the phrase
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Notification string for deletion of the list
     *
     * @return
     *          String that contains the deletion message
     */
    public static String allPhoneCallListDeleted() {
        return "All PhoneCall entries have been deleted";
    }

    /**
     * Pretty print the whole PhoneBill
     *
     * @param pw
     *          PrintWriter class to write it out to the client
     * @param name
     *          Name of the customer
     * @param calls
     *          PhoneCall list that needs to be printed
     */
    public static void formatPrettyBill(PrintWriter pw, String name, Collection<PhoneCall> calls) {
        pw.println(Messages.formatCallCount(calls.size(), name));

        // traverse and individually print via using formatPrettyCall
        for (PhoneCall call : calls) {
            pw.println(Messages.formatPrettyCall(call));
        }
    }

    /**
     * Parse through the PhoneBill which was printed by server
     *
     * @param content
     *          String form of content which was retrieved via server
     * @return
     *          PhoneBill's Call list after parse
     */
    public static Collection<PhoneCall> parsePhoneBill(String content) {
        PhoneBill bill = new PhoneBill();

        // Break by line
        String[] lines = content.split("\n");

        // Four inputs
        // 1. Caller
        // 2. Callee
        // 3. Start time
        // 3. End time
        String[] feeder = new String[4];

        // Traverse through the String list and parse
        // then initialize by using parsePhoneCall
        for (int i = 2; i < lines.length; i+=9) {
            feeder[0] = lines[i];
            feeder[1] = lines[i+1];
            feeder[2] = lines[i+2];
            feeder[3] = lines[i+3];

            PhoneCall tmp = parsePhoneCall(feeder);

            bill.addPhoneCall(tmp);
        }

        return bill.getPhoneCalls();
    }

    /**
     * Initialize PhoneCall by parsing through the input parsed by parsePhoneBill method
     *
     * @param content
     *          String parsed by parsePhoneBill method
     * @return
     *          PhoneCall initialized by this method
     */
    public static PhoneCall parsePhoneCall(String[] content){
        PhoneCall call = null;

        // Pattern match it via Regular Expression
        // All we need is the data after the ":" character
        Pattern pattern = Pattern.compile("\\s*(.*): (.*)");
        Matcher matcher1 = pattern.matcher(content[0]);
        if(!matcher1.find()){
            return null;
        }
        String caller = matcher1.group(2).trim();

        Matcher matcher2 = pattern.matcher(content[1]);
        if(!matcher2.find()){
            return null;
        }
        String callee = matcher2.group(2).trim();

        Matcher matcher3 = pattern.matcher(content[2]);
        if(!matcher3.find()){
            return null;
        }
        String startT = matcher3.group(2).trim();

        Matcher matcher4 = pattern.matcher(content[3]);
        if(!matcher4.find()){
            return null;
        }
        String endT = matcher4.group(2).trim();

        // Check error for initializing PhoneCall
        Project4.checkNumber(caller, "caller");
        Project4.checkNumber(callee, "callee");
        String[] starter = startT.split(" ");
        Date start = Project4.initDate(starter[0], starter[1], starter[2]);
        String[] ender = endT.split(" ");
        Date end = Project4.initDate(ender[0], ender[1], ender[2]);

        // Initialize and return
        call = new PhoneCall(caller, callee, start, end);

        return call;
    }

    /**
     * Parses the Search version
     *
     * @param content
     *          String returned via server
     * @param start
     *          Start time required for search option
     * @param end
     *          End time required for search option
     * @return
     *          Collection of PhoneCall after search comparison
     */
    public static Collection<PhoneCall> parseSearchCalls(String content, Date start, Date end){
        PhoneBill bill = new PhoneBill();

        String[] lines = content.split("\n");

        String[] feeder = new String[4];

        for (int i = 2; i < lines.length; i+=9) {
            feeder[0] = lines[i];
            feeder[1] = lines[i+1];
            feeder[2] = lines[i+2];
            feeder[3] = lines[i+3];

            PhoneCall tmp = parsePhoneCall(feeder);

            // Compare the start and end time for searching option
            if((tmp.getStartTime().getTime() >= start.getTime()) && (tmp.getEndTime().getTime() <= end.getTime())){
                bill.addPhoneCall(tmp);
            }
        }

        return bill.getPhoneCalls();
    }
}
