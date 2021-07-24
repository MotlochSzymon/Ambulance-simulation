package ambulancesimulation.fileElements;

import java.util.ArrayList;
import java.util.List;

public class Street {

    private final int streetId;
    private final int startHospitalId;
    private final int endHospitalId;
    private double distance;

    public Street(int streetId, int startHospitalId, int endHospitalId, double distance) {
        this.streetId = streetId;
        this.startHospitalId = startHospitalId;
        this.endHospitalId = endHospitalId;
        this.distance = distance;
    }

    public Street(Street that) {
        this(that.getStreetId(), that.getStartHospitalId(), that.getEndHospitalId(), that.getDistance());
    }

    public static List<Street> cloneList(List<Street> streetsList) {
        List<Street> clonedList = new ArrayList<Street>(streetsList.size());
        for (Street street : streetsList) {
            clonedList.add(new Street(street));
        }
        return clonedList;
    }

    public int getStreetId() {
        return this.streetId;
    }

    public int getStartHospitalId() {
        return this.startHospitalId;
    }

    public int getEndHospitalId() {
        return this.endHospitalId;
    }

    public double getDistance() {
        return this.distance;
    }
}
