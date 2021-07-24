package ambulancesimulation.simulation;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.fileElements.Patient;
import ambulancesimulation.mapLogic.Point;
import java.util.ArrayList;
import java.util.List;

public class HospitalOnTheMap {

    private final int hospitalId;
    private final String name;
    private Point pointOnMap;
    private int bedsAmount;
    private int freeBedsAmount;
    List<PatientOnMap> pepleWaitingForFreeBeds;

    public HospitalOnTheMap(Hospital hospital) {
        this.hospitalId = hospital.getHospitalId();
        this.name = hospital.getName();
        this.pointOnMap = new Point(hospital.getHospitalXCord(), hospital.getHospitalYCord());
        this.bedsAmount = hospital.getBedsAmount();
        this.freeBedsAmount = hospital.getFreeBedsAmount();
        pepleWaitingForFreeBeds = new ArrayList();
    }

    public int getHospitalId() {
        return this.hospitalId;
    }

    public String getName() {
        return this.name;
    }

    public int getBedsAmount() {
        return this.bedsAmount;
    }

    public int getFreeBedsAmount() {
        return this.freeBedsAmount;
    }

    public Point getPointOnMap() {
        return this.pointOnMap;
    }

    public double getHospitalXCord() {
        return this.pointOnMap.getX();
    }

    public double getHospitalYCord() {
        return this.pointOnMap.getY();
    }

    public List<PatientOnMap> getPeopleWaitingForFreeBeds() {
        return this.pepleWaitingForFreeBeds;
    }

    public void setBedsFreeAmount(int freeBedsAmount) {
        this.freeBedsAmount = freeBedsAmount;
    }

    public void addPatientToHospitalQueue(PatientOnMap patient) {
        this.pepleWaitingForFreeBeds.add(patient);
    }

}
