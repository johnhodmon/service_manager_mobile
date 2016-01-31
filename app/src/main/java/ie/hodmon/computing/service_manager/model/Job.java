package ie.hodmon.computing.service_manager.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Job
{

    private int id;
    private String reported_fault;
    private int miles;
    private int labour_time;
    private String status;
    private Product product;
    private Customer customer;
    private Manufacturer manufacturer;
    private Report report;
    private JobPart[] job_parts;



    public Job(int id,String reported_fault,int miles,int labour_time,int engineer_id,int customer_product_id,String status,Product product,
               Customer customer,Manufacturer manufacturer, Report report,JobPart[]job_parts)
    {
        this.id=id;
        this .reported_fault=reported_fault;
        this.miles=miles;
        this .labour_time=labour_time;
        this.status=status;
        this.customer=customer;
        this.product=product;
        this.manufacturer=manufacturer;
        this.report=report;
        this.job_parts=job_parts;

    }
    public Job()
    {

    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public JobPart[] getJob_parts()
    {
        return job_parts;
    }

    public void setJob_parts(JobPart[]job_parts)
    {
        this.job_parts = job_parts;
    }



}










