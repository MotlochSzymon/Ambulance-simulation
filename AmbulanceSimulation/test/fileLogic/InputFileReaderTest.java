package fileLogic;

import ambulancesimulation.fileLogic.InputFileReader;
import ambulancesimulation.inputAndOutputHandling.InputFilesInterface;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputFileReaderTest {

    static final String MapElementsFileFormatFolderPath = "Test files/fileLogic/MapElements/MapFileFormat/";
    static final String HospitalsFolderPath = "Test files/fileLogic/MapElements/Hospitals/";
    static final String PlacesFolderPath = "Test files/fileLogic/MapElements/Places/";
    static final String StreetFolderPath = "Test files/fileLogic/MapElements/Streets/";

    static final String PatientsFileFormatFolderPath = "Test files/fileLogic/Patients/PatientsFileFormat/";
    static final String PatientsFolderPath = "Test files/fileLogic/Patients/Patients/";

    //Tests of file with map elements
    @Test(expected = java.lang.Exception.class)
    public void readNotExistingMapElementsFile() throws Exception {
        // given
        String fileName = "Dont exist!";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsWrongPathToPatientsFile() throws Exception {
        // given
        String fileName = "wrongPath/Valid file.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongExtension() throws Exception {
        // given
        String fileName = "Wrong extension.png";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readEmptyMapElementsFile() throws Exception {
        // given
        String fileName = "Empty file.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithEmptyRowsInTheBeginning() throws Exception {
        // given
        String fileName = "Extra empty row in the beginning.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithEmptyRowsInTheMiddle() throws Exception {
        // given
        String fileName = "Extra empty row in the middle.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooManyTables() throws Exception {
        // given
        String fileName = "Too many tables.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooLittleTables() throws Exception {
        // given
        String fileName = "Too many tables.txt";
        String filePath = MapElementsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooLittleHospitalColumns() throws Exception {
        // given
        String fileName = "Hospitals - Too little columns.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooManyHospitalColumns() throws Exception {
        // given
        String fileName = "Hospitals - Too many columns.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalIDType() throws Exception {
        // given
        String fileName = "Hospitals - Wrong ID type.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalXType() throws Exception {
        // given
        String fileName = "Hospitals - Wrong X type.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalYType() throws Exception {
        // given
        String fileName = "Hospitals - Wrong Y type.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalBedsType() throws Exception {
        // given
        String fileName = "Hospitals - Wrong beds type.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalFreeBedsType() throws Exception {
        // given
        String fileName = "Hospitals - Wrong free beds type.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithNegativeHospitalBedsValue() throws Exception {
        // given
        String fileName = "Hospitals - Negative beds value.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithNegativeHospitalFreeBedsValue() throws Exception {
        // given
        String fileName = "Hospitals - Negative free beds value.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithMultipleSameHospitalIDs() throws Exception {
        // given
        String fileName = "Hospitals - Multiple same IDs.txt";
        String filePath = HospitalsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooLittlePlacesColumns() throws Exception {
        // given
        String fileName = "Places - Too little columns.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooManyPlacesColumns() throws Exception {
        // given
        String fileName = "Places - Too many columns.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongPlacesIDType() throws Exception {
        // given
        String fileName = "Places - Wrong ID type.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongPlacesXType() throws Exception {
        // given
        String fileName = "Places - Wrong X type.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongPlacesYType() throws Exception {
        // given
        String fileName = "Places - Wrong Y type.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithMultipleSamePlacesIDs() throws Exception {
        // given
        String fileName = "Places - Multiple same IDs.txt";
        String filePath = PlacesFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooLittleStreetsColumns() throws Exception {
        // given
        String fileName = "Streets - Too little columns.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithTooManyStreetsColumns() throws Exception {
        // given
        String fileName = "Streets - Too many columns.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongStreetIDType() throws Exception {
        // given
        String fileName = "Streets - Wrong street ID type.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongHospitalTypeInStreetTable() throws Exception {
        // given
        String fileName = "Streets - Wrong hospital ID type.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithWrongStreetType() throws Exception {
        // given
        String fileName = "Streets - Wrong street distance type.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithStreetContainingIDOfNotExistingHospital() throws Exception {
        // given
        String fileName = "Streets - Hospital ID doesn't exist.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readMapElementsFileWithMultipleSameStreetsIDs() throws Exception {
        // given
        String fileName = "Streets - Multiple same streets IDs.txt";
        String filePath = StreetFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    //Tests of file with patients
    @Test(expected = java.lang.Exception.class)
    public void readNotExistingPatientsFile() throws Exception {
        // given
        String fileName = "Dont exist!";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsWrongPathToPatientsFile() throws Exception {
        // given
        String fileName = "wrongPath/Valid file.txt";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithWrongExtension() throws Exception {
        // given
        String fileName = "Wrong extension.png";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readEmptyPatientsFile() throws Exception {
        // given
        String fileName = "Empty file.txt";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithEmptyRowsInTheBeginning() throws Exception {
        // given
        String fileName = "Extra empty row in the beginning.txt";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithEmptyRowsInTheMiddle() throws Exception {
        // given
        String fileName = "Extra empty row in the middle.txt";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithTooManyTables() throws Exception {
        // given
        String fileName = "Too many tables.txt";
        String filePath = PatientsFileFormatFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithTooLittleColumns() throws Exception {
        // given
        String fileName = "Patients - Too little columns.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithTooManyColumns() throws Exception {
        // given
        String fileName = "Patients - Too many columns.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithWrongIDType() throws Exception {
        // given
        String fileName = "Patients - Wrong ID type.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithWrongXType() throws Exception {
        // given
        String fileName = "Patients - Wrong X type.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileWithWrongYType() throws Exception {
        // given
        String fileName = "Patients - Wrong Y type.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }

    @Test(expected = java.lang.Exception.class)
    public void readPatientsFileMultipleSameIDs() throws Exception {
        // given
        String fileName = "Patients - Multiple same IDs.txt";
        String filePath = PatientsFolderPath + fileName;
        File inputFile = new File(filePath);

        //when
        InputFilesInterface inputFilesReader = new InputFileReader();

        //then
        inputFilesReader.loadPatientsFile(inputFile);
    }
}
