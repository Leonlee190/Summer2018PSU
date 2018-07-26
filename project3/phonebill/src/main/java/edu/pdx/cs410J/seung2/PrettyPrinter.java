package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * PrettyPrinter class implements PhoneBillDumper
 * Create designated file "BillPrint.txt" and dump out the PhoneBill data
 */
public class PrettyPrinter implements PhoneBillDumper{
    /**
     * overriding dump method from PhoneBillDumper interface
     *
     * @param abstractPhoneBill
     *         PhoneBill class object to be dumped from
     * @throws IOException
     *           If IO operation goes wrong then catch it and send out error
     */
    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        int i = 1;              // Number for index
        try {
            // Set up for writing and create "BillPrint.txt" for dumping
            File f = new File(Project3.pfileName);
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);

            // Retrieve the ArrayList of calls for iterating
            Collection<PhoneCall> calls = abstractPhoneBill.getPhoneCalls();

            // Setting up Iterator object
            Iterator<PhoneCall> iter = calls.iterator();

            // Write down the customer information
            out.write("Phone bill records for ");
            out.write("Customer: " + abstractPhoneBill.getCustomer() + "\n\n");
            out.write("******************** List of Calls *******************\n\n");

            // Loop through and write out the PhoneCall data
            while (iter.hasNext()) {
                // Pick up the current PhoneCall object
                PhoneCall obj = iter.next();

                // Write down PhoneCall data
                out.write(i + ".\n\n");
                out.write("Caller #: " + obj.getCaller() + "\n");
                out.write("Callee #: " + obj.getCallee() + "\n");
                out.write("Start of the call: " + obj.getStartTimeString() + "\n");
                out.write("End of the call: " + obj.getEndTimeString() + "\n");

                // Calculate the call duration
                long dur = obj.getEndTime().getTime() - obj.getStartTime().getTime();
                dur /= 60000;

                out.write("Duration of the call: " + dur + " min\n");
                out.write("\n******************************************************\n\n");
                i++;
            }

            // Close the file before exiting
            out.close();

        }catch(IOException e){
            System.err.println("Unable to execute writing on BillPrint.txt");
            System.exit(1);
        }
    }
}
