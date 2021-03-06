package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Session;
import ie.hodmon.computing.service_manager.model.SessionWrapper;
import ie.hodmon.computing.service_manager.push_notifications.QuickstartPreferences;
import ie.hodmon.computing.service_manager.push_notifications.RegistrationIntentService;


public class LoginScreen extends ClassForCommonAttributes {

    private EditText emailAddress;
    private EditText password;
    private TextView errorMessage;
    private Button loginButton;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver registrationBroadcastReceiver;
    private boolean isReceiverRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        password = (EditText) findViewById(R.id.password);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        errorMessage = (TextView) findViewById(R.id.error_message);
        loginButton=(Button)findViewById(R.id.button);
        registrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                Log.v("GCM","Sent token?"+sentToken);

            }
        };
        //login(loginButton);
        if(loggedIn)
        {
            startActivity(new Intent(this, JobScreen.class));
        }

    }


    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
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
        SessionWrapper sw=new SessionWrapper(new Session("jhodmon@servicemanager.ie","secret",""));
        //SessionWrapper sw=new SessionWrapper(new Session(emailAddress.getText().toString(),password.getText().toString(),""));
        if(connectedToInternet(this)) {

            new Login(this).execute("/login", sw);

        }

        else
        {

            Toast.makeText(this,"Please check internet connection",Toast.LENGTH_LONG).show();
        }
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

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }



            if(result!=null&&result.equals("login sucessful"))
            {

                registerReceiver();



                Intent intent = new Intent(LoginScreen.this, RegistrationIntentService.class);
                startService(intent);
                loggedIn=true;
                startActivity(new Intent(LoginScreen.this, JobScreen.class));

            }

            else
            {
                Toast.makeText(LoginScreen.this,"Incorrect Log In Details",Toast.LENGTH_LONG).show();
            }




        }
    }

}






















