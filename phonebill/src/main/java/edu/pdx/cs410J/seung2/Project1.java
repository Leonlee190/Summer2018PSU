package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.lang.Object;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    for (String arg : args) {
      System.out.println(arg);
    }

    if(args.length == 7){
      System.out.println("\nTesting");
      String delims = "[ //-]";
      String[] name = args[0].split(delims);
      String[] caller = args[1].split(delims);
      String[] callee = args[2].split(delims);
      String[] start = args[3].split(delims);
      String[] startTime = args[4].split(":");
      String[] end = args[5].split(delims);
      String[] endTime = args[6].split(":");

      for(String arg : name){
        System.out.println(arg);
      }
      for(String arg : caller){
        System.out.println(arg);
      }
      for(String arg : callee){
        System.out.println(arg);
      }
      for(String arg : start){
        System.out.println(arg);
      }
      for(String arg : startTime){
        System.out.println(arg);
      }
      for(String arg : end){
        System.out.println(arg);
      }
      for(String arg : endTime){
        System.out.println(arg);
      }
    }
    else{
      System.err.println("Missing command line arguments");
    }

    System.exit(1);
  }

  public void checkName(String caller){
    if(caller.length > 3 || caller.length < 0){
      System.err.println("Invalid caller name");
    }

    if(caller.length == 1){
      System.err.println("Missing First or Last name");
    }
  }

  public void checkNum(String number){

  }

}
