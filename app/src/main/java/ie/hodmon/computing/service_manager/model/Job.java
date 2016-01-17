package ie.hodmon.computing.service_manager.model;

import com.google.android.gms.maps.model.LatLng;

public class Job
{

    private String reported_fault;
    private int miles;
    private int labour_time;
    private String status;
    private Product product;
    private Customer customer;








    public Job(int id,String reported_fault,int miles,int labour_time,int engineer_id,int customer_product_id,String status,Product product, Customer customer)
    {
        this.id=id;
        this .reported_fault=reported_fault;
        this.miles=miles;
        this .labour_time=labour_time;
        this.engineer_id=engineer_id;
        this.customer_product_id=customer_product_id;
        this.status=status;
        this.customer=customer;
        this.product=product;

    }
    public Job()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReported_fault() {
        return reported_fault;
    }

    public void setReported_fault(String reported_fault) {
        this.reported_fault = reported_fault;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getLabour_time() {
        return labour_time;
    }

    public void setLabour_time(int labour_time) {
        this.labour_time = labour_time;
    }

    public int getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(int engineer_id) {
        this.engineer_id = engineer_id;
    }

    public int getCustomer_product_id() {
        return customer_product_id;
    }

    public void setCustomer_product_id(int customer_product_id) {
        this.customer_product_id = customer_product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}










