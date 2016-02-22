package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.Part;
import ie.hodmon.computing.service_manager.model.Photo;
import ie.hodmon.computing.service_manager.model.Report;
import ie.hodmon.computing.service_manager.model.SessionWrapper;
import ie.hodmon.computing.service_manager.model.Video;

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

    public static List<Photo> getPhotos(String uri) {
        String json = REST.get(uri);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<List<Photo>>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static List<Video> getVideos(String uri) {
        String json = REST.get(uri);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<List<Video>>() {
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
    public static String editJob(String call, Job job) {
        Type objType = new TypeToken<Job>(){}.getType();
        String json = new Gson().toJson(job, objType);
        Log.v("REST","json for put is: "+json);
        return REST.put(call, json);
    }


    public static String addJobPart(String call, JobPart jobPart) {
        Type objType = new TypeToken<JobPart>(){}.getType();
        String json = new Gson().toJson(jobPart, objType);
        Log.v("REST","json for post to jobparts is: "+json);
        return REST.post(call, json);
    }

    public static String addPhoto(String call, Photo photo) {
        Type objType = new TypeToken<Photo>(){}.getType();
        String json = new Gson().toJson(photo, objType);
        Log.v("REST","json for post to jobparts is: "+json);
        return REST.post(call, json);
    }

    public static String addVideo(String call, Video video) {
        Type objType = new TypeToken<Video>(){}.getType();
        String json = new Gson().toJson(video, objType);
        Log.v("REST","json for post to videos is: "+json);
        return REST.post(call, json);
    }

    public static String login (String call,SessionWrapper sw)
{
    Type objType = new TypeToken<SessionWrapper>(){}.getType();
    String json = new Gson().toJson(sw, objType);
    Log.v("REST","json for login is: "+json);


    return REST.login(call, json);
}

    public static String delete(String call)
    {
        return REST.delete(call);
    }

}
