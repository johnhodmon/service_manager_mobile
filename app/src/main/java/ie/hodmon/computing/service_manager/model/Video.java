package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 22/02/16.
 */
public class Video
{
    private String id;
    private int job_id;
    private String data;

    public Video(int job_id,String data)
    {
        this.job_id=job_id;
        this.data=data;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
