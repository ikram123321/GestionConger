package Model;

import java.util.Date;
public class leave {
    private int id;
    private String startDate;
    private String endDate;
    private String status;
    private int userId;

    public leave(int id, String startDate, String endDate, String status, int userId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userId = userId;
    }

    

	public leave(String startDate, String endDate, String status, int userId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.userId = userId;
	}



	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


	
}