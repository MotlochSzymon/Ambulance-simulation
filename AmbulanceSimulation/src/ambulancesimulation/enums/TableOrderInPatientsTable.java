package ambulancesimulation.enums;

public enum TableOrderInPatientsTable {
    PATIENTS_TABLE(1);

    private final int value;

    TableOrderInPatientsTable(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
