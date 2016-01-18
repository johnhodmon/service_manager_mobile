package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by john on 16/01/16.
 */
public class REST
{
    private static HttpURLConnection httpCon = null;
    private static URL url;


    private static final String URL = "http://192.168.1.101";


    public static void establishConnection(String request) {
        try {
            url = new URL(URL + request);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setUseCaches(false);
            httpCon.setReadTimeout(15 * 1000); // 15 seconds to timeout
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            Log.v("REST", "Http con configured for " +url);
        }
        catch (Exception e)
        {
            Log.v("REST", "REST CONNECTION ERROR" + e.getMessage());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static String get(String partUrl) {

        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        try {
            establishConnection(partUrl);
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);
            httpCon.connect();

            Log.v("REST", "GET REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);

            reader.close();
            Log.v("", "JSON GET REQUEST : " + stringBuilder.toString());
        }

        catch (Exception e) {
            Log.v("REST","GET REQUEST ERROR" + e.getMessage());
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    public static String put(String url, String json) {

        OutputStreamWriter writer = null;
        StringBuilder stringBuilder = null;

        try {
            establishConnection(url);
            httpCon.setRequestMethod("PUT");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.connect();

            Log.v("REST", "PUT REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            // read the output from the server
            writer = new OutputStreamWriter(httpCon.getOutputStream());
            writer.write(json);
            writer.close();

            stringBuilder = new StringBuilder();
            int HttpResult = httpCon.getResponseCode();
            Log.v("REST", "JSON PUT RESPONSE CODE : " + HttpResult);
            if(HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null)
                    stringBuilder.append(line + "\n");

                Log.v("REST", "JSON PUT RESPONSE : " + stringBuilder.toString());
            }
        }

        catch (Exception e) {
            Log.v("REST", "PUT REQUEST ERROR" + e.getMessage());
        }

        return stringBuilder.toString();
    }
}
