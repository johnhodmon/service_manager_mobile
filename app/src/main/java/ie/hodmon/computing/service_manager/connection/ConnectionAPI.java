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

        //////////////////////////////////////////////////////////////////////////////////
        public static List<Job> getJobs(String partUrl) {
            String json = REST.get(partUrl);
            Log.v("REST", "JSON RESULT : " + json);
            Type collectionType = new TypeToken<List<Job>>() {
            }.getType();

            return new Gson().fromJson(json, collectionType);
        }
    public static List<Customer> getCustomers(String partUrl) {
        String json = REST.get(partUrl);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<List<Customer>>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static List<Product> getProducts(String partUrl) {
        String json = REST.get(partUrl);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<List<Product>>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static List<CustomerProduct> getCustomerProducts(String partUrl) {
        String json = REST.get(partUrl);
        Log.v("REST", "JSON RESULT : " + json);
        Type collectionType = new TypeToken<List<Customer>>() {
        }.getType();

        return new Gson().fromJson(json, collectionType);
    }


}
