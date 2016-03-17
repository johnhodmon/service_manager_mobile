package ie.hodmon.computing.service_manager.model;

import android.net.Uri;

/**
 * Created by john on 22/02/16.
 */
public class Video
{
    private String id;
    private int job_id;
    private String vid_attach;
    private Uri localUri;

    public Video(int job_id) {
        this.job_id = job_id;
    }

    public Uri getLocalUri() {
        return localUri ;
    }

    public void setLocalUri(Uri localUri) {
        this.localUri = localUri;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getVid_attach() {
        return vid_attach;
    }

    public void setVid_attach(String vid_attach) {
        this.vid_attach = vid_attach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
