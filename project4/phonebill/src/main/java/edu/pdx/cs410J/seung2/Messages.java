package edu.pdx.cs410J.seung2;

import javafx.css.Match;

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
    public static String formatCallCount(int count )
    {
        return String.format( "%d PhoneCalls in the list", count );
    }

    public static String formatPrettyCall(PhoneCall call)
    {
        String format = "";

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

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String allPhoneCallListDeleted() {
        return "All PhoneCall entries have been deleted";
    }

    public static void formatPrettyBill(PrintWriter pw, Collection<PhoneCall> calls) {
        pw.println(Messages.formatCallCount(calls.size()));

        for (PhoneCall call : calls) {
            pw.println(Messages.formatPrettyCall(call));
        }
    }

    public static Collection<PhoneCall> parsePhoneBill(String content) {
        PhoneBill bill = new PhoneBill();

        String[] lines = content.split("\n");

        String[] feeder = new String[4];

        for (int i = 1; i < lines.length; i+=9) {
            feeder[0] = lines[i];
            feeder[1] = lines[i+1];
            feeder[2] = lines[i+2];
            feeder[3] = lines[i+3];

            PhoneCall tmp = parsePhoneCall(feeder);

            bill.addPhoneCall(tmp);
        }

        return bill.getPhoneCalls();
    }

    public static PhoneCall parsePhoneCall(String[] content){
        PhoneCall call = null;

        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
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

        Project4.checkNumber(caller, "caller");
        Project4.checkNumber(callee, "callee");
        String[] starter = startT.split(" ");
        Date start = Project4.initDate(starter[0], starter[1], starter[2]);
        String[] ender = endT.split(" ");
        Date end = Project4.initDate(ender[0], ender[1], ender[2]);

        call = new PhoneCall(caller, callee, start, end);

        return call;
    }

}
