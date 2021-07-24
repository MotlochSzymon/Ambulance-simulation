package ambulancesimulation.fileElements;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    private final int patientId;
    private int xCord;
    private int yCord;

    public Patient(int patientId, int xCord, int yCord) {
        this.patientId = patientId;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public Patient(Patient that) {
        this(that.getPatientId(), that.getPatientXCord(), that.getPatientYCord());
    }

    public static List<Patient> cloneList(List<Patient> patientsList) {
        List<Patient> clonedList = new ArrayList<Patient>(patientsList.size());
        for (Patient patient : patientsList) {
            clonedList.add(new Patient(patient));
        }
        return clonedList;
    }

    public int getPatientId() {
        return this.patientId;
    }

    public int getPatientXCord() {
        return this.xCord;
    }

    public int getPatientYCord() {
        return this.yCord;
    }

    public int setPatientXCord(int newXCord) {
        return this.xCord = newXCord;
    }

    public int setPatientYCord(int newYCord) {
        return this.yCord = newYCord;
    }

    public static int findBiggestPatientId(List<Patient> patients) {
        int maxId = patients.get(0).getPatientId();

        for (int i = 1; i < patients.size(); i++) {
            if (patients.get(i).getPatientId() > maxId) {
                maxId = patients.get(i).getPatientId();
            }
        }
        return maxId;
    }

}
