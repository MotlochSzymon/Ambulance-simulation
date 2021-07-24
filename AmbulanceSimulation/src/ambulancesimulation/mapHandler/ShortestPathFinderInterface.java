package ambulancesimulation.mapHandler;

import ambulancesimulation.graph.Graph;
import ambulancesimulation.graph.Node;

public interface ShortestPathFinderInterface {

    public Graph calculateShortestPathFromSource(Graph graph, Node source);
}
