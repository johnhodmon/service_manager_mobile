package ie.hodmon.computing.service_manager.model;



/**
 * Created by John on 2015-12-05.
 */
public class Engineer



{
    private int id;
    private String name;
    private String street;
    private String town;
    private String county;
    private String phone_number;
    private String email;
    private String pps;

    public Engineer(int id,String name,String street, String town,String county,String phone_number, String email,String pps)
    {
        this.id=id;
        this.name=name;
        this.street=street;
        this.town=town;
        this.county=county;
        this.phone_number=phone_number;
        this.email=email;
        this.pps=pps;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPps() {
        return pps;
    }

    public void setPps(String pps) {
        this.pps = pps;
    }
}
