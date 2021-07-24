package ambulancesimulation.gui;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.fileElements.Patient;
import ambulancesimulation.fileElements.Place;
import ambulancesimulation.fileElements.Street;
import ambulancesimulation.fileLogic.InputFileReader;
import ambulancesimulation.inputAndOutputHandling.InputFilesInterface;
import ambulancesimulation.mapHandler.BordersMapCheckingIntereface;
import ambulancesimulation.mapLogic.ConvexHullAlgorithm;
import ambulancesimulation.mapLogic.Point;
import ambulancesimulation.simulation.Simulation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUI extends Application {

    public static List<PatientAdditional> patientsAdditionalList = new ArrayList<>();

    private final Stage window = new Stage();
    private Button startButton = new Button();
    private Button stopButton = new Button();
    private Button loadMapButton = new Button();
    private Button loadPatientsButton = new Button();
    private final Button addPatientButton = new Button();
    private TextArea messageArea = new TextArea();
    private int sumLines = 1;
    private boolean mapLoaded = false;
    private boolean patientsLoaded = false;
    private boolean mapCreated = false;
    private Pane mapArea = new Pane();

    private int hospitalsCount;
    private int placesCount;
    private int patientsCount;
    private int streetsCount;

    public static int mapHeight;
    public static int mapWidth;
    public static List<ImageView> hospitalImages = new ArrayList<>();
    public static List<ImageView> placeImages = new ArrayList<>();
    public static List<ImageView> patientImages = new ArrayList<>();
    public static List<Line> streetImages = new ArrayList<>();
    public static List<Line> bordersImages = new ArrayList<>();
    public static boolean isSimulationRunning = false;

    private List<Hospital> loadedHospitals = new ArrayList<>();
    private List<Patient> loadedPatients;
    private List<Place> loadedPlaces;
    private List<Street> loadedStreets;
    List<Point> borders;
    List<Point> bordersScaledValues;

    BordersMapCheckingIntereface mapChecker = new ConvexHullAlgorithm();
    private final int margin = 20;
    private final int iconSize = 40;

    Simulation simulation;

    public static double simulationSpeed = 1;
    public static int nextPatientId = 0;
    public static boolean shouldStopSimulation = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox leftMenu = createLeftPartOfInterface();
        VBox rightMenu = createRightPartOfInterface();
        messageArea = createMessageArea();
        mapArea = createMapArea();
        BorderPane layout = drawMenu(leftMenu, rightMenu, messageArea, mapArea);

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        window.setScene(scene);
        window.setTitle("Ambulance Simulation");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setResizable(false);

        window.show();
    }

    private BorderPane drawMenu(VBox leftMenu, VBox rightMenu, TextArea messageArea, Pane map) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setLeft(leftMenu);
        layout.setRight(rightMenu);
        layout.setCenter(map);
        layout.setBottom(messageArea);
        return layout;
    }

    private VBox createLeftPartOfInterface() {
        VBox leftMenu = new VBox(20);
        leftMenu.setAlignment(Pos.CENTER);

        loadMapButton.setText("Load Map");
        loadMapButton = setLoadMapButtonAction();
        loadPatientsButton.setText("Load Patients");
        loadPatientsButton = setLoadPatientsAction();

        Label newPatientLabel = new Label("New patient:");
        newPatientLabel.setPadding(new Insets(30, 0, 0, 0));

        Label coordinateXLabel = new Label("X coordinate:");
        coordinateXLabel.setPadding(new Insets(0, 5, 0, 0));

        TextField patientsX = new TextField();
        patientsX.setPrefWidth(80);
        patientsX.setPadding(Insets.EMPTY);

        Label coordinateYLabel = new Label("Y coordinate:");
        coordinateYLabel.setPadding(new Insets(0, 5, 0, 0));

        TextField patientsY = new TextField();
        patientsY.setPrefWidth(80);
        patientsY.setPadding(Insets.EMPTY);

        HBox hboxForX = new HBox(coordinateXLabel, patientsX);
        HBox hboxForY = new HBox(coordinateYLabel, patientsY);

        addPatientButton.setText("Add patient");
        addPatientButton.setDisable(true);
        addPatientButton.setOnAction(e -> {
            if (areAdditionalPatientsCoordinatesValid(patientsX, patientsY)) {
                addAddiitonalPatient(patientsX, patientsY);
            }
        });

        leftMenu.getChildren().addAll(loadMapButton, loadPatientsButton, newPatientLabel, hboxForX, hboxForY, addPatientButton);

        return leftMenu;
    }

    private VBox createRightPartOfInterface() throws Exception {
        VBox rightMenu = new VBox(20);
        rightMenu.setAlignment(Pos.CENTER);

        Label checkBoxLabel = new Label("Interface elements: ");
        CheckBox checkBoxHospitals = new CheckBox("Hospitals");
        CheckBox checkBoxPatients = new CheckBox("Patients");
        CheckBox checkBoxPlaces = new CheckBox("Places");
        CheckBox checkBoxBorders = new CheckBox("Borders");
        checkBoxHospitals.setSelected(true);
        checkBoxPatients.setSelected(true);
        checkBoxPlaces.setSelected(true);
        checkBoxBorders.setSelected(true);
        checkBoxHospitals.selectedProperty().addListener((ov, oldValue, newValue) -> {
            hideHospitals(newValue);
        });
        checkBoxPlaces.selectedProperty().addListener((ov, oldValue, newValue) -> {
            hidePlaces(newValue);
        });
        checkBoxPatients.selectedProperty().addListener((ov, oldValue, newValue) -> {
            hidePatients(newValue);
        });
        checkBoxBorders.selectedProperty().addListener((ov, oldValue, newValue) -> {
            hideBorders(newValue);
        });

        Label simVelocityLabel = new Label("Simulation velocity:");
        simVelocityLabel.setPadding(new Insets(30, 0, 0, 0));

        Slider slider = new Slider(0, 2, 1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(0.25);
        slider.setSnapToTicks(true);

        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                GUI.simulationSpeed = (double) newValue;
            }

        }
        );

        startButton = new Button("Start simulation");

        startButton.setId(
                "start-button");
        startButton.setDisable(
                true);
        startButton.setOnAction(e
                -> {
            if( loadedPatients.size() < 1 ){
                MessageWindow.displayMessage("Before starting simulation you have to load map with at least 1 patient on map");
                return;
            }
            loadMapButton.setDisable(true);
            loadPatientsButton.setDisable(true);
            startButton.setDisable(true);
            stopButton.setDisable(false);
            messageArea.clear();
            sumLines = 1;
            displayActionDescription("Simulation started");
            try {
                this.simulation = new Simulation(this.loadedHospitals, this.loadedPlaces, this.loadedStreets, this.loadedPatients, this.borders, this);
                isSimulationRunning = true;
                simulation.start();
            } catch (Exception ex) {
                MessageWindow.displayMessage("Simulation error: " + ex);
            }
        }
        );

        stopButton = new Button("Stop simulation");

        stopButton.setId(
                "stop-button");
        stopButton.setDisable(
                true);
        stopButton.setOnAction(e
                -> {
            loadMapButton.setDisable(false);
            loadPatientsButton.setDisable(false);
            startButton.setDisable(false);
            stopButton.setDisable(true);
            shouldStopSimulation = true;
            displayActionDescription("Simulation will be stopped after delivering last patient");
        }
        );

        Button exitButton = new Button("Exit");

        VBox.setMargin(exitButton,
                new Insets(30, 0, 10, 0));
        exitButton.setOnAction(e
                -> closeProgram());

        rightMenu.getChildren()
                .addAll(checkBoxLabel, checkBoxHospitals, checkBoxPatients, checkBoxPlaces, checkBoxBorders,
                        simVelocityLabel, slider, startButton, stopButton, exitButton);

        return rightMenu;
    }

    private TextArea createMessageArea() {
        messageArea.setEditable(false);
        messageArea.setFocusTraversable(false);
        return messageArea;
    }

    private Pane createMapArea() {
        mapArea.setId("map-area");
        return mapArea;
    }

    private void closeProgram() {
        String message = "Are you sure you want to exit program? ";
        if (!stopButton.isDisabled()) {
            message = "Are you sure you want to exit program?  Simulation is still activated";
        }

        boolean answer = MessageWindow.displayConfirmation(message);
        if (answer) {
            window.close();
        }
    }

    private Button setLoadMapButtonAction() {
        loadMapButton.setOnAction(e -> {
            try {
                loadMap();
                mapLoaded = true;

                if (mapCreated) {
                    removePreviousMap();
                    mapCreated = false;
                    startButton.setDisable(true);
                    addPatientButton.setDisable(true);
                }

                if (mapLoaded == true && patientsLoaded == true) {
                    createMap();
                    mapCreated = true;
                    mapLoaded = false;
                    patientsLoaded = false;
                    startButton.setDisable(false);
                    addPatientButton.setDisable(false);
                }

            } catch (Exception ex) {
                MessageWindow.displayMessage(ex.toString().replace("java.lang.Exception: ", ""));
            }
        });
        return loadMapButton;
    }

    private Button setLoadPatientsAction() {
        loadPatientsButton.setOnAction(e -> {
            try {
                loadPatients();
                patientsLoaded = true;

                if (mapCreated) {
                    removePreviousMap();
                    mapCreated = false;
                    startButton.setDisable(true);
                    addPatientButton.setDisable(true);
                }

                if (mapLoaded == true && patientsLoaded == true) {
                    createMap();
                    mapCreated = true;
                    mapLoaded = false;
                    patientsLoaded = false;
                    startButton.setDisable(false);
                    addPatientButton.setDisable(false);
                }

            } catch (Exception ex) {
                MessageWindow.displayMessage(ex.toString().replace("java.lang.Exception: ", ""));
            }
        });
        return loadPatientsButton;
    }

    private void loadMap() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*txt"));
        File selectedFile = fileChooser.showOpenDialog(window);
        InputFilesInterface inputFilesReader = new InputFileReader();
        inputFilesReader.loadMapElementsFile(selectedFile);

        this.loadedHospitals = Hospital.cloneList(InputFileReader.hospitalsList);
        this.loadedPlaces = Place.cloneList(InputFileReader.placesList);
        this.loadedStreets = Street.cloneList(InputFileReader.streetsList);

        this.borders = findAllBorders(this.loadedHospitals, this.loadedPlaces);
    }

    private void loadPatients() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*txt"));
        File selectedFile = fileChooser.showOpenDialog(window);
        InputFilesInterface inputFilesReader = new InputFileReader();
        inputFilesReader.loadPatientsFile(selectedFile);

        this.loadedPatients = Patient.cloneList(InputFileReader.patientsList);

        if (loadedPatients.size() > 0) {
            nextPatientId = Math.max(Patient.findBiggestPatientId(loadedPatients) + 1, nextPatientId + 1);
        }

    }

    public void displayActionDescription(String message) {
        if (messageArea.getText().isEmpty()) {
            messageArea.appendText("[1] " + message);
        } else {
            messageArea.setText("[" + sumLines + "] " + message + "\n" + messageArea.getText());

        }
        sumLines++;
    }

    private boolean areAdditionalPatientsCoordinatesValid(TextField xCoordinate, TextField yCoordinate) {
        int x;
        int y;

        if (xCoordinate.getText().isEmpty() && yCoordinate.getText().isEmpty()) {
            MessageWindow.displayMessage("You didn't enter any coordinates!");
            return false;
        } else if (xCoordinate.getText().isEmpty()) {
            MessageWindow.displayMessage("X coordinate area is empty!");
            return false;
        } else if (yCoordinate.getText().isEmpty()) {
            MessageWindow.displayMessage("Y coordinate area is empty!");
            return false;
        }

        try {
            x = Integer.parseInt(xCoordinate.getText());
        } catch (NumberFormatException e) {
            MessageWindow.displayMessage("Value of X coordinate " + xCoordinate.getText() + " is not an integer!");
            return false;
        }

        try {
            y = Integer.parseInt(yCoordinate.getText());
        } catch (NumberFormatException e) {
            MessageWindow.displayMessage("Value of Y coordinate " + yCoordinate.getText() + " is not an integer!");
            return false;
        }

        if (this.mapChecker.checkIfPointIsInsideBorders(borders, new Point(x, y))) {
            MessageWindow.displayMessage("Patient with coordinates: ( " + x + " , " + y + " ) was added to the map.");
        } else {
            MessageWindow.displayMessage("Patient with coordinates: ( " + x + " , " + y + " ) is outside country and won't be added.");
            xCoordinate.setText("");
            yCoordinate.setText("");
            return false;
        }

        return true;
    }

    private void addAddiitonalPatient(TextField xCoordinate, TextField yCoordinate) {

        try {
            int x = Integer.parseInt(xCoordinate.getText());
            int y = Integer.parseInt(yCoordinate.getText());

            if (isSimulationRunning) {
                this.simulation.addAdditionalPatientToSimulation(new Patient(nextPatientId, x, y));
            } else {
                this.loadedPatients.add(new Patient(nextPatientId, x, y));
                nextPatientId++;
            }

            patientsAdditionalList.add(new PatientAdditional(x, y));
            int index = patientsAdditionalList.size() - 1;

            IconsAdjustment.setNewCoordinatesForSinglePatient(index);
            x = IconsAdjustment.adjustedAdditionalPatients.get(index).getPatientAdditionalXCord();
            y = IconsAdjustment.adjustedAdditionalPatients.get(index).getPatientAdditionalYCord();

            ImageView patient = new ImageView(new Image(new FileInputStream("Ikony/patient40.png")));
            patientImages.add(patient);
            patientsCount++;

            patient.setTranslateX(x);
            patient.setTranslateY(y);

            mapArea.getChildren().add(patient);

            xCoordinate.setText("");
            yCoordinate.setText("");

        } catch (FileNotFoundException ex) {
            System.out.println("Dodawanie pacjenta na ekran nie powiodło się!");
        }
    }

    private void createMap() {
        mapHeight = (int) mapArea.getHeight() - iconSize - 2 * margin;
        mapWidth = (int) mapArea.getWidth() - iconSize - 2 * margin;

        IconsAdjustment adjustIcons = new IconsAdjustment();
        adjustIcons.IconsAdjustment();

        try {
            displayAllBorders();
            addStreets();
        } catch (Exception ex) {
            MessageWindow.displayMessage("Dodawanie ulicy na ekran nie powiodło się!");
        }

        try {
            addHospitals();
        } catch (FileNotFoundException ex) {
            MessageWindow.displayMessage("Dodawanie szpitala na ekran nie powiodło się!");
        }

        try {
            addPlaces();
        } catch (FileNotFoundException ex) {
            MessageWindow.displayMessage("Dodawanie obiektu na ekran nie powiodło się!");
        }

        try {
            addPatients();
        } catch (FileNotFoundException ex) {
            MessageWindow.displayMessage("Dodawanie pacjenta na ekran nie powiodło się!");
        }

    }

    private void addHospitals() throws FileNotFoundException {
        hospitalsCount = IconsAdjustment.adjustedHospitals.size();

        for (int i = 0; i < hospitalsCount; i++) {
            displayHospitals(IconsAdjustment.adjustedHospitals.get(i).getHospitalXCord(), IconsAdjustment.adjustedHospitals.get(i).getHospitalYCord());
        }
    }

    private void addPlaces() throws FileNotFoundException {
        placesCount = IconsAdjustment.adjustedPlaces.size();

        for (int i = 0; i < placesCount; i++) {
            displayPlaces(IconsAdjustment.adjustedPlaces.get(i).getPlaceXCord(), IconsAdjustment.adjustedPlaces.get(i).getPlaceYCord());
        }
    }

    private void addPatients() throws FileNotFoundException {
        patientsCount = IconsAdjustment.adjustedPatients.size();

        for (int i = 0; i < patientsCount; i++) {
            displayPatients(IconsAdjustment.adjustedPatients.get(i).getPatientXCord(), IconsAdjustment.adjustedPatients.get(i).getPatientYCord());
        }
    }

    private void addStreets() {
        streetsCount = InputFileReader.streetsList.size();

        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        for (int i = 0; i < streetsCount; i++) {
            for (Hospital hospital : InputFileReader.hospitalsList) {
                if (hospital.getHospitalId() == InputFileReader.streetsList.get(i).getStartHospitalId()) {
                    x1 = hospital.getHospitalXCord();
                    y1 = hospital.getHospitalYCord();
                }
                if (hospital.getHospitalId() == InputFileReader.streetsList.get(i).getEndHospitalId()) {
                    x2 = hospital.getHospitalXCord();
                    y2 = hospital.getHospitalYCord();
                }
            }
            displayStreets(x1, y1, x2, y2);
        }

    }

    private Pane displayHospitals(int x, int y) throws FileNotFoundException {

        ImageView hospital = new ImageView(new Image(new FileInputStream("Ikony/hospital40.png")));
        hospitalImages.add(hospital);

        hospital.setTranslateX(x);
        hospital.setTranslateY(y);

        mapArea.getChildren().add(hospital);

        return mapArea;
    }

    private Pane displayPlaces(int x, int y) throws FileNotFoundException {

        ImageView place = new ImageView(new Image(new FileInputStream("Ikony/place40.png")));
        placeImages.add(place);

        place.setTranslateX(x);
        place.setTranslateY(y);

        mapArea.getChildren().add(place);

        return mapArea;
    }

    private Pane displayPatients(int x, int y) throws FileNotFoundException {

        ImageView patient = new ImageView(new Image(new FileInputStream("Ikony/patient40.png")));
        patientImages.add(patient);

        patient.setTranslateX(x);
        patient.setTranslateY(y);

        mapArea.getChildren().add(patient);

        return mapArea;
    }

    private Pane displayStreets(int x1, int y1, int x2, int y2) {

        Line street = new Line(x1, y1, x2, y2);
        street.setStyle("-fx-stroke: brown;");
        street.setStrokeWidth(3);
        streetImages.add(street);

        mapArea.getChildren().add(street);

        return mapArea;
    }

    private Pane displayAllBorders() {

        this.bordersScaledValues = findAllBorders(InputFileReader.hospitalsList, InputFileReader.placesList);

        double x1, y1, x2, y2;

        for (int i = 0; i < this.bordersScaledValues.size(); i++) {

            x1 = this.bordersScaledValues.get(i).getX();
            y1 = this.bordersScaledValues.get(i).getY();

            if (i + 1 >= this.bordersScaledValues.size()) {
                x2 = this.bordersScaledValues.get(0).getX();
                y2 = this.bordersScaledValues.get(0).getY();
            } else {
                x2 = this.bordersScaledValues.get(i + 1).getX();
                y2 = this.bordersScaledValues.get(i + 1).getY();
            }

            Line border = new Line(x1, y1, x2, y2);
            //border.getStrokeDashArray().addAll(2d, 21d);
            border.setStyle("-fx-stroke: green;");
            border.setStrokeWidth(5);
            this.bordersImages.add(border);

            mapArea.getChildren().add(border);
        }

        return mapArea;
    }

    private void removePreviousMap() {

        for (int i = 0; i < hospitalsCount; i++) {
            mapArea.getChildren().remove(hospitalImages.get(i));
        }
        hospitalImages.clear();

        for (int i = 0; i < placesCount; i++) {
            mapArea.getChildren().remove(placeImages.get(i));
        }
        placeImages.clear();

        for (int i = 0; i < patientsCount; i++) {
            mapArea.getChildren().remove(patientImages.get(i));
        }
        patientImages.clear();
        patientsAdditionalList.clear();

        for (int i = 0; i < streetsCount; i++) {
            mapArea.getChildren().remove(streetImages.get(i));
        }
        streetImages.clear();

        for (int i = 0; i < this.bordersScaledValues.size(); i++) {
            mapArea.getChildren().remove(bordersImages.get(i));
        }
        bordersImages.clear();
    }
    
    public void removePatientFromMap(){
        if(patientsCount > 0){
            mapArea.getChildren().remove(patientImages.get(0));
            patientImages.remove(0);
            loadedPatients.remove(0);
            patientsCount--;
        }
    }

    private void hideHospitals(boolean hide) {
        for (int i = 0; i < hospitalsCount; i++) {
            hospitalImages.get(i).setVisible(hide);
        }
    }

    private void hidePlaces(boolean hide) {
        for (int i = 0; i < placesCount; i++) {
            placeImages.get(i).setVisible(hide);
        }
    }

    private void hidePatients(boolean hide) {
        for (int i = 0; i < patientsCount; i++) {
            patientImages.get(i).setVisible(hide);
        }
    }

    private void hideBorders(boolean hide) {
        for (int i = 0; i < this.bordersScaledValues.size(); i++) {
            bordersImages.get(i).setVisible(hide);
        }
    }

    private List<Point> findAllBorders(List<Hospital> hospitals, List<Place> places) {
        List<Point> allObjectPoints = new ArrayList();
        Point point;
        for (int i = 0; i < hospitals.size(); i++) {
            point = new Point(hospitals.get(i).getHospitalXCord(), hospitals.get(i).getHospitalYCord());
            allObjectPoints.add(point);
        }

        for (int i = 0; i < places.size(); i++) {
            point = new Point(places.get(i).getPlaceXCord(), places.get(i).getPlaceYCord());
            allObjectPoints.add(point);
        }

        return mapChecker.getBorderPoints(allObjectPoints);
    }

    public void setStartButtonDisable(boolean isDisable) {
        startButton.setDisable(isDisable);
    }

    public void setLoadMapButtonDisable(boolean isDisable) {
        loadMapButton.setDisable(isDisable);
    }

    public void setLoadPatientsButtonDisable(boolean isDisable) {
        loadPatientsButton.setDisable(isDisable);
    }

    public void setStopButtonDisable(boolean isDisable) {
        stopButton.setDisable(isDisable);
    }

}
