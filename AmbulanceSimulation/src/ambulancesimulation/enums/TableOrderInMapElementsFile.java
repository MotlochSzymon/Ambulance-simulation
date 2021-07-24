package ambulancesimulation.enums;

public enum TableOrderInMapElementsFile {
    HOSPITALS_TABLE(1),
    PLACES_TABLE(2),
    STREETS_TABLE(3);

    private final int value;

    TableOrderInMapElementsFile(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
