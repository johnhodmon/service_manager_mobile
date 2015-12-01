package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginScreen extends ClassForCommonAttributes {

    private EditText emailAddress;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        password = (EditText) findViewById(R.id.password);
        emailAddress = (EditText) findViewById(R.id.emailAddress);





    }






    public void login(View view)
    {

       if(emailAddress.getText()==null||password.getText()==null)
        {
            Toast.makeText(this, "Enter user name and password", Toast.LENGTH_SHORT).show();
        }

        else {
            emailAddressEntered = emailAddress.getText().toString();
            String passwordEntered = password.getText().toString();


            if (!mapOfEmailsToPasswords.containsKey(emailAddressEntered)) {
                Toast.makeText(this, "No Such User", Toast.LENGTH_SHORT).show();
            } else {
                if (mapOfEmailsToPasswords.get(emailAddressEntered).equals(passwordEntered)) {
                    finish();
                    startActivity(new Intent(this, CalloutScreen.class));
                } else {
                    Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                }

            }


        }




    }


}

