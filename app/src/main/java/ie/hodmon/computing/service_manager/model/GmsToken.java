package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 23/03/16.
 */
public class GmsToken
{

    private String token_string;

    public GmsToken(String gms_token)
    {

        this.token_string =gms_token;
    }


    public String getToken_string() {
        return token_string;
    }

    public void setToken_string(String token_string) {
        this.token_string = token_string;
    }
}
