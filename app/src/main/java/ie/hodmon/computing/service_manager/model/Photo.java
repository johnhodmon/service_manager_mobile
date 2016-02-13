package ie.hodmon.computing.service_manager.model;

import android.util.Base64;

/**
 * Created by john on 13/02/16.
 */
public class Photo
{
    private int id;
    private int job_id;
    private String photo_data;


    public Photo(int job_id, byte[] photo_data)
    {
        this.job_id=job_id;
        this .photo_data=Base64.encodeToString(photo_data, Base64.DEFAULT);

    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getPhoto_data() {
        return photo_data;
    }

    public void setPhoto_data(byte[] photo_data) {
        this .photo_data=Base64.encodeToString(photo_data, Base64.DEFAULT);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
