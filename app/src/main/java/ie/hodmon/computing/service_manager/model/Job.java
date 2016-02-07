package ie.hodmon.computing.service_manager.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Job
{

    private int id;
    private String reported_fault;
    private String status;
    private Product product;
    private Customer customer;
    private Manufacturer manufacturer;
    private Report report;
    private JobPart[] job_parts;
    private PartList[] part_lists;
    private PartListWithPartNumber[]part_lists_with_part_numbers;
    private JobPartWithPartNumber [] job_parts_with_part_numbers;
    private String travel_start;
    private String travel_end;
    private String labour_start;
    private String labour_end;



    public Job(int id,String reported_fault,int miles,int labour_time,int engineer_id,int customer_product_id,String status,Product product,
               Customer customer,Manufacturer manufacturer, Report report,JobPart[]job_parts,
               String travel_start,String travel_end,String labour_start, String labour_end)
    {
        this.id=id;
        this .reported_fault=reported_fault;

        this.status=status;
        this.customer=customer;
        this.product=product;
        this.manufacturer=manufacturer;
        this.report=report;
        this.job_parts=job_parts;
        this.travel_start=travel_start;
        this.travel_end=travel_end;
        this.labour_start=labour_start;
        this.labour_end=labour_end;

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

    public PartList[] getPart_lists() {
        return part_lists;
    }

    public void setPart_lists(PartList[] part_lists) {
        this.part_lists = part_lists;
    }

    public PartListWithPartNumber[] getPart_lists_with_part_numbers() {
        return part_lists_with_part_numbers;
    }

    public void setPart_lists_with_part_numbers(PartListWithPartNumber[] part_lists_with_part_numbers) {
        this.part_lists_with_part_numbers = part_lists_with_part_numbers;
    }


    public JobPartWithPartNumber[] getJob_parts_with_part_numbers() {
        return job_parts_with_part_numbers;
    }

    public void setJob_parts_with_part_numbers(JobPartWithPartNumber[] job_parts_with_part_numbers) {
        this.job_parts_with_part_numbers = job_parts_with_part_numbers;
    }

    public String getTravel_start() {
        return travel_start;
    }

    public void setTravel_start(String travel_start) {
        this.travel_start = travel_start;
    }

    public String getTravel_end() {
        return travel_end;
    }

    public void setTravel_end(String travel_end) {
        this.travel_end = travel_end;
    }

    public String getLabour_start() {
        return labour_start;
    }

    public void setLabour_start(String labour_start) {
        this.labour_start = labour_start;
    }

    public String getLabour_end() {
        return labour_end;
    }

    public void setLabour_end(String labour_end) {
        this.labour_end = labour_end;
    }
}










