package ie.hodmon.computing.hodmonpumpservices;

/**
 * Created by John on 21/02/2015.
 */
public class Pump
{
    private String pumpNr;
    private String pumpDescription;

    public Pump()
    {

    }

    public Pump(String pumpNr,String pumpDescription)
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
