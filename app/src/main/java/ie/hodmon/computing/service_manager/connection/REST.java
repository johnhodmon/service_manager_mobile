package ie.hodmon.computing.service_manager.connection;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import ie.hodmon.computing.service_manager.model.Video;

/**
 * Created by john on 16/01/16.
 */
public class REST
{
    private static HttpURLConnection httpCon = null;
    private static URL url;
    private static CookieManager cookieManager=new CookieManager();



    private static final String URL = "http://192.168.1.103";
    private static final String cloudURL = "https://whispering-gorge-59927.herokuapp.com";


    public static void establishConnection(String request) {
        try {
            url = new URL(URL + request);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setUseCaches(false);
            httpCon.setReadTimeout(15 * 1000); // 15 seconds to timeout
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            if(cookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                httpCon.setRequestProperty("Cookie",
                        TextUtils.join(";", cookieManager.getCookieStore().getCookies()));

                Log.v("REST","Cookies being sent"+cookieManager.getCookieStore().getCookies());
            }
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



    public static String login(String url, String json) {

        OutputStreamWriter writer = null;
        StringBuilder stringBuilder = null;
        String result=null;

        try {
            establishConnection(url);
            httpCon.setRequestMethod("POST");
            httpCon.setInstanceFollowRedirects(false);
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);

            httpCon.connect();

            // read the output from the server
            writer = new OutputStreamWriter(httpCon.getOutputStream());
            writer.write(json);
            writer.close();



            Map<String, List<String>> headerFields = httpCon.getHeaderFields();
            List<String> cookiesHeader = headerFields.get("Set-Cookie");
            Log.v("REST", "POST REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());
            Log.v("REST", "HEADERS : " + headerFields.toString());
            if(cookiesHeader != null)
            {
                for (String cookie : cookiesHeader)
                {

                    Log.v("REST", "INTO COOKIE STORE: " + cookie);
                    cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));

                    if(cookie.contains("remember_token"))
                    {

                        result= "login sucessful";
                    }

                }
            }


        }

        catch (Exception e) {
            Log.v("REST","POST REQUEST ERROR" + e.getMessage());
        }

        return result;
    }

    public static String post(String url, String json) {

        OutputStreamWriter writer = null;
        StringBuilder stringBuilder = null;
        String result=null;

        try {
            establishConnection(url);
            httpCon.setRequestMethod("POST");
            httpCon.setInstanceFollowRedirects(false);
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);

            httpCon.connect();

            // read the output from the server
            writer = new OutputStreamWriter(httpCon.getOutputStream());
            writer.write(json);
            writer.close();
            stringBuilder = new StringBuilder();
            int HttpResult = httpCon.getResponseCode();
            Log.v("REST", "JSON POST JOBPART RESPONSE CODE : " + HttpResult);

            if(HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null)
                    stringBuilder.append(line + "\n");

                Log.v("REST", "JSON POST JOB RESPONSE : " + stringBuilder.toString());
            }
        }

        catch (Exception e) {
            Log.v("REST","POST REQUEST ERROR" + e.getMessage());
        }

        return result;
    }

    public static String delete(String url) {

        String response = null;

        try {
            establishConnection(url);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();

            Log.v("REST", "DELETE REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            response = httpCon.getResponseMessage();
            Log.v("REST", "JSON DELETE RESPONSE : " + response);
        }

        catch (Exception e) {
            Log.v("donate","DELETE REQUEST ERROR" + e.getMessage());
        }

        return response;
    }

    public static void uploadVideo(String url,Video v)
    {

        String fileName = v.getLocalUri().getPath();
        establishConnection(url);
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(v.getLocalUri().getPath());

            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                httpCon.setDoInput(true); // Allow Inputs
                httpCon.setDoOutput(true); // Allow Outputs
                httpCon.setUseCaches(false); // Don't use a Cached Copy
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Connection", "Keep-Alive");
                //  conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                httpCon.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                httpCon.setRequestProperty("video", fileName);
                dos = new DataOutputStream(httpCon.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                //job_id




                //video file upload
                dos.writeBytes("Content-Disposition: form-data; name=\"vid_attach\""+ lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = httpCon.getResponseCode();
                String serverResponseMessage = httpCon.getResponseMessage();

                Log.v("REST", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);



                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();


    }

            catch(Exception e)
            {
                e.printStackTrace();
                Log.v("REST","Error uploading video: "+e.getMessage());
            }
    }
}
