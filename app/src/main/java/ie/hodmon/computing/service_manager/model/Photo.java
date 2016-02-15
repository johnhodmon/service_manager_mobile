package ie.hodmon.computing.service_manager.model;

import android.util.Base64;

/**
 * Created by john on 13/02/16.
 */
public class Photo
{
    private String id;
    private int job_id;
    private String photo_data;


    public Photo(int job_id, String photo_data)
    {
        this.job_id=job_id;
        this .photo_data=photo_data;


    }

    public Photo(){}

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getPhoto_data() {
        return photo_data;
    }

    public void setPhoto_data(String photo_data) {
        this .photo_data=photo_data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
