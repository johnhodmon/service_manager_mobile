package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Engineer;


public class LoginScreen extends ClassForCommonAttributes {

    private EditText emailAddress;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        password = (EditText) findViewById(R.id.password);
        emailAddress = (EditText) findViewById(R.id.emailAddress);

    }


    public boolean connectedToInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }


    public void login(View view)
    {

    }
}






















