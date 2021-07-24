package ambulancesimulation.mapLogic;

import ambulancesimulation.graph.Graph;
import ambulancesimulation.graph.Node;
import ambulancesimulation.mapHandler.ShortestPathFinderInterface;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm implements ShortestPathFinderInterface {

    Graph graph;

    @Override
    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> unsettledNodes = new HashSet<>();
        Set<Node> settledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getShortestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Double> adjacencyPair
                    : currentNode.getEdges().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private Node getShortestDistanceNode(Set<Node> unsettledNodes) {
        Node shortestDistanceNode = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            double distanceToNode = node.getDistance();
            if (distanceToNode < shortestDistance) {
                shortestDistance = distanceToNode;
                shortestDistanceNode = node;
            }
        }
        return shortestDistanceNode;
    }

    private void calculateMinDistance(Node evaluationNode, double edgeWeigh, Node sourceNode) {
        double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
