package ambulancesimulation.simulation;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.mapLogic.Point;

public class MapIntersection {

    private Point intersectionPoint;
    private HospitalOnTheMap startHospital;
    private HospitalOnTheMap endHospital;

    public MapIntersection(Point intersectionPoint, HospitalOnTheMap startHospital, HospitalOnTheMap endHospital) {
        this.intersectionPoint = intersectionPoint;
        this.startHospital = startHospital;
        this.endHospital = endHospital;
    }

    public Point getIntersectionPoint() {
        return this.intersectionPoint;
    }

    public double getPointXCord() {
        return this.intersectionPoint.getX();
    }

    public double getPointYCord() {
        return this.intersectionPoint.getY();
    }

    public Point getPoint() {
        return this.intersectionPoint;
    }

    public HospitalOnTheMap getStartHospital() {
        return this.startHospital;
    }

    public HospitalOnTheMap getEndHospital() {
        return this.endHospital;
    }

}
