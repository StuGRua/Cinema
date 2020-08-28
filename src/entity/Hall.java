package Entity;

public class Hall {
    private int HallId;
    private String HallType;
    private int HallLineSum;
    private int HallRowSum;

    public Hall(){}
    /*
    public Hall(int id,String type,int linesum,int rowsum){
        this.HallId =id;
        this.HallLineSum =linesum;
        this.HallRowSum =rowsum;
    }
     */
    public int getHallId() {
        return HallId;
    }

    public void setHallId(int hallId) {
        HallId = hallId;
    }

    public String getHallType() {
        return HallType;
    }

    public void setHallType(String hallType) {
        HallType = hallType;
    }

    public int getHallLineSum() {
        return HallLineSum;
    }

    public void setHallLineSum(int hallLineSum) {
        HallLineSum = hallLineSum;
    }

    public int getHallRowSum() {
        return HallRowSum;
    }

    public void setHallRowSum(int hallRowSum) {
        HallRowSum = hallRowSum;
    }

}
