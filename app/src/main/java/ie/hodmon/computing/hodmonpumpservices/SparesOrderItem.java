package ie.hodmon.computing.hodmonpumpservices;

/**
 * Created by John on 22/02/2015.
 */
public class SparesOrderItem

{

    private int sparesOrderItemId;
    private int calloutId;
    private String partNumber;
    private String partDescription;
    private int quantity;

    public SparesOrderItem( int calloutId,String partNumber,String partDescription,int quantity)
    {
        this.calloutId =calloutId;
        this.quantity=quantity;
        this.partNumber=partNumber;
        this.partDescription=partDescription;
    }

    public SparesOrderItem( )
    {

    }

    public int getCalloutId() {
        return calloutId;
    }

    public void setCalloutId(int calloutId) {
        this.calloutId = calloutId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public int getSparesOrderItemId() {
        return sparesOrderItemId;
    }

    public void setSparesOrderItemId(int sparesOrderItemId) {
        this.sparesOrderItemId = sparesOrderItemId;
    }
}
