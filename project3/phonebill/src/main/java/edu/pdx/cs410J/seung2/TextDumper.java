package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Takes in an AbstractPhoneBill and write down all the PhoneBill and collection of PhoneCall information onto text file.
 */
public class TextDumper implements PhoneBillDumper{
    private String fl;

    /**
     * Setting the file name to write on it
     *
     * @param fileName
     *         File name passed on from command line argument
     */
    public void setFl(String fileName){
        fl = fileName;
    }

    /**
     * Open up the file and write all the PhoneBill and PhoneCall data onto that file
     *
     * @param abstractPhoneBill
     *         PhoneBill and collection of PhoneCall information.
     *
     * @throws IOException
     *           If writing on the file was not valid then throw IOException
     */
    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        File f = new File(fl);                          // Open up the file
        FileWriter fw = new FileWriter(f);              // Prep for writer
        BufferedWriter out = new BufferedWriter(fw);    // Set up the BufferedWriter

        // Retrieve the Collection of PhoneCall
        Collection<PhoneCall> calls = abstractPhoneBill.getPhoneCalls();

        // Use Iterator with PhoneCall object to go through the ArrayList
        Iterator<PhoneCall> iter = calls.iterator();

        // Write the customer's name on the top of the file
        out.write(abstractPhoneBill.getCustomer() + "\n");

        // Iterate through the list
        while(iter.hasNext()) {
            // Set up the object to get it's information
            PhoneCall obj = iter.next();

            // Write out the caller, callee, start time, end time
            out.write(obj.getCaller() + "\n");
            out.write(obj.getCallee() + "\n");
            out.write(obj.getStartTimeString() + "\n");
            out.write(obj.getEndTimeString() + "\n");
        }

        // Close the file
        out.close();
    }
}
