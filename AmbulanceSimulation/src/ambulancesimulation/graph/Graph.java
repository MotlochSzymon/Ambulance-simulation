package ambulancesimulation.graph;

import ambulancesimulation.simulation.HospitalOnTheMap;
import ambulancesimulation.simulation.HospitalStatistics;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Graph {

    private List<Node> nodes = new ArrayList<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void printDistancesResult() {

        for (int i = 0; i < this.nodes.size(); i++) {
            System.out.println(nodes.get(i).getNodeId() + ": " + nodes.get(i).getDistance());
        }
    }

    public Node selectNearestNotVisitedNode(Set<Integer> visitedHospitalIdies) {
        Node foundNode = null;
        double minDistance = Double.MAX_VALUE;
        double distance;
        Integer hospitalId;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getHospital() == null) {
                continue;
            }
            
            hospitalId = nodes.get(i).getHospital().getHospitalId();
            if (nodes.get(i).getDistance() != 0 && !Double.isInfinite(nodes.get(i).getDistance()) && !visitedHospitalIdies.contains(hospitalId)) {
                distance = nodes.get(i).getDistance();
                if (distance < minDistance) {
                    minDistance = distance;
                    foundNode = nodes.get(i);
                }
            }
        }
        return foundNode;
    }

    public List<HospitalStatistics> getQueueStatistics() {
        List<HospitalStatistics> statistics = new ArrayList();
        int peopleWaiting;

        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i).getHospital() == null) {
                continue;
            }
            peopleWaiting = this.nodes.get(i).getHospital().getPeopleWaitingForFreeBeds().size();

            if (peopleWaiting > 0) {
                statistics.add(new HospitalStatistics(this.nodes.get(i).getHospital(), peopleWaiting));
            }

        }
        return statistics;
    }

}
