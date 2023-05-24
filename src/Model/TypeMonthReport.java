package Model;

/**
 * Class used to make the TypeMonthReport object.
 */
public class TypeMonthReport {

    private String apptType;
    private String month;
    private int total;


    /**
     * Constructor for TypeMonthReport object.
     *
     * @param apptType the appointment type.
     * @param month the appointment month
     * @param total the appointment total.
     */
    public TypeMonthReport(String apptType, String month, int total) {
        this.apptType = apptType;
        this.month = month;
        this.total = total;
    }

    /**
     * The getter for appointment type.
     *
     * @return the apptType.
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * The setter for appointment type.
     *
     * @param apptType the apptType.
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }


    /**
     * The getter for appointment month.
     *
     * @return the month.
     */
    public String getMonth() {
        return month;
    }

    /**
     * The setter for appointment month.
     *
     * @param month the month.
     */
    public void setMonth(String month) {
        this.month = month;
    }


    /**
     * The getter for appointment total.
     *
     * @return the total.
     */
    public int getTotal() {
        return total;
    }

    /**
     * The setter for appointment total.
     *
     * @param total the total.
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
