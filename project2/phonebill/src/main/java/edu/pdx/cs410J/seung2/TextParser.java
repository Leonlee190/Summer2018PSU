package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

public class TextParser implements PhoneBillParser{
    private String fl;

    public void setFl(String fileName){
        fl = fileName;
    }

    @Override
    public AbstractPhoneBill parse() throws ParserException {
        PhoneBill bill = new PhoneBill();

        try {
            File f = new File(fl);
            FileReader fr = new FileReader(f);
            BufferedReader in = new BufferedReader(fr);

            String st;
            st = in.readLine();

            if(st == null){
                System.err.println("Invalid file format for reading");
                System.exit(1);
            }

            String[] name = st.split(" ");
            bill.setCustomer(name);

            while((st = in.readLine()) != null){
                Project2.checkNumberFormat(st, "caller");
                String[] args = new String[6];
                args[0] = st;

                st = in.readLine();
                checkNull(st, "callee");
                Project2.checkNumberFormat(st, "callee");
                args[1] = st;

                st = in.readLine();
                checkNull(st, "start time");
                String[] Start = st.split(" ");
                args[2] = Start[0];
                Project2.checkDateFormat(args[2], "start date");
                args[3] = Start[1];
                Project2.checkTimeFormat(args[3], "start time");

                st = in.readLine();
                checkNull(st, "end time");
                String[] End = st.split(" ");
                args[4] = End[0];
                Project2.checkDateFormat(args[4], "end date");
                args[5] = End[1];
                Project2.checkTimeFormat(args[5], "end time");

                PhoneCall ca = new PhoneCall();
                Project2.callInput(ca, args,-1);
                Project2.addCalls(bill, ca);
            }
        }catch(IOException e){
            System.err.println("Unable to read from " + fl);
            System.exit(1);
        }

        return bill;
    }

    public void checkNull(String str, String name){
        if(str == null){
            System.err.println("Missing " + name + " in the file");
            System.exit(1);
        }
    }
}
