package ie.hodmon.computing.hodmonpumpservices;

/**
 * Created by John on 23/02/2015.
 */
public class PartsList
{
    private int id;
    private String partNumber;
    private String pumpNumber;
    private int quantity;

    public PartsList(String partNumber,String pumpNumber,int quantity)
    {
        this.partNumber=partNumber;
        this.pumpNumber=pumpNumber;
        this.quantity=quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPumpNumber() {
        return pumpNumber;
    }

    public void setPumpNumber(String pumpNumber) {
        this.pumpNumber = pumpNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
