package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 23/02/2015.
 */
public class Part

{
    private String partNumber;
    private String partDescription;

    public Part(String partNumber, String partDescription)
    {
        this.partNumber=partNumber;
        this.partDescription=partDescription;
    }


    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }
}
