package edu.pdx.cs410J.seung2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static PhoneBill bill = new PhoneBill();

    private static String CUSTOMER_PARA = "customer";
    private static String CALLER_PARA = "caller";
    private static String CALLEE_PARA = "callee";
    private static String START_PARA = "startTime";
    private static String END_PARA = "endTime";

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customerName = getParameter(CUSTOMER_PARA, request );
        String startT = getParameter(START_PARA, request);
        String endT = getParameter(END_PARA, request);

        if(startT == null && endT == null) {
            writeAllPhoneBillPretty(response);
        }
        else if(startT != null && endT != null){
            writeSearchPhoneBillPretty(startT, endT, response);
        }
        else{
            missingRequiredParameter(response, "one of dates");
            return;
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customerName = getParameter(CUSTOMER_PARA, request );
        if (customerName == null) {
            missingRequiredParameter(response, CUSTOMER_PARA);
            return;
        }

        String callerNum = getParameter(CALLER_PARA, request );
        if ( callerNum == null) {
            missingRequiredParameter( response, CALLER_PARA );
            return;
        }

        String calleeNum = getParameter(CALLEE_PARA, request );
        if ( calleeNum == null) {
            missingRequiredParameter(response, CALLEE_PARA);
            return;
        }

        String sDate = getParameter(START_PARA, request);
        if(sDate == null){
            missingRequiredParameter(response, START_PARA);
            return;
        }

        String eDate = getParameter(END_PARA, request);
        if(eDate == null){
            missingRequiredParameter(response, END_PARA);
            return;
        }

        if(bill.getCustomer() == null){
            bill.setCustomer(customerName.split(" "));
        }
        else if(!bill.getCustomer().equals(customerName)){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Customer name does not match");
            return;
        }

        String[] startDate = sDate.split(" ");
        String[] endDate = eDate.split(" ");

        Date Starting = Project4.initDate(startDate[0], startDate[1], startDate[2]);
        Date Ending = Project4.initDate(endDate[0], endDate[1], endDate[2]);

        PhoneCall call = new PhoneCall(callerNum, calleeNum, Starting, Ending);

        bill.addPhoneCall(call);

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.bill.getPhoneCalls().clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allPhoneCallListDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatPrettyBill(PrintWriter, java.util.Collection<PhoneCall>)}
     */
    private void writeAllPhoneBillPretty(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Messages.formatPrettyBill(pw, bill.getPhoneCalls());

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    private void writeSearchPhoneBillPretty(String start, String end, HttpServletResponse response) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Date startT = null;
        Date endT = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try{
            startT = sdf.parse(start);
            endT = sdf.parse(end);
        }catch(ParseException e){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Unable to parse date while searching PhoneBill");
            return;
        }

        Collection<PhoneCall> billcall = bill.getPhoneCalls();

        PhoneBill temp = new PhoneBill();
        temp.setCustomer(bill.getCustomer().split(" "));

        for(PhoneCall call : billcall){
            if(call.getStartTime().getTime() >= startT.getTime() && call.getEndTime().getTime() <= endT.getTime()){
                temp.addPhoneCall(call);
            }
        }

        Messages.formatPrettyBill(pw, temp.getPhoneCalls());

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }
}
