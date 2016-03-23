package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 23/03/16.
 */
public class User
{
    private String email;
    private String gms_token;

    public User(String email, String gms_token)
    {
        this.email=email;
        this.gms_token=gms_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGms_token() {
        return gms_token;
    }

    public void setGms_token(String gms_token) {
        this.gms_token = gms_token;
    }
}
