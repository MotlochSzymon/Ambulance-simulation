package ambulancesimulation.gui;

public class PatientAdditional {

    private int xCord;
    private int yCord;

    public PatientAdditional(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getPatientAdditionalXCord() {
        return this.xCord;
    }

    public int getPatientAdditionalYCord() {
        return this.yCord;
    }

    public int setPatientAdditionalXCord(int newXCord) {
        return this.xCord = newXCord;
    }

    public int setPatientAdditionalYCord(int newYCord) {
        return this.yCord = newYCord;
    }

}
