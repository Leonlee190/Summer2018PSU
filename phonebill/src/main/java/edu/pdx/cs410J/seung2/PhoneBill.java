package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;
    private Collection<PhoneCall> calls = new ArrayList<>();

    public void setCustomer(String[] args){
        this.customer = args[0];

        for(int i = 1; i < args.length; i++){
            this.customer += " " + args[i];
        }
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        boolean checker = this.calls.add(phoneCall);

        if(!checker){
            System.err.println("Adding phonecall to the phonebill was unsuccessful");
            System.exit(1);
        }
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
