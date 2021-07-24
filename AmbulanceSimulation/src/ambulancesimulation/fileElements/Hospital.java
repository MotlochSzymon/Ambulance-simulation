package ambulancesimulation.fileElements;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final int hospitalId;
    private final String name;
    private int xCord;
    private int yCord;
    private int bedsAmount;
    private int freeBedsAmount;

    public Hospital(int hospitalId, String name, int xCord, int yCord, int bedsAmount, int freeBedsAmount) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
        this.bedsAmount = bedsAmount;
        this.freeBedsAmount = freeBedsAmount;
    }

    public Hospital(Hospital that) {
        this(that.getHospitalId(), that.getName(), that.getHospitalXCord(), that.getHospitalYCord(), that.getBedsAmount(), that.getFreeBedsAmount());
    }

    public static List<Hospital> cloneList(List<Hospital> hospitalList) {
        List<Hospital> clonedList = new ArrayList<Hospital>(hospitalList.size());
        for (Hospital hospital : hospitalList) {
            clonedList.add(new Hospital(hospital));
        }
        return clonedList;
    }

    public int getHospitalId() {
        return this.hospitalId;
    }

    public int getHospitalXCord() {
        return this.xCord;
    }

    public int getHospitalYCord() {
        return this.yCord;
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

    public int setHospitalXCord(int newXCord) {
        return this.xCord = newXCord;
    }

    public int setHospitalYCord(int newYCord) {
        return this.yCord = newYCord;
    }

}
