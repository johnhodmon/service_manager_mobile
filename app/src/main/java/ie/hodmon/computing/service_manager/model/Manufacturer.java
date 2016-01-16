package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 16/01/16.
 */
public class Manufacturer
{
    private int id;
    private String name;
    private String street;
    private String town;
    private String county;
    private String phone;
    private String email;
    private String country;

    public Manufacturer(int id,String name,String street, String town,String county,String phone, String email,String country)
    {
        this.id=id;
        this.name=name;
        this.street=street;
        this.town=town;
        this.county=county;
        this.phone=phone;
        this.email=email;
        this.country=country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
