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



    public JobPart(int id,int job_id,int part_id,int quantity)
    {
       this.id=id;
        this.job_id=job_id;
        this.part_id=part_id;
        this.quantity=quantity;
    }

    public JobPart()
    {

    }


}
