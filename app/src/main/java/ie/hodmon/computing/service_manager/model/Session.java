package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 23/01/16.
 */
public class Session
{
    private String email;
    private String password;
    private String gms_token;


    public Session(String email,String password,String gms_token)
    {
        this.email=email;
        this.password=password;
        this.gms_token=gms_token;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGms_token() {
        return gms_token;
    }

    public void setGms_token(String gms_token) {
        this.gms_token = gms_token;
    }
}
