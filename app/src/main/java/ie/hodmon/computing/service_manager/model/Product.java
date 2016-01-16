package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 21/02/2015.
 */
public class Product
{
    private String pumpNr;
    private String pumpDescription;

    public Product()
    {

    }

    public Product(String pumpNr, String pumpDescription)
    {
        this.pumpNr=pumpNr;
        this.pumpDescription=pumpDescription;
    }

    public String getPumpDescription() {
        return pumpDescription;
    }

    public void setPumpDescription(String pumpDescription) {
        this.pumpDescription = pumpDescription;
    }

    public String getPumpNr() {
        return pumpNr;
    }

    public void setPumpNr(String pumpNr) {
        this.pumpNr = pumpNr;
    }
}
