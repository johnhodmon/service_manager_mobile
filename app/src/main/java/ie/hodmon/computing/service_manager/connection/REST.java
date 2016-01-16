package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by john on 16/01/16.
 */
public class REST
{
    private static HttpURLConnection httpCon = null;
    private static URL url;


    private static final String URL = "http://192.168.1.101:3000";


    public static void establishConnection(String request) {
        try {
            url = new URL(URL + request);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setUseCaches(false);
            httpCon.setReadTimeout(15 * 1000); // 15 seconds to timeout
            httpCon.setRequestProperty( "Content-Type", "application/json" );
            httpCon.setRequestProperty("Accept", "application/json");
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
            Log.v("donate", "JSON GET REQUEST : " + stringBuilder.toString());
        }

        catch (Exception e) {
            Log.v("donate","GET REQUEST ERROR" + e.getMessage());
        }

        return stringBuilder.toString();
    }
}
