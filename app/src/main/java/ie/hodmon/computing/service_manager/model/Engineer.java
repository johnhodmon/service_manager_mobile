package ie.hodmon.computing.service_manager.model;

import com.parse.ParseUser;

/**
 * Created by John on 2015-12-05.
 */
public class Engineer



{
    private String name;
    private String street;
    private String town;
    private String county;
    private String phone_number;
    private String email;
    private String pps;

    public Engineer(String name,String street, String town,String county,String phone_number, String email,String pps)
    {
        this.name=name;
        this.street=street;
        this.town=town;
        this.county=county;
        this.phone_number=phone_number;
        this.email=email;
        this.pps=pps;

    }


}
