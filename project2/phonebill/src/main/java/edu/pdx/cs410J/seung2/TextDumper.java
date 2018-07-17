package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

        out.write(abstractPhoneBill.getCustomer());

        System.out.println(calls.iterator().next().getCaller());
        System.out.println(calls.iterator().next().getCallee());
        System.out.println(calls.iterator().next().getStartTimeString());
        System.out.println(calls.iterator().next().getEndTimeString());

        out.close();

    }
}
