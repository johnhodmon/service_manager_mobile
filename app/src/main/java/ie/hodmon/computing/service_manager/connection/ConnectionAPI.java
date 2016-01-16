package ie.hodmon.computing.service_manager.connection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ie.hodmon.computing.service_manager.model.Job;

/**
 * Created by john on 16/01/16.
 */
public class ConnectionAPI
{

        //////////////////////////////////////////////////////////////////////////////////
        public static List<Job> getAll(String partUrl) {
            String json = REST.get(partUrl);
            Log.v("donate", "JSON RESULT : " + json);
            Type collectionType = new TypeToken<List<Job>>() {
            }.getType();

            return new Gson().fromJson(json, collectionType);
        }



}
