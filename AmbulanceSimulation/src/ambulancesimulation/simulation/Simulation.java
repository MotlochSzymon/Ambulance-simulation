package ambulancesimulation.simulation;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.fileElements.Patient;
import ambulancesimulation.fileElements.Place;
import ambulancesimulation.fileElements.Street;
import ambulancesimulation.graph.Graph;
import ambulancesimulation.graph.Node;
import ambulancesimulation.gui.GUI;
import ambulancesimulation.gui.MessageWindow;
import ambulancesimulation.mapHandler.BordersMapCheckingIntereface;
import ambulancesimulation.mapHandler.InteresectionLinesInterface;
import ambulancesimulation.mapHandler.ShortestPathFinderInterface;
import ambulancesimulation.mapLogic.ConvexHullAlgorithm;
import ambulancesimulation.mapLogic.DijkstraAlgorithm;
import ambulancesimulation.mapLogic.IntersectionPointFidner;
import ambulancesimulation.mapLogic.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class Simulation extends Thread {

    Graph map;
    List<Point> borders;
    List<PatientOnMap> patientsOnTheMap;
    List<HospitalOnTheMap> hospitals;
    List<Place> places;
    List<Street> streets;
    BordersMapCheckingIntereface mapChecker;
    InteresectionLinesInterface intersectionLineChecker;
    ShortestPathFinderInterface shorstestPathFinder;
    List<Node> allNodes = new ArrayList();
    Set<Integer> visitedHospitalsIdies = new HashSet();

    GUI guiService;
    static int biggestMapObjectId = 0;
    static int nodeId = 0;
    static final long TIME_SIMULATION_CONST = 10;

    public Simulation(List<Hospital> hospitals, List<Place> places, List<Street> streets, List<Patient> patients, List<Point> borders, GUI guiService) {
        mapChecker = new ConvexHullAlgorithm();
        intersectionLineChecker = new IntersectionPointFidner();
        shorstestPathFinder = new DijkstraAlgorithm();
        this.guiService = guiService;
        this.places = places;
        this.streets = streets;

        createMapHospitalList(hospitals);
        this.borders = borders;
        patientsOnTheMap = findPatientstoAddToMap(patients);

        this.map = buildMapGraph(streets);
    }

    @Override
    public void run() {
        try {
            startSimulation();
        } catch (Exception ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startSimulation() throws Exception {
        if (hospitals.size() + places.size() < 3 || hospitals.size() < 1) {
            this.guiService.displayActionDescription("Before starting simulation you have to load map with at least 1 hospital and at least 3 objects on map");
            return;
        }

        for (int i = 0; i < patientsOnTheMap.size(); i++) {

            if (GUI.shouldStopSimulation == true) {
                GUI.shouldStopSimulation = false;
                return;
            }

            Point patientPoint = patientsOnTheMap.get(i).getPointOnMap();
            Platform.runLater( ()-> { this.guiService.removePatientFromMap(); }  );
            Node firstHospital = findNearestHospitalInStragihtLine(patientPoint);
            simulateWayInStraightLine(patientsOnTheMap.get(i), firstHospital);
            boolean isPatientTaken = leavePatientInHospitalIfPossible(firstHospital, patientsOnTheMap.get(i));

            if (isPatientTaken == true) {
                continue;
            }

            visitedHospitalsIdies.add(firstHospital.getHospital().getHospitalId());

            findFreeBedsInOtherHospitals(firstHospital, patientsOnTheMap.get(i));
            this.visitedHospitalsIdies.clear();
        }
        simulationSumUp();
        GUI.isSimulationRunning = false;
        patientsOnTheMap.clear();
        this.guiService.setStartButtonDisable(false);
        this.guiService.setLoadMapButtonDisable(false);
        this.guiService.setLoadPatientsButtonDisable(false);
        this.guiService.setStopButtonDisable(true);
    }

    private void simulationSumUp() {
        this.guiService.displayActionDescription("All of patients have been taken by ambulances");
        List<HospitalStatistics> statistics = this.map.getQueueStatistics();
        String hospitalName;

        if (statistics.size() == 0) {
            this.guiService.displayActionDescription("All of patients found free beds!");
        } else {
            this.guiService.displayActionDescription("List of hospitals where people are waiting for free beds: ");

            for (int i = 0; i < statistics.size(); i++) {
                hospitalName = statistics.get(i).getHospital().getName();
                this.guiService.displayActionDescription("Hospital named " + hospitalName + " has " + statistics.get(i).getAmountOfPeopleInQueue() + " people waitng in queue");
            }
        }

        this.guiService.displayActionDescription("Simulation has finished");
    }

    private Node findNearestHospitalInStragihtLine(Point point) {
        double lowestDistance = countLineLenght(this.hospitals.get(0).getPointOnMap(), point);
        HospitalOnTheMap foundHospital = this.hospitals.get(0);
        double distance;
        for (int i = 1; i < this.hospitals.size(); i++) {
            distance = countLineLenght(this.hospitals.get(i).getPointOnMap(), point);
            if (distance < lowestDistance) {
                lowestDistance = distance;
                foundHospital = this.hospitals.get(i);
            }
        }

        Node foundNode = getNodeByHospitalId(foundHospital.getHospitalId());

        if (foundNode != null) {
            return foundNode;
        } else {
            return new Node(foundHospital);
        }

    }

    private void findFreeBedsInOtherHospitals(Node startNode, PatientOnMap patient) throws InterruptedException {
        this.visitedHospitalsIdies.add(startNode.getHospital().getHospitalId());
        this.map = shorstestPathFinder.calculateShortestPathFromSource(this.map, startNode);
        Node nextHospitalNode = this.map.selectNearestNotVisitedNode(this.visitedHospitalsIdies);

        if (nextHospitalNode != null) {
            simulateWayToNextHospital(nextHospitalNode.getDistance(), patient, nextHospitalNode);
            boolean isPatientTaken = leavePatientInHospitalIfPossible(nextHospitalNode, patient);

            if (isPatientTaken) {
                return;
            } else {
                findFreeBedsInOtherHospitals(nextHospitalNode, patient);
            }

        } else {
            this.guiService.displayActionDescription("There is no free beds in all hospitals! Patient with id: " + patient.getPatientId() + " will be waiting in queue in hospital named " + startNode.getHospital().getName());
            startNode.addPatientToHospitalQueue(patient);
            // move patient on the map
        }
    }

    private synchronized void simulateWayToNextHospital(double distance, PatientOnMap patient, Node targetHospital) throws InterruptedException {
        this.guiService.displayActionDescription("Patient with id: " + patient.getPatientId() + " is going to hospital named: " + targetHospital.getHospital().getName());

        long travelTime = (long) (1 / GUI.simulationSpeed * TIME_SIMULATION_CONST * distance);
        wait(travelTime);
    }

    private synchronized void simulateWayInStraightLine(PatientOnMap patient, Node targetHospital) throws InterruptedException {
        this.guiService.displayActionDescription("Patient with id: " + patient.getPatientId() + " is going to hospital named: " + targetHospital.getHospital().getName());
        final double distance = 500;

        long travelTime = (long) (1 / GUI.simulationSpeed * TIME_SIMULATION_CONST * distance);
        wait(travelTime);
    }

    private boolean leavePatientInHospitalIfPossible(Node hospitalNode, PatientOnMap patient) {
        int freeBedsAmount = hospitalNode.getHospital().getFreeBedsAmount();
        if (freeBedsAmount > 0) {
            hospitalNode.getHospital().setBedsFreeAmount(freeBedsAmount - 1);
            this.guiService.displayActionDescription("Patient with id: " + patient.getPatientId() + " has beed taken by hospital named: " + hospitalNode.getHospital().getName());
            return true;
        } else {
            this.guiService.displayActionDescription("There is no empty beds in hospital named: " + hospitalNode.getHospital().getName() + " for patient with id: " + patient.getPatientId());
            return false;
        }
    }

    private void createMapHospitalList(List<Hospital> hospitals) {
        this.hospitals = new ArrayList();
        for (int i = 0; i < hospitals.size(); i++) {
            this.hospitals.add(new HospitalOnTheMap(hospitals.get(i)));
        }
    }

    private List<Point> findAllBorders() {
        List<Point> allObjectPoints = new ArrayList();
        Point point;
        for (int i = 0; i < hospitals.size(); i++) {
            point = hospitals.get(i).getPointOnMap();
            allObjectPoints.add(point);
        }

        for (int i = 0; i < places.size(); i++) {
            point = new Point(places.get(i).getPlaceXCord(), places.get(i).getPlaceYCord());
            allObjectPoints.add(point);
        }

        return mapChecker.getBorderPoints(allObjectPoints);
    }

    private List<PatientOnMap> findPatientstoAddToMap(List<Patient> patients) {
        patientsOnTheMap = new ArrayList();
        Point patientCord;
        for (int i = 0; i < patients.size(); i++) {
            patientCord = new Point(patients.get(i).getPatientXCord(), patients.get(i).getPatientYCord());
            if (mapChecker.checkIfPointIsInsideBorders(borders, patientCord)) {
                patientsOnTheMap.add(new PatientOnMap(patients.get(i).getPatientId(), patientCord));
            } else {
                guiService.displayActionDescription("Patient with id = " + patients.get(i).getPatientId() + " was not added becuse patient is outside country");
            }

        }

        return patientsOnTheMap;
    }

    private Graph buildMapGraph(List<Street> streets) {
        List<MapIntersection> foundIntersections;

        Point firstPointFirstLine, secondPointFirstLine;
        HospitalOnTheMap firstStartHospital, firstEndHospital;

        for (int i = 0; i < streets.size(); i++) {
            firstStartHospital = getHospitalById(streets.get(i).getStartHospitalId());
            firstEndHospital = getHospitalById(streets.get(i).getEndHospitalId());

            firstPointFirstLine = firstStartHospital.getPointOnMap();
            secondPointFirstLine = firstEndHospital.getPointOnMap();

            foundIntersections = findAllIntersectionWithLine(i + 1, firstPointFirstLine, secondPointFirstLine);

            if (foundIntersections.size() > 0) {
                addHospitalAndCrossRoadNodesAndConnections(firstStartHospital, firstEndHospital, foundIntersections, streets.get(i).getDistance());
            } else {
                addTwoHospitalsConnection(firstStartHospital, firstEndHospital, streets.get(i).getDistance());
            }

            foundIntersections.clear();
        }

        return buildGraph();
    }

    private Graph buildGraph() {
        Graph graph = new Graph();
        for (int i = 0; i < this.allNodes.size(); i++) {
            graph.addNode(this.allNodes.get(i));
        }
        return graph;
    }

    private List<MapIntersection> findAllIntersectionWithLine(int lineIndex, Point firstLinePoint, Point secondLinePoint) {
        HospitalOnTheMap nextStartHospital, nextEndHospital;
        Point firstPointNextLine, secondPointNextLine;
        Point intersectionPoint;

        List<MapIntersection> foundIntersections = new ArrayList();

        for (int j = 0; j < streets.size(); j++) {
            if (j == lineIndex) {
                continue;
            }
            nextStartHospital = getHospitalById(streets.get(j).getStartHospitalId());
            nextEndHospital = getHospitalById(streets.get(j).getEndHospitalId());

            firstPointNextLine = nextStartHospital.getPointOnMap();
            secondPointNextLine = nextEndHospital.getPointOnMap();

            intersectionPoint = intersectionLineChecker.findIntersectionPointOfTwoLines(firstLinePoint, secondLinePoint, firstPointNextLine, secondPointNextLine);

            if (intersectionPoint != null) {
                foundIntersections.add(new MapIntersection(intersectionPoint, nextStartHospital, nextEndHospital));
            }
        }

        return foundIntersections;
    }

    private List<Node> addHospitalAndCrossRoadNodesAndConnections(HospitalOnTheMap firstStartHospital, HospitalOnTheMap firstEndHospital, List<MapIntersection> foundIntersections, double distance) {
        HospitalOnTheMap HospitalWithMinCord = firstStartHospital;
        HospitalOnTheMap HospitalWithMaxCord = firstEndHospital;

        if (foundIntersections.size() > 1) {
            if (foundIntersections.get(0).getPointXCord() != foundIntersections.get(1).getPointXCord()) {
                foundIntersections.sort(Comparator.comparing(MapIntersection::getPointXCord));

                HospitalWithMinCord = getHospitalsAcendingByX(firstStartHospital, firstEndHospital).get(0);
                HospitalWithMaxCord = getHospitalsAcendingByX(firstStartHospital, firstEndHospital).get(1);

            } else {
                foundIntersections.sort(Comparator.comparing(MapIntersection::getPointYCord));
                HospitalWithMinCord = getHospitalsAcendingByY(firstStartHospital, firstEndHospital).get(0);
                HospitalWithMaxCord = getHospitalsAcendingByY(firstStartHospital, firstEndHospital).get(1);
            }
        }

        Point hospitalWithMinCordPoint = new Point(HospitalWithMinCord.getHospitalXCord(), HospitalWithMinCord.getHospitalYCord());
        Point hospitalWithMaxCordPoint = new Point(HospitalWithMaxCord.getHospitalXCord(), HospitalWithMaxCord.getHospitalYCord());

        MapIntersection firstCrossRoad = foundIntersections.get(0);
        MapIntersection lastCrossRoad = foundIntersections.get(foundIntersections.size() - 1);

        double allLinesLenght = countLineLenght(hospitalWithMaxCordPoint, hospitalWithMinCordPoint);

        Node lastAddedNode = addHospitalCrossRoadNodesAndConnection(HospitalWithMinCord, hospitalWithMinCordPoint, firstCrossRoad, allLinesLenght, distance);
        lastAddedNode = addAllCrossRoadsAndConnectionsBeetwen(foundIntersections, allLinesLenght, distance, lastAddedNode);
        addHospitalNodeAndConnectionWithLastNode(HospitalWithMaxCord, hospitalWithMaxCordPoint, lastAddedNode, allLinesLenght, distance);

        return this.allNodes;
    }

    private List<Node> addTwoHospitalsConnection(HospitalOnTheMap startHospital, HospitalOnTheMap endHospital, double distance) {
        Node firstHospitalNode = createHospitalNode(startHospital);
        Node secondHospitalNode = createHospitalNode(endHospital);

        firstHospitalNode.addTwoDirectionalEdge(secondHospitalNode, distance);

        addNodeToAllNodesList(firstHospitalNode);
        addNodeToAllNodesList(secondHospitalNode);

        return this.allNodes;
    }

    private Node addHospitalCrossRoadNodesAndConnection(HospitalOnTheMap hospital, Point hospitalPoint, MapIntersection crossRoad, double allLinesLenght, double distancesSum) {
        Node hospitalNode = createHospitalNode(hospital);
        Node crossRoadNode = createCrossRoadNode(crossRoad.getPoint());

        double lineLenght = countLineLenght(hospitalPoint, crossRoad.getPoint());
        double distance = (lineLenght / allLinesLenght) * distancesSum;

        hospitalNode.addTwoDirectionalEdge(crossRoadNode, distance);

        addNodeToAllNodesList(hospitalNode);
        addNodeToAllNodesList(crossRoadNode);

        return crossRoadNode;
    }

    private Node addHospitalNodeAndConnectionWithLastNode(HospitalOnTheMap hospital, Point hospitalPoint, Node lastAddedNode, double allLinesLenght, double distancesSum) {
        Node hospitalNode = createHospitalNode(hospital);
        Node crossRoadNode = lastAddedNode;

        double lineLenght = countLineLenght(hospitalPoint, crossRoadNode.getPointOnMap());
        double distance = (lineLenght / allLinesLenght) * distancesSum;

        hospitalNode.addTwoDirectionalEdge(crossRoadNode, distance);

        addNodeToAllNodesList(hospitalNode);

        return hospitalNode;
    }

    private void addNodeToAllNodesList(Node node) {
        if (getNodeByPoint(node.getPointOnMap()) == null) {
            this.allNodes.add(node);
        }
    }

    private Node createHospitalNode(HospitalOnTheMap hospital) {
        Node node = getNodeByHospitalId(hospital.getHospitalId());

        if (node != null) {
            return node;
        } else {
            return new Node(hospital);
        }
    }

    private Node createCrossRoadNode(Point point) {
        Node node = getNodeByPoint(point);

        if (node != null) {
            return node;
        } else {
            return new Node(null, point);
        }
    }

    private Node getNodeByHospitalId(int hospitalId) {
        HospitalOnTheMap hospital;
        for (int i = 0; i < this.allNodes.size(); i++) {
            hospital = this.allNodes.get(i).getHospital();
            if (hospital != null) {
                if (hospital.getHospitalId() == hospitalId) {
                    return this.allNodes.get(i);
                }
            }
        }

        return null;
    }

    private Node getNodeByPoint(Point point) {
        for (int i = 0; i < this.allNodes.size(); i++) {
            if (this.allNodes.get(i).getPointOnMap().equals(point)) {
                return this.allNodes.get(i);
            }
        }

        return null;
    }

    private Node getNodeById(int nodeId) {
        for (int i = 0; i < this.allNodes.size(); i++) {
            if (this.allNodes.get(i).getNodeId() == nodeId) {
                return this.allNodes.get(i);
            }
        }

        return null;
    }

    private Node addAllCrossRoadsAndConnectionsBeetwen(List<MapIntersection> foundIntersections, double allLinesLenght, double distancesSum, Node lastAddedNode) {
        for (int i = 0; i < foundIntersections.size(); i++) {
            if (i + 1 >= foundIntersections.size()) {
                return lastAddedNode;
            }

            Node secondNode = createCrossRoadNode(foundIntersections.get(i).getPoint());

            double lineLenght = countLineLenght(foundIntersections.get(i).getPoint(), foundIntersections.get(i + 1).getPoint());
            double distance = (lineLenght / allLinesLenght) * distancesSum;

            lastAddedNode.addTwoDirectionalEdge(secondNode, distance);

            addNodeToAllNodesList(secondNode);
            lastAddedNode = secondNode;
        }

        return lastAddedNode;
    }

    private HospitalOnTheMap getHospitalById(int hospitalId) {
        for (int i = 0; i < hospitals.size(); i++) {
            if (hospitals.get(i).getHospitalId() == hospitalId) {
                return hospitals.get(i);
            }
        }

        return null;
    }

    private double countLineLenght(Point firstPoint, Point secondPoint) {
        return Math.sqrt(Math.pow(firstPoint.getX() - secondPoint.getX(), 2) + Math.pow(firstPoint.getY() - secondPoint.getY(), 2));
    }

    private List<HospitalOnTheMap> getHospitalsAcendingByX(HospitalOnTheMap firstHospital, HospitalOnTheMap secondHospital) {
        List<HospitalOnTheMap> hospitals = new ArrayList();
        if (firstHospital.getHospitalXCord() < secondHospital.getHospitalXCord()) {
            hospitals.add(firstHospital);
            hospitals.add(secondHospital);
        } else {
            hospitals.add(secondHospital);
            hospitals.add(firstHospital);
        }
        return hospitals;
    }

    private List<HospitalOnTheMap> getHospitalsAcendingByY(HospitalOnTheMap firstHospital, HospitalOnTheMap secondHospital) {
        List<HospitalOnTheMap> hospitals = new ArrayList();
        if (firstHospital.getHospitalYCord() < secondHospital.getHospitalYCord()) {
            hospitals.add(firstHospital);
            hospitals.add(secondHospital);
        } else {
            hospitals.add(secondHospital);
            hospitals.add(firstHospital);
        }
        return hospitals;
    }

    public void addAdditionalPatientToSimulation(Patient patient) {
        this.patientsOnTheMap.add(new PatientOnMap(patient.getPatientId(), new Point(patient.getPatientXCord(), patient.getPatientYCord())));
    }

}
