package ambulancesimulation.simulation;

import ambulancesimulation.mapLogic.Point;

public class PatientOnMap {

    private int patientId;
    Point pointOnMap;

    public PatientOnMap(int patientId, Point pointOnMap) {
        this.patientId = patientId;
        this.pointOnMap = pointOnMap;
    }

    public int getPatientId() {
        return this.patientId;
    }

    public Point getPointOnMap() {
        return this.pointOnMap;
    }
}
