package ambulancesimulation.graph;

import ambulancesimulation.fileElements.Hospital;
import ambulancesimulation.mapLogic.Point;
import ambulancesimulation.simulation.HospitalOnTheMap;
import ambulancesimulation.simulation.PatientOnMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private int nodeId;

    private HospitalOnTheMap hospital;

    private Point pointOnMap;

    private List<Node> shortestPath = new LinkedList<>();

    private Double distance = Double.MAX_VALUE;

    Map<Node, Double> edges = new HashMap<>();

    private static int nodesLastId = 0;

    public void addTwoDirectionalEdge(Node destination, double distance) {
        edges.put(destination, distance);
        destination.edges.put(this, distance);
    }

    public void addEdge(Node destination, double distance) {
        edges.put(destination, distance);
    }

    public Node(HospitalOnTheMap hospital) {
        nodesLastId++;
        this.nodeId = nodesLastId;
        this.hospital = hospital;
        if (hospital != null) {
            this.pointOnMap = new Point(hospital.getHospitalXCord(), hospital.getHospitalYCord());
        }

    }

    public Node(HospitalOnTheMap hospital, Point point) {
        nodesLastId++;
        this.nodeId = nodesLastId;
        this.hospital = hospital;
        this.pointOnMap = point;
    }

    public int getNodeId() {
        return this.nodeId;
    }

    public Point getPointOnMap() {
        return this.pointOnMap;
    }

    public List<Node> getShortestPath() {
        return this.shortestPath;
    }

    public double getDistance() {
        return distance;
    }

    public HospitalOnTheMap getHospital() {
        return this.hospital;
    }

    public Map<Node, Double> getEdges() {
        return this.edges;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
        this.edges = adjacentNodes;
    }

    public void addPatientToHospitalQueue(PatientOnMap patient) {
        this.hospital.addPatientToHospitalQueue(patient);
    }
}
