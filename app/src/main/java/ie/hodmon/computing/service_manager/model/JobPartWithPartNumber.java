package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 04/02/16.
 */
public class JobPartWithPartNumber
{
    private int jobPartId;
    private String partNumber;
    private String description;
    private int quantity;

    public JobPartWithPartNumber(int jobPartId,String partNumber,String description,int quantity)
    {
        this.jobPartId=jobPartId;
        this.partNumber=partNumber;
        this.description=description;
        this.quantity=quantity;
    }


    public int getJobPartId() {
        return jobPartId;
    }

    public void setJobPartId(int jobPartId) {
        this.jobPartId = jobPartId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
