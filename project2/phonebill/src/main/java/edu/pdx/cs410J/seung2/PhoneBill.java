package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * PhoneBill class that stores and returns customer name and collection of PhoneCall class
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;                                        // Stores customer's name
    private Collection<PhoneCall> calls = new ArrayList<>();        // Array list of PhoneCall classes

    /**
     * Parses and sets the customer's name
     *
     * @param args
     *         Array of String that was parsed from command line argument
     */
    public void setCustomer(String[] args){
        // Get the first name
        this.customer = args[0];

        // Get rest of the name
        for(int i = 1; i < args.length; i++){
            this.customer += " " + args[i];
        }
    }

    /**
     * Returns the customer's name
     *
     * @return
     *          Returns the customer's name in String form
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Add single PhoneCall class object into the collection
     *
     * @param phoneCall
     *         PhoneCall class object initialized in the Project1's main
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        // Check if the adding is correctly done
        boolean checker = this.calls.add(phoneCall);

        // If not added correctly then error
        if(!checker){
            System.err.println("Adding phonecall to the phonebill was unsuccessful");
            System.exit(1);
        }
    }

    /**
     * Returns the PhoneCall class collection
     *
      * @return
     *           PhoneCall collection stored in the PhoneBill class
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
