package ambulancesimulation.simulation;

public class HospitalStatistics {

    private HospitalOnTheMap hospital;
    private int amountOfPepleInQueue;

    public HospitalStatistics(HospitalOnTheMap hospital, int amountOfPepleInQueue) {
        this.hospital = hospital;
        this.amountOfPepleInQueue = amountOfPepleInQueue;
    }

    public HospitalOnTheMap getHospital() {
        return this.hospital;
    }

    public int getAmountOfPeopleInQueue() {
        return this.amountOfPepleInQueue;
    }

}
