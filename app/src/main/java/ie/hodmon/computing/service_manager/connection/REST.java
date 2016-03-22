package ie.hodmon.computing.service_manager.connection;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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



    private static final String URL = "http://192.168.1.105";
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
            Log.v("donate", "DELETE REQUEST ERROR" + e.getMessage());
        }

        return response;
    }

    public static void uploadVideo(String url,Video v)
    {
        try {
            MultipartUtility mp = new MultipartUtility("http://192.168.1.102/videos", "UTF-8");
            mp.addFormField("job_id", "1");
            File videoFile=new File(v.getLocalUri().getPath());
            mp.addFilePart("video_attachment",videoFile);
            Log.v("REST","Launching multipart form:");
            List responses= mp.finish();
            Log.v("REST","responses"+responses.get(0));
        }

        catch (Exception e)
        {
            e.printStackTrace();
            Log.v("REST","ERROR UPLOADING VIDEO"+e.getMessage());
        }
    }

    static class MultipartUtility {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";
            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);    // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            if(cookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                httpConn.setRequestProperty("Cookie",
                        TextUtils.join(";", cookieManager.getCookieStore().getCookies()));

                Log.v("REST","Cookies being sent"+cookieManager.getCookieStore().getCookies());
            }
            Log.v("REST", "Http con configured for " +url);


            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public List<String> finish() throws IOException {
            List<String> response = new ArrayList<String>();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }
            return response;
        }
    }


}
