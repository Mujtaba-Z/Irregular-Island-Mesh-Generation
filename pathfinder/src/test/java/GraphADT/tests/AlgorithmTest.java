package GraphADT.tests;

import GraphADT.Algorithm;
import GraphADT.Edge;
import GraphADT.Graph;
import GraphADT.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    Map<Node, Node> testExpected = new HashMap<>();
    Map<Node, Node> testResult;

    Graph G;
    Algorithm a;

    Node source;

    @BeforeEach
    public void setup() {
        Node Toronto = new Node(5, "Toronto", 5.0, 5.0, false, true, 0);
        Node NYC = new Node(4, "NYC", 5.0, 10.0, true, true, 1);
        Node London = new Node(6, "London", 30.0, 5.0, false, true, 2);
        Node Paris = new Node(7, "Paris", 40.0, 15.0, false, true, 3);
        Node Amsterdam = new Node(8, "Amsterdam", 50.0, 10.0, false, true, 4);
        Node Dubai = new Node(9, "Dubai", 75.0, 30.0, false, true, 5);
        Node Filler1 = new Node(10, "Filler1", 75.0, 100.0, false, false, 6);
        Node Filler2 = new Node(11, "Filler2", 105.0, 30.0, false, false, 7);
        Node Filler3 = new Node(12, "Filler3", 705.0, 50.0, false, false, 8);

        testExpected.put(Toronto, NYC);
        testExpected.put(NYC, NYC);
        testExpected.put(London, NYC);
        testExpected.put(Paris, NYC);
        testExpected.put(Amsterdam, London);
        testExpected.put(Dubai, Paris);

        source = NYC;

        G = new Graph();
        G.addNode(Toronto);
        G.addNode(NYC);
        G.addNode(London);
        G.addNode(Paris);
        G.addNode(Amsterdam);
        G.addNode(Dubai);
        G.addNode(Filler1);
        G.addNode(Filler2);
        G.addNode(Filler3);

        int distanceTorNYC = (int) Math.sqrt( Math.pow( Math.abs(Toronto.getXpos() - NYC.getXpos()) , 2) + Math.pow( Math.abs(Toronto.getYpos() - NYC.getYpos()) , 2) );
        G.addEdge(Toronto, NYC, distanceTorNYC, 0);
        int distanceLonNYC = (int) Math.sqrt( Math.pow( Math.abs(London.getXpos() - NYC.getXpos()) , 2) + Math.pow( Math.abs(London.getYpos() - NYC.getYpos()) , 2) );
        G.addEdge(London, NYC, distanceLonNYC, 0);
        int distanceParNYC = (int) Math.sqrt( Math.pow( Math.abs(Paris.getXpos() - NYC.getXpos()) , 2) + Math.pow( Math.abs(Paris.getYpos() - NYC.getYpos()) , 2) );
        G.addEdge(Paris, NYC, distanceParNYC, 0);
        int distanceAmLon = (int) Math.sqrt( Math.pow( Math.abs(Amsterdam.getXpos() - London.getXpos()) , 2) + Math.pow( Math.abs(Amsterdam.getYpos() - London.getYpos()) , 2) );
        G.addEdge(Amsterdam, London, distanceAmLon, 0);
        int distanceAmPar = (int) Math.sqrt( Math.pow( Math.abs(Amsterdam.getXpos() - Paris.getXpos()) , 2) + Math.pow( Math.abs(Amsterdam.getYpos() - Paris.getYpos()) , 2) );
        G.addEdge(Amsterdam, Paris, distanceAmPar, 0);
        int distanceAmDub = (int) Math.sqrt( Math.pow( Math.abs(Amsterdam.getXpos() - Dubai.getXpos()) , 2) + Math.pow( Math.abs(Amsterdam.getYpos() - Dubai.getYpos()) , 2) );
        G.addEdge(Amsterdam, Dubai, distanceAmDub, 0);
        int distanceDubPar = (int) Math.sqrt( Math.pow( Math.abs(Dubai.getXpos() - Paris.getXpos()) , 2) + Math.pow( Math.abs(Dubai.getYpos() - Paris.getYpos()) , 2) );
        G.addEdge(Dubai, Paris, distanceDubPar, 0);

        a = new Algorithm();
        testResult = a.ShortestPath(G, source);
    }

    @Test
    @DisplayName("Tests shortest path from source to all other nodes.")
    public void findShortestPath() {
        assertEquals(testExpected, testResult);
    }

    @Test
    @DisplayName("Tests shortest path from source node that does not exist")
    public void nullPath() {
        testResult = a.ShortestPath(G, null);
        assertNull(testResult);
    }

    @Test
    @DisplayName("Tests shortest path if source node is unconnected")
    public void findShortestPathUnconnected() {
        testResult = a.ShortestPath(G, new Node(10, "Filler1", 75.0, 100.0, false, false, 5));
        assertNotEquals(testExpected, testResult);
    }

    // all paths between two nodes are removed so no path through the node is existent
    @Test
    @DisplayName("Nonexistent path: Nodes in path removed")
    public void removeNodeInPath() {
        Node Paris = new Node(7, "Paris", 40.0, 15.0, false, true, 3);
        G.removeNode(Paris);
        testResult = a.ShortestPath(G, source);
        assertFalse(testResult.containsKey(Paris));
    }

    // data removed should be apparent right after deletion
    @Test
    @DisplayName("Source node removed")
    public void removeSourceNode() {
        G.removeNode(source);
        testResult = a.ShortestPath(G, source);
        Map<Node, Node> SourceRemovedExpected = new HashMap<>();
        SourceRemovedExpected.put(source, source);
        assertEquals(testResult, SourceRemovedExpected);
    }

    // boundary condition testing (graph is null)
    @Test
    @DisplayName("Empty graph")
    public void emptyGraph() {
        G.clearGraph();
        testResult = a.ShortestPath(G, null);
        assertNull(testResult);
    }

    // data inserted should be searchable right after insertion
    @Test
    @DisplayName("Node/edge inserted")
    public void dataInsertion() {
        Node Vancouver = new Node(5, "Vancouver", 5.0, 100.0, false, true, 0);
        G.addNode(Vancouver);
        G.addNode(source);
        G.addEdge(source, Vancouver, 100, 0);
        testResult = a.ShortestPath(G, Vancouver);
        assertTrue(testResult.containsKey(Vancouver));
    }

}