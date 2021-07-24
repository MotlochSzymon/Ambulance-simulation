package ambulancesimulation.fileLogic;

import ambulancesimulation.enums.TableOrderInMapElementsFile;
import ambulancesimulation.enums.EntryFiles;
import ambulancesimulation.enums.TableOrderInPatientsTable;
import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.fileElements.Patient;
import ambulancesimulation.fileElements.Place;
import ambulancesimulation.fileElements.Street;
import ambulancesimulation.gui.MessageWindow;
import ambulancesimulation.inputAndOutputHandling.InputFilesInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader implements InputFilesInterface {

    private int lineNumber;
    private int loadingTableNumber;
    public static List<Hospital> hospitalsList = new ArrayList<>();
    public static List<Patient> patientsList = new ArrayList<>();
    public static List<Street> streetsList = new ArrayList<>();
    public static List<Place> placesList = new ArrayList<>();
    private static final int NUMBER_OF_TABLES_IN_MAP_ELEMENTS_FILE = 3;
    private static final int NUMBER_OF_TABLES_IN_PATIENTS_FILE = 1;
    private static final int NUMBER_OF_COLUMNS_IN_HOSPITALS_TABLE = 6;
    private static final int NUMBER_OF_COLUMNS_IN_PLACES_TABLE = 4;
    private static final int NUMBER_OF_COLUMNS_IN_STREETS_TABLE = 4;
    private static final int NUMBER_OF_COLUMNS_IN_PATIENTS_TABLE = 3;

    private static final String HOSPITAL_TABLE_NAME = "Szpitale";
    private static final String PLACES_TABLE_NAME = "Obiekty";
    private static final String STREETS_TABLE_NAME = "Połączenie producentów z aptekami";
    private static final String PATIENTS_TABLE_NAME = "Pacjenci";

    public InputFileReader() {
        this.lineNumber = 0;
        this.loadingTableNumber = 0;
    }

    @Override
    public void loadMapElementsFile(File mapFile) throws Exception {

        clearAllMapElementsValues();
        BufferedReader br = new BufferedReader(new FileReader(mapFile));

        String row;
        while ((row = br.readLine()) != null) {
            if (!row.isEmpty()) {
                loadAndValidateMapElementsTableRow(row);
            }
        }

        checkLoadedTablesAmount(EntryFiles.MAP_ELEMENTS_FILE.getValue());
        checkMapElementsRecordsAmount();

        MessageWindow.displayMessage("All map data was successfully loaded!");
    }

    @Override
    public void loadPatientsFile(File patientsFile) throws Exception {

        clearAllPatientsValues();
        BufferedReader br = new BufferedReader(new FileReader(patientsFile));

        String row;
        while ((row = br.readLine()) != null) {
            loadAndValidatePatientsTableRow(row);
        }

        checkLoadedTablesAmount(EntryFiles.PATIENTS_FILE.getValue());

        MessageWindow.displayMessage("All patients data was successfully loaded!");
    }

    private void clearAllMapElementsValues() {
        this.lineNumber = 0;
        this.loadingTableNumber = 0;
        this.hospitalsList.clear();
        this.placesList.clear();
        this.streetsList.clear();
    }

    private void clearAllPatientsValues() {
        this.lineNumber = 0;
        this.loadingTableNumber = 0;
        this.patientsList.clear();
    }

    private void loadAndValidateMapElementsTableRow(String row) throws Exception {
        lineNumber++;
        boolean isDescriptionRow = validateRowAndCheckIsDescriptionRow(row);

        String[] columnValues = row.split(" [|] ");

        if (!isDescriptionRow) {

            if (this.loadingTableNumber == TableOrderInMapElementsFile.HOSPITALS_TABLE.getValue()) {
                loadHospitalRow(columnValues);
            } else if (this.loadingTableNumber == TableOrderInMapElementsFile.PLACES_TABLE.getValue()) {
                loadPlaceRow(columnValues);
            } else if (this.loadingTableNumber == TableOrderInMapElementsFile.STREETS_TABLE.getValue()) {
                loadStreetRow(columnValues);
            } else {
                throw new java.lang.Exception("Before adding data you must add table description! Line number: " + this.lineNumber);
            }
        }
    }

    private void loadAndValidatePatientsTableRow(String row) throws Exception {
        lineNumber++;
        boolean isDescriptionRow = validateRowAndCheckIsDescriptionRow(row);

        String[] columnValues = row.split(" [|] ");

        if (!isDescriptionRow) {

            if (this.loadingTableNumber == TableOrderInPatientsTable.PATIENTS_TABLE.getValue()) {
                loadPatientsRow(columnValues);
            } else {
                throw new java.lang.Exception("Before adding data you must add table description! Line number: " + this.lineNumber);
            }
        }
    }

    private void loadPatientsRow(String[] columnValues) throws Exception {
        checkNumberOfColumns(columnValues, NUMBER_OF_COLUMNS_IN_PATIENTS_TABLE, PATIENTS_TABLE_NAME);
        int patientId = loadColumnAsIntegerValue(columnValues[0], "Id pacjenta");
        int patientXCord = loadColumnAsIntegerValue(columnValues[1], "Patient x coordinate");
        int patientYCord = loadColumnAsIntegerValue(columnValues[2], "Patient y coordinate");
        addPatientToList(patientId, patientXCord, patientYCord);
    }

    private void loadHospitalRow(String[] columnValues) throws Exception {
        checkNumberOfColumns(columnValues, NUMBER_OF_COLUMNS_IN_HOSPITALS_TABLE, HOSPITAL_TABLE_NAME);
        int hospitalId = loadColumnAsIntegerValue(columnValues[0], "Hospital id");
        String hospitalName = columnValues[1];
        int hospitalXCord = loadColumnAsIntegerValue(columnValues[2], "Hospital x coordinate");
        int hospitalYCord = loadColumnAsIntegerValue(columnValues[3], "Hospital y coordinate");
        int bedsAmount = loadColumnAsUnsignedValue(columnValues[4], "Amount of beds");
        int freeBedsAmount = loadColumnAsUnsignedValue(columnValues[5], "Amount of free beds");
        addHospitalToList(hospitalId, hospitalName, hospitalXCord, hospitalYCord, bedsAmount, freeBedsAmount);
    }

    private void loadPlaceRow(String[] columnValues) throws Exception {
        checkNumberOfColumns(columnValues, NUMBER_OF_COLUMNS_IN_PLACES_TABLE, PLACES_TABLE_NAME);
        int placeId = loadColumnAsIntegerValue(columnValues[0], "Object id");
        String placeName = columnValues[1];
        int placeXCord = loadColumnAsIntegerValue(columnValues[2], "Object x coordinate");
        int placeYCord = loadColumnAsIntegerValue(columnValues[3], "Object x coordinate");
        addPlaceToList(placeId, placeName, placeXCord, placeYCord);
    }

    private void loadStreetRow(String[] columnValues) throws Exception {
        checkNumberOfColumns(columnValues, NUMBER_OF_COLUMNS_IN_STREETS_TABLE, STREETS_TABLE_NAME);
        int streetId = loadColumnAsIntegerValue(columnValues[0], "Id drogi");
        int startHospitalId = loadColumnAsIntegerValue(columnValues[1], "Start hospital id");
        int endHospitalId = loadColumnAsIntegerValue(columnValues[2], "End hospital id");
        double distance = loadColumnAsNonNegativeDoubleValue(columnValues[3], "Distance");
        addStreetToList(streetId, startHospitalId, endHospitalId, distance);
    }

    private void addPatientToList(int patientId, int patientXCord, int patientYCord) throws Exception {
        if (isAlreadyPatientAdded(patientId)) {
            throw new java.lang.Exception("Patient with id: " + patientId + " has been already added! Number line: " + this.lineNumber);
        } else {
            this.patientsList.add(new Patient(patientId, patientXCord, patientYCord));
        }
    }

    private void addHospitalToList(int hospitalId, String hospitalName, int hospitalXCord, int hospitalYCord, int bedsAmount, int freeBedsAmount) throws Exception {
        if (isAlreadyHospitalAdded(hospitalId)) {
            throw new java.lang.Exception("Hospital with id: " + hospitalId + " has been already added! Line number: " + this.lineNumber);
        } else {
            this.hospitalsList.add(new Hospital(hospitalId, hospitalName, hospitalXCord, hospitalYCord, bedsAmount, freeBedsAmount));
        }
    }

    private void addPlaceToList(int placeId, String placeName, int placeXCord, int placeYCord) throws Exception {
        if (isAlreadyPlaceAdded(placeId)) {
            throw new java.lang.Exception("Object id: " + placeId + " has been already added! Number line: " + this.lineNumber);
        } else {
            this.placesList.add(new Place(placeId, placeName, placeXCord, placeYCord));
        }
    }

    private void addStreetToList(int streetId, int startHospitalId, int endHospitalId, double distance) throws Exception {
        if (isAlreadyStreetAdded(streetId)) {
            throw new java.lang.Exception("Street with id: " + streetId + " has been already added! Line number: " + this.lineNumber);
        }

        if (!doesHospitalExist(startHospitalId)) {
            throw new java.lang.Exception("Given start hospital in streets table doesn't exist! Line number: " + this.lineNumber);
        }

        if (!doesHospitalExist(endHospitalId)) {
            throw new java.lang.Exception("Given end hospital in streets table doesn't exist! Line number: " + this.lineNumber);
        }

        this.streetsList.add(new Street(streetId, startHospitalId, endHospitalId, distance));

    }

    private boolean isAlreadyPatientAdded(int patientId) {
        for (int i = 0; i < this.patientsList.size(); i++) {
            if (this.patientsList.get(i).getPatientId() == patientId) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyHospitalAdded(int hospitalId) {
        for (int i = 0; i < this.hospitalsList.size(); i++) {
            if (this.hospitalsList.get(i).getHospitalId() == hospitalId) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyPlaceAdded(int placeId) {
        for (int i = 0; i < this.placesList.size(); i++) {
            if (this.placesList.get(i).getPlaceId() == placeId) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyStreetAdded(int streetId) {
        for (int i = 0; i < this.streetsList.size(); i++) {
            if (this.streetsList.get(i).getStreetId() == streetId) {
                return true;
            }
        }
        return false;
    }

    private boolean validateRowAndCheckIsDescriptionRow(String row) throws Exception {
        if (row.chars().allMatch(Character::isWhitespace)) {
            throw new java.lang.Exception("Empty row was given! You must delete it. Line number: " + this.lineNumber);
        } else if (row.charAt(0) == '#') {
            this.loadingTableNumber++;

            if (loadingTableNumber > NUMBER_OF_TABLES_IN_MAP_ELEMENTS_FILE) {
                throw new java.lang.Exception("Amount of given tables is too large!. Line number: " + this.lineNumber);
            }

            return true;
        } else if (row.charAt(0) == ' ') {
            throw new java.lang.Exception("Row can't start with empty char: " + this.lineNumber);
        }
        return false;
    }

    private void checkNumberOfColumns(String[] columnValues, int validValue, String tableName) throws Exception {
        if (columnValues.length < validValue) {
            throw new java.lang.Exception("Table " + tableName + " has too little columns! Line number: " + this.lineNumber);
        }

        if (columnValues.length > validValue) {
            throw new java.lang.Exception("Table " + tableName + " has too many columns! Line number:: " + this.lineNumber);
        }
    }

    private int loadColumnAsIntegerValue(String valueToRead, String columnName) throws Exception {
        int columnValue = 0;
        try {
            columnValue = Integer.parseInt(valueToRead.trim());

        } catch (NumberFormatException nfe) {
            throw new java.lang.Exception("Column value: " + columnName + " must be integer! Line number: " + this.lineNumber);
        }

        return columnValue;
    }

    private double loadColumnAsNonNegativeDoubleValue(String valueToRead, String columnName) throws Exception {
        double columnValue = 0;
        try {
            columnValue = Double.parseDouble(valueToRead.trim().replace(',', '.'));
            columnValue = Math.floor(columnValue * 100) / 100;

            if (columnValue < 0) {
                throw new java.lang.Exception("Column value: " + columnName + " can't be negative number! Line number: " + this.lineNumber);
            }

        } catch (NumberFormatException nfe) {
            throw new java.lang.Exception("Column value: " + columnName + "must be real number! Line number: " + this.lineNumber);
        }
        return columnValue;
    }

    private int loadColumnAsUnsignedValue(String valueToRead, String columnName) throws Exception {
        int columnValue = 0;
        try {
            columnValue = Integer.parseInt(valueToRead.trim());
            if (columnValue < 0) {
                throw new java.lang.Exception("Column value: " + columnName + " must be plus integer! Numer lini: " + this.lineNumber);
            }
        } catch (NumberFormatException nfe) {
            throw new java.lang.Exception("Column value: " + columnName + " must be integer " + this.lineNumber);
        }

        return columnValue;
    }

    private void checkLoadedTablesAmount(int table) throws Exception {
        if (lineNumber == 0) {
            throw new java.lang.Exception("Podany plik jest pusty!");
        }

        if (table == EntryFiles.MAP_ELEMENTS_FILE.getValue()) {
            if (loadingTableNumber != NUMBER_OF_TABLES_IN_MAP_ELEMENTS_FILE) {
                throw new java.lang.Exception("There is no enough tables in map file!");
            }

        } else if (table == EntryFiles.PATIENTS_FILE.getValue()) {
            if (loadingTableNumber != NUMBER_OF_TABLES_IN_PATIENTS_FILE) {
                throw new java.lang.Exception("There is no enough tables in patients file!");
            }
        }
    }

    private void checkMapElementsRecordsAmount() throws Exception {
        if (this.hospitalsList.size() < 2) {
            throw new java.lang.Exception("Hospital table must have at least 2 records!");
        } else if (this.streetsList.size() < 1) {
            throw new java.lang.Exception("Streets table must have at least 1 record!");
        }
    }

    private boolean doesHospitalExist(int hospitalId) {
        for (int i = 0; i < this.hospitalsList.size(); i++) {
            if (this.hospitalsList.get(i).getHospitalId() == hospitalId) {
                return true;
            }
        }
        return false;
    }

}
