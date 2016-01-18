package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.hodmon.computing.service_manager.model.Customer;
import ie.hodmon.computing.service_manager.model.CustomerProduct;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Product;

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

    public static String editJob(String call,Job job) {
        Type objType = new TypeToken<Job>(){}.getType();
        String json = new Gson().toJson(job, objType);

        return REST.put(call, json);
    }



}
