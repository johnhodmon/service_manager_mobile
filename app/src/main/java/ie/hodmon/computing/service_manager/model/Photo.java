package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 13/02/16.
 */
public class Photo
{
    private int job_id;
    private byte[] photo_data;

    public Photo(int job_id, byte[] photo_data)
    {
        this.job_id=job_id;
        this .photo_data=photo_data;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public byte[] getPhoto_data() {
        return photo_data;
    }

    public void setPhoto_data(byte[] photo_data) {
        this.photo_data = photo_data;
    }
}
