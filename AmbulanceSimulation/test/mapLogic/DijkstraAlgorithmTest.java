/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapLogic;

import ambulancesimulation.graph.Graph;
import ambulancesimulation.graph.Node;
import ambulancesimulation.mapLogic.DijkstraAlgorithm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dom
 */
public class DijkstraAlgorithmTest {

    public DijkstraAlgorithmTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findShortestPath() throws Exception {
        double excpeted = 10;
        
        Node nodeA = new Node(null);
        Node nodeB = new Node(null);
        Node nodeC = new Node(null);
        Node nodeD = new Node(null);
        Node nodeE = new Node(null);
        Node nodeF = new Node(null);

        nodeA.addTwoDirectionalEdge(nodeB, 10);
        nodeB.addEdge(nodeA, 10);
        nodeA.addEdge(nodeC, 15);

        nodeB.addEdge(nodeD, 12);
        nodeB.addEdge(nodeF, 15);

        nodeC.addEdge(nodeE, 10);

        nodeD.addEdge(nodeE, 2);
        nodeD.addEdge(nodeF, 1);

        nodeF.addEdge(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        DijkstraAlgorithm al = new DijkstraAlgorithm();

        graph = al.calculateShortestPathFromSource(graph, nodeB);
        
        double distance = graph.getNodes().get(0).getDistance();
        
        assertEquals(distance, excpeted, 0.1);
        

        
    }
}
