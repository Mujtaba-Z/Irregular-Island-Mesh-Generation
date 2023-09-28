package settlements;

import GraphADT.Algorithm;
import GraphADT.Graph;
import GraphADT.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RoadBuilder {

    Graph roadNetwork;

    Structs.Mesh.Builder aMesh;

    List<Structs.Segment> roadsegments = new ArrayList<>();

    public RoadBuilder(Graph G, Structs.Mesh.Builder aMesh) {
        this.roadNetwork = G;
        this.aMesh = aMesh;
    }

    private Node findSourceNode() {
        Node sourceNode = null;
        for (Node hubnode : roadNetwork.getNodes()) {
            if (hubnode.isHub()) {
                sourceNode = hubnode;
                break;
            }
        }
        return sourceNode;
    }

    public List<Structs.Segment> BuildRoads() {
        Algorithm algo = new Algorithm();
        Node sourceNode = findSourceNode();
        HashMap<Node, Node> SP = algo.ShortestPath(roadNetwork, sourceNode);
        List<Node> keys = new ArrayList<>();
        List<Node> values = new ArrayList<>();

        for (Node node : SP.keySet()) {
            keys.add(node);
            values.add(SP.get(node));
        }

        for (Node node : SP.keySet()) {
            if (node == null) {
                continue;
            }
            if (node.isRealCity()) {
                Node current = node;
                Node next = null;
                while (current != null && !current.isHub() ) {
                    next = SP.get(current);
                    Structs.Segment.Builder road = Structs.Segment.newBuilder().setV1Idx(current.getVertexID()).setV2Idx(next.getVertexID());
                    Structs.Property p = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
                    road.addProperties(p);
                    p = Structs.Property.newBuilder().setKey("thickness").setValue("2").build();
                    road.addProperties(p);
                    Structs.Segment builtroad = road.build();
                    if (!this.roadsegments.contains(builtroad)) {
                        this.roadsegments.add(builtroad);
                    }
                    current = next;
                }
            }
        }
        return this.roadsegments;
    }
}
