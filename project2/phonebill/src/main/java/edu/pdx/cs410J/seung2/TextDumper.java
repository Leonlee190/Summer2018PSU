package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TextDumper implements PhoneBillDumper{
    private String fl;

    public void setFl(String fileName){
        fl = fileName;
    }

    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        File f = new File(fl);
        FileWriter fw = new FileWriter(f);
        BufferedWriter out = new BufferedWriter(fw);

        Collection<PhoneCall> calls = abstractPhoneBill.getPhoneCalls();

        Iterator<PhoneCall> iter = calls.iterator();

        out.write(abstractPhoneBill.getCustomer() + "\r\n");

        while(iter.hasNext()) {
            PhoneCall obj = iter.next();
            out.write(obj.getCaller() + "\r\n");
            out.write(obj.getCallee() + "\r\n");
            out.write(obj.getStartTimeString() + "\r\n");
            out.write(obj.getEndTimeString() + "\r\n");
        }

        out.close();

    }
}
