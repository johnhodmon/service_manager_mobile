package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.Part;
import ie.hodmon.computing.service_manager.model.Report;
import ie.hodmon.computing.service_manager.model.SessionWrapper;

/**
 * Created by john on 16/01/16.
 */
public class ConnectionAPI
{


        public static List<Job> getJobs(String uri) {
            String json = REST.get(uri);
            Log.v("REST", "JSON RESULT : " + json);
            Type collectionType = new TypeToken<List<Job>>() {
            }.getType();

            return new Gson().fromJson(json, collectionType);
        }

    public static Job getJob(String uri) {
        String json = REST.get(uri);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<Job>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static Part getPart(String uri) {
        String json = REST.get(uri);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<Part>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static String editReport(String call, Report report) {
        Type objType = new TypeToken<Report>(){}.getType();
        String json = new Gson().toJson(report, objType);
        Log.v("REST","json for put is: "+json);
        return REST.put(call, json);
    }

    public static String editJobPartQuantity(String call, JobPart jobPart) {
        Type objType = new TypeToken<JobPart>(){}.getType();
        String json = new Gson().toJson(jobPart, objType);
        Log.v("REST","json for put is: "+json);
        return REST.put(call, json);
    }



    public static String addJobPart(String call, JobPart jobPart) {
        Type objType = new TypeToken<JobPart>(){}.getType();
        String json = new Gson().toJson(jobPart, objType);
        Log.v("REST","json for post to jobparts is: "+json);
        return REST.post(call, json);
    }

    public static String login (String call,SessionWrapper sw)
{
    Type objType = new TypeToken<SessionWrapper>(){}.getType();
    String json = new Gson().toJson(sw, objType);
    Log.v("REST","json for login is: "+json);


    return REST.login(call, json);
}

    public static String deleteJobPart(String call)
    {
        return REST.delete(call);
    }

}
