package GraphADT;

import java.util.*;

public class Graph {

    private Map<Node, List<Edge>> adjacencyList = new HashMap<>();
    //Set<ArrayList<Node>> adjacencyList = new HashSet<>();

    boolean contains(Node node) {
        return adjacencyList.containsKey(node);
    }

    public void addNode(Node newnode) {
        if (!adjacencyList.containsKey(newnode)) {
            adjacencyList.put(newnode, null);
        }
    }

    public boolean containsNode(Node contains) {
        return adjacencyList.containsKey(contains);
    }

    public void removeNode(Node killnode) {
        if (!adjacencyList.containsKey(killnode)) {
            System.out.println("Node not in graph");
            return;
        }
        List<Edge> containNodes = adjacencyList.get(killnode);
        adjacencyList.remove(killnode);
        for (Edge containskill : containNodes) {
            removeEdge(containskill.getEdgenode(), killnode.asEdge(containskill.getWeight()), 0);
        }
    }

    public List<Edge> getEdges(Node node) {
        if (!adjacencyList.containsKey(node)) {
            System.out.println("Node not in graph.");
            List<Edge> tempEdges = new ArrayList<>();
            return tempEdges;
        }
        if (adjacencyList.get(node) == null) {
            List<Edge> tempEdges = new ArrayList<>();
            Node tempNode = new Node(-1000, "Impasta");
            Edge tempEdge = new Edge(tempNode, -1000);
            tempEdges.add(tempEdge);
            return tempEdges;
        }

        return adjacencyList.get(node);
    }

    public void addEdge(Node node, Node edgeNode, int weight, int tracker) {
        if (!adjacencyList.containsKey(node)) {
            System.out.println("Home Node does not exist");
            return;
        }
        Edge nedgeNode = new Edge(edgeNode, weight);
        List<Edge> newEdges = adjacencyList.get(node);
        if (newEdges == null) {
            newEdges = new ArrayList<>();
            newEdges.add(nedgeNode);
            adjacencyList.put(node, newEdges);
            if (tracker == 0) {
                tracker++;
                addEdge(edgeNode, node, weight, tracker);
            }
            return;
        }
        if (newEdges.contains(nedgeNode)) {
            if (tracker == 0) {
                tracker++;
                addEdge(edgeNode, node, weight, tracker);
            }
            return;
        }
        newEdges.add(nedgeNode);
        adjacencyList.put(node, newEdges);

        Edge nedgeNode2 = new Edge(node, weight);
        if (!adjacencyList.containsKey(edgeNode)) {
            List<Edge> newEdges2 = new ArrayList<>();
            newEdges2.add(nedgeNode2);
            adjacencyList.put(edgeNode, newEdges2);
            if (tracker == 0) {
                tracker++;
                addEdge(edgeNode, node, weight, tracker);
            }
            return;
        }
        List<Edge> newEdges2 = adjacencyList.get(edgeNode);
        if (newEdges2 == null) {
            newEdges2 = new ArrayList<>();
            newEdges2.add(nedgeNode2);
            adjacencyList.put(edgeNode, newEdges2);
            if (tracker == 0) {
                tracker++;
                addEdge(edgeNode, node, weight, tracker);
            }
            return;
        }
        newEdges2.add(nedgeNode2);
        adjacencyList.put(edgeNode, newEdges2);

        if (tracker == 0) {
            tracker++;
            addEdge(edgeNode, node, weight, tracker);
        }

    }

    public void addEdges(Node node, List<Edge> edgeNodes) {
        List<Edge> newEdges = adjacencyList.get(node);
        newEdges.addAll(edgeNodes);
        adjacencyList.put(node, newEdges);
    }

    public void removeEdge(Node node, Edge edgeNode, int tracker) {
        if (adjacencyList.get(node) == null || !adjacencyList.get(node).contains(edgeNode)) {
            return;
        }
        List<Edge> newEdges = adjacencyList.get(node);
        int i;
        for (i = 0; i < newEdges.size(); i++) {
            if (newEdges.get(i).equals(edgeNode)) {
                break;
            }
        }
        newEdges.remove(i);
        adjacencyList.put(node, newEdges);
        if (tracker == 0) {
            tracker++;
            removeEdge(edgeNode.getEdgenode(), node.asEdge(edgeNode.getWeight()), tracker);
        }

    }

    public void removeEdges(Node node, List<Edge> edgeNodes) {
        List<Edge> newEdges = adjacencyList.get(node);
        newEdges.removeAll(edgeNodes);
        adjacencyList.put(node, newEdges);
    }

    public Map<Node, List<Edge>> getGraph() {
        return adjacencyList;
    }

    public void removeAllEdges(Node node) {
        adjacencyList.remove(node);
        adjacencyList.put(node, null);
    }

    public void clearGraph() {
        adjacencyList.clear();
    }

    public List<Node> getNodes() {
        List<Node> nodesofGraph= new ArrayList<Node>();
        for (Node node : adjacencyList.keySet()) {
            nodesofGraph.add(node);
        }
        return nodesofGraph;
    }

    public Node getNode(Node filler) {
        for (Node node : adjacencyList.keySet()) {
            if (node.equals(filler)) {
                return node;
            }
        }
        return null;
    }
}
