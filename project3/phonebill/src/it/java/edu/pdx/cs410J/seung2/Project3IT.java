package edu.pdx.cs410J.seung2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
    /*
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain("");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }
  */
    /**
     * Tests that invoking the -README option corrrectly works
     */
    /*
    @Test
    public void dashReadmeOptionPrintsOnlyReadme() {
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(Project3.README + "\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
    }
  */

    /**
     * Tests that invoking -print option with PhoneBill and PhoneCall information correctly initializes both
     */
    /*
    @Test
    public void dashPrintOptionsPrintsNewlyCreatedPhoneCall() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        MainMethodResult result =
                invokeMain("-print", "My Customer", caller, callee, startDate, startTime, endDate, endTime);

        assertThat(result.getExitCode(), equalTo(0));
        String phoneCallToString = String.format("Phone call from %s to %s from %s %s to %s %s",
                caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getTextWrittenToStandardOut(), equalTo(phoneCallToString + "\n"));
    }
  */

    /**
     * Provided testing by the professor
     */
    /*
    @Test
    public void validCommandLineWithNoDashPrintOptionPrintsNothingToStandardOut() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);

        String phoneCallToString = String.format("Phone call from %s to %s from %s %s to %s %s",
                caller, callee, startDate, startTime, endDate, endTime);

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(phoneCallToString + "\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

    }
  */

    /**
     * Testing starting/ending date formatting
     *  - Not using "-"
     *  - Incorrect input digits for month, date, and year
     *  - Non integer
     *  - More than three input for date
     */
    /*
    @Test
    public void incorrectDateForm(){
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07-04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        // Testing incorrect date format "/" instead of "-"
        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing incorrect year length - 3 instead of 4
        startDate = "07/04/218";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing incorrect month digit - 3 instead of 2
        startDate = "004/04/2018";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing incorrect date digit - 3 instead of 2
        startDate = "07/104/2018";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing non-integer input
        startDate = "07/04aa/2018";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing more input
        startDate = "07/04/2018/22";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
    }
  */

    /**
     * Testing caller/callee number formatting
     *  - Not using "-"
     *  - Non integer
     *  - Four dashes with information instead of three dashes
     */
    /*
    @Test
    public void incorrectNumberForm(){
        String caller = "123.456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        // Testing incorrect number format - "." instead of "-"
        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing non-integer input
        caller = "123-45d-4555";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing phone number that has more than three sections
        caller = "123-453-4555-222";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
    }
  */

    /**
     * Testing starting/ending time formatting
     *  - Not using ":"
     *  - Incorrect input digits for hour and min
     *  - Non integer
     *  - More input than hour and min value - "07:06:03"
     */
    /*
    @Test
    public void incorrectTimeForm(){
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6-24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        // Testing incorrect time format - "-" instead of ":"
        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing incorrect hour digit - 3 instead of less than 3
        startTime = "006:24";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing incorrect min digit - 3 instead of less than 3
        startTime = "06:024";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing non-integer input
        startTime = "06:a24";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        // Testing more than hour and min input
        startTime = "06:24:22";
        result = invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
    }
  */

    /*
    @Test
    public void incorrectFileInput(){
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        MainMethodResult result =
                invokeMain("-textFile", "leon.txt", "My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(0));

        result = invokeMain("-textfile", "leon.txt", "My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        result = invokeMain("-textFile", "hey.txt", "leon.txt", "My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));

        result = invokeMain("-textFile", "My Customer", caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
    }
  */
}
