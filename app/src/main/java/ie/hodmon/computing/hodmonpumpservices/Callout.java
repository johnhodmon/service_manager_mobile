package ie.hodmon.computing.hodmonpumpservices;

import com.google.android.gms.maps.model.LatLng;

public class Callout
{
    private int id;


    private String engineerEmail;
    private String date;
    private String customerName;
    private String street;
    private String town;
    private String county;
    private String phoneNumber;
    private String pumpNumber;
    private String reportedFault;
    private String reportText;
    private LatLng latLng;


    public Callout(String engineerEmail,String date, String customerName,String street,String town,String county,String phoneNumber,
                   String pumpNumber, String reportedFault,String reportText, LatLng latLng)
    {
        this.engineerEmail=engineerEmail;
        this.date=date;
        this.customerName=customerName;
        this.street=street;
        this.town=town;
        this.county=county;
        this.phoneNumber=phoneNumber;
        this.pumpNumber=pumpNumber;
        this. reportedFault=reportedFault;
        this.reportText=reportText;


        this.latLng=latLng;

    }
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getEngineerEmail() {
        return engineerEmail;
    }

    public void setEngineerEmail(String engineerEmail) {
        this.engineerEmail = engineerEmail;
    }



    public String getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(String pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public Callout()
    {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportedFault() {
        return reportedFault;
    }

    public void setReportedFault(String reportedFault) {
        this.reportedFault = reportedFault;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
}
