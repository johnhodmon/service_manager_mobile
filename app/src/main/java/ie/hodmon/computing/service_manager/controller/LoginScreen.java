package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Engineer;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Session;
import ie.hodmon.computing.service_manager.model.SessionWrapper;


public class LoginScreen extends ClassForCommonAttributes {

    private EditText emailAddress;
    private EditText password;
    private TextView errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        password = (EditText) findViewById(R.id.password);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        errorMessage = (TextView) findViewById(R.id.error_message);


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

        errorMessage.setText("");
        SessionWrapper sw=new SessionWrapper(new Session(emailAddress.getText().toString(),password.getText().toString()));
        new Login(this).execute("/login",sw);
    }


    private class Login extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public Login(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Attempting Login");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.v("REST", "Loggin in");
                return  ConnectionAPI.login((String) params[0],(SessionWrapper)params[1]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

         Log.v("REST","login result: "+result);

        }
    }

}






















