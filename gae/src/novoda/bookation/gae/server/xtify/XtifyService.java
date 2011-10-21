package novoda.bookation.gae.server.xtify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class XtifyService {

	private static final Logger logger = Logger.getLogger(XtifyService.class.getSimpleName());

	public void sendMessage(String xtifyId) {
		String urlString = "http://notify.xtify.com/api/1.0/SdkNotification";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<sdk-notification>" +
                	"<actionType>LAUNCH_APP</actionType>" +
                	"<appId>27d70c2b-4982-48f2-885e-e8230ba3df00</appId>" +
                	"<userKey>" + xtifyId + "</userKey>" +
                	"<notificationBody>Test Web Service Notification</notificationBody>" +
                	"<notificationTitle>Test Web Service Notification</notificationTitle>" +
                "</sdk-notification>";
        String result = null;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        HttpURLConnection urlConn = null;
        OutputStream out = null;
        BufferedReader in = null;
        if (url != null) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.addRequestProperty("Content-Type","application/xml");
                urlConn.setRequestMethod("PUT");
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                urlConn.connect();
                out = urlConn.getOutputStream();
                out.write(content.getBytes());
                out.flush();
                // Check response code
                if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()), 8192);
                    StringBuffer strBuff = new StringBuffer();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        strBuff.append(inputLine);
                    }
                    result = strBuff.toString();
                    logger.info(result);
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            } finally {
                if (in != null) {
                	try {
                		in.close();
                	} catch(Exception e) {
                		//TODO
                	}
                }
                if (out != null) {
                	try {
                		out.close();
                	} catch(Exception e) {
                		//TODO
                	}
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
            }
        }

    }

}
