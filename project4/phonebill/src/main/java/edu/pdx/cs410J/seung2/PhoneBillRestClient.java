package edu.pdx.cs410J.seung2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


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
     * Returns all dictionary entries from the server
     */
    public Collection<PhoneCall> getAllPhoneCalls() throws IOException {
      Response response = get(this.url);
      return Messages.parsePhoneBill(response.getContent());
    }

    public Collection<PhoneCall> getSearchCalls(Date start, Date end) throws IOException{
        Response response = get(this.url);
        return Messages.parseSearchCalls(response.getContent(), start, end);
    }

    public void addPhoneCall(String customerName, PhoneCall call) throws IOException {
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

    @VisibleForTesting
    Response postToMyURL(String... callVariables) throws IOException {
      return post(this.url, callVariables);
    }

    public void removeAllPhoneBill() throws IOException {
      Response response = delete(this.url);
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new PhoneBillRestException(code);
      }
      return response;
    }

    private class PhoneBillRestException extends RuntimeException {
      public PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

}
