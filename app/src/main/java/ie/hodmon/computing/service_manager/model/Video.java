package ie.hodmon.computing.service_manager.model;

import android.net.Uri;

/**
 * Created by john on 22/02/16.
 */
public class Video
{
    private String id;
    private int job_id;
    private String video_attachment_file_name;
    private String url;


    public Video(int job_id) {
        this.job_id = job_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getVideo_attachment_file_name() {
        return video_attachment_file_name;
    }

    public void setVideo_attachment_file_name(String video_attachment_file_name) {
        this.video_attachment_file_name = video_attachment_file_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

