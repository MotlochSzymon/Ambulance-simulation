package ambulancesimulation.enums;

public enum EntryFiles {
    MAP_ELEMENTS_FILE(1),
    PATIENTS_FILE(2);

    private final int value;

    EntryFiles(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
