package ambulancesimulation.gui;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.fileElements.Patient;
import ambulancesimulation.fileElements.Place;
import ambulancesimulation.fileLogic.InputFileReader;
import java.util.List;

public class IconsAdjustment {

    public static List<Hospital> adjustedHospitals;
    public static List<Place> adjustedPlaces;
    public static List<Patient> adjustedPatients;
    public static List<PatientAdditional> adjustedAdditionalPatients;

    public static int maxX = Integer.MIN_VALUE;
    public static int maxY = Integer.MIN_VALUE;
    public static int minX = Integer.MAX_VALUE;
    public static int minY = Integer.MAX_VALUE;

    public int hospitalsCount = 1;
    public int placesCount = 1;
    public int patientsCount = 1;

    private final int margin = 20;

    public void IconsAdjustment() {
        hospitalsCount = InputFileReader.hospitalsList.size();
        placesCount = InputFileReader.placesList.size();
        patientsCount = InputFileReader.patientsList.size();

        adjustedHospitals = InputFileReader.hospitalsList;
        adjustedPlaces = InputFileReader.placesList;
        adjustedPatients = InputFileReader.patientsList;
        adjustedAdditionalPatients = GUI.patientsAdditionalList;

        findMaximumAndMinimumXYCord();
        //System.out.println(maxX + " " + minX + " " + maxY + " " + minY);
        setNewCoordinates();
    }

    private void findMaximumAndMinimumXYCord() {
        for (int i = 0; i < hospitalsCount; i++) {
            if (InputFileReader.hospitalsList.get(i).getHospitalXCord() > maxX) {
                maxX = InputFileReader.hospitalsList.get(i).getHospitalXCord();
            }
            if (InputFileReader.hospitalsList.get(i).getHospitalYCord() > maxY) {
                maxY = InputFileReader.hospitalsList.get(i).getHospitalYCord();
            }
            if (InputFileReader.hospitalsList.get(i).getHospitalXCord() < minX) {
                minX = InputFileReader.hospitalsList.get(i).getHospitalXCord();
            }
            if (InputFileReader.hospitalsList.get(i).getHospitalYCord() < minY) {
                minY = InputFileReader.hospitalsList.get(i).getHospitalYCord();
            }
        }
        for (int i = 0; i < placesCount; i++) {
            if (InputFileReader.placesList.get(i).getPlaceXCord() > maxX) {
                maxX = InputFileReader.placesList.get(i).getPlaceXCord();
            }
            if (InputFileReader.placesList.get(i).getPlaceYCord() > maxY) {
                maxY = InputFileReader.placesList.get(i).getPlaceYCord();
            }
            if (InputFileReader.placesList.get(i).getPlaceXCord() < minX) {
                minX = InputFileReader.placesList.get(i).getPlaceXCord();
            }
            if (InputFileReader.placesList.get(i).getPlaceYCord() < minY) {
                minY = InputFileReader.placesList.get(i).getPlaceYCord();
            }
        }
        for (int i = 0; i < patientsCount; i++) {
            if (InputFileReader.patientsList.get(i).getPatientXCord() > maxX) {
                maxX = InputFileReader.patientsList.get(i).getPatientXCord();
            }
            if (InputFileReader.patientsList.get(i).getPatientYCord() > maxY) {
                maxY = InputFileReader.patientsList.get(i).getPatientYCord();
            }
            if (InputFileReader.patientsList.get(i).getPatientXCord() < minX) {
                minX = InputFileReader.patientsList.get(i).getPatientXCord();
            }
            if (InputFileReader.patientsList.get(i).getPatientYCord() < minY) {
                minY = InputFileReader.patientsList.get(i).getPatientYCord();
            }
        }
    }

    private void setNewCoordinates() {

        if (minX < 0) {
            for (int i = 0; i < hospitalsCount; i++) {
                adjustedHospitals.get(i).setHospitalXCord(adjustedHospitals.get(i).getHospitalXCord() - minX);
            }
            for (int i = 0; i < placesCount; i++) {
                adjustedPlaces.get(i).setPlaceXCord(adjustedPlaces.get(i).getPlaceXCord() - minX);
            }
            for (int i = 0; i < patientsCount; i++) {
                adjustedPatients.get(i).setPatientXCord(adjustedPatients.get(i).getPatientXCord() - minX);
            }
            maxX -= minX;
        }

        if (minY < 0) {
            for (int i = 0; i < hospitalsCount; i++) {
                adjustedHospitals.get(i).setHospitalYCord(adjustedHospitals.get(i).getHospitalYCord() - minY);
            }
            for (int i = 0; i < placesCount; i++) {
                adjustedPlaces.get(i).setPlaceYCord(adjustedPlaces.get(i).getPlaceYCord() - minY);
            }
            for (int i = 0; i < patientsCount; i++) {
                adjustedPatients.get(i).setPatientYCord(adjustedPatients.get(i).getPatientYCord() - minY);
            }
            maxY -= minY;
        }

        for (int i = 0; i < hospitalsCount; i++) {
            adjustedHospitals.get(i).setHospitalXCord(adjustedHospitals.get(i).getHospitalXCord() * GUI.mapWidth / maxX + margin);
            adjustedHospitals.get(i).setHospitalYCord(adjustedHospitals.get(i).getHospitalYCord() * GUI.mapHeight / maxY + margin);
        }
        for (int i = 0; i < placesCount; i++) {
            adjustedPlaces.get(i).setPlaceXCord(adjustedPlaces.get(i).getPlaceXCord() * GUI.mapWidth / maxX + margin);
            adjustedPlaces.get(i).setPlaceYCord(adjustedPlaces.get(i).getPlaceYCord() * GUI.mapHeight / maxY + margin);
        }
        for (int i = 0; i < patientsCount; i++) {
            adjustedPatients.get(i).setPatientXCord(adjustedPatients.get(i).getPatientXCord() * GUI.mapWidth / maxX + margin);
            adjustedPatients.get(i).setPatientYCord(adjustedPatients.get(i).getPatientYCord() * GUI.mapHeight / maxY + margin);
        }
    }

    public static void setNewCoordinatesForSinglePatient(int index) {

        if (minX < 0) {
            adjustedAdditionalPatients.get(index).setPatientAdditionalXCord(adjustedAdditionalPatients.get(index).getPatientAdditionalXCord() - minX);
            maxX -= minX;
        }

        if (minY < 0) {
            adjustedAdditionalPatients.get(index).setPatientAdditionalYCord(adjustedAdditionalPatients.get(index).getPatientAdditionalYCord() - minY);
            maxY -= minY;
        }

        adjustedAdditionalPatients.get(index).setPatientAdditionalXCord(adjustedAdditionalPatients.get(index).getPatientAdditionalXCord() * GUI.mapWidth / maxX);
        adjustedAdditionalPatients.get(index).setPatientAdditionalYCord(adjustedAdditionalPatients.get(index).getPatientAdditionalYCord() * GUI.mapHeight / maxY);
    }
}
