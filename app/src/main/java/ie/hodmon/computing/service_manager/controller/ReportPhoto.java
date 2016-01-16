package ie.hodmon.computing.service_manager.controller;

/**
 * Created by John on 2015-12-03.
 */
public class ReportPhoto

{
    private String photoId;
    private int jobId;
    private byte[] blob;

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public ReportPhoto (String photoId,int jobId, byte[] blob)
    {
        this.photoId=photoId;
        this.jobId=jobId;
        this.blob=blob;

    }
    public ReportPhoto()
    {

    }


}
