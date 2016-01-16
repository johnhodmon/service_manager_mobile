package ie.hodmon.computing.service_manager.model;

/**
 * Created by john on 16/01/16.
 */
public class Report
{
    private int id;
    private String engineer_report;
    private int job_id;

    public Report(int id,String engineer_report, int job_id)
    {
        this.id=id;
        this.engineer_report=engineer_report;
        this.job_id=job_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngineer_report() {
        return engineer_report;
    }

    public void setEngineer_report(String engineer_report) {
        this.engineer_report = engineer_report;
    }
}
