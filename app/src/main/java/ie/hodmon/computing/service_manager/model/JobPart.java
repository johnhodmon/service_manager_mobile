package ie.hodmon.computing.service_manager.model;

/**
 * Created by John on 22/02/2015.
 */
public class JobPart

{
    private int id;
    private int job_id;
    private int part_id;
    private int quantity;




    public JobPart(int job_id,int part_id,int quantity)
    {

        this.job_id=job_id;
        this.part_id=part_id;
        this.quantity=quantity;


    }

    public JobPart()
    {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
