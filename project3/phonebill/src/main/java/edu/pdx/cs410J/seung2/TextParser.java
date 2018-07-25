package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


/**
 * TextParser reads from a file and initialize PhoneBill and collection of PhoneCall.
 * Check for validity of the text file's information as it initializes PhoneBill.
 * Create and return the PhoneBill
 */
public class TextParser implements PhoneBillParser{
    private String fl;

    /**
     * Setting up the file name to open for reading
     *
     * @param fileName
     *         File name passed in from command line argument
     */
    public void setFl(String fileName){
        fl = fileName;
    }

    /**
     * Read and parse from the file to create PhoneBill and its PhoneCalls
     *
     * @return bill
     *          Return the PhoneBill class created from the parsing
     *
     * @throws ParserException
     *          When reading has failed then throw ParserException
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        PhoneBill bill = new PhoneBill(); // Set up the PhoneBill for initializing and returning
        try {
            // Set up the BufferedReader for parsing
            File f = new File(fl);
            FileReader fr = new FileReader(f);
            BufferedReader in = new BufferedReader(fr);

            // Get the first line which is the customer's name
            String st;
            st = in.readLine();

            // If first line is null then get out
            if(st == null){
                System.err.println("Invalid file format for reading");
                System.exit(1);
            }

            // Split it into String array and use PhoneBill's setCustomer method
            String[] name = st.split(" ");
            bill.setCustomer(name);

            // Read until empty line
            while((st = in.readLine()) != null){
                // Since while statement read a line, check validity of caller number
                String[] args = new String[8];
                // Put it into the argument String array in order
                args[0] = st;

                // Read another line and do check it for callee format
                st = in.readLine();
                checkNull(st, "callee");
                args[1] = st;

                // Read another line and split them and check for Date and Time format
                st = in.readLine();
                checkNull(st, "start time");
                String[] Start = st.split(" ");
                args[2] = Start[0];
                args[3] = Start[1];
                args[4] = Start[2];

                // Read another line and split them and check for Date and Time format
                st = in.readLine();
                checkNull(st, "end time");
                String[] End = st.split(" ");
                args[5] = End[0];
                args[6] = End[1];
                args[7] = End[2];

                // Create PhoneCall -> initialize it -> add it to PhoneBill
                PhoneCall ca = Project3.initCall(args);

                bill.addPhoneCall(ca);
            }
        }catch(IOException e){          // If anything wrong with IO handling
            System.err.println("Unable to read from " + fl);
            System.exit(1);
        }

        // Return initialized PhoneBill
        return bill;
    }

    /**
     * Check if the String that's been read is null or not
     *
     * @param str
     *         Most recently read String from file
     *
     * @param name
     *         Name to print for error
     */
    public void checkNull(String str, String name){
        if(str == null){
            System.err.println("Missing " + name + " in the file");
            System.exit(1);
        }
    }
}
