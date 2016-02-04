package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 04/02/16.
 */
public class PartListWithPartNumber
{
    private int part_id;
    private String part_number;
    private String description;

    public PartListWithPartNumber(int part_id,String part_number,String description)
    {
        this.part_id=part_id;
        this.part_number=part_number;
        this.description=description;
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
