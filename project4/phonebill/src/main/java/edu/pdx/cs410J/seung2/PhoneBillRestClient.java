package edu.pdx.cs410J.seung2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    // Set of String for initializing URL
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";
    private final String url;

    // Date formatting
    private SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); // Date formatter


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Retrieves all the PhoneCall collection
     *
     * @param name
     *          Name of the customer to find the Phone Bill
     * @return
     *          Retrieved PhoneCall collection
     * @throws IOException
     *          Throws an exception for IO
     */
    public Collection<PhoneCall> getAllPhoneCalls(String name) throws IOException {
      Response response = get(this.url, "customer", name);
      return Messages.parsePhoneBill(response.getContent());
    }

    /**
     * Retrieves all the searched PhoneCall collection
     *
     * @param name
     *          Name of the customer to find the Phone Bill
     * @param start
     *          Search comparison start time
     * @param end
     *          Search comparison end time
     * @return
     *          Retrieved PhoneCall collection
     * @throws IOException
     *          Throws an exception for IO
     */
    public Collection<PhoneCall> getSearchCalls(String name, Date start, Date end) throws IOException{
        String startT = ft.format(start);
        String endT = ft.format(end);

        Response response = get(this.url,"customer", name, "startTime", startT, "endTime", endT);
        return Messages.parseSearchCalls(response.getContent(), start, end);
    }

    /**
     * Add the PhoneCall to the server
     *
     * @param customerName
     *          Name of the customer to find the Phone Bill
     * @param call
     *          PhoneCall we're adding
     * @throws IOException
     *          Throws an exception for IO
     */
    public void addPhoneCall(String customerName, PhoneCall call) throws IOException {
        // Set up the parameter
        String[] postParameters = {
                "customer", customerName,
                "caller", call.getCaller(),
                "callee", call.getCallee(),
                "startTime", call.getStartTimeString(),
                "endTime", call.getEndTimeString(),
        };
      Response response = postToMyURL(postParameters);
      throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Use POST method to send it to the server
     *
     * @param callVariables
     *          Variable and it's values sent
     * @return
     *          Returns the response of the server whether success or not
     * @throws IOException
     *          Throws an exception for IO
     */
    @VisibleForTesting
    Response postToMyURL(String... callVariables) throws IOException {
      return post(this.url, callVariables);
    }

    /**
     * Removes all the server data for testing resetting
     *
     * @throws IOException
     *          Throws an exception for IO
     */
    public void removeAllPhoneBill() throws IOException {
      Response response = delete(this.url);
      throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Checks the response if it's success or not
     *
     * @param response
     *          Returned response from the server
     * @return
     *          If it's success then return the response
     */
    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new PhoneBillRestException(code);
      }
      return response;
    }

    /**
     * Private Exception for PhoneBillRestClient for throwing
     */
    private class PhoneBillRestException extends RuntimeException {
      public PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

}
