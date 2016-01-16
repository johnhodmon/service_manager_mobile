package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 16/01/16.
 */
public class Customer

{
    private String name;
    private String street;
    private String town;
    private String county;
    private String phone;
    private String email;
    private String lat_lng;

    public Customer(String name,String street, String town,String county,String phone, String email,String lat_lng)
    {
        this.name=name;
        this.street=street;
        this.town=town;
        this.county=county;
        this.phone=phone;
        this.email=email;
        this.lat_lng=lat_lng;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat_lng() {
        return lat_lng;
    }

    public void setLat_lng(String lat_lng) {
        this.lat_lng = lat_lng;
    }
}
