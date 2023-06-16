package Model;

public class DemandeConger {
    private int demandId;
    private user userId;
    private leave leaveId;
    private String status;

    public DemandeConger(int demandId, user currentUser, leave currentLeave) {
        this.demandId = demandId;
        this.userId = currentUser;
        this.leaveId = currentLeave;
    }

    public DemandeConger(int demandId2, user currentUser, leave currentLeave, String status2) {
		// TODO Auto-generated constructor stub
    	  this.demandId = demandId2;
          this.userId = currentUser;
          this.leaveId = currentLeave;
          this.status = status2;
          }

	public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
    }

    public user getUserId() {
        return userId;
    }
 

    public leave getLeaveId() {
        return leaveId;
    }
    public String getstatus() {
        return status;
    }
   
}
